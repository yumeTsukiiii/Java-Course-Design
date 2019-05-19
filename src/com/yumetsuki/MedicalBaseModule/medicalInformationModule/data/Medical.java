package com.yumetsuki.MedicalBaseModule.medicalInformationModule.data;

/**
 * 药品信息类
 * @author 王小伟
 * @version 1.0*/
public class Medical {

    /*必选信息*/
    private String code;  //药品编码
    private String name;  //药品名称
    private double maximumPrice;  //最高限价
    private String drugDosageUnit;  //药品计量单位
    private String chargeItemLevel;  //收费项目等级
    private String hospitalLevel;  //药品医院等级
    private String hospitalCode; //医院编码


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getDrugDosageUnit() {
        return drugDosageUnit;
    }

    public void setDrugDosageUnit(String drugDosageUnit) {
        this.drugDosageUnit = drugDosageUnit;
    }

    public String getChargeItemLevel() {
        return chargeItemLevel;
    }

    public void setChargeItemLevel(String chargeItemLevel) {
        this.chargeItemLevel = chargeItemLevel;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
