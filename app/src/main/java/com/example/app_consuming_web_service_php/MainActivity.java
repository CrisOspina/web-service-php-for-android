package com.example.app_consuming_web_service_php;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_consuming_web_service_php.fragments.IniciarSesionFragment;
import com.example.app_consuming_web_service_php.fragments.RegistrarseFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // El fragment se inflará en el layout con id escenario.
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.escenario,new IniciarSesionFragment()).commit();

    }
}
