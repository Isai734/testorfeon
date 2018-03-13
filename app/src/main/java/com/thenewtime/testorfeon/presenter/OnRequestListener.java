package com.thenewtime.testorfeon.presenter;



import com.thenewtime.testorfeon.model.pojos.ResponseApi;

import retrofit2.Response;

public interface OnRequestListener {

    void onFailure(ResponseApi response);

    void onSucces(Response response);

}
