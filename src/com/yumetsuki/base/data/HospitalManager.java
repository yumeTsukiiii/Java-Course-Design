package com.yumetsuki.base.data;

import com.yumetsuki.base.BaseManager;

/**
 * 医院管理者，单例，用于做医院的增删改查
 * @author 王小伟
 * @version 1.0*/
public class HospitalManager extends BaseManager<Hospital> {
    private static HospitalManager ourInstance = new HospitalManager();

    /**
     * 获取单例
     * @return 医院管理者单例*/
    public static HospitalManager getInstance() {
        return ourInstance;
    }

    private HospitalManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/医院信息.txt","code","name");
    }
}
