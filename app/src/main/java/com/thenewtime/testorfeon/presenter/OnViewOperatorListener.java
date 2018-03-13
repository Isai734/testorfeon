package com.thenewtime.testorfeon.presenter;



public interface OnViewOperatorListener {

    void onHideProgress();

    void onShowProgress(String message);

    void onRequestError(String error);

    void onRequestOk(String message);

}