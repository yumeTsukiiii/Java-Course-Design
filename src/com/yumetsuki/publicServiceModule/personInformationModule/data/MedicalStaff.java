package com.yumetsuki.publicServiceModule.personInformationModule.data;

import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;

import java.util.Date;

/**
 * 人员基本信息类
 * @author 王小伟
 * @version 1.0*/
public class MedicalStaff {

    //必填信息
    private String id;  //个人id
    private String name;  //姓名
    private String sex;  //性别
    private String nationality;  //民族
    private String birthday; //出生日期
    private String typeOfCertificate;  //证件类型
    private String certificateNumber; //证件编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeOfCertificate() {
        return typeOfCertificate;
    }

    public void setTypeOfCertificate(String typeOfCertificate) {
        this.typeOfCertificate = typeOfCertificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
