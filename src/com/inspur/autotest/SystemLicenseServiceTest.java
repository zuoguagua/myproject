package com.inspur.autotest;

import org.testng.annotations.Test;

import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.SystemLicenseService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.License;
import com.inspur.ics.pojo.TaskInfo;

/**
 * SystemLicenseService 测试类.
 * @author ychau
 */
public class SystemLicenseServiceTest {
    /**
     * SystemLicenseService.
     */
    private static SystemLicenseService service;
    private static TaskService taskService;

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getSystemLicenseService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 获取许可证相关信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getLicense() {
        List<License> license = service.getLicense();
        AssertJUnit.assertNotNull(license);
//        System.out.println(FormatUtil.toJson(license));
    }

    /**
     * 添加许可证.
     * @throws Exception 
     */
    @Test(groups={"create"},alwaysRun=true)
    public void addLicence() throws Exception {
    	License license = new License();
        license.setContent("vafche6-vafche6-55742913976556-5556555309317747");
        TaskIntermediateResult<?> taskResult = service.addLicense(license);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("任务ID：" + taskResult.getTaskId());
    }
    /**
     * 导入或更新许可证书.
     * @throws Exception 
     */
    @Test(groups={"delete"},alwaysRun=true)
    public void removeLicence() throws Exception {
        License license = new License();
        license.setContent("UTW-Vafche-55742913976555-5581055872070445");
        TaskIntermediateResult<?> taskResult = service.removeLicense(license);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.ERROR, taskInfo.getState());
//        System.out.println("任务ID：" + taskResult.getTaskId());
    }
}
