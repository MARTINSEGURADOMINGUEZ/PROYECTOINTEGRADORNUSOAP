package com.example.martin.proyectointegradornusoap;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ActionActivity extends Activity {

    TextView lbl1;
    EditText edt1, edt2, edt3;
    Button btn1, btn2;

    String codigo = "";
    String nombre = "";
    String apellido = "";

    String estado = "";
    int valor = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        lbl1 = (TextView) findViewById(R.id.lblEditable);

        edt1 = (EditText) findViewById(R.id.editText);
        edt2 = (EditText) findViewById(R.id.editText2);
        edt3 = (EditText) findViewById(R.id.editText3);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        final Intent intentrecibido = getIntent();

        String mensajeResivido = intentrecibido.getStringExtra("TipoAccion");


        switch (mensajeResivido) {

            case "modificar":

                lbl1.setText("ESTA PANTALLA SERA PARA MODIFICAR");

                edt1.setHint(intentrecibido.getStringExtra("Codigo") );
                edt2.setText(intentrecibido.getStringExtra("Nombre"));
                edt3.setText(intentrecibido.getStringExtra("Apellido"));
                edt2.setFocusable(true);

                codigo = intentrecibido.getStringExtra("Codigo");

                nombre= "";
                apellido = "";

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        codigo = intentrecibido.getStringExtra("Codigo");
                        nombre = edt2.getText().toString();
                        apellido = edt3.getText().toString();

                        if (nombre.isEmpty() || apellido.isEmpty()) {

                            edt2.setError("CAMPO NO PUEDE ESTAR VACIO");
                            edt3.setError("CAMPO NO PUEDE ESTAR VACIO");

                            Toast.makeText(getApplicationContext(), "HAY CAMPOS VACIOS", Toast.LENGTH_SHORT).show();

                        } else {

                            metodoModificar(codigo,nombre,apellido);

                        }

                    }
                });


                break;

            case "agregar":

                lbl1.setText("ESTA PANTALLA ES PARA GUARDAR");
                edt1.setEnabled(true);
                edt1.setFocusable(true);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        codigo = edt1.getText().toString();
                        nombre = edt2.getText().toString();
                        apellido = edt3.getText().toString();

                        valor = Integer.parseInt(codigo);

                        if (codigo.isEmpty() || valor <= 0 || nombre.isEmpty() || apellido.isEmpty()) {

                            edt1.setError("PROBLEMA EN LOS CAMPOS , VERIFICAR ANTES DE PROCEDER...");

                        } else {

                            metodoGuardar(codigo ,nombre,apellido);
                            edt1.setText("");
                            edt2.setText("");
                            edt3.setText("");
                            valor = 0;

                        }

                    }
                });

                break;

            default:

                Toast.makeText(getApplicationContext(), "NO LLEGO PARAMETRO...", Toast.LENGTH_SHORT).show();

        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salir();
            }
        });

    }

    public void Salir() {

        Intent intent = new Intent(ActionActivity.this, ClienteWS.class);
        startActivity(intent);

    }

    public void metodoModificar (String codigo, String nombre, String apellido){

        estado = "";

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorCLIENTE.php?op=3";

        RequestParams params = new RequestParams();
        params.add("codperso", codigo);
        params.add("nombperso", nombre);
        params.add("apelliperso",apellido);

        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {

                        Toast.makeText(getApplicationContext(), " SE ACTUALIZO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();

                    } catch (Exception ex) {

                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(), " OCURRIO ALGO EN EL PROCESO... ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void metodoGuardar(String codigo ,String nombre, String apellido){

        estado = "";

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorCLIENTE.php?op=4";

        RequestParams params = new RequestParams();
        params.add("codperso", codigo);
        params.add("nombperso", nombre);
        params.add("apelliperso",apellido);

        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {

                            JSONObject object = new JSONObject(new String(responseBody));
                            JSONArray jsonArray = object.getJSONArray("PERSONA");

                            JSONObject respuesta = jsonArray.getJSONObject(0);
                            estado = respuesta.getString("ESTADO");


                        if(estado.contains("Existe")){

                            Toast.makeText(getApplicationContext(), "Error: "+estado, Toast.LENGTH_SHORT).show();

                        }else if(estado.contains("success")){

                            Toast.makeText(getApplicationContext(), "SE GRABO SATISFACTORIAMENTE!!!", Toast.LENGTH_SHORT).show();

                        }else{

                            Toast.makeText(getApplicationContext(), "PROBLEMAS AL INGRESAR DATOS A BD...", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception ex) {

                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(), " OCURRIO ALGO EN EL PROCESO... ", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
