package com.yumetsuki.MedicalBaseModule.medicalInformationModule;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;
import com.yumetsuki.base.BaseManager;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * 药品信息管理者
 * @author 王小伟
 * @version 1.0*/
public class MedicalManager extends BaseManager<Medical> {

    private static MedicalManager ourInstance= new MedicalManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static MedicalManager getInstance(){
        return ourInstance;
    }

    private MedicalManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/药品信息.txt","code","name");
    }

}
