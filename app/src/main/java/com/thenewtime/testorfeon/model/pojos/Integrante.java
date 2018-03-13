package com.thenewtime.testorfeon.model.pojos;

import android.provider.BaseColumns;

/**
 * Created by Isai on 11/03/2018.
 */

public class Integrante {
    private String id;
    private String name;
    private String apPaterno;
    private String apMaterno;
    
    private String email;
    private String telefono;
    private String fotoUrl;
    public Integrante(){}

    public Integrante(String id, String name, String apPaterno, String apMaterno, String email, String telefono, String fotoUrl) {
        this.id = id;
        this.name = name;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.email = email;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
    }

    public static class Columns implements BaseColumns {
        private Columns() {
        }

        public final static String NOMBRE = "nombre";
        public final static String A_PATERNO = "apPaterno";
        public final static String A_MATERNO = "apMaterno";
        public final static String EMAIL = "email";
        public final static String TEL = "telefono";
        public final static String FOTO_URL = "fotoUrl";
    }
}
