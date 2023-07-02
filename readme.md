# Mejorando el reciclaje doméstico mediante Visión por Computador 🌍💻

¡Hola! Soy Juan Manuel Camara, estudiante de Ingeniería Informática con mención en Computación, y este es el repositorio de mi Trabajo de Fin de Grado (TFG) en la Universidad, completado en 2023. En este proyecto, exploramos cómo las tecnologías de visión por computadora pueden facilitar y mejorar el reciclaje doméstico, un componente esencial en la protección del medio ambiente y la conservación de los recursos naturales.

📚 En este README encontrarás una descripción detallada de cada carpeta del proyecto, lo que te ayudará a entender mejor la estructura y el funcionamiento de todo el trabajo. 

## 🗃️ Contenido del repositorio 

Cada carpeta del repositorio tiene un propósito específico en el proyecto:

- **📁Dataset - Firebase EcoMate**: Este dataset ha sido recopilado por los usuarios a través de la aplicación EcoMate. 

- **📁Dataset - Imagenes test**: Aquí encontrarás las imágenes que se seleccionaron para realizar las pruebas con Aire, CLIP y los modelos de clasificación seleccionados (XCeption y MobileNetV2).

- **📁Dataset - Redimensionado 224x224**: Este es un dataset creado manualmente a partir de varias fuentes, incluyendo sitios web scrapeados, otros conjuntos de datos y fotografías propias.

- **📁EcoMate - API**: Aquí encontrarás el código y los archivos necesarios para desplegar la API. Necesitarás usar `uvicorn` para iniciarlo e indicar el host con la IP de tu computadora.

- **📁EcoMate - App**: Aquí está la aplicación creada con Android Studio. Los archivos están en el formato que utiliza Android Studio.

- **📁Informe - Figuras**: Imágenes usadas para el informe final del TFG.

- **📁Informe - Version Final**: Aquí está la versión final del informe.

- **📁Informe - Versiones Antiguas**: Aquí puedes encontrar versiones antiguas de los informes.

- **📁Pruebas - Clasificacion**: Código utilizado para realizar las pruebas de los modelos de clasificación.

- **📁Pruebas - CLIP**: Código utilizado para realizar las pruebas de CLIP.

- **📁Pruebas - Otros**: Aquí está el resto del código que no se clasifica en las otras categorías.

- **📁Pruebas - Segment Anything**: Código utilizado para realizar pruebas con la herramienta Segment Anything.

## 🛠️ Creación del entorno con Conda

Este proyecto utiliza Python 3.9.X y Conda para gestionar el entorno. Si aún no lo tienes, puedes descargar Conda [aquí](https://docs.conda.io/projects/conda/en/latest/user-guide/install/index.html). 

Para crear y activar el entorno siguiendo el archivo `requirements.txt`, abre una terminal en la raíz del proyecto y sigue estos pasos:

1. Crea un nuevo entorno Conda con Python 3.9.X:
    ```
    conda create -n ecoMate python=3.9
    ```

2. Activa el entorno recién creado:
    ```
    conda activate ecoMate
    ```

3. Instala las dependencias del proyecto desde el archivo `requirements.txt`:
    ```
    pip install -r requirements.txt
    ```

¡Y eso es todo! Ahora deberías tener todo lo que necesitas para ejecutar y explorar este proyecto. Si

 tienes alguna pregunta o problema, no dudes en ponerte en contacto conmigo.

## 💾 Modelos de clasificación pre-entrenados

- **MobileNetV2**: [Descargar](https://drive.google.com/file/d/1QG4A9DLrQjRvonoX1jg9gN_-wVba2SkB/view?usp=sharing)
- **XCeption**: [Descargar](https://drive.google.com/file/d/1JHpsSZAp25RXXl-l0szzCrgS7Ygkvgpp/view?usp=sharing)
- **Features**: [Descargar](https://drive.google.com/file/d/1CQsPUjVp4wRYnaOHgTGvpSmsp5wRGCwl/view?usp=sharing)

## :arrow_down: Descarga la aplicación

Puedes descargar la ultima version aplicación desde [aquí](https://drive.google.com/file/d/1KxPcby8A10lMBfXiZTabOC3sAkJ64KjU/view?usp=sharing).

O puedes descargar la aplicación desde la [Play Store](https://play.google.com/store/apps/details?id=com.ecomate&hl=es_PA) Nota: La versión de la Play Store puede no estar actualizada.

## 📱 Demo de la aplicación

<a href="https://youtu.be/myf1cMW1o3E"><img src="https://github.com/juanmacaaz/ecomate/blob/main/Informe%20-%20Figuras/new_app1.jpeg" alt="drawing" width="200"/>

---

Este proyecto ha sido tutorizado por Coen Antes. Agradezco su orientación y apoyo a lo largo del trabajo.
