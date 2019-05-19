package com.yumetsuki.base;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * 基础数据管理者，用于数据的增删改查
 * @author 王小伟
 * @version 1.0*/
public class BaseManager<T>{

    //操作文件
    protected File file;

    //用于单一搜索的唯一键属性名
    protected String idPropertyName;

    //用于多项搜索的键属性名
    protected String markPropertyName;

    /**
     * 构造方法初始化必要数据
     * @param route 文件路径
     * @param idPropertyName 用于单一搜索的唯一键属性名
     * @param markPropertyName 用于多项搜索的键属性名*/
    public BaseManager(String route,String idPropertyName,String markPropertyName){
        file = new File(route);
        this.idPropertyName = idPropertyName;
        this.markPropertyName = markPropertyName;
    }

    /**
     * 构造方法初始化必要数据
     * @param route 文件路径
     * @param idPropertyName 用于单一搜索的唯一键属性名 */
    public BaseManager(String route,String idPropertyName){
        file = new File(route);
        this.idPropertyName = idPropertyName;
    }

    /**
     * 添加数据到文件
     * @param data 添加的数据
     * @return 添加成功与否*/
    public boolean addToFile(T data){
        try{

            //使用Gson做数据转换
            Gson gson = new Gson();
            String mId = getDataIdOrMarkByClass(data,idPropertyName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s;

            //默认如果有id相同项，则不进行添加
            while ((s = reader.readLine())!=null){
                String id = getDataIdOrMarkByJson(s,idPropertyName);
                if (id != null && mId != null){
                    if (id.equals(mId)){
                        return false;
                    }
                }
            }

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

    /**
     * 从文件移除数据
     * @param data 移除的数据
     * @return 移除成功与否
     * @throws  IOException 文件操作受检异常*/
    public boolean removeFromFile(T data) throws IOException{
        return doSomeThingWithDataList(data,(list, s) -> list.remove(s));
    }

    /**
     * 更新数据到文件
     * @param data 更新的数据
     * @param oldData 老的数据
     * @return 更新成功与否
     * @throws  IOException 文件操作受检异常*/
    public boolean upDate(T data,T oldData) throws IOException{
        return doSomeThingWithDataList(oldData,(list, s) -> {
            Gson gson = new Gson();
            list.set(list.indexOf(s),gson.toJson(data));
        });
    }

    /**
     * 通过id搜索唯一数据
     * @param id 搜索id
     * @param tClass 数据类型
     * @return 检索到的数据，没有检索到则返回null
     * @throws  IOException 文件操作受检异常*/
    public T searchById(String id,Class<T> tClass) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s ;
        T t;
        while ((s = reader.readLine()) != null) {
            Gson gson = new Gson();
            //获取文件里的一行记录,并得到其id
            t = gson.fromJson(s,tClass);
            String i = getDataIdOrMarkByJson(s,idPropertyName);
            if (i != null){
                if (i.equals(id)) return t;
            }
        }
        reader.close();
        return null;
    }

    /**
     * 通过mark搜索多项数据
     * @param mark 搜索标识
     * @param tClass 数据类型
     * @return 检索到的数据，没有检索到则返回空集合
     * @throws  IOException 文件操作受检异常*/
    public ArrayList<T> searchByMark(String mark,Class<T> tClass) throws IOException{
        if (markPropertyName == null) {
            System.out.println("未初始化mark-----------------");
            return null;
        }

        ArrayList<T> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s ;
        T t;
        while ((s = reader.readLine()) != null) {
            Gson gson = new Gson();
            t = gson.fromJson(s,tClass);
            String i = getDataIdOrMarkByJson(s,markPropertyName);
            if (i != null){
                if (i.equals(mark))
                    list.add(t);
            }
        }
        reader.close();
        return list;
    }

    /**
     * 通过JsonParser去获取文件中记录的某行数据的id或者mark
     * @param s 文件中的某行数据
     * @param idOrMark 输入的id或mark属性标识名
     * @return 从记录中提取出的id或mark的值*/
    protected final String getDataIdOrMarkByJson(String s,String idOrMark){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(s);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonElement element1 = jsonObject.get(idOrMark);
        if ( element1 != null){
            return element1.getAsString();
        }
        return null;
    }

    /**
     * 通过反射去获取文件中记录的某行数据的id或者mark
     * @param data 文件中的某行数据（已转换成对象）
     * @param idOrMark 输入的id或mark属性标识名
     * @return 从记录中提取出的id或mark的值*/
    protected final String getDataIdOrMarkByClass(T data,String idOrMark){
        Field field;
        try{
            field = data.getClass().getDeclaredField(idOrMark);
            field.setAccessible(true);
        } catch (NoSuchFieldException e){
            e.printStackTrace();
            field = null;
        }

        if (field != null){
            try {
                Object object = field.get(data);
                if (object == null){
                    return null;
                }
                return field.get(data).toString();
            } catch (IllegalAccessException e){
                return null;
            }
        }

        return null;
    }

    /**
     * 抽取删除和更新的公共逻辑，不同操作通过传入lambda表达式实现
     * @param oldData 老的数据项
     * @param consumer 执行的不同操作
     * @throws  IOException 文件操作受检异常
     * @return 添加成功与否的标识*/
    protected final boolean doSomeThingWithDataList(T oldData,BiConsumer<ArrayList<String>,String> consumer) throws IOException{
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s;  //读出文件项
        while ((s = reader.readLine()) != null){
            list.add(s);
        }

        String mId = getDataIdOrMarkByClass(oldData,idPropertyName);

        boolean flag = false;

        for (String s1:list){
            String id = getDataIdOrMarkByJson(s1,idPropertyName);
            if (id != null && mId != null){
                if (id.equals(mId)){
                    flag = true;
                    consumer.accept(list,s1);
                    break;
                }
            }
        }

        if (!flag){
            return false;
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String s1: list){
            writer.write(s1);
            writer.newLine();
            writer.flush();
        }
        writer.close();
        return true;
    }

}


