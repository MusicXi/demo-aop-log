package com.myron.ims.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="分页对象",description="存放分页请求参数及分页结果信息")
public class PageResult<T> {
    /** 页码*/
    @ApiModelProperty(value="页码", required=true, example = "1")
    private int pageNum;
    /** 分页大小*/
    @ApiModelProperty(value="分页大小", required=true, example = "10")
    private int pageSize;
    /** 总数*/
    @ApiModelProperty(value="总数")
    private long total;
    /** 分页结果数据*/
    @ApiModelProperty(value="分页结果数据")
    private T rows;


}
