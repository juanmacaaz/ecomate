import os
import cv2

def rezize_directory(dir, out, size = (224, 224)):
    for file in os.listdir(dir):
        if file.endswith('.jpg'):
            img = cv2.imread(os.path.join(dir, file))
            img = cv2.resize(img, size, interpolation=cv2.INTER_AREA)
            cv2.imwrite(os.path.join(out, file), img)
            print('Archivo {} redimensionado.'.format(file))

if __name__ == '__main__':
    rezize_directory('Imagenes_firebase', 'Imagenes_firebase_224x224')
    print('Redimensionamiento completo.')