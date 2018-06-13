package com.zoe.spring.resultInfo;

import lombok.Data;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
@Data
public class HttpDO {
    private String code;
    private String msg;
    private boolean success;
    private List<CompanyInfo> data;
}
