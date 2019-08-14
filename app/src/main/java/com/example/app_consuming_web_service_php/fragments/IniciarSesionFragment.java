package com.example.app_consuming_web_service_php.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_consuming_web_service_php.MostrarDataActivity;
import com.example.app_consuming_web_service_php.R;
import com.example.app_consuming_web_service_php.RegistroActivity;
import com.example.app_consuming_web_service_php.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IniciarSesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Button iniciar, registrar;
    private EditText usr, clave;

    //objetos para la conexión.
    private RequestQueue rq;
    private JsonRequest jrq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_iniciar_sesion,container,false);
        usr = vista.findViewById(R.id.etUsr);
        clave = vista.findViewById(R.id.etClave);
        iniciar = vista.findViewById(R.id.btnIniciarSesion);
        registrar = vista.findViewById(R.id.btnRegistrar);

        //Requerimiento Volley
        rq = Volley.newRequestQueue(getContext());

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
                Intent intent = new Intent(getContext(), RegistroActivity.class);
                startActivity(intent);
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

        Intent intent = new Intent(getContext(), MostrarDataActivity.class);
        intent.putExtra(MostrarDataActivity.nombre, usua.getNombre());
        intent.putExtra(MostrarDataActivity.correo, usua.getCorreo());
        startActivity(intent);
    }
}
