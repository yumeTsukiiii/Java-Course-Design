package com.yumetsuki.otherModule.personnelInformationModule.data;

/**
 * 人员诊疗信息类
 * @author 王小伟
 * @version 1.0*/
public class PersonnelVisitInformation {

    private String id;
    private String clinicNumber;  //门诊号
    private String medicalCategory; //医疗类别
    private String hospitalCode;  //医院编号
    private String hospitalName; //医院名称
    private String admissionDate; //入院日期
    private String dischargeDate; //出院日期
    private String reasonForDischarge; //出院原因

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getReasonForDischarge() {
        return reasonForDischarge;
    }

    public void setReasonForDischarge(String reasonForDischarge) {
        this.reasonForDischarge = reasonForDischarge;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
