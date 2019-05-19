package com.yumetsuki.BasicModule.preSettleData;

/**
 * 数据类用于储存预算结果
 * @author 王小伟
 * @version 1.0*/
public class PreSettleResult {

    private String id; //人员id

    private String name;  //人员姓名

    private String code; //报销编码(门诊号)

    private String certificateNumber;  //证件编号

    private int startStandard = 100;  //起付标准

    private double totalExpenses; //费用总额

    private double reimbursementAmount;  //报销金额

    private double selfFundedAmount;//自费金额

    private double yearTotalReimbursementAmount; //年度总计报销

    private double firstRangeAmount;  //第一阶段自费

    private double secondRangeAmount;  //第二阶段自费

    private double thirdRangeAmount;   //第三阶段自费


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStartStandard() {
        return startStandard;
    }

    public void setStartStandard(int startStandard) {
        this.startStandard = startStandard;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public double getYearTotalReimbursementAmount() {
        return yearTotalReimbursementAmount;
    }

    public void setYearTotalReimbursementAmount(double yearTotalReimbursementAmount) {
        this.yearTotalReimbursementAmount = yearTotalReimbursementAmount;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getReimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(double reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }

    public double getSelfFundedAmount() {
        return selfFundedAmount;
    }

    public void setSelfFundedAmount(double selfFundedAmount) {
        this.selfFundedAmount = selfFundedAmount;
    }

    public double getFirstRangeAmount() {
        return firstRangeAmount;
    }

    public void setFirstRangeAmount(double firstRangeAmount) {
        this.firstRangeAmount = firstRangeAmount;
    }

    public double getSecondRangeAmount() {
        return secondRangeAmount;
    }

    public void setSecondRangeAmount(double secondRangeAmount) {
        this.secondRangeAmount = secondRangeAmount;
    }

    public double getThirdRangeAmount() {
        return thirdRangeAmount;
    }

    public void setThirdRangeAmount(double thirdRangeAmount) {
        this.thirdRangeAmount = thirdRangeAmount;
    }
}
