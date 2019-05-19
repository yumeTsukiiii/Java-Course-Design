package com.yumetsuki.BasicModule.reimburseData;

import com.yumetsuki.BasicModule.settleData.SettleResult;
import com.yumetsuki.BasicModule.preSettleData.PreSettleResult;
import com.yumetsuki.BasicModule.preSettleData.PreSettleResultManager;
import com.yumetsuki.BasicModule.preSettleData.view.PreSettleResultView;
import com.yumetsuki.BasicModule.settleData.SettleResultManager;
import com.yumetsuki.BasicModule.settleData.SettleResultView;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.ServiceInfoManager;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data.ServiceInfo;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.TreatmentInfoManager;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data.TreatmentInfo;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.MedicalManager;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;
import com.yumetsuki.base.MDDialog.MDDialog;
import com.yumetsuki.base.Value;
import com.yumetsuki.base.data.Hospital;
import com.yumetsuki.base.data.HospitalManager;
import com.yumetsuki.otherModule.personnelInformationModule.VisitInformationManager;
import com.yumetsuki.otherModule.personnelInformationModule.data.PersonnelVisitInformation;
import com.yumetsuki.otherModule.prescriptionDetailsModule.PrescriptionDetailsManager;
import com.yumetsuki.otherModule.prescriptionDetailsModule.data.PrescriptionDetails;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 报销管理者，用于进行报销相关操作
 * @author 王小伟
 * @version 1.0*/
public class ReimburseManager {

    private static ReimburseManager ourInstance = new ReimburseManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static ReimburseManager getInstance() {
        return ourInstance;
    }

    private ReimburseManager() { }

