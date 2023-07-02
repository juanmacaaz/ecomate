import cv2
import numpy as np
import os
import random
import matplotlib.pyplot as plt

from glob import glob

# Rutas a las carpetas de imágenes y máscaras
image_folder = 'Final_dataset_small2/*/*.jpg'
mask_folder = 'masks/*/*.png'

# Obtenemos todas las imágenes y máscaras
mask_files = glob(mask_folder)

class_index = dict(enumerate(os.listdir('Final_dataset_small2')))
class_index = {v: k+1 for k, v in class_index.items()}

# Funciones de transformación
def translate_image(img, x, y):
    rows, cols = img.shape[:2]
    M = np.float32([[1, 0, x], [0, 1, y]])
    return cv2.warpAffine(img, M, (cols, rows))

def rotate_image(img, angle):
    rows, cols = img.shape[:2]
    M = cv2.getRotationMatrix2D((cols / 2, rows / 2), angle, 1)
    return cv2.warpAffine(img, M, (cols, rows))

def scale_image(img, fx, fy):
    # Redimensionar la imagen
    resized = cv2.resize(img, None, fx=fx, fy=fy, interpolation=cv2.INTER_LINEAR)
    
    # Verificar si la imagen es en color o en escala de grises
    if len(img.shape) == 3:
        color = [0,0,0]  # Color para los bordes (negro)
    else:
        color = [0]  # Color para los bordes (negro)
    
    # Si la imagen es más pequeña, agregar bordes
    if fx < 1.0 or fy < 1.0:
        # Calcular las dimensiones del borde
        top = max(0, int((img.shape[0] - resized.shape[0]) / 2))
        bottom = max(0, int((img.shape[0] - resized.shape[0] + 1) / 2))  # +1 para compensar el redondeo
        left = max(0, int((img.shape[1] - resized.shape[1]) / 2))
        right = max(0, int((img.shape[1] - resized.shape[1] + 1) / 2))  # +1 para compensar el redondeo
        
        # Crear bordes
        resized = cv2.copyMakeBorder(resized, top, bottom, left, right, cv2.BORDER_CONSTANT, value=color)
    
    # Si la imagen es más grande, recortarla
    elif fx > 1.0 or fy > 1.0:
        # Calcular las dimensiones del recorte
        dy = int((resized.shape[0] - img.shape[0]) / 2)
        dx = int((resized.shape[1] - img.shape[1]) / 2)
        
        # Recortar la imagen
        resized = resized[dy:dy+img.shape[0], dx:dx+img.shape[1]]
        
    return resized

def change_brightness(img, value):
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    h, s, v = cv2.split(hsv)
    v = cv2.add(v, value)
    v[v > 255] = 255
    v[v < 0] = 0
    return cv2.cvtColor(cv2.merge((h, s, v)), cv2.COLOR_HSV2BGR)

index = 0

# Loop de transformación y creación de nuevas imágenes
for _ in range(5):
    for mask_file in mask_files:
        try:
            class_name = mask_file.split('\\')[1]
            image_file = mask_file.replace('masks', 'Final_dataset_small2').replace('png', 'jpg')
            
            img = cv2.imread(image_file)
            #img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            
            new_img = np.zeros(img.shape, img.dtype)

            mask_img = cv2.imread(mask_file, cv2.IMREAD_GRAYSCALE)
            mask_img[mask_img > 0] = class_index[class_name]
            new_img[mask_img > 0] = img[mask_img > 0]

            number = random.randint(1, 2)

            mask_files_sample = random.sample(mask_files, number)
        except:
            continue

        for mask_file2 in mask_files_sample:
            try:
                class_name_2 = mask_file2.split('\\')[1]
                img_n = cv2.imread(mask_file2.replace('masks', 'Final_dataset_small2').replace('png', 'jpg'))
                #img_n = cv2.cvtColor(img_n, cv2.COLOR_BGR2RGB)
                mask = cv2.imread(mask_file2, cv2.IMREAD_GRAYSCALE)

                tx = random.randint(-80, 80)
                ty = random.randint(-80, 80)
                r = random.randint(-180, 180)
                fx = random.uniform(0.5, 0.8)
                fy = random.uniform(0.5, 0.8)
                mask = translate_image(mask, tx, ty)
                img_n = translate_image(img_n, tx, ty)
                mask = rotate_image(mask, r)
                img_n = rotate_image(img_n, r)
                mask = scale_image(mask, fx, fy)
                img_n = scale_image(img_n, fx, fy)
                
                #img_n = change_brightness(img_n, random.randint(-50, 50))

                mask_indices = mask > 0
                
                new_img[mask_indices] = img_n[mask_indices]
                mask_img[mask_indices] = class_index[class_name_2]
            except:
                pass

        # Guardar la imagen aumentada
        try:
            out_name = os.path.join('augmented_images', str(index))
            cv2.imwrite(out_name + '.jpg', new_img)
            cv2.imwrite(out_name + '.png', mask_img)
            index += 1
        except:
            pass
    