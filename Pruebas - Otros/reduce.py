import os
import random
import shutil

# Establecer la ruta del directorio de entrenamiento
train_dir = './Separated/Train'

# Establecer el número de imágenes que se seleccionarán de cada clase
num_images = 1500

# Obtener la lista de todas las clases en el directorio de entrenamiento
class_names = os.listdir(train_dir)

# Iterar sobre cada clase y copiar num_images imágenes al directorio de destino
for class_name in class_names:
    # Obtener la ruta completa de la carpeta de la clase actual
    class_dir = os.path.join(train_dir, class_name)
    
    # Obtener una lista de todas las imágenes en la carpeta de la clase actual
    images = os.listdir(class_dir)
    
    # Si hay menos de num_images imágenes en la carpeta actual, seleccionarlas todas
    if len(images) <= num_images:
        selected_images = images
    # De lo contrario, seleccionar num_images imágenes aleatorias
    else:
        selected_images = random.sample(images, num_images)
    
    # Crear el directorio de destino si aún no existe
    dest_dir = os.path.join('./subset/train', class_name)
    os.makedirs(dest_dir, exist_ok=True)
    
    # Copiar las imágenes seleccionadas al directorio de destino
    for image_name in selected_images:
        src_path = os.path.join(class_dir, image_name)
        dest_path = os.path.join(dest_dir, image_name)
        shutil.copy(src_path, dest_path)