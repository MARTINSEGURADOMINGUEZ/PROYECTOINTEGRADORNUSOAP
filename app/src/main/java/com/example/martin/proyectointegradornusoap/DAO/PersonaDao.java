package com.example.martin.proyectointegradornusoap.DAO;

import android.widget.ListView;

import com.example.martin.proyectointegradornusoap.BEAN.PersonaBean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonaDao {

    PersonaBean personaBean;
    List<PersonaBean> lista;

    String namespace="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/";

    String url="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php";

    String soapactionBuscar="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php/BuscarPersonas";
    String soapactionInsertar="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php/InsertarPersonas";
    String soapactionListar="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php/ListarPersonas";
    String soapactionEliminar="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php/EliminarPersonas";
    String soapactionModificar="http://examenfinal2016.esy.es/PROYECTOUNIFICADONUSOAPWS/CONTROLADOR/PersonaControladorWS.php/ModificarPersonas";

    String metodoBuscar="BuscarPersonas";
    String metodoInsertar="InsertarPersonas";
    String metodoListar="ListarPersonas";
    String metodoEliminar="EliminarPersonas";
    String metodoModificar="ModificarPersonas";

    String  ruta="http://10.0.2.2:85/PROYECTOSERVICIOWEBBUSCARWS/CONTROLADOR/PersonaControladorWS.php";


    public List<PersonaBean> obtenerPersonas(){

        lista.clear();
        String TRAMA="";

        try{

            SoapObject  request= new SoapObject(namespace, metodoInsertar);

            SoapSerializationEnvelope  referencia=new SoapSerializationEnvelope(SoapEnvelope.VER10);
            referencia.dotNet=true;

            referencia.setOutputSoapObject(request);
            HttpTransportSE  transporte=new HttpTransportSE(url);
            transporte.call(soapactionListar,referencia);

            SoapPrimitive  response=(SoapPrimitive)referencia.getResponse();
            TRAMA=response.toString();
            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject    jsonObject = new JSONObject(TRAMA);
            JSONArray personas = jsonObject.getJSONArray("PERSONA");
            lista=new ArrayList<PersonaBean>();
            // Recorremos el array con los elementos employees
            for(int i = 0; i < personas.length(); i++)
            {  	JSONObject worker =personas.getJSONObject(i);
                PersonaBean   objPersona=new PersonaBean();
                objPersona.setCODPERSO(worker.getString("CODPERSO"));
                objPersona.setNOMBPERSO(worker.getString("NOMBPERSO"));
                objPersona.setAPELLIPERSO(worker.getString("APELLIPERSO"));
                lista.add(objPersona);
            }

        }catch (Exception ex){

        }

        return lista;

    }

    public  List<PersonaBean>  grabarPersonas(PersonaBean  objpbean)
    {	String  TRAMA;
        try
        {
            String codigo=objpbean.getCODPERSO();
            String   nombre=objpbean.getNOMBPERSO();
            String   apellido=objpbean.getAPELLIPERSO();

            SoapObject  request= new SoapObject(namespace, metodoInsertar);
            Map<String,String> parametros= new HashMap<String, String>();
            parametros.put("CODIGO", codigo);
            parametros.put("NOMBRE", nombre);
            parametros.put("APELLIDO", apellido);
            for(Map.Entry<String,String> datos:parametros.entrySet())
            {
                request.addProperty(datos.getKey(),datos.getValue());
            }
            SoapSerializationEnvelope  referencia=new SoapSerializationEnvelope(SoapEnvelope.VER10);
            referencia.dotNet=true;
            referencia.setOutputSoapObject(request);
            HttpTransportSE  transporte=new HttpTransportSE(url);
            transporte.call(soapactionInsertar,referencia);
            SoapPrimitive  response=(SoapPrimitive)referencia.getResponse();
            TRAMA=response.toString();
            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject    jsonObject = new JSONObject(TRAMA);
            JSONArray personas = jsonObject.getJSONArray("PERSONA");
            lista=new ArrayList<PersonaBean>();
            // Recorremos el array con los elementos employees
            for(int i = 0; i < personas.length(); i++)
            {  	JSONObject worker =personas.getJSONObject(i);
                PersonaBean   objPersona=new PersonaBean();
                objPersona.setCODPERSO(worker.getString("CODPERSO"));
                objPersona.setNOMBPERSO(worker.getString("NOMBPERSO"));
                objPersona.setAPELLIPERSO(worker.getString("APELLIPERSO"));
                lista.add(objPersona);
            }
        } catch (Exception e)
        {
        }
        return  lista;
    }

    public List<PersonaBean>  buscarPersonas(PersonaBean  objpbean)
    {
        String  TRAMA;

        try
        {
            String   apellido=objpbean.getAPELLIPERSO();


            SoapObject request= new SoapObject(namespace, metodoBuscar);

            Map<String,String> parametros= new HashMap<String, String>();
            parametros.put("APELLIDO", apellido);

            for(Map.Entry<String,String> datos:parametros.entrySet())
            {
                request.addProperty(datos.getKey(),datos.getValue());
            }

            SoapSerializationEnvelope referencia=new SoapSerializationEnvelope(SoapEnvelope.VER10);

            referencia.dotNet=true;
            referencia.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(url);

            transporte.call(soapactionBuscar,referencia);
            SoapPrimitive response=(SoapPrimitive)referencia.getResponse();

            TRAMA=response.toString();

            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject jsonObject = new JSONObject(TRAMA);

            JSONArray personas = jsonObject.getJSONArray("PERSONA");

            lista=new ArrayList<PersonaBean>();
            // Recorremos el array con los elementos employees
            for(int i = 0; i < personas.length(); i++)
            {
                JSONObject worker =personas.getJSONObject(i);
                PersonaBean   objPersona=new PersonaBean();
                objPersona.setCODPERSO(worker.getString("CODPERSO"));
                objPersona.setNOMBPERSO(worker.getString("NOMBPERSO"));
                objPersona.setAPELLIPERSO(worker.getString("APELLIPERSO"));
                lista.add(objPersona);
            }

        } catch (Exception e)
        {

        }
        return lista ;
    }

    public List<PersonaBean> eliminarPersonas(PersonaBean personaBean){

        lista.clear();
        String TRAMA ;

        try{

            String codigo = personaBean.getCODPERSO();


            SoapObject request= new SoapObject(namespace, metodoEliminar);

            Map<String,String> parametros= new HashMap<String, String>();
            parametros.put("CODIGO", codigo);

            for(Map.Entry<String,String> datos:parametros.entrySet())
            {
                request.addProperty(datos.getKey(),datos.getValue());
            }

            SoapSerializationEnvelope referencia=new SoapSerializationEnvelope(SoapEnvelope.VER10);

            referencia.dotNet=true;
            referencia.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(url);

            transporte.call(soapactionEliminar,referencia);
            SoapPrimitive response=(SoapPrimitive)referencia.getResponse();

            TRAMA=response.toString();

            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject jsonObject = new JSONObject(TRAMA);

            JSONArray personas = jsonObject.getJSONArray("PERSONA");

            lista=new ArrayList<PersonaBean>();
            // Recorremos el array con los elementos employees
            for(int i = 0; i < personas.length(); i++)
            {
                JSONObject worker =personas.getJSONObject(i);
                PersonaBean   objPersona=new PersonaBean();
                objPersona.setCODPERSO(worker.getString("CODPERSO"));
                objPersona.setNOMBPERSO(worker.getString("NOMBPERSO"));
                objPersona.setAPELLIPERSO(worker.getString("APELLIPERSO"));
                lista.add(objPersona);
            }

        }catch (Exception ex){

        }

        return lista;

    }

    public List<PersonaBean> modificarPersona(PersonaBean personaBean){

        lista.clear();
        String TRAMA ;

        try{

            String codigo= personaBean.getCODPERSO();
            String   nombre = personaBean.getNOMBPERSO();
            String   apellido = personaBean.getAPELLIPERSO();

            SoapObject  request= new SoapObject(namespace, metodoModificar);
            Map<String,String> parametros= new HashMap<String, String>();
            parametros.put("CODIGO", codigo);
            parametros.put("NOMBRE", nombre);
            parametros.put("APELLIDO", apellido);
            for(Map.Entry<String,String> datos:parametros.entrySet())
            {
                request.addProperty(datos.getKey(),datos.getValue());
            }
            SoapSerializationEnvelope  referencia=new SoapSerializationEnvelope(SoapEnvelope.VER10);
            referencia.dotNet=true;
            referencia.setOutputSoapObject(request);
            HttpTransportSE  transporte=new HttpTransportSE(url);
            transporte.call(soapactionModificar,referencia);
            SoapPrimitive  response=(SoapPrimitive)referencia.getResponse();
            TRAMA=response.toString();
            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject    jsonObject = new JSONObject(TRAMA);
            JSONArray personas = jsonObject.getJSONArray("PERSONA");
            lista=new ArrayList<PersonaBean>();
            // Recorremos el array con los elementos employees
            for(int i = 0; i < personas.length(); i++)
            {  	JSONObject worker =personas.getJSONObject(i);
                PersonaBean   objPersona=new PersonaBean();
                objPersona.setCODPERSO(worker.getString("CODPERSO"));
                objPersona.setNOMBPERSO(worker.getString("NOMBPERSO"));
                objPersona.setAPELLIPERSO(worker.getString("APELLIPERSO"));
                lista.add(objPersona);
            }

        }catch (Exception ex){

        }

        return lista;
    }

}
