package com.yumetsuki.base;

/**
 * 储存常量的类
 * @author 王小伟
 * @version 1.0*/
public class Value {
    public static final double DEDUCTIBLE_STANDARD = 100; //起付标准

    public static final int[] FIRST_RANGE = {100,10000};  //第一阶段

    public static final int[] SECOND_RANGE = {10001,20000};  //第二阶段

    public static final int[] THIRD_RANGE = {20001,999999999};  //第三阶段

    public static final double FIRST_PROPORTION = 0.2;  //第一阶段自费比例

    public static final double SECOND_PROPORTION = 0.1;  //第二阶段自费比例

    public static final double THIRD_PROPORTION = 0.05;  //第三阶段自费比例

    public static final String EMPLOYEES_IN_SERVER = "在职职工";

    public static final String RETIREES = "退休人员";

    public static final String INCUMBENTS_WITH_MINIMUM_SECURITY = "享受最低保障的在职人员";

    public static final String RETIREES_WITH_MINIMUM_SECURITY = "享受最低保障的退休人员";

    public enum Sex{
        MALE("男"),FEMALE("女");

        private String name;
        Sex(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
