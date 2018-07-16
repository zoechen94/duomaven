package com.wx.controller;

import com.util.spring.resultInfo.ResultData;
import com.zoe.entity.SysPermission;
import com.zoe.service.SysPermissionService;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
@RequestMapping("/permission")
@RestController
public class PermissionController {
    private static final Logger logger=LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private SysPermissionService sysPermissionService;

    @RequiresPermissions("delete-user")
    @GetMapping
    @Cacheable(value="thisRedis", key="'permission_123'")
    public ResultData getAllPermission(){
        logger.info("getAll-permissions查询数据库");
        List<SysPermission> result=sysPermissionService.selectAll();
        return ResultData.success(result);
    }

    @RequiresRoles(value = {"管理员","超级管理员"},logical = Logical.OR)
    @Cacheable(value="thisRedis", key="'permission_123'+#id")
    @GetMapping(value = "/getOne/{id}")
    @ApiImplicitParam(name = "id",value ="数据库id",dataType = "Long",paramType = "path")
    public ResultData getOne(@PathVariable Long id){
        logger.info("getOne-查询数据库");
        return ResultData.success(sysPermissionService.findOne(id));
    }
}
