package com.zoe.spring.test;

import lombok.Data;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
@Data
public class ObjectInfo {
    private String status;
    private List<PatentInfo> result;
    private String info;
}
