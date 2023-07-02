import cv2
import numpy as np
import onnxruntime as ort

model_path = r"model.onnx".replace("\\", "/")

# Ruta de la imagen
image_path = r"Final_dataset_small\Train\Amarillo\0BIPXH89OJOG.jpg".replace("\\", "/")

# Cargar el modelo ONNX
net = cv2.dnn.readNetFromONNX(model_path)
# Print layers
for lyer in net.getLayerNames():
    print(lyer)


# Cargar y preprocesar la imagen
image = cv2.imread(image_path)

# Crear un blob a partir de la imagen
blob = cv2.dnn.blobFromImage(image, 1.0, (224, 224), (0, 0, 0), swapRB=True, crop=True)

# Establecer el blob como entrada de la red
net.setInput(blob)

# Realizar la inferencia
output = net.forward()

# Encontrar la clase con la mayor probabilidad
class_id = np.argmax(output)

print(f"Clase predicha: {class_id}")