package com.yumetsuki.MedicalBaseModule.TreatmentInfoModule;

import com.yumetsuki.base.BaseManager;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data.TreatmentInfo;

/**
 * 诊疗服务信息管理者
 * @author 王小伟
 * @version 1.0*/
public class TreatmentInfoManager extends BaseManager<TreatmentInfo> {
    private static TreatmentInfoManager ourInstance = new TreatmentInfoManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static TreatmentInfoManager getInstance() {
        return ourInstance;
    }

    private TreatmentInfoManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/诊疗项目信息.txt","projectCode","projectName");
    }
}
