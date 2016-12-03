package com.example.martin.proyectointegradornusoap.UTIL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.martin.proyectointegradornusoap.BEAN.PersonaBean;
import com.example.martin.proyectointegradornusoap.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PersonalizacionLSTVW extends BaseAdapter{

    Context context;
    List<PersonaBean> list;
    LayoutInflater layoutInflater;
    TextView lblnombre , lblcodigo,lblapellido,linea;
    int tamano ;

    public PersonalizacionLSTVW(Context getApplicationContext, int combinacion) {

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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.grilla,null);

        lblcodigo = (TextView)viewGroup.findViewById(R.id.lblcodigo);
        lblnombre = (TextView)viewGroup.findViewById(R.id.lblnombre);
        linea = (TextView)viewGroup.findViewById(R.id.linea);

        lblcodigo.setText(list.get(position).getCODPERSO().toString());
        //lblcodigo.setTextColor(getResources().getColor(R.color.colorPrimary));

        lblnombre.setText(list.get(position).getNOMBPERSO().toString());
        //lblnombre.setTextColor(getResources().getColor(R.color.colorAccent));

        lblapellido.setText(list.get(position).getAPELLIPERSO().toString());


        return viewGroup;

    }
}
