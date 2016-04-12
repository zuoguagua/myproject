package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VCTemplateService;
import com.inspur.ics.client.VClusterService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.VirtualDeviceConfigFileOperation;
import com.inspur.ics.common.Types.VirtualDeviceConfigOperation;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.ISOFile;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VCluster;
import com.inspur.ics.pojo.VClusterTemplate;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMConfigInfo;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VirtualCdrom;
import com.inspur.ics.pojo.VirtualDisk;

/**
 * VClusterRest测试.
 * @author zuolanhai
 */
public class VClusterServiceTest {
    /**
     * VCluster service.
     */
    private static VClusterService vclusterService;
    /**
    *
    */
    private static VCTemplateService vcTemplateClient;
    private static TaskService taskService;
    private static String vclusterUUID = null;
    private static String vctUUID = null;
    private String vmUUID1 = null;
    private String vmUUID2 = null;

    /**
     * init.
     */
    @Parameters({ "url", "username", "password", "userLocale" })
    @Test(groups = { "init" })
    public static void init(String url, String username,
            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        vclusterService = factory.getVClusterService();
        vcTemplateClient = factory.getVCTemplateService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 创建VCluster测试.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = {
            "com.inspur.ics.client.autotest.VmTemplateServiceTest.cpTemplate" }, alwaysRun = true)
    public void createVCluster() throws Exception {
        VCluster vcluster = new VCluster();
        vcluster.setName("test_sdk_vcluster");
        TaskIntermediateResult taskResult = vclusterService.createVCluster(vcluster);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 根据名称获取虚拟集群信息测试.
     */
    @Test(groups = { "create" }, dependsOnMethods = { "createVCluster" }, alwaysRun = true)
    public void getVClusterInfoByName() {
        VCluster vc = vclusterService.getVClusterInfoByName("test_sdk_vcluster");
        vclusterUUID = vc.getUuid();
        // System.out.println("虚拟集群UUID：" + vc.getUuid());
        // System.out.println("虚拟集群名称：" + vc.getName());
        // System.out.println("活跃虚拟机数目：" + vc.getActivitiVMNum());
        // System.out.println("虚拟集群总内存：" + vc.getMemTotal());
        // System.out.println("虚拟集群状态：" + vc.getStatus());
        // System.out.println("总虚拟机数目：" + vc.getTotalVMNum());
        // System.out.println("虚拟集群uuid：" + vc.getUuid());
        // System.out.println("Vcpu核数：" + vc.getVcpuNum());
    }

    /**
     * 复制虚拟集群测试.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "getVClusterInfoByName" }, alwaysRun = true)
    public void copyVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.copyVCluster(
                vclusterUUID, "test_sdk_copy_vcluster");
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "copyVCluster" }, alwaysRun = true)
    public void createVcTemplate() throws Exception {
        // String vcUUID = "d8f56aec-a90b-44d7-a806-1335d981c150";
        // VCluster vc = vclusterService.getVClusterInfoByName("test_sdk_vcluster");
        String templateName = "test_sdk_vclusterTemplate";
        String tmplDesp = "create vclusterTemplate for testing sdk";
        VClusterTemplate template = new VClusterTemplate();
        template.setName(templateName);
        template.setDescription(tmplDesp);
        TaskIntermediateResult taskResult = vcTemplateClient.createVclusterTemplate(vclusterUUID, template);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 模版创建VCluster测试.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "createVcTemplate" }, alwaysRun = true)
    public void createVClusterByTemplate() throws Exception {
        List<VClusterTemplate> vcTempList = vcTemplateClient.getVClusterTemplateList();
        AssertJUnit.assertNotNull(vcTempList);
        for (VClusterTemplate vct : vcTempList) {
            if (vct.getName().equals("test_sdk_vclusterTemplate")) {
                vctUUID = vct.getUuid();
                break;
            }
        }
        TaskIntermediateResult taskResult = vclusterService
                .createVClusterByTemplate("test_sdk_from_template",
                        vctUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 获取可移入虚拟机列表测试.
     */
    @Test(groups = { "querybefore" }, alwaysRun = true)
    public void getVMsCanBeMovedIn() {
        List<VM> vms = vclusterService.getVMsCanBeMovedIn();
        AssertJUnit.assertNotNull(vms);
        for (VM vm : vms) {
            if (vm.getName().equals("vmttttt_1")) {
                vmUUID1 = vm.getConfig().getUuid();
            } else if (vm.getName().equals("vmttttt_2")) {
                vmUUID2 = vm.getConfig().getUuid();
            }
        }

        // List<VM> vms = vclusterService.getVMsCanBeMovedIn("43fc5811-c93d-4df1-bee0-7cef158dc7d9");
        // for (VM vm:vms) {
        // System.out.println("虚拟机名称：" + vm.getName());
        // System.out.println("虚拟机uuid：" + vm.getConfig().getUuid());
        // System.out.println("虚拟机状态：" + vm.getStatus());
        // }
    }

    /**
     * 获取VCluster信息测试.
     */
    @Test(groups = { "query" }, alwaysRun = true)
    public void getVClusterInfo() {
        VCluster vc = vclusterService.getVClusterInfo(vclusterUUID);
        // System.out.println("虚拟集群名称：" + vc.getName());
        // System.out.println("活跃虚拟机数目：" + vc.getActivitiVMNum());
        // System.out.println("虚拟集群总内存：" + vc.getMemTotal());
        // System.out.println("虚拟集群状态：" + vc.getStatus());
        // System.out.println("总虚拟机数目：" + vc.getTotalVMNum());
        // System.out.println("虚拟集群uuid：" + vc.getUuid());
        // System.out.println("Vcpu核数：" + vc.getVcpuNum());
        // System.out.println(vc.getVms().size());
        // System.out.println(vc == vc.getVms().get(0).getvCluster());
        // System.out.println(FormatUtil.toJson(vc));
        // FormatUtil.toObject(FormatUtil.toJson(vc), VCluster.class);
    }

    @Test(groups = { "query" }, alwaysRun = true)
    public void getVMInVCluster() {
        List<VM> vms = vclusterService.getVmInVCluster(vclusterUUID);
        // System.out.println(vms.size());
        // for (VM vm : vms) {
        // System.out.println(vm.getName());
        // }
    }

    @Test(groups = { "query" }, alwaysRun = true)
    public void getVClusterList() {
        List<VCluster> vclusters = vclusterService.getVClusterList();
        // for (VCluster vc : vclusters) {
        // System.out.println("虚拟集群名称：" + vc.getName());
        // System.out.println("活跃虚拟机数目：" + vc.getActivitiVMNum());
        // System.out.println("虚拟集群总内存：" + vc.getMemTotal());
        // System.out.println("虚拟集群状态：" + vc.getStatus());
        // System.out.println("总虚拟机数目：" + vc.getTotalVMNum());
        // System.out.println("虚拟集群uuid：" + vc.getUuid());
        // System.out.println("Vcpu核数：" + vc.getVcpuNum());
        // }
    }
    /**
     * 获取集群中的VCluster列表测试.
     */
    // @Test
    // public void getVClusterListInCluster() {
    // List<VCluster> vcs = vclusterService.getVClustersInCluster("43fc5811-c93d-4df1-bee0-7cef158dc7d9");
    // for (VCluster vc:vcs) {
    // System.out.println("虚拟集群名称：" + vc.getName());
    // System.out.println("活跃虚拟机数目：" + vc.getActivitiVMNum());
    // System.out.println("虚拟集群总内存：" + vc.getMemTotal());
    // System.out.println("虚拟集群状态：" + vc.getStatus());
    // System.out.println("总虚拟机数目：" + vc.getTotalVMNum());
    // System.out.println("虚拟集群uuid：" + vc.getUuid());
    // System.out.println("Vcpu核数：" + vc.getVcpuNum());
    // }
    // }

    /**
     * 虚拟集群中模版创建虚拟机测试.
     */
    // @Test
    // public void createVMByTemplateInVCluster() {
    // TaskIntermediateResult taskResult = vclusterService
    // .createVMByTemplateInVCluster(
    // "60d0d34c-970c-46bb-ae44-cc2f8c09d3ad",
    // "cbe93701-93e3-4e78-9b19-59dc3b3cf89f", "vmmtest");
    // System.out.println("任务ID：" + taskResult.getTaskId());
    // }
    // /**
    // * 虚拟集群中复制创建虚拟机测试.
    // */
    // @Test
    // public void copyVMInVCluster() {
    // TaskIntermediateResult taskResult = vclusterService.copyVMInVCluster(
    // "60d0d34c-970c-46bb-ae44-cc2f8c09d3ad",
    // "f324f80b-e1cf-4a34-8ce3-c38fa5070b79", "vmmtest01");
    // System.out.println("任务ID：" + taskResult.getTaskId());
    // }
    // /**
    // * 虚拟集群中模版创建虚拟机测试.
    // */
    // @Test
    // public void batchCreateVMByTemplateInVCluster() {
    // TaskIntermediateResult taskResult = vclusterService
    // .batchCreateVMByTemplateInVCluster(
    // "60d0d34c-970c-46bb-ae44-cc2f8c09d3ad",
    // "cbe93701-93e3-4e78-9b19-59dc3b3cf89f", "vmmm", 2);
    // System.out.println("任务ID：" + taskResult.getTaskId());
    // }
    /**
     * 虚拟集群中删除指定虚拟机测试.
     * @throws Exception
     */
    @Test(groups = { "delete" }, dependsOnMethods = {
            "com.inspur.ics.client.autotest.VcTemplateServiceTest.deleteVcTemplate" }, alwaysRun = true, enabled = false)
    public void deleteVMInVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.deleteVMInVCluster(
                vclusterUUID, vmUUID2);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 删除虚拟集群测试.
     * @throws Exception
     */
    @Test(groups = { "delete" },alwaysRun = true)
    public void deleteVCluster() throws Exception {
        VCluster vc = vclusterService.getVClusterInfoByName("test_sdk_copy_vcluster");
        if (vc != null) {
            vclusterService.deleteVCluster(vc.getUuid());
        }
        vc = vclusterService.getVClusterInfoByName("test_sdk_from_template");
        if (vc != null) {
            vclusterService.deleteVCluster(vc.getUuid());
        }
        if (vclusterUUID != null) {
            TaskIntermediateResult taskResult = vclusterService
                    .deleteVCluster(vclusterUUID);
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                    TaskState.FINISHED, taskInfo.getState());
        }

        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 移入虚拟机测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "modifyVCluster" }, alwaysRun = true)
    public void moveVMInVCluster() throws Exception {
        // String vmUUID1 = "c6c61a62-4e79-475d-95b5-801523a5bb7b";
        // String vmUUID2 = "c1711263-1373-4669-973b-fdfdf74f1ce0";
        // String vclusterUUID = "5d5eaf51-bdcd-41a9-b522-a3899a961826";
        List<String> vms = new ArrayList<String>();
        vms.add(vmUUID1);
        vms.add(vmUUID2);
        TaskIntermediateResult taskResult = vclusterService.moveVMInVCluster(vclusterUUID, vms);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 移出虚拟机测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "powerOffVCluster" }, alwaysRun = true)
    public void moveVMOutVCluster() throws Exception {
        // String vmUUID = "745e7ee7-57da-43ee-bf54-2e678c4686f0";
        // String vclusterUUID = "d8f56aec-a90b-44d7-a806-1335d981c150";
        TaskIntermediateResult taskResult = vclusterService.moveVMOutVCluster(vclusterUUID, vmUUID1);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 打开虚拟集群电源测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "moveVMInVCluster" }, alwaysRun = true)
    public void powerOnVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.powerOnVCluster(vclusterUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        try {
  			java.lang.Thread.sleep(120000);
  		} catch (Exception e) {
  			// TODO: handle exception
  		}
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 关闭虚拟集群电源测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "restartVCluster" }, alwaysRun = true)
    public void powerOffVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.powerOffVCluster(vclusterUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 强制重启集群电源测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "powerOnVCluster" }, alwaysRun = true)
    public void restartVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.restartVCluster(vclusterUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        try {
			java.lang.Thread.sleep(120000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 关闭集群电源测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "restartVCluster" }, alwaysRun = true, enabled = false)
    public void shutdownVCluster() throws Exception {
        TaskIntermediateResult taskResult = vclusterService.shutdownVCluster(vclusterUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

    /**
     * 虚拟集群改名测试.
     * @throws Exception
     */
    @Test(groups = { "update" }, alwaysRun = true)
    public void modifyVCluster() throws Exception {
        VCluster vc = new VCluster();
        vc.setName("test_sdk_modify_vcluster");
        vc.setUuid(vclusterUUID);
        TaskIntermediateResult taskResult = vclusterService.modifyVCluster(vc);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.println("任务ID：" + taskResult.getTaskId());
    }

}
