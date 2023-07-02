import os
import shutil
import cv2

MAIN_DATASET = 'Final_dataset'
DESTINATION_DATASET = 'Final_dataset_small'

if os.path.exists(DESTINATION_DATASET):
    shutil.rmtree(DESTINATION_DATASET)

os.mkdir(DESTINATION_DATASET)

'''
    Redimensiona las imagenes a 224x224 de la carpeta MAIN_DATASET/Train/images/[Clase]/[Imagen]
    y las guarda en la carpeta DESTINATION_DATASET/Train/[Clase]/[Imagen]
'''

# Crear las carpetas de Train y Validation
os.mkdir(os.path.join(DESTINATION_DATASET, 'Train'))
os.mkdir(os.path.join(DESTINATION_DATASET, 'Validation'))

# Crear las carpetas de cada clase
for folder in os.listdir(os.path.join(MAIN_DATASET, 'Train', 'images')):
    os.mkdir(os.path.join(DESTINATION_DATASET, 'Train', folder))

for folder in os.listdir(os.path.join(MAIN_DATASET, 'Validation', 'images')):
    os.mkdir(os.path.join(DESTINATION_DATASET, 'Validation', folder))

for folder in os.listdir(os.path.join(MAIN_DATASET, 'Train', 'images')):
    for image in os.listdir(os.path.join(MAIN_DATASET, 'Train', 'images', folder)):
        img = cv2.imread(os.path.join(MAIN_DATASET, 'Train', 'images', folder, image))
        img = cv2.resize(img, (224, 224))
        cv2.imwrite(os.path.join(DESTINATION_DATASET, 'Train', folder, image), img)

'''
    Redimensiona las imagenes a 224x224 de la carpeta MAIN_DATASET/Validation/images/[Clase]/[Imagen]
    y las guarda en la carpeta DESTINATION_DATASET/Validation/[Clase]/[Imagen]
'''

for folder in os.listdir(os.path.join(MAIN_DATASET, 'Validation', 'images')):
    for image in os.listdir(os.path.join(MAIN_DATASET, 'Validation', 'images', folder)):
        img = cv2.imread(os.path.join(MAIN_DATASET, 'Validation', 'images', folder, image))
        img = cv2.resize(img, (224, 224))
        cv2.imwrite(os.path.join(DESTINATION_DATASET, 'Validation', folder, image), img)
