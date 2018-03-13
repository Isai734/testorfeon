package com.thenewtime.testorfeon.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thenewtime.testorfeon.MainActivity;
import com.thenewtime.testorfeon.R;
import com.thenewtime.testorfeon.model.pojos.Integrante;
import com.thenewtime.testorfeon.presenter.OnViewOperatorListener;
import com.thenewtime.testorfeon.utils.prefs.SessionPreferences;


public class SignUpActivity extends AppCompatActivity implements OnViewOperatorListener {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private EditText userId;
    private EditText pass;
    private EditText pass2;
    private EditText userName;
    private Integrante usuario;

    private Button sigUpBtn;
    private View mProgressView;
    private LoginImpOperator personaInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        personaInteractor = new LoginImpOperator(this);
        userId = (EditText) findViewById(R.id.user_email);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);
        userName = (EditText) findViewById(R.id.user_name);
        sigUpBtn = (Button) findViewById(R.id.email_sign_in_button);

        mProgressView = findViewById(R.id.login_progress);
        sigUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields())
                    return;

                //SessionPreferences.get(getApplicationContext()).saveLogin(usuario);
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //finish();

            }
        });
    }

    public boolean validateFields() {
        if (userId.getText().toString().isEmpty()) {
            pass.setError("Campo vacio");
            return false;
        }
        if (!pass.getText().toString().equals(pass2.getText().toString())) {
            pass2.setError("Contraseñas no coinciden");
            return false;
        }
        if (pass.getText().toString().isEmpty()) {
            pass.setError("Campo vacio");
            return false;
        }
        if (userName.getText().toString().isEmpty()) {
            userName.setError("Campo vacio");
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /*public void initProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMessage("Agregando persona");
        progressBar.setMax(100);
    }*/

    @Override
    public void onHideProgress() {
        showProgress(false);
    }

    @Override
    public void onShowProgress(String message) {
        showProgress(true);
    }

    @Override
    public void onRequestError(String error) {
        showProgress(false);
        Snackbar.make(mProgressView, error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onRequestOk(String message) {
        SessionPreferences.get(this).saveLogin(usuario);
        Log.i(TAG, "On request Ok");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
