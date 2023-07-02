import os
import random
import shutil

# Ruta a la carpeta que contiene las imágenes
source_folder = "Clasificado_text/"

# Ruta a la carpeta donde se almacenarán las imágenes divididas
destination_folder = "Separated_text/"

# Nombres de las carpetas de división de datos
folders = ['Train', 'Validation']

# Porcentaje de división de datos
train_pct = 0.9
val_pct = 0.1

# Recorrer todas las carpetas y archivos en la carpeta fuente
for root, dirs, files in os.walk(source_folder):
    # Recorrer todos los archivos en la carpeta actual
    for file in files:
        if file.endswith('.txt'): continue
        try:
            # Obtener la ruta completa del archivo
            file_path = os.path.join(root, file)

            # Obtener el nombre de la carpeta de la clase
            class_folder = os.path.basename(os.path.dirname(file_path))

            # Crear una carpeta para cada tipo de conjunto de datos (entrenamiento, validación, prueba)
            for folder in folders:
                new_folder_path = os.path.join(destination_folder, folder, class_folder)
                os.makedirs(new_folder_path, exist_ok=True)

            # Elegir al azar el tipo de conjunto de datos para cada imagen
            rand = random.random()
            if rand < train_pct:
                dataset_type = folders[0]
            else:
                dataset_type = folders[1]

            # Mover el archivo .jpg a la carpeta correspondiente del conjunto de datos
            new_file_path = os.path.join(destination_folder, dataset_type, class_folder, file)
            shutil.move(file_path, new_file_path)

            # Mover el archivo .txt a la carpeta correspondiente del conjunto de datos
            file_path = os.path.join(root, file[:-4]+'.txt')
            new_file_path = os.path.join(destination_folder, dataset_type, class_folder, file[:-4]+'.txt')
            shutil.move(file_path, new_file_path)

            print('Moved: '+file_path)
        except:
            print('Error: '+file_path)