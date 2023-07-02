package com.ecomate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    EditText ipAddress;
    Button saveButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IP_ADDRESS = "ipAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ipAddress = findViewById(R.id.ipAddress);
        saveButton = findViewById(R.id.saveButton);

        // Cargamos la dirección IP guardada
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String savedIpAddress = sharedPreferences.getString(IP_ADDRESS, "");
        ipAddress.setText(savedIpAddress);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardamos la dirección IP cuando se hace click en el botón
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(IP_ADDRESS, ipAddress.getText().toString());
                editor.apply();

                // Mostramos un toast y cerramos la actividad
                Toast.makeText(SettingActivity.this, "Dirección IP guardada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

