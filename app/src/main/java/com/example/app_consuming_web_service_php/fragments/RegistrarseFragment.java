package com.example.app_consuming_web_service_php.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_consuming_web_service_php.MainActivity;
import com.example.app_consuming_web_service_php.MostrarDataActivity;
import com.example.app_consuming_web_service_php.R;
import com.example.app_consuming_web_service_php.RegistroActivity;
import com.example.app_consuming_web_service_php.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseFragment extends Fragment {

    private EditText usr, clave, nombre, correo;
    private Button registrar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_registrarse,container,false);
        usr = vista.findViewById(R.id.etUsr);
        clave = vista.findViewById(R.id.etClave);
        nombre = vista.findViewById(R.id.etNombre);
        correo = vista.findViewById(R.id.etCorreo);
        registrar = vista.findViewById(R.id.btnRegistrarse);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarIngreso();
            }
        });
        return vista;
    }

    private void validarIngreso(){
        final String userName = usr.getText().toString();
        final String password = clave.getText().toString();
        final String name     = nombre.getText().toString();
        final String email    = correo.getText().toString();

        if(userName.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()){
            Toast.makeText(getContext(), "Campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            registrarUsuario();
            limpiarCampos();
            navegarIniciarSesion();
        }
    }

    private  void registrarUsuario(){
        final String userName = usr.getText().toString();
        final String password = clave.getText().toString();
        final String name     = nombre.getText().toString();
        final String email    = correo.getText().toString();

        String url = "http://192.168.1.74:8089/web-services-php-for-android/registro.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fallo el registro, por favor verifica nuevamente", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usr", userName);
                parametros.put("clave", password);
                parametros.put("correo", email);
                parametros.put("nombre", name);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void  limpiarCampos(){
        usr.setText("");
        clave.setText("");
        nombre.setText("");
        correo.setText("");
    }

    private void navegarIniciarSesion(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
