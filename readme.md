Haz un readme para un dosier del trabajo de final de carrera, siguiendo las siguientes indicaciones:

- Me llamo Juan Manuel Camara Diaz
- He cursado Ingeniria Informatica, mencion de computacion
- Año de finalizacion 2023
- Explica lo que hay en cada carpeta.
    - Dataset - Firebase EcoMate: Dataser recogido por los usuarios mediante la aplicacion EcoMate
    - Dataset - Imagenes test: Imagenes que se han escogido para hacer el test con Aire, CLIP y los modelos de clasificacion escogidos (XCeption y MovileNetV2)
    - Dataset - Redimensionado 224x244: Dataset Creado a mano de diferentes sitios, scrapeados, otros dataset y fotos propias
    - EcoMate - API: Codigo y archivos necesarios para desplagar la API. Se necesita usar uvicorn para iniciarla. Y indicar el host con la IP del ordenador actual. Explica paso por paso.
    - EcoMate - App: Aplicacion creada con Android studio. Formato de ficheros el que usa Android Studio.
    - Informe - Figuras: Imagenes usadas para el informe.
    - Informe - Version Final: Version final del informe (paper)
    - Informe - Versiones Antiguas: Versiones antiguas de los informes.
    - Pruebas - Clasificacion: Codigo usado para realizar las pruebas de los modelos de clasificacion.
    - Pruebas - CLIP: Codigo usado para realizar las pruebas de CLIP.
    - Pruebas - Otros: El resto de codigo que no se donde ponerlo.
    - Pruebas - Segment Anything: Codigo usado para realizar las pruebas Segment Anything
- Indica que para crear el entorno utilizar conda. Version de python 3.9.X y explica como crear el entorno con conda y el requirements.txt paso por paso.
- El tutor se llama Coen Antes.
- El titulo del TFG es "Mejorando el reciclaje domestico mediante Vision por Computador"
- El abstract del informe es el siguiente, usalo para hacer una introduccion al readme: Este artículo explora el papel esencial del reciclaje en la protección del medio ambiente y la conservación de los recursos naturales, destacando la capacidad de la tecnología para otorgar herramientas a los ciudadanos y facilitar el reciclaje doméstico. En particular, se examinan los avances en visión por computadora y su aplicación en el desarrollo de herramientas para la clasificación precisa de los residuos. Se discute la invención de instrumentos específicos que simplifican el etiquetado de imágenes de residuos, un componente esencial para el entrenamiento de modelos de aprendizaje automático. Todo esto se lleva a cabo con el diseño y la implementación de una aplicación móvil que tiene como objetivo promover y facilitar el reciclaje en el hogar.\\
- Agrega emogis en el titulo de cada apartado.


# Mejorando el reciclaje doméstico mediante Visión por Computador 🌍💻

¡Hola! Soy Juan Manuel Camara Diaz, estudiante de Ingeniería Informática con mención en Computación, y este es el repositorio de mi Trabajo de Fin de Grado (TFG) en la Universidad, completado en 2023. En este proyecto, exploramos cómo las tecnologías de visión por computadora pueden facilitar y mejorar el reciclaje doméstico, un componente esencial en la protección del medio ambiente y la conservación de los recursos naturales.

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

## Descarga la aplicación

Puedes descargar la ultima version aplicación desde [aquí](https://drive.google.com/file/d/1KxPcby8A10lMBfXiZTabOC3sAkJ64KjU/view?usp=sharing).

O puedes descargar la aplicación desde la [Play Store](https://play.google.com/store/apps/details?id=com.ecomate&hl=es_PA) Nota: La versión de la Play Store puede no estar actualizada.

---

Este proyecto ha sido tutorizado por Coen Antes. Agradezco su orientación y apoyo a lo largo del trabajo.
