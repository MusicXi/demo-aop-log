package com.myron.ims.bean;

import com.github.pagehelper.Page;
import lombok.Data;

/**
 * @author myron
 * @date 2018/6/7.
 */
@Data
public class ApiResult<T> {
    /**消息提示*/
    private String message;
    /**状态码*/
    private Integer code;
    /**内容数据*/
    private T data;

    public ApiResult() {
        this(null);
    }
    public ApiResult(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;

    }

    /**
     * 包装pagehelper分页对象
     * @param page
     * @return
     */
    public static ApiResult wrapPage(Page page) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setRows(page);
        pageResult.setTotal(page.getTotal());
        ApiResult<PageResult>  apiResult = new ApiResult<>();
        apiResult.setData(pageResult);
        return apiResult;
    }


}
