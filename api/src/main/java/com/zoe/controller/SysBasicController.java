package com.zoe.controller;

import com.zoe.entity.SysPermission;
import com.zoe.entity.SysRole;
import com.zoe.entity.SysRolePermission;
import com.zoe.entity.SysUserRole;
import com.zoe.service.*;
import com.util.spring.resultInfo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@RestController
@RequestMapping("/sys")
public class SysBasicController {
    @Autowired
    SysPermissionService sysPermissionService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysRolePermissionService sysRolePermissionService;
    @Autowired
    SysUserRoleService userRoleService;
    @Autowired
    SysUserService sysUserService;

    @ApiIgnore
    @PostMapping("/permission/insertBatch")
    @ApiOperation("权限-批量添加")
    public ResultData insertBatch(){
        List list=new ArrayList();
        list.add(new SysPermission("测试1","test1","/xx/test1"));
        list.add(new SysPermission("测试2","test2","/xx/test2"));
        list.add(new SysPermission("测试3","test3","/xx/test3"));
        list.add(new SysPermission("测试4","test4","/xx/test4"));
        list.add(new SysPermission("测试5","test5","/xx/test5"));
        list.add(new SysPermission("测试6","test6","/xx/test6"));
        list.add(new SysPermission("测试7","test7","/xx/test7"));
        list.add(new SysPermission("测试8","test8","/xx/test8"));
        list.add(new SysPermission("测试9","test9","/xx/test9"));
        list.add(new SysPermission("测试10","test10","/xx/test10"));
        return ResultData.success(sysPermissionService.insertBatch(list));
    }

    @ApiIgnore
    @ApiOperation("角色-批量添加")
    @PostMapping("/role/insertBatch")
    public ResultData insertRoleBatch(){
        List<SysRole> list=new ArrayList<>();
        list.add(new SysRole(1,"超级管理员"));
        list.add(new SysRole(2,"管理员"));
        list.add(new SysRole(3,"普通用户"));
        return ResultData.success(sysRoleService.insertBatch(list));
    }

    @ApiIgnore
    @ApiOperation("角色权限表-批量添加")
    @PostMapping("/rolePermission/insertBatch")
    public ResultData insertRolePermissionBatch(){
        List<SysRolePermission> list=new ArrayList<>();
        list.add(new SysRolePermission(1L,1L));
        list.add(new SysRolePermission(1L,2L));
        list.add(new SysRolePermission(1L,3L));
        list.add(new SysRolePermission(1L,4L));
        list.add(new SysRolePermission(1L,5L));
        list.add(new SysRolePermission(1L,6L));
        list.add(new SysRolePermission(1L,7L));
        list.add(new SysRolePermission(1L,8L));
        list.add(new SysRolePermission(1L,9L));
        list.add(new SysRolePermission(1L,10L));

        //管理员
        list.add(new SysRolePermission(2L,2L));
        list.add(new SysRolePermission(2L,3L));
        list.add(new SysRolePermission(2L,4L));
        list.add(new SysRolePermission(2L,5L));
        list.add(new SysRolePermission(2L,6L));
        list.add(new SysRolePermission(2L,7L));
        list.add(new SysRolePermission(2L,8L));
        list.add(new SysRolePermission(2L,9L));
        list.add(new SysRolePermission(2L,20L));

        //普通用户
        list.add(new SysRolePermission(3L,3L));
        list.add(new SysRolePermission(3L,3L));
        list.add(new SysRolePermission(3L,4L));
        list.add(new SysRolePermission(3L,5L));
        list.add(new SysRolePermission(3L,6L));
        list.add(new SysRolePermission(3L,7L));
        list.add(new SysRolePermission(3L,8L));
        list.add(new SysRolePermission(3L,9L));
        list.add(new SysRolePermission(3L,30L));
        return ResultData.success(sysRolePermissionService.insertBatch(list));
    }

    @ApiIgnore
    @ApiOperation("用户角色表-批量添加")
    @PostMapping("/userRole/insertBatch")
    public ResultData userRoleInsertBatch(){
        List<SysUserRole> list=new ArrayList<>();
        list.add(new SysUserRole(1L,1l));
        list.add(new SysUserRole(2L,2L));
        list.add(new SysUserRole(3L,3L));
        return ResultData.success(userRoleService.insertBatch(list));
    }

    @ApiOperation("根据账户名查询")
    @GetMapping("/findByAccount")
    @RequiresUser
    @ApiImplicitParam(name = "account",value = "账户名",dataType = "String",paramType = "query")
    public ResultData findByAccount(String account){
        return ResultData.success(sysUserService.selectByAccount(account));
    }


    @RequiresRoles(value = {"管理员","超级管-理员"},logical = Logical.OR)
    @RequiresUser
    @ApiOperation("查询所有用户")
    @GetMapping("/selectAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",defaultValue = "1",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "size",value = "每页个数",defaultValue = "10",dataType = "Integer",paramType = "query")
    })
    public ResultData selectAll(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size){
        return ResultData.success(sysUserService.getAll(page,size));
    }
}
