package com.zoe.entity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@RedisHash(value = "accountToken")
public class UserToken implements Serializable {
    private static final long serialVersionUID = -4725473990960505275L;
    @Id
    private String account;
    private String token;
    private Set<String> permissions;
    private Date expireDate;
    public UserToken(String account,String token,Set<String> permissions,Date expireDate){
        this.account=account;
        this.token=token;
        this.permissions=permissions;
        this.expireDate=expireDate;
    }
    public UserToken(){}
}
