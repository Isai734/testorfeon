package com.thenewtime.testorfeon.model.local;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class Contract {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTORIDAD = "com.thenewtime.testorfeon.Contract";
    /**
     * Representación de las tablas a consultar
     */

    public static final String EVENTO = "Evento";
    public static final String INTEGRANTE = "Integrante";
    public static final String ASISTENCIA = "Asistencia";

    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */

    public final static String SIMPLE_MIME_EVENTO =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + EVENTO;
    public final static String SIMPLE_MIME_INTEGRANTE =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + INTEGRANTE;
    public final static String SIMPLE_MIME_ASISTENCIA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + ASISTENCIA;

    /**
     * Tipo MIME que retorna la consulta de {@link }
     */

    public final static String MULTIPLE_MIME_EVENTO =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + EVENTO;
    public final static String MULTIPLE_MIME_INTEGRANTE =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + INTEGRANTE;
    public final static String MULTIPLE_MIME_ASISTENCIA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + ASISTENCIA;
    /*
     * URI de contenido principal
     */

    public final static Uri CONTENT_URI_EVENTO =
            Uri.parse("content://" + AUTORIDAD + "/" + EVENTO);
    public final static Uri CONTENT_URI_INTEGRANTE =
            Uri.parse("content://" + AUTORIDAD + "/" + INTEGRANTE);
    public final static Uri CONTENT_URI_ASISTENCIA =
            Uri.parse("content://" + AUTORIDAD + "/" + ASISTENCIA);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * C�digo para URIs de multiples registros
     */
    public static final int EVENTO_TBL = 1;
    public static final int INTEGRANTES_TBL = 2;
    public static final int ASISTENCIAS_TBL = 3;
    /**
     * C�digo para URIS de un solo registro
     */
    public static final int EVENTO_ID = 7;
    public static final int INTEGRANTE_ID = 8;
    public static final int ASISTENCIAS_ID = 9;
    public static final CharSequence[] tipos = {"Estudio Lunes", "Estudio Miercoles", "Estudio Sabado",
            "Servicio Domingo", "Servicio Jueves", "Dominical", "Consagracion 04:30", "Horcion 06:00"};

    // AsignaciÓn de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTORIDAD, EVENTO, EVENTO_TBL);
        uriMatcher.addURI(AUTORIDAD, EVENTO + "/#", EVENTO_ID);
        uriMatcher.addURI(AUTORIDAD, INTEGRANTE, INTEGRANTES_TBL);
        uriMatcher.addURI(AUTORIDAD, INTEGRANTE + "/#", INTEGRANTE_ID);
        uriMatcher.addURI(AUTORIDAD, ASISTENCIA, ASISTENCIAS_TBL);
        uriMatcher.addURI(AUTORIDAD, ASISTENCIA + "/#", ASISTENCIAS_ID);
    }

    /**
     * Estructura de la tabla EVENTO
     */
    public static class ColumnasTipo implements BaseColumns {
        private ColumnasTipo() {
        }
        public final static String TIPO = "tipo";
        public final static String DSCR = "descripccion";
        public final static String LUGAR = "lugar";
        public final static String FPROGRDA = "fechaProgramada";
    }

    /**
     * Estructura de la tabla INTEGRANTE
     */
    public static class ColumnsIntegrante implements BaseColumns {
        private ColumnsIntegrante() {
        }
        public final static String NOMBRE = "nombre";
        public final static String A_PATERNO = "apPaterno";
        public final static String A_MATERNO = "apMaterno";
        public final static String EMAIL = "email";
        public final static String TEL = "telefono";
        public final static String FOTO_URL = "fotoUrl";
           }

    /**
     * Estructura de la tabla ASISTENCIA
     */
    public static class ColumnasAsistencia implements BaseColumns {
        private ColumnasAsistencia() {
        }

        public final static String ASISTIO = "asistio";
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String ID_PASE_LISTA = "eventoId";
        public final static String ID_MIEMBRO = "integranteId";
    }
}
