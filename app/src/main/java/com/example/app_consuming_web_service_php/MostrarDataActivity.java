package com.example.app_consuming_web_service_php;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MostrarDataActivity extends AppCompatActivity {

    public static final String nombre = "nombre";
    public static final String correo = "correo";
    TextView rNombreUsuario;
    TextView rCorreoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_data);

        //flecha atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rNombreUsuario = findViewById(R.id.tvNombre);
        rCorreoUsuario = findViewById(R.id.tvCorreo);

        String mNombre = getIntent().getStringExtra("nombre");
        rNombreUsuario.setText(rNombreUsuario.getText().toString() + " Bienvenido " + mNombre);

        String mCorreo = getIntent().getStringExtra("correo");
        rCorreoUsuario.setText(rCorreoUsuario.getText().toString() + " tu correo es " + mCorreo);
    }
}
