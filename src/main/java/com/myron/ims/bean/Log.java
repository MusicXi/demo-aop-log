package com.myron.ims.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * sys_log 日志表
 * @author myron
 * @date 2019/08/29 16:21:40
 */
@TableName("sys_log")
@Data
@Builder
@AllArgsConstructor
public class Log implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 日志主键*/
    @ApiModelProperty(value="日志主键")
    @TableId(type = IdType.UUID)
    private String logId;


    /** 日志类型*/
    @ApiModelProperty(value="日志类型")
    private String type;

    /** 日志标题*/
    @ApiModelProperty(value="日志标题")
    private String title;

    /** 请求IP*/
    @ApiModelProperty(value="请求IP")
    private String ip;

    /** URI*/
    @ApiModelProperty(value="URI")
    private String requestUri;

    /** 请求方式*/
    @ApiModelProperty(value="请求方式")
    private String method;

    /** 提交参数*/
    @ApiModelProperty(value="提交参数")
    private String params;

    /** 异常*/
    @ApiModelProperty(value="异常")
    private String exception;

    /** 操作时间*/
    @ApiModelProperty(value="操作时间")
    private Date operateDate;

    /** 请求时长*/
    @ApiModelProperty(value="请求时长")
    private String timeout;

    /** 用户登入名*/
    @ApiModelProperty(value="用户登入名")
    private String loginName;

    /** requestID*/
    @ApiModelProperty(value="requestID")
    private String requestId;

    /** 历史数据*/
    @ApiModelProperty(value="历史数据")
    private String dataSnapshot;

    /** 请求时间戳*/
    @ApiModelProperty(value="请求时间戳")
    private Long requestTimestamp;

    /** 日志状态*/
    @ApiModelProperty(value="日志状态")
    private Integer status;


    public Log(){
        super();
    }



    @Override
    public String toString() {
        return "Log ["
                + "logId = " + logId
                + ", type = " + type
                + ", title = " + title
                + ", ip = " + ip
                + ", requestUri = " + requestUri
                + ", method = " + method
                + ", params = " + params
                + ", exception = " + exception
                + ", operateDate = " + operateDate
                + ", timeout = " + timeout
                + ", loginName = " + loginName
                + ", requestId = " + requestId
                + ", dataSnapshot = " + dataSnapshot
                + ", requestTimestamp = " + requestTimestamp
                + ", status = " + status
                + "]";
    }

}