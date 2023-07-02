package com.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText email, password, password2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        password2 = findViewById(R.id.confirm_password_register);

        // Create user with email and password
        findViewById(R.id.register_button).setOnClickListener(v -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            String password2 = this.password2.getText().toString();

            if (email.isEmpty()) {
                this.email.setError("El correo electrónico es requerido");
                this.email.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                this.password.setError("La contraseña es requerida");
                this.password.requestFocus();
                return;
            }

            if (password.length() < 6) {
                this.password.setError("La contraseña debe tener al menos 6 caracteres");
                this.password.requestFocus();
                return;
            }

            if (!password.equals(password2)) {
                this.password2.setError("Las contraseñas no coinciden");
                this.password2.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (task.isSuccessful()) {
                    // Create user in database with uid as document id
                    Map<String, Object> userData = new HashMap<>();
                    for (ObjectType type : ObjectType.values()) {
                        userData.put(type.toString(), 0);
                    }
                    userData.put("total", 0);
                    userData.put("last_img", "None");
                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userData);
                    // Verify email
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            Toast.makeText(Register.this, "La verificación del correo electrónico ha sido enviada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "No se pudo enviar la verificación del correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } else {  // if registration is unsuccessful
                    this.email.setError("El correo electrónico ya está registrado");
                    this.email.requestFocus();
                }
            });


        });
    }
}