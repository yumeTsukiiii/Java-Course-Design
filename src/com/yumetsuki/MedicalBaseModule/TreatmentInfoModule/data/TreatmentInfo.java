package com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data;

/**
 * 诊疗服务信息类
 * @author 王小伟
 * @version 1.0*/
public class TreatmentInfo {
    private String projectCode;  //(项目编码): String
    private String projectName;  //(项目名称): String
    private String chargeItemLevel;  //(收费等级): String,
    private String hospitalLevel;  //(项目医院等级): String
    private String hospitalCode;  //医院编码

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
