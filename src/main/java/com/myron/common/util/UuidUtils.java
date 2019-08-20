package com.myron.common.util;

import java.util.UUID;
/**
 * UUID 工具类
 * @author lin
 *
 */
public class UuidUtils {

    public static String creatUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }	
    
     public static void main(String[] args) {
		System.out.println(UuidUtils.creatUUID());
	}
}
