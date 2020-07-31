package com.myron.ims.constants;

public enum MessageEnum {
    /** 测试错误*/
    HELLO_ERROR(500),
    /** 用户使用中*/
    BUSINESS_USER_IN_USE(500);

    private Integer code;

    MessageEnum(Integer code) {
        this.code = code;
    }

    public String getSimpleInfo() {
        return this.name() + "_" +  this.code ;
    }

    public Integer getCode() {
        return code;
    }

    public static void main(String[] args) {
        System.out.println(MessageEnum.HELLO_ERROR.getSimpleInfo());
    }

}
