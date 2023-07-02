import numpy as np
import matplotlib.pyplot as plt
from matplotlib.widgets import Button
from keras.preprocessing.image import ImageDataGenerator
import os

MAIN_PATH = 'Final_dataset_small'

val_datagen = ImageDataGenerator(rescale=1./255)

val_generator = val_datagen.flow_from_directory(
    os.path.join(MAIN_PATH, 'Validation'),
    target_size=(224, 224),
    batch_size=32,
    class_mode='categorical',
    shuffle=False
)

# Load features 2d
features_3d = np.load('features_3d_t2.npy')
print(features_3d.shape)

# Plot scatter plot
fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
plt.subplots_adjust(bottom=0.2)

colors = ['b', 'g', 'r', 'c', 'm', 'y', 'k', 'orange', 'lime', 'purple']

y_true = val_generator.classes[val_generator.index_array]
labels = y_true[0]
classes_names = list(val_generator.class_indices.keys())
scatter_plots = []

# Show clases names in legend and create button to show/hide classes
for i in range(len(classes_names)):
    idx = labels == i
    sc = ax.scatter(features_3d[idx, 0], features_3d[idx, 1], features_3d[idx, 2], c=colors[i], label=classes_names[i], alpha=0.5)
    scatter_plots.append(sc)

ax.legend()

def on_button_click(event):
    for idx, button in buttons.items():
        if button.ax == event.inaxes:
            label = idx
            break
    sc = scatter_plots[label]
    sc.set_visible(not sc.get_visible())  # Muestra u oculta la clase
    plt.draw()

# Crea los botones para mostrar y ocultar las clases
buttons = {}
for i in range(10):
    button_ax = plt.axes([0.01 + i * 0.1, 0.05, 0.08, 0.075])
    button = Button(button_ax, classes_names[i], color=colors[i], hovercolor='0.7')
    button.on_clicked(on_button_click)
    buttons[i] = button

plt.show()