package com.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class ImageSelector extends AppCompatActivity {

    private ImageView imageView;
    private Spinner spinner;
    private Button button;
    private Bitmap imageBitmap;
    private Uri imageUri;

    private TextView iaClass;
    private TextView iaName;
    private FloatingActionButton similar;

    public static final String IP_ADDRESS = "ipAddress";
    public static final String SHARED_PREFS = "sharedPrefs";

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

        imageView = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.upload_image);
        iaClass = findViewById(R.id.ia_pred_class);
        iaName = findViewById(R.id.ia_pred_name);
        similar = findViewById(R.id.imagenes_similares_btn);

        // Inflate the spinner with AMARILLO,AZUL,VERDE,FARMACIA,PILAS,RESTA,ROPA,RAEE,PUNTO,MARRON
        list.add("Envase ligero");
        list.add("Papel y cartón");
        list.add("Envase de vidrio");
        list.add("Medicamento");
        list.add("Pilas y acumuladores");
        list.add("Resta/No reciclable");
        list.add("Ropa y tegidos textiles");
        list.add("Residuos electrónicos");
        list.add("Desecheria/Punto verde");
        list.add("Productos organicos");

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list));

        // Get image from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Extra image is the photo path string
            imageBitmap = null;
            try {
                imageUri = Uri.fromFile(new File(extras.getString("image")));
                imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        sendPostRequest(imageBitmap);

        button.setOnClickListener(v -> onClickButton());
        setResult(-2);
    }

    public void sendPostRequest(Bitmap bitmap) {
        new Thread(() -> {
            try {
                // Convierte Bitmap a File primero.
                File filesDir = getApplicationContext().getFilesDir();
                File imageFile = new File(filesDir, "image.jpg");

                FileOutputStream fos = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                // Ahora construye la petición.
                OkHttpClient client = new OkHttpClient();

                // Post
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", "image.jpg",
                                RequestBody.create(MediaType.parse("image/*jpg"), imageFile))
                        .build();

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String savedIpAddress = sharedPreferences.getString(IP_ADDRESS, "");
                String url = "http://" + savedIpAddress + ":8000/process_image";

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                // Finalmente, haz la petición.
                Response response = client.newCall(request).execute();
                // Show response in logcat
                // Devuelve un JSON con dos atributos: class y name
                String jsonString = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonString);
                // Tambien hay un atributo "similar" que es un array de strings con las etiquetas de las imagenes similares
                // Y un atributo "images" que es un array de strings con las imagenes similares en base64
                JSONArray jsonArray = jsonObject.getJSONArray("images");
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String base64 = jsonArray.getString(i);
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    bitmaps.add(decodedByte);
                }
                JSONArray jsonArray2 = jsonObject.getJSONArray("similar");
                ArrayList<String> similar = new ArrayList<>();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    int index_class = ObjectType.getIndex(jsonArray2.getString(i));
                    similar.add(list.get(index_class));
                }
                ArrayList<ImageData> imageData = new ArrayList<>();
                for (int i = 0; i < bitmaps.size(); i++) {
                    imageData.add(new ImageData(bitmaps.get(i), similar.get(i)));
                }


                runOnUiThread(() -> {
                    try {
                        // Set text
                        int index_class = ObjectType.getIndex(jsonObject.getString("class"));
                        iaClass.setText(list.get(index_class));
                        iaName.setText(jsonObject.getString("name"));
                        spinner.setSelection(ObjectType.getIndex(jsonObject.getString("class")));
                        // Print json
                        System.out.println(jsonObject.toString());

                        iaClass.setVisibility(View.VISIBLE);
                        iaName.setVisibility(View.VISIBLE);
                        this.similar.setVisibility(View.VISIBLE);

                        this.similar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CarouselFragment carouselFragment = new CarouselFragment(imageData);
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(android.R.id.content, carouselFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error de conexión con el servidor de IA", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                // None
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private Bitmap rescaleBitmap(Bitmap image) {
        // Rescale only if image is bigger than 1920
        int inWidth = image.getWidth();
        int inHeight = image.getHeight();
        if(inWidth > inHeight){
            if (image.getWidth() < 1920) {
                return image;
            }
        } else {
            if (image.getHeight() < 1920) {
                return image;
            }
        }

        final int maxSize = 1920;
        int outWidth;
        int outHeight;
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        // Resize image with smooth filter
        return Bitmap.createScaledBitmap(image, outWidth, outHeight, true);
    }

    private void onClickButton() {
        try {
            uploadImage();
        } catch (Exception e) {
            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void uploadImage(){
        // Upload image to server, add timestamp and object type as ID document, and type as string atribute type use Firestore and Firebase Storage
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = String.format("%04d", (int) (Math.random() * 10000)) + "_" + timeStamp + "_" + ObjectType.getByIndex(spinner.getSelectedItemPosition()) + "_NONE.jpg";

        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference imageRef = imagesRef.child(imageFileName);

        // Upload image to Firebase Storage
        // First rescale the image
        imageBitmap = rescaleBitmap(imageBitmap);
        // Save the image to the local storage
        String new_url = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, imageUri.toString(), "Image of " + ObjectType.getByIndex(spinner.getSelectedItemPosition()));
        imageRef.putFile(Uri.parse(new_url));

        // Add document to Firestore
        long timestamp = System.currentTimeMillis();
        Map<String, Object> fotoData = new HashMap<>();
        fotoData.put("usertype", ObjectType.getByIndex(spinner.getSelectedItemPosition()));
        fotoData.put("iatype", "NONE");
        fotoData.put("timestamp", timestamp);
        fotoData.put("image_url", imageRef.getPath());
        fotoData.put("user_id", mAuth.getCurrentUser().getUid());
        db.collection("fotos").add(fotoData);

        // Delete the image from the local storage
        //getContentResolver().delete(imageUri, null, null);

        // Update the user's
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> userData = task.getResult().getData();
                userData.replace("total", (long) userData.get("total") + 1);
                userData.replace("last_img", imageRef.getPath());
                // Si es medicamento, agregar una s al final
                String nombre = ObjectType.getByIndex(spinner.getSelectedItemPosition()).toString().toUpperCase();
                if (nombre.equals("MEDICAMENTO")) {
                    nombre += "S";
                }
                userData.replace(nombre, (long) userData.get(nombre) + 1);
                //userData.replace(ObjectType.getByIndex(spinner.getSelectedItemPosition()), (long) userData.get(ObjectType.getByIndex(spinner.getSelectedItemPosition())) + 1);

                db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userData);

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ecomate-31315-default-rtdb.europe-west1.firebasedatabase.app");

                database.getReference().get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        Map<String, Object> globalData = (Map<String, Object>) task2.getResult().getValue();
                        globalData.replace("total", (long) globalData.get("total") + 1);
                        String nombre2 = ObjectType.getByIndex(spinner.getSelectedItemPosition()).toString().toUpperCase();
                        if (nombre2.equals("MEDICAMENTO")) {
                            nombre2 += "S";
                        }
                        globalData.replace(nombre2, (long) globalData.get(nombre2) + 1);
                        //globalData.replace(ObjectType.getByIndex(spinner.getSelectedItemPosition()), (long) globalData.get(ObjectType.getByIndex(spinner.getSelectedItemPosition())) + 1);
                        database.getReference().setValue(globalData);

                        // Si el total del usuario es mas grande que el total global, actualizar el total global y el id del usuario
                        if ((long) globalData.get("total_foto") <= (long) userData.get("total")) {
                            database.getReference().child("id").setValue(mAuth.getCurrentUser().getUid());
                            database.getReference().child("total_foto").setValue((long) userData.get("total"));
                        }
                    }
                });
            }
        });

        Toast.makeText(this, "Imagen subida correctamente", Toast.LENGTH_SHORT).show();

        // Update global data
        Intent intent = new Intent();
        intent.putExtra("image", imageUri.toString());
        intent.putExtra("type", spinner.getSelectedItem().toString());
        setResult(spinner.getSelectedItemPosition(), intent);
    }
}