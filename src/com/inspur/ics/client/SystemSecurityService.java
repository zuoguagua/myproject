package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.security.SecurityPriv;
import com.inspur.ics.pojo.security.SecurityRole;
import com.inspur.ics.pojo.security.SecurityUser;

/**
 * 系统安全接口.
 * @author ychau
 */
public interface SystemSecurityService {

    /**
     * 获取所有权限列表.
     * @return 所有权限List列表
     * @throws
     */
    List<SecurityPriv> getPrivAll();

    /**
     * 根据privid唯一标示获取指定权限信息.
     * @param privid 权限唯一标示
     * @return 指定privid权限的相关信息
     * @throws
     */
    SecurityPriv getPrivInfoByID(int privid);

    /**
     * 根据权限名称获取指定权限信息.
     * @param privname 权限名称
     * @return 指定名为name权限的相关信息
     * @throws
     */
    SecurityPriv getPrivInfoByName(String privname);

    /**
     * 创建新的角色同时分配相应的权限.
     * @param role 新的角色信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createRole(SecurityRole role);

    /**
     * 获取系统默认初始化角色信息.
     * @return 系统默认初始化角色(admin, read only)
     * @throws
     */
    List<SecurityRole> getDefaultRoles();

    /**
     * 获取系统内所有角色.
     * @return 角色列表
     * @throws
     */
    List<SecurityRole> getRoleAll();

    /**
     * 根据角色唯一标示获取指定角色信息.
     * @param roleid 角色唯一标示
     * @return 指定角色信息
     * @throws
     */
    SecurityRole getRoleInfoByID(int roleid);

    /**
     * 根据角色名称获取指定角色信息.
     * @param rolename 指定角色名称
     * @return 指定角色信息
     * @throws
     */
    SecurityRole getRoleInfoByName(String rolename);

    /**
     * 根据角色唯一标示删除角色.
     * @param roleid 指定用户唯一性标示
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult deleteRole(int roleid);

    /**
     * 修改角色信息.
     * @param role 被删除的角色
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult modifyRoleInfo(SecurityRole role);

    /**
     * 创建新的系统用户.
     * @param user 新建的用户信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createUser(SecurityUser user);

    /**
     * 获取系统默认初始化用户信息.
     * @return 系统默认初始化用户列表
     * @throws
     */
    List<SecurityUser> getDefaultUsers();

    /**
     * 获取系统内所有用户信息.
     * @return 系统用户列表
     * @throws
     */
    List<SecurityUser> getUserAll();

    /**
     * 根据用户ID获取用户信息.
     * @param userid 用户ID
     * @return 用户信息
     * @throws
     */
    SecurityUser getUserInfoByID(int userid);

    /**
     * 根据用户名称获取用户信息.
     * @param username 指定用户名称
     * @return 用户信息
     * @throws
     */
    SecurityUser getUserInfoByName(String username);

    /**
     * 根据用户ID删除指定用户.
     * @param userid 用户ID
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult deleteUser(int userid);

    /**
     * 修改给定用户信息.
     * @param user 给定用户信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult modifyUserInfo(SecurityUser user);

    /**
     * 用户登录接口.
     * @param username 用户名
     * @param passwd 用户密码
     * @param userLocale 本地语言集
     * @return 用户登录成功后产生的会话令牌
     */
    String userLogin(String username, String passwd, String userLocale);

    /**
     * 用户退出系统接口.
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult userLogout();

    /**
     * 检查操作用户是否具有指定权限.
     * @param privname 指定权限名称
     * @return 返回判断后结果, 是为true, 否为false
     */
    Boolean checkAuthorityofUser(String privname);

}
