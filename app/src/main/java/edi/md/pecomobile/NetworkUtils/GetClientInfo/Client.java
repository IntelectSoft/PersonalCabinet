
package edi.md.pecomobile.NetworkUtils.GetClientInfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("Amount")
    @Expose
    private Double amount;
    @SerializedName("Balance")
    @Expose
    private Double balance;
    @SerializedName("CardsBalance")
    @Expose
    private Double cardsBalance;
    @SerializedName("Contracts")
    @Expose
    private List<ContractClient> contracts = null;
    @SerializedName("Credit")
    @Expose
    private Double credit;
    @SerializedName("IDNP")
    @Expose
    private String iDNP;
    @SerializedName("MasterBalance")
    @Expose
    private Double masterBalance;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("NonInvoicedConsumptionAmount")
    @Expose
    private Double nonInvoicedConsumptionAmount;
    @SerializedName("Overdraft")
    @Expose
    private Double overdraft;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("TotalDebtSum")
    @Expose
    private Double totalDebtSum;
    @SerializedName("UnpaidDocuments")
    @Expose
    private List<Object> unpaidDocuments = null;
    @SerializedName("UnpaidInvoiceConsumptionAmount")
    @Expose
    private Double unpaidInvoiceConsumptionAmount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCardsBalance() {
        return cardsBalance;
    }

    public void setCardsBalance(Double cardsBalance) {
        this.cardsBalance = cardsBalance;
    }

    public List<ContractClient> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractClient> contracts) {
        this.contracts = contracts;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getIDNP() {
        return iDNP;
    }

    public void setIDNP(String iDNP) {
        this.iDNP = iDNP;
    }

    public Double getMasterBalance() {
        return masterBalance;
    }

    public void setMasterBalance(Double masterBalance) {
        this.masterBalance = masterBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNonInvoicedConsumptionAmount() {
        return nonInvoicedConsumptionAmount;
    }

    public void setNonInvoicedConsumptionAmount(Double nonInvoicedConsumptionAmount) {
        this.nonInvoicedConsumptionAmount = nonInvoicedConsumptionAmount;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Double overdraft) {
        this.overdraft = overdraft;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotalDebtSum() {
        return totalDebtSum;
    }

    public void setTotalDebtSum(Double totalDebtSum) {
        this.totalDebtSum = totalDebtSum;
    }

    public List<Object> getUnpaidDocuments() {
        return unpaidDocuments;
    }

    public void setUnpaidDocuments(List<Object> unpaidDocuments) {
        this.unpaidDocuments = unpaidDocuments;
    }

    public Double getUnpaidInvoiceConsumptionAmount() {
        return unpaidInvoiceConsumptionAmount;
    }

    public void setUnpaidInvoiceConsumptionAmount(Double unpaidInvoiceConsumptionAmount) {
        this.unpaidInvoiceConsumptionAmount = unpaidInvoiceConsumptionAmount;
    }

}
