package edi.md.pecomobile.ServiceApi;

import android.app.Application;

public class GlobalVariable extends Application {
    private Boolean LoginAutenth=false;
    private Boolean ClientInfo=false;

    public Boolean getLoginAutentificate() {
        return LoginAutenth;
    }
    public void setLoginAutentificate(Boolean NewData) {
        this.LoginAutenth = NewData;
    }
    public Boolean getClientInfoReceived() {
        return ClientInfo;
    }
    public void setClientInfoReceived(Boolean NewData) {
        this.ClientInfo = NewData;
    }
}
