package com.thenewtime.testorfeon.model.local;

import android.content.Context;
import android.util.Log;

import com.thenewtime.testorfeon.model.pojos.Asistencia;
import com.thenewtime.testorfeon.model.pojos.Integrante;

import java.util.LinkedList;
import java.util.List;

public class DataSource {
    private static DataSource INSTANCE;
    private static DBHelper INSTANCE_DB = null;
    private static final String TAG = "DataSource";
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "orfeondb";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia del administrador de BD
     */
    private Object object;
    private List<Integrante> UsersList;
    private List<Asistencia> PalabrasList;

    public Object getObject() {
        return object;
    }

    public void storeObject(Object object) {
        this.object = object;
    }

    private DataSource() {
    }

    public static DBHelper getDb(Context context) {
        if (INSTANCE_DB == null) {
            INSTANCE_DB = new DBHelper(
                    context,
                    DATABASE_NAME,
                    null,
                    DATABASE_VERSION
            );
        }
        return INSTANCE_DB;
    }

    public static DataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataSource();
        }
        return INSTANCE;
    }

    public List getListObject(Class clas) {
        Log.i(TAG, "Peticion de lista de : " + clas.getClass().getSimpleName());
        switch (clas.getSimpleName()) {
            case "Login":
                Log.i(TAG, "Se retornan lista de Login");
                return UsersList;
        }
        return new LinkedList();
    }

    public void storeListObject(List list) {
        if (!list.isEmpty()) {
            if (list.get(0) instanceof Integrante)
                this.UsersList = list;

        }
        if (!list.isEmpty()) {
            if (list.get(0) instanceof Asistencia)
                this.PalabrasList = list;
        }
        Log.i(TAG, "Lista vacia");
    }
}