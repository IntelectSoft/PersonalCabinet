
package edi.md.pecomobile.NetworkUtils.ContractInfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardsList {

    @SerializedName("AdditionalLimit")
    @Expose
    private Double additionalLimit;
    @SerializedName("AdditionalLimitDate")
    @Expose
    private String additionalLimitDate;
    @SerializedName("BalanceAccountCard")
    @Expose
    private Double balanceAccountCard;
    @SerializedName("CardAssortments")
    @Expose
    private List<CardAssortment> cardAssortments = null;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("DailyLimit")
    @Expose
    private Double dailyLimit;
    @SerializedName("DailyLimitRemain")
    @Expose
    private Double dailyLimitRemain;
    @SerializedName("DailyLimitUsed")
    @Expose
    private Double dailyLimitUsed;
    @SerializedName("ForbidsChangesAssortmentsFromWeb")
    @Expose
    private Boolean forbidsChangesAssortmentsFromWeb;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("LimitType")
    @Expose
    private Integer limitType;
    @SerializedName("MonthlyLimit")
    @Expose
    private Double monthlyLimit;
    @SerializedName("MonthlyLimitRemain")
    @Expose
    private Double monthlyLimitRemain;
    @SerializedName("MonthlyLimitUsed")
    @Expose
    private Double monthlyLimitUsed;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SeparateClientAccount")
    @Expose
    private Boolean separateClientAccount;
    @SerializedName("WeeklyLimit")
    @Expose
    private Double weeklyLimit;
    @SerializedName("WeeklyLimitRemain")
    @Expose
    private Double weeklyLimitRemain;
    @SerializedName("WeeklyLimitUsed")
    @Expose
    private Double weeklyLimitUsed;

    public Double getAdditionalLimit() {
        return additionalLimit;
    }

    public void setAdditionalLimit(Double additionalLimit) {
        this.additionalLimit = additionalLimit;
    }

    public String getAdditionalLimitDate() {
        return additionalLimitDate;
    }

    public void setAdditionalLimitDate(String additionalLimitDate) {
        this.additionalLimitDate = additionalLimitDate;
    }

    public Double getBalanceAccountCard() {
        return balanceAccountCard;
    }

    public void setBalanceAccountCard(Double balanceAccountCard) {
        this.balanceAccountCard = balanceAccountCard;
    }

    public List<CardAssortment> getCardAssortments() {
        return cardAssortments;
    }

    public void setCardAssortments(List<CardAssortment> cardAssortments) {
        this.cardAssortments = cardAssortments;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Double getDailyLimitRemain() {
        return dailyLimitRemain;
    }

    public void setDailyLimitRemain(Double dailyLimitRemain) {
        this.dailyLimitRemain = dailyLimitRemain;
    }

    public Double getDailyLimitUsed() {
        return dailyLimitUsed;
    }

    public void setDailyLimitUsed(Double dailyLimitUsed) {
        this.dailyLimitUsed = dailyLimitUsed;
    }

    public Boolean getForbidsChangesAssortmentsFromWeb() {
        return forbidsChangesAssortmentsFromWeb;
    }

    public void setForbidsChangesAssortmentsFromWeb(Boolean forbidsChangesAssortmentsFromWeb) {
        this.forbidsChangesAssortmentsFromWeb = forbidsChangesAssortmentsFromWeb;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public Double getMonthlyLimitRemain() {
        return monthlyLimitRemain;
    }

    public void setMonthlyLimitRemain(Double monthlyLimitRemain) {
        this.monthlyLimitRemain = monthlyLimitRemain;
    }

    public Double getMonthlyLimitUsed() {
        return monthlyLimitUsed;
    }

    public void setMonthlyLimitUsed(Double monthlyLimitUsed) {
        this.monthlyLimitUsed = monthlyLimitUsed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSeparateClientAccount() {
        return separateClientAccount;
    }

    public void setSeparateClientAccount(Boolean separateClientAccount) {
        this.separateClientAccount = separateClientAccount;
    }

    public Double getWeeklyLimit() {
        return weeklyLimit;
    }

    public void setWeeklyLimit(Double weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public Double getWeeklyLimitRemain() {
        return weeklyLimitRemain;
    }

    public void setWeeklyLimitRemain(Double weeklyLimitRemain) {
        this.weeklyLimitRemain = weeklyLimitRemain;
    }

    public Double getWeeklyLimitUsed() {
        return weeklyLimitUsed;
    }

    public void setWeeklyLimitUsed(Double weeklyLimitUsed) {
        this.weeklyLimitUsed = weeklyLimitUsed;
    }

}
