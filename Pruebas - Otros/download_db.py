import os
import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage

# Configura las credenciales de Firebase
cred = credentials.Certificate('ecomate-31315-firebase-adminsdk-q703l-f0fbfce960.json')
firebase_admin.initialize_app(cred, {
    'storageBucket': 'ecomate-31315.appspot.com'
})

# Obtiene una instancia del módulo de almacenamiento
bucket = storage.bucket()

# Define el directorio de destino para guardar las imágenes descargadas
directorio_destino = 'Imagenes_firebase'

# Descarga todas las imágenes del módulo de almacenamiento que están dentro del directorio 'images'
blobs = bucket.list_blobs(prefix='images/')
for blob in blobs:
    # Obtiene el nombre del archivo
    nombre_archivo = blob.name.split('/')[-1]
    # Define el directorio de destino del archivo
    directorio_archivo = os.path.join(directorio_destino, nombre_archivo)
    # Descarga el archivo
    blob.download_to_filename(directorio_archivo)
    print('Archivo {} descargado.'.format(nombre_archivo))

print('Descarga completa.')
