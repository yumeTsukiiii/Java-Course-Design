package com.yumetsuki.BasicModule.settleData;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * 结算结果信息类
 * @author 王小伟
 * @version 1.0*/
public class SettleResult {

    private String id;  //个人编号

    private String name;  //人员姓名

    private String category;  //人员类别

    private String settleDate;  //结算日期

    private double startStandard;  //起付标准

    private ArrayList<String> medicalHospital;  //就诊医院

    private ArrayList<String> medicalTimeRange; //就诊时段  start -- end

    private int inHospitalTime;  //住院次数

    private double totalExpenses;  //年度费用总额

    private double selfFundedAmount;//年度自费金额

    private double totalReimbursementAmount; //年度累计报销金额

    private ArrayList<String> selfFundedItem; //自费项目

    private ArrayList<String> bClassItem; //乙类项目

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public int getInHospitalTime() {
        return inHospitalTime;
    }

    public void setInHospitalTime(int inHospitalTime) {
        this.inHospitalTime = inHospitalTime;
    }

    public double getStartStandard() {
        return startStandard;
    }

    public void setStartStandard(double startStandard) {
        this.startStandard = startStandard;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public ArrayList<String> getMedicalHospital() {
        return medicalHospital;
    }

    public void setMedicalHospital(ArrayList<String> medicalHospital) {
        this.medicalHospital = medicalHospital;
    }

    public ArrayList<String> getMedicalTimeRange() {
        return medicalTimeRange;
    }

    public void setMedicalTimeRange(ArrayList<String> medicalTimeRange) {
        this.medicalTimeRange = medicalTimeRange;
    }

    public ArrayList<String> getBClassItem() {
        return bClassItem;
    }

    public void setBClassItem(ArrayList<String> bClassItem) {
        this.bClassItem = bClassItem;
    }

    public ArrayList<String> getSelfFundedItem() {
        return selfFundedItem;
    }

    public void setSelfFundedItem(ArrayList<String> selfFundedItem) {
        this.selfFundedItem = selfFundedItem;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getSelfFundedAmount() {
        return selfFundedAmount;
    }

    public void setSelfFundedAmount(double selfFundedAmount) {
        this.selfFundedAmount = selfFundedAmount;
    }

    public double getTotalReimbursementAmount() {
        return totalReimbursementAmount;
    }

    public void setTotalReimbursementAmount(double totalReimbursementAmount) {
        this.totalReimbursementAmount = totalReimbursementAmount;
    }
}
