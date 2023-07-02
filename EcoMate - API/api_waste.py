from fastapi import FastAPI, UploadFile, File
from PIL import Image
import numpy as np
import io
import os
import csv
import time
import base64
import open_clip
import torch
from sklearn.neighbors import NearestNeighbors

MAIN_ROUTE = 'SimilarImages'
CLASES = 'objetos.csv'
PROMPT = 'a picture of a {}, a type of domestic waste'
K = 5
CLASES_NAME = os.listdir(MAIN_ROUTE)

model, _, preprocess = open_clip.create_model_and_transforms('ViT-g-14', pretrained='laion2b_s34b_b88k')
model.cuda()

tokenizer = open_clip.get_tokenizer('ViT-g-14')

with open(CLASES, newline='', encoding='utf-8') as csvfile:
    reader = csv.reader(csvfile, delimiter=',', quotechar='|')
    clases = list(reader)

eng_sub_clases = [clase[1] for clase in clases]
esp_sub_clases = [clase[0] for clase in clases]
clases = [clase[2] for clase in clases]

final_promps = [PROMPT.format(promp2).lower() for promp2 in eng_sub_clases]
with torch.no_grad(), torch.cuda.amp.autocast():
    final_promps = tokenizer(final_promps)
    text_features = model.encode_text(final_promps.to('cuda'))
    text_features /= text_features.norm(dim=-1, keepdim=True)

app = FastAPI()

grouped = np.load('features_clip.npy', allow_pickle=True)

@app.post("/process_image")
async def process_image(file: UploadFile = File(...)):
    contents = await file.read()

    image = Image.open(io.BytesIO(contents))
    image = image.resize((224, 224))

    with torch.no_grad(), torch.cuda.amp.autocast():
        image_pre = preprocess(image).unsqueeze(0).to('cuda')

        features_not_norm = model.encode_image(image_pre)
        features = features_not_norm / features_not_norm.norm(dim=-1, keepdim=True)
        text_probs = (100.0 * features @ text_features.T).softmax(dim=-1)
        top_probs, top_labels = text_probs.cpu().topk(1)

        features_val, labels_val, filenames = zip(*grouped)
        neigh = NearestNeighbors(n_neighbors=K, algorithm='brute', metric='cosine')
        neigh.fit(features_val)
        distances, indices = neigh.kneighbors(features_not_norm.cpu().numpy())
        print(distances, indices)
        images_base_64 = []
        clases_similar = []
        for i in range(K):
            path_img = filenames[indices[0][i]]
            print(path_img, distances[0][i])
            with open(path_img, "rb") as image_file:
                encoded_string = base64.b64encode(image_file.read())
                images_base_64.append(encoded_string.decode('utf-8'))
                clases_similar.append(CLASES_NAME[labels_val[indices[0][i]]])

    return {
        'class': clases[top_labels[0][0]],
        'name': esp_sub_clases[top_labels[0][0]],
        'prob': top_probs[0][0].item(),
        'similar': clases_similar,
        'images': images_base_64
    }