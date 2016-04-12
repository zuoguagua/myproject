package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.SystemSecurityService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.security.SecurityPriv;
import com.inspur.ics.pojo.security.SecurityRole;
import com.inspur.ics.pojo.security.SecurityUser;

/**
 * SystemSecurityService 测试类.
 * @author ychau
 */
public class SystemSecurityServiceTest {
    /**
     * SystemSecurityService.
     */
    private static SystemSecurityService service;
    private static TaskService taskService;

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getSystemSecurityService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 获取所有权限列表.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getPrivAll() {
        List<SecurityPriv> list = service.getPrivAll();
        if(list != null) {
             AssertJUnit.assertNotNull("获取权限列表成功", list);
        } else {
        	AssertJUnit.assertNull("获取权限列表为空", list);
        }
        System.out.println(FormatUtil.toJson(list.get(0)));
    }

    /**
     * 根据privid唯一标示获取指定权限信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getPrivInfoByID() {
    	List<SecurityPriv> list = service.getPrivAll();
        int privid = 97;
        privid = list.get(list.size() / 2).getId();
        SecurityPriv priv = service.getPrivInfoByID(privid);
        if(priv != null) {
            AssertJUnit.assertNotNull("获取指定ID的权限成功", list);
       }
        System.out.println(FormatUtil.toJson(priv));
    }

    /**
     * 根据权限名称获取指定权限信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getPrivInfoByName() {
    	List<SecurityPriv> list = service.getPrivAll();
        String privname = "nfs_mount"; 
        privname = list.get(list.size() / 2).getName();
        SecurityPriv priv = service.getPrivInfoByName(privname);
        if(priv != null) {
            AssertJUnit.assertNotNull("获取指定名称的权限成功", list);
       }
        System.out.println(FormatUtil.toJson(priv));
    }

    /**
     * 创建新的角色同时分配相应的权限.
     * @throws Exception 
     */
    @Test(groups={"create"},alwaysRun=true)
    public void createRole() throws Exception {
        SecurityRole role = new SecurityRole();
        role.setName("sdkrole");
        role.setDescription("Ychau Wang Role Test Case.");
        role.setDisabled(true);
        role.setSysDefault(false);
        TaskIntermediateResult<?> taskResult = service.createRole(role);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 获取系统默认初始化角色信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getDefaultRoles() {
        List<SecurityRole> list = service.getDefaultRoles();
        AssertJUnit.assertNotNull("获取默认初始化角色信息成功",list);
        System.out.println(FormatUtil.toJson(list.get(0)));
    }

    /**
     * 获取系统内所有角色.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getRoleAll() {
        List<SecurityRole> list = service.getRoleAll();
        AssertJUnit.assertNotNull("获取系统所有角色信息成功",list);
        System.out.println(FormatUtil.toJson(list.get(1)));
    }

    /**
     * 根据角色唯一标示获取指定角色信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getRoleInfoByID() {
        int roleid = 1;
        SecurityRole role = service.getRoleInfoByID(roleid);
        AssertJUnit.assertNotNull(role);
        System.out.println(FormatUtil.toJson(role));
    }

    /**
     * 根据角色名称获取指定角色信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getRoleInfoByName() {
        String rolename = "sdkrole";
        SecurityRole role = service.getRoleInfoByName(rolename);
        AssertJUnit.assertNotNull(role);
        System.out.println(FormatUtil.toJson(role));
    }


    /**
     * 修改角色信息.
     * @throws Exception 
     */
    @Test(groups={"update"},alwaysRun=true)
    public void modifyRoleInfo() throws Exception {
        String rolename = "sdkrole";
        SecurityRole role = service.getRoleInfoByName(rolename);
        role.setDescription("new rolename");
        TaskIntermediateResult<?> taskResult = service.modifyRoleInfo(role);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        role = service.getRoleInfoByName(rolename);
        AssertJUnit.assertEquals("new rolename", role.getDescription());
        System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 根据角色唯一标示删除角色.
     * @throws Exception 
     */
    @Test(groups={"delete"},alwaysRun=true)
    public void deleteRole() throws Exception {
        int roleid = 3;
        roleid = service.getRoleInfoByName("sdkrole").getId();
        TaskIntermediateResult<?> taskResult = service.deleteRole(roleid);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 创建新的系统用户.
     * @throws Exception 
     */
    @Test(groups={"create"},dependsOnMethods={"createRole"},alwaysRun=true)
    public void createUser() throws Exception {
        SecurityUser user = new SecurityUser();
        user.setName("sdkuser");
        user.setPassword("123456");
        user.setRole(service.getRoleInfoByID(1));
        user.setSysDefault(false);
        user.setTelephone("1233333333");
        TaskIntermediateResult<?> taskResult = service.createUser(user);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 获取系统默认初始化用户信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getDefaultUsers() {
        List<SecurityUser> list = service.getDefaultUsers();
        AssertJUnit.assertNotNull(list);
//        System.out.println(FormatUtil.toJson(list.get(0)));
    }

    /**
     * 获取系统内所有用户信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getUserAll() {
        List<SecurityUser> list = service.getUserAll();
        AssertJUnit.assertNotNull(list);
//        System.out.println(FormatUtil.toJson(list.get(0)));
    }

    /**
     * 根据用户ID获取用户信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getUserInfoByID() {
        int userid = 12;
        userid = service.getUserInfoByName("sdkuser").getId();
        SecurityUser user = service.getUserInfoByID(userid);
        AssertJUnit.assertNotNull(user);
//        System.out.println(FormatUtil.toJson(user));
    }

    /**
     * 根据用户名称获取用户信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getUserInfoByName() {
        String username = "sdkuser";
        SecurityUser user = service.getUserInfoByName(username);
        AssertJUnit.assertNotNull(user);
//        System.out.println(FormatUtil.toJson(user));
    }

    /**
     * 修改给定用户信息.
     * @throws Exception 
     */
    @Test(groups={"update"},alwaysRun=true)
    public void modifyUserInfo() throws Exception {
    	String username = "sdkuser";
        SecurityUser user = service.getUserInfoByName(username);
        user.setTelephone("123456789");
        TaskIntermediateResult<?> taskResult = service.modifyUserInfo(user);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        user = service.getUserInfoByName(username);
        AssertJUnit.assertEquals("123456789", user.getTelephone());
//        System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 根据用户ID删除指定用户.
     * @throws Exception 
     */
    @Test(groups={"delete"},alwaysRun=true)
    public void deleteUser() throws Exception {
        int userid = 12;
        userid = service.getUserInfoByName("sdkuser").getId();
        TaskIntermediateResult<?> taskResult = service.deleteUser(userid);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 用户登录接口.
     */
//    @Test(groups={"query"})
//    public void userLogin() {
//        String token = service.userLogin("admin", "ivirtual@inspur", "zh");
//        System.out.println("The Valid token" + token);
//    }

    /**
     * 用户退出系统接口.
     */
//    @Test
//    public void userLogout() {
//        TaskIntermediateResult<?> taskResult = service.userLogout();
//        if (taskResult == null) {
//            System.out.println("用户成功退出。");
//        }
//    }

    /**
     * 检查操作用户是否具有指定权限.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void checkAuthorityofUser() {
        String privname = "nfs_umount";
        Boolean result = service.checkAuthorityofUser(privname);
        Boolean is = true;
        AssertJUnit.assertEquals(is, result);
    }
}