    /**
     * 进行报销预结算
     * @param reimburseTableData 报销清单
     * @return 预结算结果
     * @throws  IOException 文件操作受检异常*/
    public PreSettleResult preSettle(ReimburseTableData reimburseTableData) throws IOException{

        double allValue = 0;  //总费用

        double allTotalReimburseValue = 0;  //总报销金额

        double selfFundedAmount = 0;  //总自费金额

        double firstRange = 0;  //第一阶段自费

        double secondRange = 0;  //第二阶段自费

        double thirdRange = 0;   //第三阶段自费


        PersonnelVisitInformation
                information = VisitInformationManager
                .getInstance().searchById(reimburseTableData.getCode(),PersonnelVisitInformation.class);

        if (information == null){
            return null;
        }

        double max = 200000;

        String personType = information.getMedicalCategory();
        if (personType.equals(Value.EMPLOYEES_IN_SERVER)){
            max = 200000;
        } else if (personType.equals(Value.RETIREES)){
            max = 150000;
        } else if (personType.equals(Value.INCUMBENTS_WITH_MINIMUM_SECURITY)){
            max = 120000;
        } else if (personType.equals(Value.RETIREES_WITH_MINIMUM_SECURITY)){
            max = 100000;
        }

        PreSettleResult result = new PreSettleResult();

        ArrayList<PreSettleResult> arrayList = PreSettleResultManager.getInstance().searchByMark(reimburseTableData.getId(),PreSettleResult.class);

        //上一次报销得到的年度报销总额
        double yearAmount = 0;

        //如果在这一年，本次报销前存在报销记录，则获取上次报销统计的年度报销金额
        if (!arrayList.isEmpty()) {
            yearAmount = arrayList.get(arrayList.size()-1).getYearTotalReimbursementAmount();
            //超过最大限额，则不报销
            if (yearAmount > max){
                result = arrayList.get(arrayList.size()-1);
                result.setReimbursementAmount(0);
                result.setFirstRangeAmount(0);
                result.setSecondRangeAmount(0);
                result.setThirdRangeAmount(0);
                result.setSelfFundedAmount(allValue);
                result.setTotalExpenses(allValue);
                result.setYearTotalReimbursementAmount(yearAmount);
                return result;
            }
        }


        //遍历所有处方
        ArrayList<PrescriptionDetails> list;
        try{
            list = PrescriptionDetailsManager.getInstance().searchByMark(information.getClinicNumber(),PrescriptionDetails.class);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        if (list.isEmpty()) return null;

        //增加总费用
        /*如果一次报销总和小于100，则不报销*/
        for (PrescriptionDetails details: list){
            allValue += details.getValue();
        }

        if (allValue < 100) {
            selfFundedAmount += allValue;
        }

        //一次报销申请的总报销
        double totalReimburseValue = 0;

        //一次报销申请中未缩减比例的总报销
        double totalHalfReimburseValue = 0;

        //乙类项目自费
        double bClassItemSelfAmount = 0;

        try{

            //遍历一次报销的所有明细
            for (PrescriptionDetails details: list){

                //如果是药品，按药品算
                Medical medical = MedicalManager.getInstance().searchById(details.getItemCode(),Medical.class);
                if (medical != null){
                    //返回为0，则为自费
                    double v = calculateMedical(details,medical);
                    if (medical.getChargeItemLevel().equals("乙类")) bClassItemSelfAmount += v;
                    if (v == -1){
                        return null;
                    }
                    if (v == 0){
                        selfFundedAmount += details.getValue();
                    }
                    totalHalfReimburseValue += v;
                }

                //是医疗服务，按服务算
                ServiceInfo serviceInfo = ServiceInfoManager.getInstance().searchById(details.getItemCode(),ServiceInfo.class);
                if (serviceInfo != null){
                    totalHalfReimburseValue += calculateService(details);
                }

                //是诊疗项目，按诊疗算
                TreatmentInfo treatmentInfo = TreatmentInfoManager.getInstance().searchById(details.getItemCode(),TreatmentInfo.class);
                if (treatmentInfo !=null){
                    double v = calculateTreatment(details,treatmentInfo);
                    if (treatmentInfo.getChargeItemLevel().equals("乙类")) bClassItemSelfAmount += v;
                    if (v == 0){
                        selfFundedAmount += details.getValue();
                    }
                    totalHalfReimburseValue += v;
                }
            }

            double [][] r = calculateRange(totalHalfReimburseValue);

            //增加三个阶段自费金额
            firstRange += r[0][0];
            secondRange += r[0][1];
            thirdRange += r[0][2];

            //增加一次报销的报销总金额
            totalReimburseValue += r[1][0];
            totalReimburseValue += r[1][1];
            totalReimburseValue += r[1][2];

            allTotalReimburseValue += totalReimburseValue;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }


        selfFundedAmount += firstRange + secondRange + thirdRange;

        //这一次报销后的年度报销金额
        double newYearAmount = allTotalReimburseValue + yearAmount;

        //超过，则年度总报销金额等于最大金额，本次报销金只取最后抵达上限部分
        if (newYearAmount > max){
            allTotalReimburseValue = max - yearAmount;
            selfFundedAmount += newYearAmount - max;
        }

        result.setTotalExpenses(allValue);
        result.setFirstRangeAmount(firstRange);
        result.setSecondRangeAmount(secondRange);
        result.setThirdRangeAmount(thirdRange);
        result.setSelfFundedAmount(selfFundedAmount);
        result.setReimbursementAmount(allTotalReimburseValue);
        result.setId(reimburseTableData.getId());
        result.setName(reimburseTableData.getName());
        result.setCode(reimburseTableData.getCode());
        result.setCertificateNumber(reimburseTableData.getCertificateNumber());
        result.setYearTotalReimbursementAmount(newYearAmount);

        return result;
    }

    /**
     * 展现预结算信息，使用自封装dialog进行显示预结算明细
     * @param result 预结算结果对象
     * */
    public void showPreSettleResult(PreSettleResult result){
        if (result != null){
            PreSettleResultView view = new PreSettleResultView(result);
            MDDialog dialog = new MDDialog.Builder()
                    .setView(view)
                    .showPositiveButton(true)
                    .showNegativeButton(true)
                    .setTitle("本次预结算信息")
                    .build();
            dialog.show();
        }
    }

    /**
     * 将预结算信息写入文件
     * @param result 预结算结果对象
     * @return 是否写入成功*/
    public boolean writePreSettleResultToFile(PreSettleResult result){
        return PreSettleResultManager.getInstance().addToFile(result);
    }

    /**
     * 更新报销申请信息
     * @param data 更新前的报销申请数据
     * @param result 预结算结果对象
     * @param newData 更新后的报销申请数据，这里是一个空的容器
     * @return 更新后的报销申请数据
     * @throws IOException 读写文件受检异常
     * */
    public ReimburseTableData upDateReimburseTableData(ReimburseTableData data, PreSettleResult result,ReimburseTableData newData) throws IOException{
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setCertificateNumber(data.getCertificateNumber());
        newData.setValue(result.getReimbursementAmount());
        newData.setAlreadyReimburse(true);
        newData.setCode(data.getCode());
        ReimburseTableDataManager.getInstance().upDate(newData,data);
        return newData;
    }

    /**
     * 报销结算方法，对预报销进行年度结算
     * @param reimburseTableData 报销清单集合，用于搜索预结算清单及人员信息
     * @return 结算结果数据对象
     * @throws IOException 读写文件受检异常
     * */
    public SettleResult settle(ArrayList<ReimburseTableData> reimburseTableData) throws IOException{

        if (reimburseTableData == null || reimburseTableData.isEmpty()) return null;

        SettleResult settleResult = new SettleResult();

        //预结算信息
        ArrayList<PreSettleResult> results = new ArrayList<>();

        double totalExpenses = 0;  //年度费用总额

        double selfFundedAmount = 0;//年度自费金额

        double totalReimbursementAmount = 0; //年度累计报销金额

        //从文件中查询预结算信息
        for (ReimburseTableData data: reimburseTableData){
            PreSettleResult result = PreSettleResultManager.getInstance().searchById(data.getCode(),PreSettleResult.class);
            if (result != null){
                results.add(result);
                totalExpenses += result.getTotalExpenses();
                selfFundedAmount += result.getSelfFundedAmount();
                if (reimburseTableData.indexOf(data) == reimburseTableData.size()-1){
                    totalReimbursementAmount = result.getYearTotalReimbursementAmount();
                }
            }
        }

        //传入为预结算信息为null或者为空，则返回null
        if (results.isEmpty()) return null;

        //人员id
        String id = reimburseTableData.get(0).getId();
        settleResult.setId(id);
        //人员姓名
        String name = reimburseTableData.get(0).getName();
        //就诊医院
        ArrayList<String> hospital = new ArrayList<>();

        //就诊时段
        //ArrayList<Pair<String,String>>  medicalTimeRange = new ArrayList<>();
        ArrayList<String> medicalTimeRange = new ArrayList<>();

        /*根据人员id得到本年度所有就诊信息*/
        ArrayList<PersonnelVisitInformation> information = VisitInformationManager.getInstance().searchByMark(id,PersonnelVisitInformation.class);

        for (int i = 0; i < information.size(); i++) {
            hospital.add("第"+(i+1)+"次报销："+" - "+information.get(i).getHospitalName());
            medicalTimeRange.add(information.get(i).getAdmissionDate()+ " to " +information.get(i).getDischargeDate());
        }

        //人员类别
        String category = information.get(0).getMedicalCategory();

        /*根据就诊信息得到所有处方信息*/
        ArrayList<PrescriptionDetails> detailsArrayList = new ArrayList<>();
        for (PersonnelVisitInformation visitInformation: information){
            detailsArrayList.addAll(PrescriptionDetailsManager.getInstance().searchByMark(visitInformation.getClinicNumber(),PrescriptionDetails.class));
        }

        //乙类项目
        ArrayList<String> bClassItem = new ArrayList<>();
        //自费项目
        ArrayList<String> cClassItem = new ArrayList<>();

        /*遍历处方信息，获取乙类和丙类项目*/
        for (PrescriptionDetails details: detailsArrayList){
            Medical medical = MedicalManager.getInstance().searchById(details.getItemCode(),Medical.class);
            if (medical != null){
                if (medical.getChargeItemLevel().equals("乙类")){
                    //将药品乙类项目名称添加
                    boolean flag = true;
                    for (String s: bClassItem){
                        if (s.equals(medical.getName())){
                            flag = false;
                            break;
                        }
                    }
                    if (flag) bClassItem.add(medical.getName());
                }
                if (medical.getChargeItemLevel().equals("丙类")){
                    //丙类为自费项目
                    boolean flag = true;
                    for (String s: cClassItem){
                        if (s.equals(medical.getName())){
                            flag = false;
                            break;
                        }
                    }
                    if (flag) cClassItem.add(medical.getName());
                }
            }

            TreatmentInfo treatmentInfo = TreatmentInfoManager.getInstance().searchById(details.getItemCode(),TreatmentInfo.class);
            if (treatmentInfo !=null){
                if (treatmentInfo.getChargeItemLevel().equals("乙类")){
                    //将乙类诊疗项目名称添加
                    boolean flag = true;
                    for (String s: bClassItem){
                        if (s.equals(treatmentInfo.getProjectName())){
                            flag = false;
                            break;
                        }
                    }
                    if (flag) bClassItem.add(treatmentInfo.getProjectName());
                }
                if (treatmentInfo.getChargeItemLevel().equals("丙类")){
                    //丙类为自费项目
                    boolean flag = true;
                    for (String s: bClassItem){
                        if (s.equals(treatmentInfo.getProjectName())){
                            flag = false;
                            break;
                        }
                    }
                    if (flag) cClassItem.add(treatmentInfo.getProjectName());
                }
            }
        }

        //住院次数
        int inHospitalTime = results.size();

        //结算日期
        LocalDate date = LocalDate.now();
        String settleDate = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();

        //起付标准
        double standard = 100;

        settleResult.setName(name);
        settleResult.setId(id);
        settleResult.setSelfFundedAmount(selfFundedAmount);
        settleResult.setBClassItem(bClassItem);
        settleResult.setCategory(category);
        settleResult.setTotalExpenses(totalExpenses);
        settleResult.setInHospitalTime(inHospitalTime);
        settleResult.setMedicalHospital(hospital);
        settleResult.setMedicalTimeRange(medicalTimeRange);
        settleResult.setSettleDate(settleDate);
        settleResult.setStartStandard(standard);
        settleResult.setTotalReimbursementAmount(totalReimbursementAmount);
        settleResult.setSelfFundedItem(cClassItem);

        return settleResult;
    }

    /**
     * 通过弹窗展示报销结果
     * @param result 结算结果
     * */
    public void showSettleResult(SettleResult result){
        if (result != null){
            SettleResultView view = new SettleResultView(result);
            MDDialog dialog = new MDDialog.Builder()
                    .setView(view)
                    .showPositiveButton(true)
                    .showNegativeButton(true)
                    .setTitle("本次预结算信息")
                    .build();
            dialog.show();
        }
    }

    /**
     * 将报销结果写入文件
     * @param result 结算结果
     * @return 是否写入成功标识*/
    public boolean writeSettleResultToFile(SettleResult result){
        return SettleResultManager.getInstance().addToFile(result);
    }

    /**
     * 更新报销申请信息
     * @param data 更新前的报销申请数据
     * @param newData 更新后的报销申请数据，这里是一个空的容器
     * @return 更新后的报销申请数据
     * @throws IOException 读写文件受检异常
     * */
    public ReimburseTableData upDateReimburseTableData(ReimburseTableData data,ReimburseTableData newData) throws IOException{
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setCertificateNumber(data.getCertificateNumber());
        newData.setValue(data.getValue());
        newData.setFinallyReimburse(true);
        newData.setAlreadyReimburse(true);
        newData.setCode(data.getCode());
        ReimburseTableDataManager.getInstance().upDate(newData,data);
        return newData;
    }

    /**
     * 打印结算报表,使用poi库操作xls文件表格每一项
     * @param reimburseTableData 报销申请信息数据
     * @throws IOException 读写文件受检异常*/
    public void printSettleResultToExcel(ReimburseTableData reimburseTableData) throws IOException{

        SettleResult result = SettleResultManager.getInstance().searchById(reimburseTableData.getId(),SettleResult.class);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("结算清单");

        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleRowCell = titleRow.createCell(0);

        HSSFCellStyle cellStyle=workbook.createCellStyle();
        HSSFFont fontStyle = workbook.createFont();
        fontStyle.setBold(true);
        fontStyle.setFontHeightInPoints((short) 18);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(fontStyle);
        titleRow.getCell(0).setCellStyle(cellStyle);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //表头
        titleRowCell.setCellValue("报销结算清单");
        sheet.addMergedRegion(new CellRangeAddress(0,3,0,10));
        HSSFRow settleDataRow = sheet.createRow(4);
        sheet.addMergedRegion(new CellRangeAddress(4,4,0,4));
        settleDataRow.createCell(5).setCellValue("结算日期");

        //录入结算日期
        settleDataRow.createCell(6).setCellValue(result.getSettleDate());
        sheet.addMergedRegion(new CellRangeAddress(4,4,6,10));

        HSSFRow baseRow = sheet.createRow(5);
        baseRow.createCell(0).setCellValue("姓名");
        baseRow.createCell(1).setCellValue(result.getName());
        sheet.addMergedRegion(new CellRangeAddress(5,5,1,2));
        baseRow.createCell(3).setCellValue("个人编号");
        baseRow.createCell(4).setCellValue(result.getId());
        sheet.addMergedRegion(new CellRangeAddress(5,5,4,5));
        baseRow.createCell(6).setCellValue("人员类别");
        baseRow.createCell(7).setCellValue(result.getCategory());
        sheet.addMergedRegion(new CellRangeAddress(5,5,7,10));

        /*就诊医院和诊疗时段*/
        HSSFRow medicalRow = sheet.createRow(6);
        medicalRow.createCell(0).setCellValue("就诊医院");
        medicalRow.getCell(0).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(6,6 + result.getInHospitalTime() - 1,0,0));
        medicalRow.createCell(6).setCellValue("就诊时段");
        medicalRow.getCell(6).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(6,6 + result.getInHospitalTime() - 1,6,6));
        medicalRow.createCell(1).setCellValue(result.getMedicalHospital().get(0));
        sheet.addMergedRegion(new CellRangeAddress(6,6,1,5));
        medicalRow.createCell(7).setCellValue(result.getMedicalTimeRange().get(0));
        sheet.addMergedRegion(new CellRangeAddress(6,6,7,10));
        for (int i = 1; i < result.getInHospitalTime(); i++) {
            HSSFRow row = sheet.createRow(6+i);
            row.createCell(1).setCellValue(result.getMedicalHospital().get(i));
            sheet.addMergedRegion(new CellRangeAddress(6+i,6+i,1,5));
            row.createCell(7).setCellValue(result.getMedicalTimeRange().get(i));
            sheet.addMergedRegion(new CellRangeAddress(6+i,6+i,7,10));
        }

