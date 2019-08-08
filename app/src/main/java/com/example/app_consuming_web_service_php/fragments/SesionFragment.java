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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_consuming_web_service_php.Main2Activity;
import com.example.app_consuming_web_service_php.R;
import com.example.app_consuming_web_service_php.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    //Definir los objetos que se requieran para la conexión.
    private RequestQueue rq;
    private JsonRequest jrq;
    private EditText usr, clave, nombre, correo;
    private Button iniciar, registrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_sesion,container,false);
        usr = vista.findViewById(R.id.etUsr);
        clave = vista.findViewById(R.id.etClave);
        nombre = vista.findViewById(R.id.etNombre);
        correo = vista.findViewById(R.id.etCorreo);
        iniciar = vista.findViewById(R.id.btnIniciarSesion);
        registrar = vista.findViewById(R.id.btnRegistrar);

        rq = Volley.newRequestQueue(getContext()); //Requerimiento Volley

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
                limpiarCampos();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        return vista;
    }

    @Override
    public void onResponse(JSONObject response) {
        String userName = usr.getText().toString();
        Toast.makeText(getContext(), "Se ha encontrado el usuario " + userName, Toast.LENGTH_SHORT).show();
        obtenerDatosDelUsuario(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String userName = usr.getText().toString();
        Toast.makeText(getContext(), "No se ha encontrado el usuario " + userName + " verifica...", Toast.LENGTH_SHORT).show();
    }

    private void iniciarSesion() {
        String userName = usr.getText().toString();
        String password = clave.getText().toString();
        String url = "http://192.168.1.74:8089/web-services-php-for-android/sesion.php?usr="+userName+"&clave="+password;
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    private void  limpiarCampos(){
        usr.setText("");
        clave.setText("");
        nombre.setText("");
        correo.setText("");
    }

    private  void obtenerDatosDelUsuario(JSONObject response){
        //Se utiliza la clase usuario para tomar los campos del arreglo datos del archivo php
        Usuario usua = new Usuario();

        //datos: arreglo que envia los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("datos");

        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0); //posición 0 del arreglo
            usua.setUsr(jsonObject.optString("usr"));
            usua.setClave(jsonObject.optString("clave"));
            usua.setNombre(jsonObject.optString("nombre"));
            usua.setCorreo(jsonObject.optString("correo"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(getContext(), Main2Activity.class);
        intent.putExtra(Main2Activity.nombre, usua.getNombre());
        intent.putExtra(Main2Activity.correo, usua.getCorreo());
        startActivity(intent);
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
}
