package com.example.martin.proyectointegradornusoap.BEAN;

import java.io.Serializable;

public class PersonaBean implements Serializable{

    String  CODPERSO;
    String NOMBPERSO;
    String APELLIPERSO;

    public PersonaBean(String CODPERSO, String NOMBPERSO, String APELLIPERSO) {
        this.CODPERSO = CODPERSO;
        this.NOMBPERSO = NOMBPERSO;
        this.APELLIPERSO = APELLIPERSO;
    }

    public PersonaBean(){

    }

    public String getCODPERSO() {
        return CODPERSO;
    }

    public void setCODPERSO(String CODPERSO) {
        this.CODPERSO = CODPERSO;
    }

    public String getNOMBPERSO() {
        return NOMBPERSO;
    }

    public void setNOMBPERSO(String NOMBPERSO) {
        this.NOMBPERSO = NOMBPERSO;
    }

    public String getAPELLIPERSO() {
        return APELLIPERSO;
    }

    public void setAPELLIPERSO(String APELLIPERSO) {
        this.APELLIPERSO = APELLIPERSO;
    }
}
