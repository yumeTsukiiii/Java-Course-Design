package com.yumetsuki.BasicModule.reimburseData;

/**
 * 报销申请信息类
 * @author 王小伟
 * @version 1.0*/
public class ReimburseTableData {

    private String id; //人员id
    private String name; //人员名字
    private String certificateNumber;  //证件编号
    private String code; //报销编码(门诊号)
    private double value;  //报销金额
    private boolean isAlreadyReimburse; //是否已经预结算
    private boolean isFinallyReimburse;  //是否已经结算

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isAlreadyReimburse() {
        return isAlreadyReimburse;
    }

    public void setAlreadyReimburse(boolean alreadyReimburse) {
        isAlreadyReimburse = alreadyReimburse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinallyReimburse() {
        return isFinallyReimburse;
    }

    public void setFinallyReimburse(boolean finallyReimburse) {
        isFinallyReimburse = finallyReimburse;
    }


}
