package com.thenewtime.testorfeon.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para las actividades
 */
public class Provider extends ContentProvider {
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
    private DBHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DBHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = Contract.uriMatcher.match(uri);
        switch (match) {
            // Consultando todos los registros
            case Contract.INTEGRANTES_TBL:
                return query(Contract.CONTENT_URI_INTEGRANTE, Contract.INTEGRANTE, projection, selection, selectionArgs, sortOrder);
            case Contract.EVENTO_TBL:
                return query(Contract.CONTENT_URI_EVENTO, Contract.EVENTO, projection, selection, selectionArgs, sortOrder);
            case Contract.ASISTENCIAS_TBL:
                return query(Contract.CONTENT_URI_ASISTENCIA, Contract.ASISTENCIA, projection, selection, selectionArgs, sortOrder);
            // Consultando registros por el _id
            case Contract.INTEGRANTE_ID:
                return idQuery(uri, Contract.CONTENT_URI_INTEGRANTE, Contract.INTEGRANTE, projection, selectionArgs, sortOrder);
            case Contract.EVENTO_ID:
                return idQuery(uri, Contract.CONTENT_URI_EVENTO, Contract.EVENTO, projection, selectionArgs, sortOrder);
            case Contract.ASISTENCIAS_ID:
                return idQuery(uri, Contract.CONTENT_URI_ASISTENCIA, Contract.ASISTENCIA, projection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
    }

    public Cursor query(Uri contenUri, String tabla, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        c = db.query(tabla, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    public Cursor idQuery(Uri uri, Uri contenUri, String tabla, String[] projection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        long videoID = ContentUris.parseId(uri);
        c = db.query(tabla, projection,
                Contract.ColumnasAsistencia._ID + " = " + videoID, selectionArgs, null, null, sortOrder
        );
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (Contract.uriMatcher.match(uri)) {
            case Contract.INTEGRANTES_TBL:
                return Contract.MULTIPLE_MIME_INTEGRANTE;
            case Contract.EVENTO_TBL:
                return Contract.MULTIPLE_MIME_EVENTO;
            case Contract.ASISTENCIAS_TBL:
                return Contract.MULTIPLE_MIME_ASISTENCIA;
            //mime por id
            case Contract.INTEGRANTE_ID:
                return Contract.SIMPLE_MIME_INTEGRANTE;
             case Contract.EVENTO_ID:
                return Contract.SIMPLE_MIME_EVENTO;
            case Contract.ASISTENCIAS_ID:
                return Contract.SIMPLE_MIME_ASISTENCIA;
             default:
                throw new IllegalArgumentException("Tipo de tabla desconocida: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        switch (Contract.uriMatcher.match(uri)) {
            case Contract.INTEGRANTES_TBL:
                return insert(uri, Contract.INTEGRANTE, Contract.CONTENT_URI_INTEGRANTE, values);
            case Contract.EVENTO_TBL:
                return insert(uri, Contract.EVENTO, Contract.CONTENT_URI_EVENTO, values);
            case Contract.ASISTENCIAS_TBL:
                return insert(uri, Contract.ASISTENCIA, Contract.CONTENT_URI_ASISTENCIA, values);
            default:
                throw new SQLException("Falla al insertar fila en : " + uri);
        }
    }

    private Uri insert(Uri uri, String tabla, Uri contentUri, ContentValues values) {
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }
        // Si es necesario, verifica los valores
        // Inserci�n de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(tabla, null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = Contract.uriMatcher.match(uri);
        switch (match) {
            case Contract.INTEGRANTES_TBL:
                return delete(Contract.INTEGRANTE, selection, selectionArgs);
            case Contract.EVENTO_TBL:
                return delete(Contract.EVENTO, selection, selectionArgs);
            case Contract.ASISTENCIAS_TBL:
                return delete(Contract.ASISTENCIA, selection, selectionArgs);
            //eliminar por id
            case Contract.INTEGRANTE_ID:
                return idDelete(Contract.INTEGRANTE, uri, selection, selectionArgs);
             case Contract.EVENTO_ID:
                return idDelete(Contract.EVENTO, uri, selection, selectionArgs);
            case Contract.ASISTENCIAS_ID:
                return idDelete(Contract.ASISTENCIA, uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Elemento desconocido: " +
                        uri);
        }
    }

    public int delete(String tabla, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.delete(tabla, selection, selectionArgs);
        return affected;
    }

    public int idDelete(String tabla, Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long videoId = ContentUris.parseId(uri);
        int affected = db.delete(tabla, Contract.ColumnasAsistencia._ID + "=" + videoId
                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        // Notificar cambio asociado a la uri
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (Contract.uriMatcher.match(uri)) {
            case Contract.INTEGRANTES_TBL:
                return update(Contract.INTEGRANTE, uri, values, selection, selectionArgs);
            case Contract.EVENTO_TBL:
                return update(Contract.EVENTO, uri, values, selection, selectionArgs);
            case Contract.ASISTENCIAS_TBL:
                return update(Contract.ASISTENCIA, uri, values, selection, selectionArgs);
            //eliminar por id
            case Contract.INTEGRANTE_ID:
                return idUpdate(Contract.INTEGRANTE, uri, values, selection, selectionArgs);
              case Contract.EVENTO_ID:
                return idUpdate(Contract.EVENTO, uri, values, selection, selectionArgs);
            case Contract.ASISTENCIAS_ID:
                return idUpdate(Contract.ASISTENCIA, uri, values, selection, selectionArgs);
             default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    public int update(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.update(tabla, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    public int idUpdate(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        String videoId = uri.getPathSegments().get(1);
        affected = db.update(tabla, values, Contract.ColumnasAsistencia._ID + "=" + videoId
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : ""),
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}

