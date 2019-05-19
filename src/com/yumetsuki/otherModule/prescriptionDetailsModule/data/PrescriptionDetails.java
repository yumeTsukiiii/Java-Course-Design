package com.yumetsuki.otherModule.prescriptionDetailsModule.data;

/**
 * 处方明细信息类
 * @author 王小伟
 * @version 1.0*/
public class PrescriptionDetails {

    private String clinicNumber;  //门诊号

    private String itemCode;  //项目编码

    private double price;  //单价

    private int count; //数量

    private double value = getValue();  //金额

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        value = getValue();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        value = getValue();
    }

    public double getValue(){
        return count * price;
    }
}
