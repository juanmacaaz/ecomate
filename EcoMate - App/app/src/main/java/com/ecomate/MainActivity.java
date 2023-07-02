package com.ecomate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FloatingActionButton addImageBtn, infoBtn;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private ImageView imageView;
    private TextView azul, amarillo, verde, medicamentos, pilas, gris, punto, raee, ropa, marron, total, primer_puesto_txt, ultima_type;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance("https://ecomate-31315-default-rtdb.europe-west1.firebasedatabase.app");

        addImageBtn = findViewById(R.id.add);
        addImageBtn.setOnClickListener(v -> dispatchTakePictureIntent());
        infoBtn = findViewById(R.id.info_main);
        infoBtn.setOnClickListener(v -> {
            // Load FullscreenInfoFragment into fullscreen
            FullscreenInfoFragment fullscreenInfoFragment = new FullscreenInfoFragment();

            // Send text to FullscreenInfoFragment get text prom string.xml
            Bundle bundle = new Bundle();
            bundle.putString("text", getString(R.string.como_usar));
            fullscreenInfoFragment.setArguments(bundle);

            fullscreenInfoFragment.show(getSupportFragmentManager(), "FullscreenInfoFragment");


        });

        azul = findViewById(R.id.azul_data);
        amarillo = findViewById(R.id.amarillo_data);
        verde = findViewById(R.id.verde_data);
        medicamentos = findViewById(R.id.medicamentos_data);
        pilas = findViewById(R.id.pilas_data);
        gris = findViewById(R.id.gris_data);
        punto = findViewById(R.id.punto_data);
        raee = findViewById(R.id.RAEE_data);
        ropa = findViewById(R.id.ropa_data);
        marron = findViewById(R.id.marron_data);
        total = findViewById(R.id.total_data);
        ultima_type = findViewById(R.id.ultima_type);

        storage = FirebaseStorage.getInstance();

        imageView = findViewById(R.id.ultima_imagen_data);

        primer_puesto_txt = findViewById(R.id.primer_puesto_msg);

        // Get user info
        update_user_info();

        database.getReference().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                azul.setText(update_string_total(azul.getText().toString(), snapshot.child("AZUL").getValue().toString()));
                amarillo.setText(update_string_total(amarillo.getText().toString(), snapshot.child("AMARILLO").getValue().toString()));
                verde.setText(update_string_total(verde.getText().toString(), snapshot.child("VERDE").getValue().toString()));
                medicamentos.setText(update_string_total(medicamentos.getText().toString(), snapshot.child("MEDICAMENTOS").getValue().toString()));
                pilas.setText(update_string_total(pilas.getText().toString(), snapshot.child("PILAS").getValue().toString()));
                gris.setText(update_string_total(gris.getText().toString(), snapshot.child("RESTA").getValue().toString()));
                punto.setText(update_string_total(punto.getText().toString(), snapshot.child("PUNTO").getValue().toString()));
                raee.setText(update_string_total(raee.getText().toString(), snapshot.child("RAEE").getValue().toString()));
                ropa.setText(update_string_total(ropa.getText().toString(), snapshot.child("ROPA").getValue().toString()));
                marron.setText(update_string_total(marron.getText().toString(), snapshot.child("MARRON").getValue().toString()));
                total.setText(update_string_total(total.getText().toString(), snapshot.child("total").getValue().toString()));
                if (snapshot.child("id").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    primer_puesto_txt.setText("Enorabuena, eres el primer puesto con: " + snapshot.child("total_foto").getValue().toString() + " fotos");
                }else {
                    primer_puesto_txt.setText("El primer puesto ha contribuido con: " + snapshot.child("total_foto").getValue().toString() + " fotos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Update at the start
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                azul.setText(update_string_total(azul.getText().toString(), snapshot.child("AZUL").getValue().toString()));
                amarillo.setText(update_string_total(amarillo.getText().toString(), snapshot.child("AMARILLO").getValue().toString()));
                verde.setText(update_string_total(verde.getText().toString(), snapshot.child("VERDE").getValue().toString()));
                medicamentos.setText(update_string_total(medicamentos.getText().toString(), snapshot.child("MEDICAMENTOS").getValue().toString()));
                pilas.setText(update_string_total(pilas.getText().toString(), snapshot.child("PILAS").getValue().toString()));
                gris.setText(update_string_total(gris.getText().toString(), snapshot.child("RESTA").getValue().toString()));
                punto.setText(update_string_total(punto.getText().toString(), snapshot.child("PUNTO").getValue().toString()));
                raee.setText(update_string_total(raee.getText().toString(), snapshot.child("RAEE").getValue().toString()));
                ropa.setText(update_string_total(ropa.getText().toString(), snapshot.child("ROPA").getValue().toString()));
                marron.setText(update_string_total(marron.getText().toString(), snapshot.child("MARRON").getValue().toString()));
                total.setText(update_string_total(total.getText().toString(), snapshot.child("total").getValue().toString()));
                // Si el primer puesto es el usuario actual entonces mostrar el mensaje de que es el primer puesto
                if (snapshot.child("id").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    primer_puesto_txt.setText("Enorabuena, eres el primer puesto con: " + snapshot.child("total_foto").getValue().toString() + " fotos");
                }else {
                    primer_puesto_txt.setText("El primer puesto ha contribuido con: " + snapshot.child("total_foto").getValue().toString() + " fotos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String update_string_total(String input, String new_value) {
        // Split the string with spaces and replace the last value
        String[] split = input.split(" ");
        split[split.length - 1] = new_value;
        // Join the string again
        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            builder.append(s).append(" ");
        }
        return builder.toString();
    }

    private String first_update_string_total(String input, String new_value) {
        // Split the string with spaces and replace the first value
        String[] split = input.split(" ");
        split[0] = new_value;
        // Join the string again
        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            builder.append(s).append(" ");
        }
        return builder.toString();
    }

    private void update_user_info() {
        ultima_type.setText("");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    azul.setText(first_update_string_total(azul.getText().toString(), task.getResult().get("AZUL", Long.class).toString()));
                    amarillo.setText(first_update_string_total(amarillo.getText().toString(), task.getResult().get("AMARILLO", Long.class).toString()));
                    verde.setText(first_update_string_total(verde.getText().toString(), task.getResult().get("VERDE", Long.class).toString()));
                    medicamentos.setText(first_update_string_total(medicamentos.getText().toString(), task.getResult().get("MEDICAMENTOS", Long.class).toString()));
                    pilas.setText(first_update_string_total(pilas.getText().toString(), task.getResult().get("PILAS", Long.class).toString()));
                    gris.setText(first_update_string_total(gris.getText().toString(), task.getResult().get("RESTA", Long.class).toString()));
                    punto.setText(first_update_string_total(punto.getText().toString(), task.getResult().get("PUNTO", Long.class).toString()));
                    raee.setText(first_update_string_total(raee.getText().toString(), task.getResult().get("RAEE", Long.class).toString()));
                    ropa.setText(first_update_string_total(ropa.getText().toString(), task.getResult().get("ROPA", Long.class).toString()));
                    marron.setText(first_update_string_total(marron.getText().toString(), task.getResult().get("MARRON", Long.class).toString()));
                    total.setText(first_update_string_total(total.getText().toString(), task.getResult().get("total", Long.class).toString()));
                    // Set the last image, if it exists
                    String last_img = task.getResult().get("last_img", String.class);
                    if (!last_img.equals("None")) {
                        StorageReference storageRef = storage.getReference();
                        StorageReference imageRef = storageRef.child(task.getResult().get("last_img", String.class));
                        String url = imageRef.toString();
                        final long ONE_MEGABYTE = 1024 * 1024;
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                            Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap1);
                        }).addOnFailureListener(exception -> {
                            // Handle any errors
                        });
                        String[] split = url.split("_");
                        String ultima_type_string = split[split.length - 2];
                        ultima_type.setText(ultima_type_string);
                        int type = ObjectType.valueOf(ultima_type_string).ordinal();
                        List<String> list = new ArrayList<>();
                        list.add("Envase ligero");
                        list.add("Papel y cartón");
                        list.add("Envase de vidrio");
                        list.add("Medicamentos");
                        list.add("Pilas y acumuladores");
                        list.add("Resta/No reciclable");
                        list.add("Ropa y tegidos textiles");
                        list.add("Residuos electrónicos");
                        list.add("Desecheria/Punto verde");
                        list.add("Productos orgranicos");
                        ultima_type.setText(list.get(type));
                    }else {
                        ultima_type.setText("");
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create a button to close session
        MenuItem item2 = menu.add("Ajustes");
        item2.setOnMenuItemClickListener(item1 -> {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            return true;
        });
        MenuItem item = menu.add("Cerrar sesión");
        item.setOnMenuItemClickListener(item1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
            // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(this, "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    BuildConfig.APPLICATION_ID + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }else {
            Toast.makeText(this, "File is null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ImageSelector.class);
            intent.putExtra("image", currentPhotoPath);
            startActivityForResult(intent, 2);
        }
        if (requestCode == 2 && resultCode >= 0 && resultCode != -2) {
            int tmp;
            switch (resultCode) {
                case 1:
                    tmp = Integer.parseInt(azul.getText().toString().split(" ")[0]) + 1;
                    azul.setText(first_update_string_total(azul.getText().toString(), String.valueOf(tmp)));
                    break;
                case 0:
                    tmp = Integer.parseInt(amarillo.getText().toString().split(" ")[0]) + 1;
                    amarillo.setText(first_update_string_total(amarillo.getText().toString(), String.valueOf(tmp)));
                    break;
                case 2:
                    tmp = Integer.parseInt(verde.getText().toString().split(" ")[0]) + 1;
                    verde.setText(first_update_string_total(verde.getText().toString(), String.valueOf(tmp)));
                    break;
                case 3:
                    tmp = Integer.parseInt(medicamentos.getText().toString().split(" ")[0]) + 1;
                    medicamentos.setText(first_update_string_total(medicamentos.getText().toString(), String.valueOf(tmp)));
                    break;
                case 4:
                    tmp = Integer.parseInt(pilas.getText().toString().split(" ")[0]) + 1;
                    pilas.setText(first_update_string_total(pilas.getText().toString(), String.valueOf(tmp)));
                    break;
                case 5:
                    tmp = Integer.parseInt(gris.getText().toString().split(" ")[0]) + 1;
                    gris.setText(first_update_string_total(gris.getText().toString(), String.valueOf(tmp)));
                    break;
                case 8:
                    tmp = Integer.parseInt(punto.getText().toString().split(" ")[0]) + 1;
                    punto.setText(first_update_string_total(punto.getText().toString(), String.valueOf(tmp)));
                    break;
                case 7:
                    tmp = Integer.parseInt(raee.getText().toString().split(" ")[0]) + 1;
                    raee.setText(first_update_string_total(raee.getText().toString(), String.valueOf(tmp)));
                    break;
                case 6:
                    tmp = Integer.parseInt(ropa.getText().toString().split(" ")[0]) + 1;
                    ropa.setText(first_update_string_total(ropa.getText().toString(), String.valueOf(tmp)));
                    break;
                case 9:
                    tmp = Integer.parseInt(marron.getText().toString().split(" ")[0]) + 1;
                    marron.setText(first_update_string_total(marron.getText().toString(), String.valueOf(tmp)));
                    break;
            }
            tmp = Integer.parseInt(total.getText().toString().split(" ")[0]) + 1;
            total.setText(first_update_string_total(total.getText().toString(), String.valueOf(tmp)));

            // Get extras from intent
            String image = data.getStringExtra("image");
            String type = data.getStringExtra("type");

            // Put image to imageView
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageView.setImageBitmap(bitmap);

            // Set text to textView
            ultima_type.setText(type);
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}