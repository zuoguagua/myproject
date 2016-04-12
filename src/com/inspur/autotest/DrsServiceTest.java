package com.inspur.autotest;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DrsService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.ClusterGroup;
import com.inspur.ics.pojo.ClusterHostGroup;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DrsRule;
import com.inspur.ics.pojo.DrsStrategy;

import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;

/**
 * Test DrsService.
 * @author kangzhx
 */
public class DrsServiceTest {

    /**
     * DrsService.
     */
    private static DrsService drsService;
    /**
     * clusterService.
     */
    private static ClusterService clusterService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
    private int drsGroupId = -1;
    private int drsRuleId = -1;
    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        drsService = factory.getDrsService();
        clusterService = factory.getClusterService();
        taskService = factory.getTaskService();
    }
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.VClusterServiceTest.createVClusterByTemplate"},alwaysRun=true)
    public void initPara() {
        List<Cluster> clusterList = clusterService.getAllCluster();
//        AssertJUnit.assertNotNull(clusterList);
        if (clusterList.size() > 0){
            for(int i = 0; i < clusterList.size(); i++) {
            	if("sdk_cluster".equals(clusterList.get(i).getDisplayName())){
            		clusterUUID = clusterList.get(i).getUuid();
            		break;
            	}
            }
        }
        
    }

    /**
     * Test getStatus.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetStatus() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        boolean result = drsService.isDrsEnabled(clusterUUID);
        System.out.println(result);
    }

    /**
     * Test configDrs.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testConfigDrs() throws Exception {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        boolean drsEnabled = drsService.isDrsEnabled(clusterUUID);
        TaskIntermediateResult result = drsService.configDrs(clusterUUID,
                drsEnabled);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * Test GetDrsStrategy.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetDrsStrategy() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        DrsStrategy strategy = drsService.getDrsStrategy(clusterUUID);
        System.out.println("CpuDiffThread:" + strategy.getCpuDiffThreshold());
        System.out.println("Cpu Threshold:" + strategy.getCpuThreshold());
        System.out.println("Del bad cpu record num:"
                + strategy.getDelBadCpuRecordNum());
        System.out.println("Nic diff threashold:"
                + strategy.getNicDiffThreshold());
        System.out.println("Nic Threshold:" + strategy.getNicThreshold());
        System.out.println("uuid:" + strategy.getUuid());
        System.out.println("count: " + strategy.getVmMigrationCount());
    }

    /**
     * Test ConfigStrategy.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testConfigStrategy() throws Exception {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        DrsStrategy strategy = drsService.getDrsStrategy(clusterUUID);
        strategy.setCpuDiffThreshold(40);
        TaskIntermediateResult result = drsService.configStrategy(clusterUUID,
                strategy);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * Test CreateDrsGroup.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"initPara"},alwaysRun=true)
    public void testCreateDrsGroup() throws Exception {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        ClusterHostGroup clusterGroup = new ClusterHostGroup();
        int id = 6;
        List<Host> host = drsService.getAddableHosts(clusterUUID, id);
        clusterGroup.setName("drshostg");
        clusterGroup.setType("HOST");
        clusterGroup.setHosts(host);
        TaskIntermediateResult result = drsService.createDrsGroup(clusterGroup,
                clusterUUID);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        ClusterHostGroup clusterGroup1 = new ClusterHostGroup();
        clusterGroup1.setName("drsvmgg");
        clusterGroup1.setType("VM");
        result = drsService.createDrsGroup(clusterGroup1,
                clusterUUID);
        System.out.println(result.getTaskId());
    }

    /**
     * Test ModifyDrsGroup.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testModifyDrsGroup() throws Exception {
        ClusterHostGroup clusterGroup = (ClusterHostGroup) drsService.getDrsGroupInfo(drsGroupId);
        List<Host> hosts = clusterService.getAvailableHosts();
        clusterGroup.setHosts(hosts);
        TaskIntermediateResult result = drsService.modifyDrsGroup(clusterGroup);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * Test DeleteDrsGroup.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},alwaysRun=true)
    public void testDeleteDrsGroup() throws Exception {
        TaskIntermediateResult result = drsService.deleteDrsGroup(drsGroupId);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * Test GetDrsGroupInCluster.
     */
    @Test(groups={"querybefore"},alwaysRun=true)
    public void testGetDrsGroupInCluster() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<ClusterGroup> group = drsService.getDrsGroupInCluster(clusterUUID);
        for (ClusterGroup s : group) {
        	if(s.getName().equals("drshostg")) {
        		drsGroupId = s.getId();
        		break;
        	}
            System.out.println("name:" + s.getName());
            System.out.println("id:" + s.getId());
            System.out.println("clusterDisplayName :" + s.getCluster().getDisplayName());
        }
    }

    /**
     * Test GetDrsGroupInfo.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetDrsGroupInfo() {
        ClusterHostGroup s = (ClusterHostGroup) drsService.getDrsGroupInfo(drsGroupId);
        System.out.println("name:" + s.getName());
        System.out.println("id:" + s.getId());
        System.out.println("该主机DRS组主机数量 :" + s.getHosts().size());
    }

    /**
     * Test GetAddableVms.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetAddableVms() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<VM> vm = drsService.getAddableVms(clusterUUID, drsGroupId);
        for (VM v : vm) {
            System.out.println("虚拟机名字：" + v.getName());
        }
    }

    /**
     * Test GetAddableHosts.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetAddableHosts() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<Host> host = drsService.getAddableHosts(clusterUUID, drsGroupId);
//        for (Host s : host) {
//            System.out.println(s.getNum());
//            System.out.println(s.getUuid());
//            System.out.println(s.getPassword());
//            System.out.println(s.getVirtualDegree());
//            System.out.println(s.getIp());
//            System.out.println(s.isPreNode());
//        }
    }

    /**
     * Test CreateDrsRule.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"testCreateDrsGroup"},alwaysRun=true)
    public void testCreateDrsRule() throws Exception {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        DrsRule drsRule = new DrsRule();
        drsRule.setName("drsr01");
        TaskIntermediateResult result = drsService.createDrsRule(drsRule,
                clusterUUID);
        System.out.println(result.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * Test ModifyDrsRule.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testModifyDrsRule() throws Exception {
		if (drsRuleId != -1) {
			// DrsRule drsRule = null;
			DrsRule drsRule = new DrsRule();
			drsRule.setId(drsRuleId);
			drsRule.setName("drsr02");
			TaskIntermediateResult result = drsService.modifyDrsRule(drsRule);
			System.out.println(result.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

    /**
     * Test deleteDrsRule.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},alwaysRun=true)
    public void testDeleteDrsRule() throws Exception {
		if (drsRuleId != -1) {
			TaskIntermediateResult result = drsService.deleteDrsRule(drsRuleId);
			System.out.println(result.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

    /**
     * Test GetDrsRuleInCluster.
     */
    @Test(groups={"querybefore"},alwaysRun=true)
    public void testGetDrsRuleInCluster() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<DrsRule> group = drsService.getDrsRuleInCluster(clusterUUID);
        for (DrsRule s : group) {
        	if(s.getName().equals("drsr01")) {
        		drsRuleId = s.getId();
        		break;
        	}
            System.out.println("name:" + s.getName());
            System.out.println("id:" + s.getId());
            System.out.println("clusterDisplayName :" + s.getCluster().getDisplayName());
        }
    }

    /**
     * Test GetDrsRuleInfo.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetDrsRuleInfo() {
		if (drsRuleId != -1) {
			DrsRule group = drsService.getDrsRuleInfo(drsRuleId);
			System.out.println(group.getName());
			System.out.println(group.getType());
		}
    }

    /**
     * Test GetAddableVmsForRule.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetAddableVmsForRule() {
        int id = 35;
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<VM> vms = drsService.getAddableVmsForRule(clusterUUID, id);
        for (VM vm : vms) {
            System.out.println(vm.getDescription());
            System.out.println(vm.getMaxSnap());
            System.out.println(vm.getName());
            System.out.println(vm.getSnapUID());
            System.out.println(vm.getState());
        }
    }

}
