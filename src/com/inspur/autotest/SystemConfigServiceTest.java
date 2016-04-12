package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.AssertJUnit;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.SystemConfigService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.SystemConfig;
import com.inspur.ics.pojo.TaskInfo;
/**
 * SystemConfigService 测试类.
 * @author ychau
 */
public class SystemConfigServiceTest {
    /**
     * SystemConfigService.
     */
    private static SystemConfigService service;
    private static TaskService taskService;


    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getSystemConfigService();
        taskService = factory.getTaskService();
//        System.out.println("Init Junit Test");
    }

    /**
     * 获取系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getSystemConfigInfo() {
        SystemConfig config = service.getSystemConfigInfo();
        AssertJUnit.assertNotNull(config);
//        System.out.println(FormatUtil.toJson(config));
    }

    /**
     * 设置系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @throws Exception 
     */
    @Test(groups={"create"},alwaysRun=true)
    public void setSystemConfigInfo() throws Exception {
        SystemConfig config = service.getSystemConfigInfo();
        config.setLogRetainSet(12);
        TaskIntermediateResult<?> taskResult = service.setSystemConfigInfo(config);
	    TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        config = service.getSystemConfigInfo();
        AssertJUnit.assertEquals(12, config.getLogRetainSet());
//        System.out.println("任务ID：" + taskResult.getTaskId());
//        System.out.println(FormatUtil.toJson(service.getSystemConfigInfo()));
    }

    /**
     * 设置icenter管理节点NTP服务器 .
     * @throws Exception 
     */
    @Test(groups={"create"},alwaysRun=true)
    public void setNtpServers() throws Exception {
        String[] ntpServers = {"localhost",
                "1.centos.pool.ntp.org",
                "2.centos.pool.ntp.org",
                "3.centos.pool.ntp.org"};
        TaskIntermediateResult<?> taskResult = service.setNtpServers(ntpServers);
//        System.out.println("任务ID：" + taskResult.getTaskId());
	    TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 获取icenter管理节点NTP服务器 .
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getNtpServers() {
        List<String> ntpServers = service.getNtpServers();
        AssertJUnit.assertNotNull(ntpServers);
        AssertJUnit.assertEquals("127.127.1.0", ntpServers.get(0));
//        System.out.println("NTP Servers json format: \r\n    "
//                + FormatUtil.toJson(ntpServers));
//        System.out.println("Count of NTP Servers:" + ntpServers.size());
//        System.out.println("The first ntp server: " + ntpServers.get(0));
    }
    /**
     * 获取系统版本.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getIcsVersionTest() {
        String icsVersion = service.getIcsVersion();
        System.out.println(icsVersion);
        AssertJUnit.assertEquals("InCloud Sphere v4.0.1(β)", icsVersion);
    }

}
