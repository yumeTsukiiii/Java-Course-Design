package com.yumetsuki.publicServiceModule.personInformationModule;

import com.yumetsuki.base.BaseManager;
import com.yumetsuki.publicServiceModule.personInformationModule.data.MedicalStaff;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 人员基本信息管理者
 * @author 王小伟
 * @version 1.0*/
public class MedicalStaffManager extends BaseManager<MedicalStaff> {

    private static MedicalStaffManager ourInstance = new MedicalStaffManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static MedicalStaffManager getInstance() {
        return ourInstance;
    }

    private MedicalStaffManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/人员信息.txt","id","name");
    }


}
