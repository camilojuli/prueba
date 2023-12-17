package com.example.conexion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellido, ciudad;
    Button insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txtnombre);
        apellido = findViewById(R.id.txtapellido);
        ciudad = findViewById(R.id.txtciudad);

        insertar = findViewById(R.id.button);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inser();
            }
        });
    }

    private void inser() {
        String nombre1 = nombre.getText().toString().trim();
        String apellido1 = apellido.getText().toString().trim();
        String ciudad1= ciudad.getText().toString().trim();

        ProgressDialog dialogo = new ProgressDialog(this);

        if(nombre1.isEmpty()){
            nombre.setError("Complete los datos de nombre");
        }else if(apellido1.isEmpty()){
            apellido.setError("Complete los datos de apellido");
        }else if(ciudad1.isEmpty()){
            ciudad.setError("Complete los datos de ciudad");
        }else {
            dialogo.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.105/nuevo/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Datos insertados")) {
                        Toast.makeText(MainActivity.this, "Datos ingresados", Toast.LENGTH_SHORT).show();
                        dialogo.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        dialogo.dismiss();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogo.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nombre", nombre1);
                    params.put("apellido", apellido1);
                    params.put("email", ciudad1);

                    return params;
                }
            };
            RequestQueue rett = Volley.newRequestQueue(MainActivity.this);
            rett.add(request);
        }

    }

}