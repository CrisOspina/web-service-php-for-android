package com.example.app_consuming_web_service_php;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.app_consuming_web_service_php.fragments.IniciarSesionFragment;
import com.example.app_consuming_web_service_php.fragments.RegistrarseFragment;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //flecha atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // El fragment se inflará en el layout con id escenario.
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.escenario,new RegistrarseFragment()).commit();
    }
}
