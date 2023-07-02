package com.ecomate;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button loginBtn;
    private EditText email, password;
    private TextView register, forgot_password, condiciones_uso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.login_button);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        register = findViewById(R.id.register_btn);
        forgot_password = findViewById(R.id.forgot_password);

        condiciones_uso = findViewById(R.id.condiciones_uso);
        condiciones_uso.setOnClickListener(v -> {
            // Load FullscreenInfoFragment into fullscreen
            FullscreenInfoFragment fullscreenInfoFragment = new FullscreenInfoFragment();

            // Send text to FullscreenInfoFragment get text prom string.xml
            Bundle bundle = new Bundle();
            bundle.putString("text", getString(R.string.terminos_de_uso));
            fullscreenInfoFragment.setArguments(bundle);

            fullscreenInfoFragment.show(getSupportFragmentManager(), "FullscreenInfoFragment");

        });

        loginBtn.setOnClickListener(v -> loginUser());
        register.setOnClickListener(v -> register());
        forgot_password.setOnClickListener(v -> forgot_password());


        // If user is already logged and exist in database, go to MainActivity
        if (mAuth.getCurrentUser() != null) {
            // Test if user has verified email
            if (mAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(Login.this, "Por favor verifique su correo electrónico", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }

    private void forgot_password() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Correo electrónico es requerido");
            email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "Contraseña restablecida, por favor revise su correo electrónico", Toast.LENGTH_LONG).show();
            } else {
                email.setError("Correo electrónico no registrado");
                email.requestFocus();
            }
        });
    }

    private void register() {
        startActivity(new Intent(Login.this, Register.class));
    }

    private void loginUser() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {  // if login is successful go to MainActivity
                if (!mAuth.getCurrentUser().isEmailVerified()) {
                    this.email.setError("Por favor verifique su correo electrónico");
                    // Send verification email
                    mAuth.getCurrentUser().sendEmailVerification();
                    mAuth.signOut();
                }else {
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            } else {  // if login is unsuccessful
                this.email.setError("Correo electrónico o contraseña incorrectos");
                this.email.requestFocus();
            }
        });
    }
}