package com.yumetsuki.otherModule.prescriptionDetailsModule;

import com.google.gson.Gson;
import com.yumetsuki.otherModule.prescriptionDetailsModule.data.PrescriptionDetails;
import com.yumetsuki.base.BaseManager;

import java.io.*;
import java.util.ArrayList;

/**
 * 处方明细信息管理者
 * @author 王小伟
 * @version 1.0*/
public class PrescriptionDetailsManager extends BaseManager<PrescriptionDetails> {
    private static PrescriptionDetailsManager ourInstance = new PrescriptionDetailsManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static PrescriptionDetailsManager getInstance() {
        return ourInstance;
    }

    private PrescriptionDetailsManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/处方明细信息.txt","clinicNumber","clinicNumber");
    }

    @Override
    public boolean addToFile(PrescriptionDetails data) {
        try{
            Gson gson = new Gson();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            String json = gson.toJson(data);
            writer.write(json);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
