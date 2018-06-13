package com.zoe.spring.resultInfo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Value;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
@Data
public class CompanyInfo {
    @SerializedName(value = "companyId")
    private String id;

    @SerializedName(value = "companyName")
    private String name;
    private String title;

    @SerializedName(value = "timestamp")
    private String Timestamp;
    private String url;
    private String content;
}
