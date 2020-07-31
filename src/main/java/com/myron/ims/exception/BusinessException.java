package com.myron.ims.exception;


import com.myron.ims.constants.MessageEnum;

/**
 * Created by linrx on 2018/11/13.
 */
public class BusinessException extends Exception{
    private MessageEnum messageEnum;
    private String[] variable;

    public BusinessException(MessageEnum messageEnum) {
        super(messageEnum.getSimpleInfo());
        this.messageEnum = messageEnum;
    }
    public BusinessException(MessageEnum messageEnum, String... variable) {
        super(messageEnum.getSimpleInfo() + ":" + variable.toString());
        this.messageEnum = messageEnum;
        this.variable = variable;
    }

    public BusinessException(MessageEnum messageEnum, Throwable cause) {
        super(messageEnum.getSimpleInfo(), cause);
        this.messageEnum = messageEnum;
    }

    public MessageEnum getMessageEnum() {
        return messageEnum;
    }

    public String[] getVariable() {
        return variable;
    }
}
