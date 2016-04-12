package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 系统安全REST接口.
 * @author ychau
 *
 */
@Path("/")
public interface SystemSecurityRestService {
    /**
     * 获取所有权限列表.
     * @param token 会话令牌
     * @return 所有权限List列表
     */
    @POST
    @Path("system_security_getPrivAll")
    String getPrivAll(@FormParam("token") String token);

    /**
     * 根据privid唯一标示获取指定权限信息.
     * @param token 会话令牌
     * @param privid 权限唯一标示
     * @return 指定privid权限的相关信息
     */
    @POST
    @Path("system_security_getPrivInfoById")
    String getPrivInfoByID(@FormParam("token") String token, @FormParam("privid") int privid);

    /**
     * 根据权限名称获取指定权限信息.
     * @param token 会话令牌
     * @param privname 权限名称
     * @return 指定名为name权限的相关信息
     */
    @POST
    @Path("system_security_getPrivInfoByInfo")
    String getPrivInfoByName(@FormParam("token") String token, @FormParam("privname") String privname);

    /**
     * 创建新的角色同时分配相应的权限.
     * @param token 操作用户会话令牌
     * @param role 新的角色信息
     * @return 返回任务信息
     */
    @POST
    @Path("system_security_createRole")
    String createRole(@FormParam("token") String token, @FormParam("role") String role);

    /**
     * 获取系统默认初始化角色信息.
     * @param token 操作用户会话令牌
     * @return 系统默认初始化角色(admin, read only)
     */
    @POST
    @Path("system_security_getDefaultRoles")
    String getDefaultRoles(@FormParam("token") String token);

    /**
     * 获取系统内所有角色.
     * @param token 操作用户会话令牌
     * @return 角色列表
     */
    @POST
    @Path("system_security_getRoleAll")
    String getRoleAll(@FormParam("token") String token);

    /**
     * 根据角色唯一标示获取指定角色信息.
     * @param token 操作用户会话令牌
     * @param roleid 角色唯一标示
     * @return 指定角色信息
     */
    @POST
    @Path("system_security_getRoleInfoById")
    String getRoleInfoByID(@FormParam("token") String token, @FormParam("roleid") int roleid);

    /**
     * 根据角色名称获取指定角色信息.
     * @param token 操作用户会话令牌
     * @param rolename 指定角色名称
     * @return 指定角色信息
     */
    @POST
    @Path("system_security_getRoleInfoByName")
    String getRoleInfoByName(@FormParam("token") String token, @FormParam("rolename") String rolename);

    /**
     * 根据角色唯一标示删除角色.
     * @param token 操作用户会话令牌
     * @param roleid 指定用户唯一性标示
     * @return 返回任务信息
     */
    @POST
    @Path("system_security_deleteRole")
    String deleteRole(@FormParam("token") String token, @FormParam("roleid") int roleid);

    /**
     * 修改角色信息.
     * @param token 操作用户会话令牌
     * @param role 被删除的角色
     * @return 返回任务信息
     */
    @POST
    @Path("system_security_modifyRoleInfo")
    String modifyRoleInfo(@FormParam("token") String token, @FormParam("role") String role);

    /**
     * 创建新的系统用户.
     * @param token 操作用户会话令牌
     * @param user 新建的用户信息
     * @return 返回任务信息
     */
    @POST
    @Path("system_security_createUser")
    String createUser(@FormParam("token") String token, @FormParam("user") String user);

    /**
     * 获取系统默认初始化用户信息.
     * @param token 操作用户会话令牌
     * @return 系统默认初始化用户列表
     */
    @POST
    @Path("system_security_getDefaultUsers")
    String getDefaultUsers(@FormParam("token") String token);

    /**
     * 获取系统内所有用户信息.
     * @param token 操作用户会话令牌
     * @return 系统用户列表
     */
    @POST
    @Path("system_security_getUserAll")
    String getUserAll(@FormParam("token") String token);

    /**
     * 根据用户ID获取用户信息.
     * @param token 操作用户会话令牌
     * @param userid 用户ID
     * @return 用户信息
     */
    @POST
    @Path("system_security_getUserInfoById")
    String getUserInfoByID(@FormParam("token") String token, @FormParam("userid") int userid);

    /**
     * 根据用户名称获取用户信息.
     * @param token 操作用户会话令牌
     * @param username 指定用户名称
     * @return 用户信息
     */
    @POST
    @Path("system_security_getUserInfoByName")
    String getUserInfoByName(@FormParam("token") String token, @FormParam("username") String username);

    /**
     * 根据用户ID删除指定用户.
     * @param token 操作用户会话令牌
     * @param userid 用户ID
     * @return 返回任务信息
     * @throws
     */
    @POST
    @Path("system_security_deleteUser")
    String deleteUser(@FormParam("token") String token, @FormParam("userid") int userid);

    /**
     * 修改给定用户信息.
     * @param token 操作用户会话令牌
     * @param user 给定用户信息
     * @return 返回任务信息
     * @throws
     */
    @POST
    @Path("system_security_modifyUserInfo")
    String modifyUserInfo(@FormParam("token") String token, @FormParam("user") String user);

    /**
     * 用户登录接口.
     * @param username 用户名
     * @param passwd 用户密码
     * @param userLocale 本地语言集
     * @return 用户登录成功后产生的会话令牌
     */
    @POST
    @Path("system_security_userLogin")
    String userLogin(@FormParam("username") String username,
                        @FormParam("passwd") String passwd,
                        @FormParam("userLocale") String userLocale);

    /**
     * 用户退出系统接口.
     * @param token 操作用户会话令牌
     * @return 操作结果
     */
    @POST
    @Path("system_security_userLogout")
    String userLogout(@FormParam("token") String token);

    /**
     * 检查操作用户是否具有指定权限.
     * @param token 操作用户会话令牌
     * @param privname 指定权限名称
     * @return 返回判断后结果, 是为true, 否为false
     */
    @POST
    @Path("system_security_checkAuthorityofUser")
    String checkAuthorityofUser(@FormParam("token") String token, @FormParam("privname") String privname);
}
