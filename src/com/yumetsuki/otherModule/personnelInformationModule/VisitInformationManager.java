package com.yumetsuki.otherModule.personnelInformationModule;

import com.yumetsuki.otherModule.personnelInformationModule.data.PersonnelVisitInformation;
import com.yumetsuki.base.BaseManager;

/**
 * 人员诊疗管理者
 * @author 王小伟
 * @version 1.0*/
public class VisitInformationManager extends BaseManager<PersonnelVisitInformation> {
    private static VisitInformationManager ourInstance = new VisitInformationManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static VisitInformationManager getInstance() {
        return ourInstance;
    }

    private VisitInformationManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/人员就诊信息.txt","clinicNumber","id");
    }
}