        int m = 5 + result.getInHospitalTime() + 1;

        sheet.createRow(m).createCell(0).setCellValue("结算明细：");
        sheet.addMergedRegion(new CellRangeAddress(m,m,0,10));
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        hssfCellStyle.setFont(font);
        sheet.getRow(m).getCell(0).setCellStyle(hssfCellStyle);


        sheet.createRow(m+1).createCell(0).setCellValue("起付标准：");
        sheet.addMergedRegion(new CellRangeAddress(m+1,m+1,0,2));
        sheet.createRow(m+2).createCell(0).setCellValue("100元");
        sheet.getRow(m+2).getCell(0).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(m+2,m+2,0,2));

        int e = m + 3 + result.getBClassItem().size() + result.getSelfFundedItem().size() + 1;

        sheet.createRow(m+3).createCell(0).setCellValue("自费项目：");
        sheet.addMergedRegion(new CellRangeAddress(m+3,m+3,0,2));
        if (result.getSelfFundedItem().isEmpty()){
            sheet.getRow(m+3).getCell(0).setCellValue("自费项目：无");
        }
        for (int i = 0; i < result.getSelfFundedItem().size(); i++){
            sheet.createRow(m+4 + i).createCell(0).setCellValue(result.getSelfFundedItem().get(i));
            sheet.addMergedRegion(new CellRangeAddress(m+4 + i,m+4 + i,0,2));
            sheet.getRow(m+4 + i).getCell(0).setCellStyle(style);
        }

        sheet.createRow(m+3 + result.getSelfFundedItem().size() + 1).createCell(0).setCellValue("乙类项目：");
        sheet.addMergedRegion(new CellRangeAddress(m+3 + result.getSelfFundedItem().size() + 1,m+3 + result.getSelfFundedItem().size() + 1,0,2));
        if (result.getBClassItem().isEmpty()){
            sheet.getRow(m+3 + result.getSelfFundedItem().size() + 1).getCell(0).setCellValue("乙类项目：无");
        }
        for (int i = 0; i < result.getBClassItem().size(); i++){
            sheet.createRow(m+3 + result.getSelfFundedItem().size() + 1 + i + 1).createCell(0).setCellValue(result.getBClassItem().get(i));
            sheet.addMergedRegion(new CellRangeAddress(m+3 + result.getSelfFundedItem().size() + 1 + i + 1,m+3 + result.getSelfFundedItem().size() + 1 + i + 1,0,2));
            sheet.getRow(m+3 + result.getSelfFundedItem().size() + 1 + i + 1).getCell(0).setCellStyle(style);
        }

        sheet.addMergedRegion(new CellRangeAddress(m+1,e,3,10));
        sheet.createRow(e+1).createCell(0).setCellValue("个人自费费用：");
        sheet.addMergedRegion(new CellRangeAddress(e+1,e+1,0,2));
        sheet.createRow(e+2).createCell(0).setCellValue("中心报销金额：");
        sheet.addMergedRegion(new CellRangeAddress(e+2,e+2,0,2));
        sheet.createRow(e+3).createCell(0).setCellValue("年度花费总额：");
        sheet.getRow(e+1).createCell(3).setCellValue(result.getSelfFundedAmount() + " 元");
        sheet.getRow(e+2).createCell(3).setCellValue(result.getTotalReimbursementAmount() + " 元");
        sheet.getRow(e+3).createCell(3).setCellValue(result.getTotalExpenses() + "元");
        sheet.addMergedRegion(new CellRangeAddress(e+1,e+1,3,10));
        sheet.addMergedRegion(new CellRangeAddress(e+2,e+2,3,10));
        sheet.addMergedRegion(new CellRangeAddress(e+3,e+3,3,10));

        try {
            File file = new File("out/production/MedicialClass/com/yumetsuki/file/报销结算打印单" + result.getId() + ".xls");
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e1){
            e1.printStackTrace();
        }

    }

    /**
     * 计算该药物的可报销价格
     * @param details 处方明细
     * @param medical 药物数据
     * @return 该药物的可报销价格*/
    private double calculateMedical(PrescriptionDetails details,Medical medical) throws IOException{
        double reimburseValue = details.getValue();
        if (details.getPrice() > medical.getMaximumPrice()){
            reimburseValue = medical.getMaximumPrice()*details.getCount();
        }

        Hospital hospital = HospitalManager.getInstance().searchById(medical.getHospitalCode(),Hospital.class);
        if (hospital == null || !hospital.getCode().equals(medical.getHospitalCode())) return -1;
        if (!isHospitalLevelMoreThanItemLevel(hospital.getLevel(),medical.getHospitalLevel())) return 0;
        if (medical.getChargeItemLevel().equals("乙类")) {
            reimburseValue = reimburseValue / 2;
        } else if (medical.getChargeItemLevel().equals("丙类")){
            return 0;
        }
        return reimburseValue;
    }

    /**
     * 计算该服务设施的可报销价格
     * @param details 处方明细
     * @return 该服务设施的可报销价格*/
    private double calculateService(PrescriptionDetails details){
        return details.getValue();
    }

    /**
     * 计算该诊疗服务的可报销价格
     * @param details 处方明细
     * @param treatmentInfo 诊疗服务项目*/
    private double calculateTreatment(PrescriptionDetails details,TreatmentInfo treatmentInfo) throws IOException{
        double reimburseValue = details.getValue();
        if (treatmentInfo.getChargeItemLevel().equals("乙类")) {
            reimburseValue = reimburseValue / 2;
        } else if (treatmentInfo.getChargeItemLevel().equals("丙类")){
            return 0;
        }
        return reimburseValue;
    }

    /**
     * 通过医院等级判断该项目是否可报销
     * @param hospitalLevel 医院等级
     * @param itemLevel 项目等级
     * @return 是否可报销标识*/
    private boolean isHospitalLevelMoreThanItemLevel(String hospitalLevel, String itemLevel){

        if (hospitalLevel.equals("三级") && itemLevel.equals("三级")){
            return true;
        } else if (hospitalLevel.equals("二级") && itemLevel.equals("三级")){
            return true;
        } else if (hospitalLevel.equals("一级") && itemLevel.equals("三级")){
            return true;
        } else if (hospitalLevel.equals("二级") && itemLevel.equals("二级")){
            return true;
        } else if (hospitalLevel.equals("一级") && itemLevel.equals("二级")){
            return true;
        } else if (hospitalLevel.equals("一级") && itemLevel.equals("一级")){
            return true;
        } else if (hospitalLevel.equals("社区") && itemLevel.equals("一级")){
            return true;
        } else if (hospitalLevel.equals("社区") && itemLevel.equals("社区")){
            return true;
        }
        return false;
    }

    /**
     * 计算分阶段自费和报销费用
     * @param halfValue 经过计算的可报销费用
     * @return 分阶段自费和报销费用*/
    private double[][] calculateRange(double halfValue){

        double firstSelfRange = 0;
        double secondSelfRange = 0;
        double thirdSelfRange = 0;

        double firstReimburseRange = 0;
        double secondReimburseRange = 0;
        double thirdReimburseRange = 0;

        if (halfValue <= 10000 && halfValue > 100){
            firstSelfRange = (halfValue - 100) * 0.2;
            firstReimburseRange = (halfValue - 100) * (1 - 0.2);
        } else if (halfValue <= 20000 && halfValue > 10000) {
            firstSelfRange = (10000 - 100) * 0.2;
            firstReimburseRange = (10000 - 100) * (1 - 0.2);
            secondSelfRange = (halfValue - 10000) * 0.1;
            secondReimburseRange = (halfValue - 10000) * (1 - 0.1);

        } else if (halfValue > 20000){
            firstSelfRange = (10000 - 100) * 0.2;
            firstReimburseRange = (10000 - 100) * (1 - 0.2);
            secondSelfRange = (20000 - 10000) * 0.1;
            secondReimburseRange = (20000 - 10000) * (1 - 0.1);
            thirdSelfRange = (halfValue - 20000) * 0.05;
            thirdReimburseRange = (halfValue - 20000) * (1 - 0.05);
        }

        double [][] result = new double[2][3];
        result[0][0] = firstSelfRange;  //第一阶段自费
        result[0][1] = secondSelfRange;  //第二阶段自费
        result[0][2] = thirdSelfRange;  //第三阶段自费
        result[1][0] = firstReimburseRange;  //第一阶段报销
        result[1][1] = secondReimburseRange;  //第二阶段报销
        result[1][2] = thirdReimburseRange;  //第三阶段报销
        return result;

    }
}
