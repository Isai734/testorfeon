package com.thenewtime.testorfeon.model.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context,
                    String name,
                    SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase database) {
        //Crear tabla materia
        String cmd = "CREATE TABLE " + Contract.EVENTO + " (" +
                Contract.ColumnasTipo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.ColumnasTipo.TIPO + " TEXT," +
                Contract.ColumnasTipo.LUGAR + " TEXT," +
                Contract.ColumnasTipo.DSCR + " TEXT," +
                Contract.ColumnasTipo.FPROGRDA + " TEXT)";
        database.execSQL(cmd);

        //Crear tabla INTEGRANTE
        cmd = "CREATE TABLE " + Contract.INTEGRANTE + " (" +
                Contract.ColumnsIntegrante._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.ColumnsIntegrante.NOMBRE + " TEXT," +
                Contract.ColumnsIntegrante.A_PATERNO + " TEXT," +
                Contract.ColumnsIntegrante.A_MATERNO + " TEXT," +
                Contract.ColumnsIntegrante.EMAIL + " TEXT," +
                Contract.ColumnsIntegrante.TEL + " TEXT" +
                Contract.ColumnsIntegrante.FOTO_URL + " TEXT)";
        database.execSQL(cmd);

        //Crear tabla INTEGRANTE
        cmd = "CREATE TABLE " + Contract.ASISTENCIA + " (" +
                Contract.ColumnasAsistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.ColumnasAsistencia.ASISTIO + " TEXT, " +
                Contract.ColumnasAsistencia.FECHA + " TEXT, " +
                Contract.ColumnasAsistencia.HORA + " TEXT, " +
                Contract.ColumnasAsistencia.ID_PASE_LISTA + " INTEGER, " +
                Contract.ColumnasAsistencia.ID_MIEMBRO + " INTEGER)";
        database.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizaciones
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void createTable(SQLiteDatabase database) {
        database.execSQL("INSERT INTO EVENTO VALUES(NULL,'243')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Isai','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Kelly','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Jael','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Azael','Castro','Adame','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Aviamel','Tolentino','MIllan','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Francisco','Rivas','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Sabdi Melec','Castro','Gonzales','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Sarahi','Giles','Vega','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Sulema','Castro','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Asmadeli','Castro','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Clemencia','Tolentino','Cruz','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Jazmin','Marino','Algo','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Elizama','Millan','De Jesus','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Alma Marleni','Morales','Toolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO INTEGRANTE VALUES(null,'Gloria','Castro','Sanchez','Coro 10 de Junio','1')");
    }


    /**
     * Carga datos de ejemplo en la tabla
     * @param database Instancia de la base de datos
     */

}
