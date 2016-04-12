package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.SystemSecurityService;
import com.inspur.ics.client.rest.SystemSecurityRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.security.SecurityPriv;
import com.inspur.ics.pojo.security.SecurityRole;
import com.inspur.ics.pojo.security.SecurityUser;

/**
 * 系统安全接口实现类.
 * @author ychau
 *
 */
public class SystemSecurityServiceImpl implements SystemSecurityService {

    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 系统安全REST接口服务类.
     */
    private SystemSecurityRestService restService;

    /**
     * 系统安全接口实现类构造方法.
     * @param url 请求Action路径
     * @param token 用户会话令牌.
     */
    public SystemSecurityServiceImpl(String url, String token) {
        this.restService = ProxyFactory.create(SystemSecurityRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取所有权限列表.
     * @return 所有权限List列表
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityPriv> getPrivAll() {
        String resultXML = restService.getPrivAll(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, SecurityPriv.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<SecurityPriv>();
        }  else {
            return (List<SecurityPriv>) result.getData();
        }
    }

    /**
     * 根据privid唯一标示获取指定权限信息.
     * @param privid 权限唯一标示
     * @return 指定privid权限的相关信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityPriv getPrivInfoByID(int privid) {
        String resultXML = restService.getPrivInfoByID(token, privid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, SecurityPriv.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SecurityPriv>) result.getData()).get(0);
        }
    }

    /**
     * 根据权限名称获取指定权限信息.
     * @param privname 权限名称
     * @return 指定名为name权限的相关信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityPriv getPrivInfoByName(String privname) {
        if (privname == null || privname.isEmpty()) {
            throw new RemoteException("The name of privilege cannot be null or empty.");
        }

        String resultXML = restService.getPrivInfoByName(token, privname);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, SecurityPriv.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SecurityPriv>) result.getData()).get(0);
        }
    }

    /**
     * 创建新的角色同时分配相应的权限.
     * @param role 新的角色信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult createRole(SecurityRole role) {
        String resultStr = restService.createRole(token, FormatUtil.toJson(role));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 获取系统默认初始化角色信息.
     * @return 系统默认初始化角色(admin, read only)
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityRole> getDefaultRoles() {
        String resultXML = restService.getDefaultRoles(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityRole.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<SecurityRole>();
        }  else {
            return (List<SecurityRole>) result.getData();
        }
    }

    /**
     * 获取系统内所有角色.
     * @return 角色列表
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityRole> getRoleAll() {
        String resultXML = restService.getRoleAll(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityRole.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<SecurityRole>();
        }  else {
            return (List<SecurityRole>) result.getData();
        }
    }

    /**
     * 根据角色唯一标示获取指定角色信息.
     * @param roleid 角色唯一标示
     * @return 指定角色信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityRole getRoleInfoByID(int roleid) {
        String resultXML = restService.getRoleInfoByID(token, roleid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityRole.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new SecurityRole();
        }  else {
            return ((List<SecurityRole>) result.getData()).get(0);
        }
    }

    /**
     * 根据角色名称获取指定角色信息.
     * @param rolename 指定角色名称
     * @return 指定角色信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityRole getRoleInfoByName(String rolename) {
        String resultXML = restService.getRoleInfoByName(token, rolename);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityRole.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new SecurityRole();
        }  else {
            return ((List<SecurityRole>) result.getData()).get(0);
        }
    }

    /**
     * 根据角色唯一标示删除角色.
     * @param roleid 指定用户唯一性标示
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult deleteRole(int roleid) {
        String resultStr = restService.deleteRole(token, roleid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 修改角色信息.
     * @param role 被删除的角色
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult modifyRoleInfo(SecurityRole role) {
       String resultStr = restService.modifyRoleInfo(token, FormatUtil.toJson(role));
       Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
               Result.class, TaskIntermediateResult.class });

       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    /**
     * 创建新的系统用户.
     * @param user 新建的用户信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult createUser(SecurityUser user) {
        String resultStr = restService.createUser(token, FormatUtil.toJson(user));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 获取系统默认初始化用户信息.
     * @return 系统默认初始化用户列表
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityUser> getDefaultUsers() {
        String resultXML = restService.getDefaultUsers(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityUser.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<SecurityUser>();
        }  else {
            return (List<SecurityUser>) result.getData();
        }
    }

    /**
     * 获取系统内所有用户信息.
     * @return 系统用户列表
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecurityUser> getUserAll() {
        String resultXML = restService.getUserAll(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityUser.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<SecurityUser>();
        }  else {
            return (List<SecurityUser>) result.getData();
        }
    }

    /**
     * 根据用户ID获取用户信息.
     * @param userid 用户ID
     * @return 用户信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityUser getUserInfoByID(int userid) {
        String resultXML = restService.getUserInfoByID(token, userid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityUser.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SecurityUser>) result.getData()).get(0);
        }
    }

    /**
     * 根据用户名称获取用户信息.
     * @param username 指定用户名称
     * @return 用户信息
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public SecurityUser getUserInfoByName(String username) {
        if (username == null || username.isEmpty()) {
            throw new RemoteException("The name of privilege cannot be null or empty.");
        }

        String resultXML = restService.getUserInfoByName(token, username);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{
                Result.class, SecurityUser.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SecurityUser>) result.getData()).get(0);
        }
    }

    /**
     * 根据用户ID删除指定用户.
     * @param userid 用户ID
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult deleteUser(int userid) {
        String resultStr = restService.deleteUser(token, userid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 修改给定用户信息.
     * @param user 给定用户信息
     * @return 返回任务信息
     * @throws
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult modifyUserInfo(SecurityUser user) {
        String userJson = FormatUtil.toJson(user);
        String resultStr = restService.modifyUserInfo(token, userJson);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 用户登录接口.
     * @param username 用户名
     * @param passwd 用户密码
     * @param userLocale 本地语言集
     * @return 用户登录成功后产生的会话令牌
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String userLogin(String username, String passwd, String userLocale) {
        String resultStr = restService.userLogin(username, passwd, userLocale);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, String.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return null;
        } else {
            return (String) ((List) result.getData()).get(0);
        }
    }

    /**
     * 用户退出系统接口.
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult userLogout() {
        String resultStr = restService.userLogout(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 检查操作用户是否具有指定权限.
     * @param privname 指定权限名称
     * @return 返回判断后结果, 是为true, 否为false
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Boolean checkAuthorityofUser(String privname) {
        String resultStr = restService.checkAuthorityofUser(token, privname);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, Boolean.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (Boolean) ((List) result.getData()).get(0);
        }
    }

}
