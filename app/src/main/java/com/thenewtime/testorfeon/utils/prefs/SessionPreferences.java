package com.thenewtime.testorfeon.utils.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.facebook.login.LoginManager;
import com.thenewtime.testorfeon.model.pojos.Integrante;

/**
 * Preferencias de Login== Las preferncias se guardan para que no se consulte el servicio web cada vez que se inicie sesion
 * por seguridad se sugiere que cada vez que se consume un servicio se verifique el usuario que consume el web service este activo
 */
public class SessionPreferences {

    private final SharedPreferences mPrefs;
    private final String DB_CREATED = "dbcreate";
    private boolean mIsLoggedIn = false;
    private boolean mIsLoggedFacebook = false;
    private boolean mIsDbCreated = false;

    private static SessionPreferences INSTANCE;

    public static SessionPreferences get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    private SessionPreferences(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(Integrante.Columns.EMAIL, Context.MODE_PRIVATE);
        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(Integrante.Columns.EMAIL, null));
        mIsLoggedFacebook = "SN".equals(mPrefs.getString(Integrante.Columns.EMAIL, null));
        mIsDbCreated = mPrefs.getBoolean(DB_CREATED, false);
    }

    public String getEmailCliente() {
        return mPrefs.getString(Integrante.Columns.EMAIL, null);
    }

    public String getPassCliente() {
        return mPrefs.getString(Integrante.Columns.NOMBRE, null);
    }

    public void setImgPath(String path) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(Integrante.Columns.FOTO_URL, path);
        editor.apply();
    }

    public Integrante getUserLogged() {
        return new Integrante(

        );
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public boolean isLoggedFacebook() {
        return mIsLoggedFacebook;
    }

    public boolean isDbCreated() {

        return mIsDbCreated;
    }

    public void setDbCreated(boolean bol) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(DB_CREATED, bol);
        editor.apply();
        mIsDbCreated = bol;
    }

    public void saveLogin(Integrante integrante) {
        if (integrante != null) {
            SharedPreferences.Editor editor = mPrefs.edit();

            editor.apply();
            mIsLoggedIn = true;
        }
    }

    public void saveLoginFacebook(Integrante integrante) {
        if (integrante != null) {
            SharedPreferences.Editor editor = mPrefs.edit();

            editor.apply();
            mIsLoggedFacebook = true;
        }
    }

    public void logOut() {
        if (mIsLoggedFacebook)
            LoginManager.getInstance().logOut();
        mIsLoggedIn = false;
        mIsLoggedFacebook = false;
        SharedPreferences.Editor editor = mPrefs.edit();

        editor.apply();
    }
}
