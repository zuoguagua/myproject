package com.inspur.autotest;


import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.AlarmService;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.AlarmEvent;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.alarm.DataStoreAlarmStrategy;
import com.inspur.ics.pojo.alarm.HostAlarmStrategy;
import com.inspur.ics.pojo.alarm.VmAlarmStrategy;

/**
 * Test AlarmService.
 * @author kangzhx
 */
public class AlarmServiceTest {

    /**
     * AlarmService.
     */
    private static AlarmService alarmService;
    /**
     * hostService.
     */
    private static  HostService hostService;
    /**
     * dataStoreService.
     */
    private static  DataStoreService dataStoreService;
    /**
     * vmService.
     */
    private static VMService vmService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    /**
     * hostUUID.
     */
    private String hostUUID = "";
    /**
     * dsUUID.
     */
    private String dsUUID = "";
    /**
     * vmUUID.
     */
    private  String vmUUID = "";
    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        alarmService = factory.getAlarmService();
        hostService = factory.getHostService();
        dataStoreService = factory.getDataStoreService();
        vmService = factory.getVMService();
        taskService = factory.getTaskService();
    }
       
    /**
     * Test getAllEvents.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetAllEvents() {
        List<AlarmEvent> list = alarmService.getAllEvents();
        AssertJUnit.assertNotNull(list);
    }

    /**
     * TestUpdateHostAlarmStrategy.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testUpdateHostAlarmStrategy() throws Exception {
    	HostAlarmStrategy alarmStrategy = null;
        List<Host> hostList = hostService.getAllHostList();
        if(hostList.size() > 0) {
        	for(Host host:hostList){
        		alarmStrategy = alarmService
                        .getHostAlaramStrategy(host.getUuid());
        		if(alarmStrategy != null) {
        			hostUUID = host.getUuid();
        			alarmStrategy.setMemPercent(23.2f);
                    TaskIntermediateResult result = alarmService
                            .updateHostAlarmStrategy(alarmStrategy);
    	            TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
    	            AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        			break;
        		}
        	}
        }
    }

    /**
     * TestUpdateAlarmStrategy.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testUpdateAlarmStrategy() throws Exception {
    	VmAlarmStrategy alarmStrategy = null;
    	String tempuuid = "";
    	if (vmService.getAll().size() > 0) {
    		for (int i = 0; i < vmService.getAll().size(); i++) {
    			tempuuid = vmService.getAll().get(i).getConfig().getUuid();
    			alarmStrategy = alarmService
		                .getVmAlaramStrategy(tempuuid);
    		    if (alarmStrategy != null) {
    		    	this.vmUUID = tempuuid;
    		    	System.out.println("vmUUID:"+vmUUID);
    				alarmStrategy.setMemPercent(90);
    	            TaskIntermediateResult result = alarmService
    	                .updateAlarmStrategy(alarmStrategy);
    	            TaskInfo taskInfo =  TestUtil.waitFor(result.getTaskId());
    	            AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    				break;
    			}
    		}
    	}
    }

    /**
     * TestUpdateDataStoreAlarmStrategy.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void testUpdateDataStoreAlarmStrategy() throws Exception {
    	DataStoreAlarmStrategy alarmStrategy = null;
    	String tempuuid = "";
    	if(dataStoreService.getAllDataStore().size()>0) {
    		for (int i = 0; i < dataStoreService.getAllDataStore().size(); i++) {
    			tempuuid = dataStoreService.getAllDataStore().get(i).getUuid();
                alarmStrategy = alarmService
                     .getDataStoreAlarmStrategy(tempuuid);
                if(alarmStrategy != null) {
                	this.dsUUID = tempuuid;
            		alarmStrategy.setrRate(60f);
            		TaskIntermediateResult result = alarmService
                            .updateDataStoreAlarmStrategy(alarmStrategy);
    	            TaskInfo taskInfo =  TestUtil.waitFor(result.getTaskId());
    	            AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
                    break;
                }
    		}
        }
    }

//    /**
//     * TestGetHostAlaramStrategy.
//     */
//    @Test(groups={"query"},alwaysRun=true)
//    public void testGetHostAlaramStrategy() {
//        HostAlarmStrategy alarmStrategy = alarmService
//             .getHostAlaramStrategy(hostUUID);
//        AssertJUnit.assertEquals(alarmStrategy.getMemPercent(), 23.2f, 0.001f);
//    }
//
//    /**
//     * TestGetVmAlaramStrategy.
//     */
//    @Test(groups={"query"},alwaysRun=true)
//    public void testGetVmAlaramStrategy() {
//       VmAlarmStrategy alarmStrategy = alarmService
//           .getVmAlaramStrategy(vmUUID);
//       AssertJUnit.assertEquals(alarmStrategy.getMemPercent(), 90, 0.001f);
//    }
//
//    /**
//     * TestGetDataStoreAlarmStrategy.
//     */
//    @Test(groups={"query"},alwaysRun=true)
//    public void testGetDataStoreAlarmStrategy() {
//    	AssertJUnit.assertEquals(dsUUID,"6e621c79-88ea-46b8-90b8-c938b8d66309");
////       DataStoreAlarmStrategy dsAlarmStrategy = alarmService
////             .getDataStoreAlarmStrategy(dsUUID);
////       AssertJUnit.assertEquals(dsAlarmStrategy.getrRate(),60f, 0.001f);
//    }
}
