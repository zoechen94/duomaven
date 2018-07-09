package com.wx.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by ChenYaLan on 2018/7/9
 * 微信的后台登陆访问返回json-对象
 **/
@Data
public class WXJSON {
    private String openid;
    @SerializedName(value = "session_key")
    private String sessionKey;
    @SerializedName(value = "expires_in")
    private String expireIn;
//    @SerializedName(value = "unionid")
//    private String unionID;
}
