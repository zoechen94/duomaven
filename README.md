# duomaven
maven多模块项目
主要是搭建基础框架，目前还没有完成权限认证。
6.20 shiro框架搭建完毕

注意务必先把file下ojdbc解压放在你的maven库中的com下

shiro注解
//属于user角色
@RequiresRoles("user")

//必须同时属于user和admin角色
@RequiresRoles({"user","admin"})

//属于user或者admin之一;修改logical为OR 即可
@RequiresRoles(value={"user","admin"},logical=Logical.OR)


//符合index:hello权限要求
@RequiresPermissions("index:hello")
 
//必须同时复核index:hello和index:world权限要求
@RequiresPermissions({"index:hello","index:world"})
 
//符合index:hello或index:world权限要求即可
@RequiresPermissions(value={"index:hello","index:world"},logical=Logical.OR)