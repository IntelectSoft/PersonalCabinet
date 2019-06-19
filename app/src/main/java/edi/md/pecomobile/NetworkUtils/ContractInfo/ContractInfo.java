
package edi.md.pecomobile.NetworkUtils.ContractInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContractInfo {

    @SerializedName("ErrorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("Contract")
    @Expose
    private Contract contract;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

}
