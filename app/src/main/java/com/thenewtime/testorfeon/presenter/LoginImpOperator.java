package com.thenewtime.testorfeon.presenter;

import android.util.Log;

import com.thenewtime.testorfeon.model.local.DataSource;
import com.thenewtime.testorfeon.model.pojos.Integrante;
import com.thenewtime.testorfeon.model.pojos.ResponseApi;
import com.thenewtime.testorfeon.model.remote.AbstractOperInteractor;
import com.thenewtime.testorfeon.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;



public class LoginImpOperator extends AbstractOperInteractor<Integrante> implements OnRequestListener {

    private static final String TAG = LoginImpOperator.class.getSimpleName();

    OnViewOperatorListener vListener;
    String method = "";

    public LoginImpOperator(OnViewOperatorListener vListener) {
        super.setMiddlewareListener(this);
        this.vListener = vListener;

    }

    public void getAllAutos() {
        vListener.onShowProgress("Obteniendo datos de autos...");
        method = Constants.GETS;
        //Call<List<Usuario>> call = mRetrofitService.allUsers();
        //super.getAll(call);
    }

    public void getUser(String id) {
        vListener.onShowProgress("Obteniendo datos de Usuario...");
        method = Constants.GET;
        //Call<Usuario> call = mRetrofitService.getUser(id);
        //super.get(call);
    }

    public void putUser(Integrante integrante) {
        vListener.onShowProgress("Actualzando datos de Usuario...");
        method = Constants.PUT;
        //Call<ResponseApi> call = mRetrofitService.putUser(usuario);
        //super.put(call);
    }

    public void addUser(Integrante integrante) {
        vListener.onShowProgress("Agregando usuario...");
        method = Constants.POST;
        //Call<ResponseApi> call = mRetrofitService.addUser(usuario);
        //super.post(call);
    }


    @Override
    public void onFailure(ResponseApi response) {
        vListener.onHideProgress();
        vListener.onRequestError(response.getMensaje());
    }

    @Override
    public void onSucces(Response response) {
        /**
         * Se define el tipo de peticion para posteriormente procesar la respuesta
         */
        switch (method) {
            case Constants.GETS:
                List<Integrante> list = (List<Integrante>) response.body();
                DataSource.getInstance().storeListObject(list);
                vListener.onHideProgress();
                vListener.onRequestOk("Recursos obtenidos");
                Log.i(TAG, "Respuesta OnSucces Method : GETS List : " + list.toString());
                break;
            case Constants.GET:
                Integrante auto = (Integrante) response.body();
                DataSource.getInstance().storeObject(auto);
                vListener.onHideProgress();
                vListener.onRequestOk("Conectado...");
                break;
            case Constants.POST:
                ResponseApi responseApi = (ResponseApi) response.body();
                vListener.onHideProgress();
                if (responseApi.getEstado() == 1)
                    vListener.onRequestOk(responseApi.getMensaje());
                else
                    vListener.onRequestError(responseApi.getMensaje());
                break;

            case Constants.PUT:
                responseApi = (ResponseApi) response.body();
                vListener.onHideProgress();
                if (responseApi.getEstado() == 1)
                    vListener.onRequestOk(responseApi.getMensaje());
                else
                    vListener.onRequestError(responseApi.getMensaje());
                break;
        }

    }
}
