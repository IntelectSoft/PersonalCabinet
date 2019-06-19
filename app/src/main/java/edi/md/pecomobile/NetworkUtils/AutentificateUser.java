
package edi.md.pecomobile.NetworkUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutentificateUser {

    @SerializedName("ErrorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("SID")
    @Expose
    private String sID;
    @SerializedName("AuthType")
    @Expose
    private Integer authType;
    @SerializedName("IDNO")
    @Expose
    private String iDNO;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("User")
    @Expose
    private String user;

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getIDNO() {
        return iDNO;
    }

    public void setIDNO(String iDNO) {
        this.iDNO = iDNO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSID() {
        return sID;
    }

    public void setSID(String sID) {
        this.sID = sID;
    }

}
