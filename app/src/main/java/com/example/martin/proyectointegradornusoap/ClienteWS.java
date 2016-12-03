package com.example.martin.proyectointegradornusoap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.proyectointegradornusoap.BEAN.PersonaBean;
import com.example.martin.proyectointegradornusoap.DAO.PersonaDao;
import com.example.martin.proyectointegradornusoap.UTIL.PersonalizacionLSTVW;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ClienteWS extends Activity {

    ListView listView ;
    PersonaBean personaBean;
    PersonaDao personaDao;
    List<PersonaBean> lista = null ;

    //-------EXTRAS-----------------------
    ArrayList codigo = new ArrayList();
    ArrayList nombre = new ArrayList();
    ArrayList apellido = new ArrayList();
    ArrayList combinacion = new ArrayList();

    Button btn, btn1, btn2;
    EditText edt1;

    String tipo = "";

    //-------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ws);

        listView = (ListView) findViewById(R.id.LSTVW);
        btn = (Button)findViewById(R.id.btnBuscar);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);

        CargarLista();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validar = edt1.getText().toString();

                if (validar.isEmpty()) {

                    CargarLista();
                    edt1.setError("Ingrese Nombre xfavor...");

                } else {

                    BuscarProximamente(validar);


                }

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AgregarNuevo();

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarLista();
                Toast.makeText(getApplicationContext(), "Actualizando Registros....", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void BuscarProximamente(String mensaje){

        Toast.makeText(getApplicationContext(), "ESTO SE EXPLICARA EN OTRA APP-> BUSQUEDA X TEXTO :"+mensaje ,Toast.LENGTH_SHORT).show();

    }

    public void AgregarNuevo() {

        tipo = null;

        tipo = "agregar";

        Intent intent = new Intent(ClienteWS.this, ActionActivity.class);

        intent.putExtra("TipoAccion", tipo);

        startActivity(intent);

    }

    public void CargarLista(){

        codigo.clear();
        nombre.clear();
        apellido.clear();
        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorCLIENTE.php?op=1", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode == 200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++){

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODPERSO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBPERSO"));
                            apellido.add(jsonArray.getJSONObject(i).getString("APELLIPERSO"));
                            combinacion.add(codigo+" "+nombre+" "+apellido);
                        }

                        if(combinacion.size()>0){

                            listView.setAdapter(new MiAdapterLSTVW(getApplicationContext(),combinacion.size()));

                        }else {

                            Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_SHORT).show();

                        }
                    }catch(Exception ex){}

                }}

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(),"NO OCURRE NADA",Toast.LENGTH_SHORT).show();

            }

        });
    }

    public void Modificar(String Codigo, String Nombre , String Apellido) {

        tipo = null;

        tipo = "modificar";

        Intent intent = new Intent(ClienteWS.this, ActionActivity.class);
        intent.putExtra("TipoAccion", tipo);
        intent.putExtra("Codigo", Codigo);
        intent.putExtra("Nombre", Nombre);
        intent.putExtra("Apellido", Apellido);

        startActivity(intent);

    }

    public void EliminarElemento(String codigoElem){

        codigo.clear();
        nombre.clear();
        apellido.clear();

        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();

        String url ="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorCLIENTE.php?op=2";

        RequestParams params = new RequestParams();
        params.add("codperso", codigoElem);

        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode==200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++) {

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODPERSO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBPERSO"));
                            apellido.add(jsonArray.getJSONObject(i).getString("APELLIPERSO"));

                            //Este array se creo para concatenar los array's cargados , sirve para el BaseAdapter.
                            combinacion.add(codigo + " " + nombre+" "+apellido);

                        }

                        /*for(int j=0; j<combinacion.size(); j++ ){

                            Toast.makeText(getApplicationContext(), "->"+combinacion.get(j).toString(), Toast.LENGTH_SHORT).show();

                        }*/

                        listView.setAdapter(new MiAdapterLSTVW(getApplicationContext(), combinacion.size()));


                    }catch (Exception ex){

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private class MiAdapterLSTVW extends BaseAdapter{

        int tamano ;
        Context context;
        LayoutInflater layoutInflater;

        TextView lblcodigo , lblnombre , lblapellido, linea;
        Button btnEliminar, btnModificar;

        public MiAdapterLSTVW(Context getApplicationContext , int combinacion) {

            this.context = getApplicationContext;
            layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.tamano = combinacion;

        }

        @Override
        public int getCount() {
            return tamano;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.grilla,null);

            lblcodigo = (TextView)viewGroup.findViewById(R.id.lblcodigo);
            lblnombre = (TextView)viewGroup.findViewById(R.id.lblnombre);
            lblapellido = (TextView)viewGroup.findViewById(R.id.lblapellido);
            linea = (TextView)viewGroup.findViewById(R.id.linea);

            btnEliminar = (Button)viewGroup.findViewById(R.id.btnEliminar);
            btnModificar = (Button)viewGroup.findViewById(R.id.btnModificar);

            lblcodigo.setText(codigo.get(position).toString());
            lblcodigo.setTextColor(getResources().getColor(R.color.colorAccent));

            lblnombre.setText(nombre.get(position).toString());
            lblnombre.setTextColor(getResources().getColor(R.color.colorPrimary));

            lblapellido.setText(apellido.get(position).toString());
            lblapellido.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getApplicationContext(), "ESTO ELIMINARA EL CODIGO :"+codigo.get(position).toString(), Toast.LENGTH_SHORT).show();
                    EliminarElemento(codigo.get(position).toString());
                }
            });

            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getApplicationContext(), "ESTO MODIFICARA EL CODIGO "+codigo.get(position).toString(), Toast.LENGTH_SHORT).show();

                    Modificar(codigo.get(position).toString(),nombre.get(position).toString(),apellido.get(position).toString());

                }
            });

            return viewGroup;
        }
    }

}
