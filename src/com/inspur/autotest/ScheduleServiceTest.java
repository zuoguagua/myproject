package com.inspur.autotest;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Date;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ScheduleService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.core.task.TaskType;
import com.inspur.ics.pojo.OnceTaskScheduler;
import com.inspur.ics.pojo.ScheduledTask;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.TaskScheduler;
import com.inspur.ics.pojo.VM;
/**
 * Test ScheduleService.
 * @author kangzhx
 */
public class ScheduleServiceTest {

    /**
     * AlarmService.
     */
    private static ScheduleService scheduleService;
    private static VMService vmService;
    private static TaskService taskService;
    private String vmUUID = "017d7329-a874-41db-906a-51bcc0afe353";

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        scheduleService = factory.getScheduleService();
        vmService = factory.getVMService();
        taskService = factory.getTaskService();
    }
    /**
     * TestCreateScheduledTask.
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.DrsServiceTest.testCreateDrsGroup"},alwaysRun=true)
    public void testCreateScheduledTask() throws Exception {
//    	vmUUID = vmService.getVMInfoByName("vmtest0001").getConfig().getUuid();
    	 List<VM> vmList = vmService.getAll();
         for(VM vm:vmList) {
         	if(vm.getName().equals("vmtest0001")){
         		 vmUUID = vm.getConfig().getUuid();
         		 break;
         	}
         }
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setNextRunTime(new Date(116, 5, 31));
        scheduledTask.setDescription("abcd1");
        //调度任务名字
        scheduledTask.setName("createVmTask");
        scheduledTask.setTaskParams("aaaaa");
        //虚拟机的UUID
        scheduledTask.setDefinedUUID(vmUUID);
        scheduledTask.setTaskType(TaskType.CREATE_SCHEDULED_TASK);
        scheduledTask.setScheduler(new TaskScheduler());
        TaskIntermediateResult result = scheduleService.createScheduledTask(scheduledTask);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    /**
     * TestCreateScheduledTask.
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    @Test(groups={"update"},alwaysRun=true)
    public void testModifyScheduledTask() throws Exception {
    	int id = 4;
    	List<ScheduledTask> list = scheduleService.getAllScheduledTask(vmUUID);
    	for(int i =0;i<list.size();i++){
    		if(list.get(i).getName().equals("createVmTask")) {
    			id = list.get(i).getId();
    			break;
    		}
    	}
        ScheduledTask scheduledTask = scheduleService.getScheduledTask(id);
        //调度任务名字
        scheduledTask.setName("createVmTaskNew");
        scheduledTask.setTaskParams("aaaaa123");
        OnceTaskScheduler task = new OnceTaskScheduler();
        task.setRunAt(new Date(117, 5, 31));
        scheduledTask.setScheduler(task);
        //虚拟机的UUID
        TaskIntermediateResult result = scheduleService.modifyScheduledTask(scheduledTask);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        scheduledTask = scheduleService.getScheduledTask(id);
        AssertJUnit.assertEquals("createVmTaskNew", scheduledTask.getName());
    }
    /**
     * Test DeleteScheduledTask.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},alwaysRun=true)
    public void testDeleteScheduledTask() throws Exception {
        int id = 3;
        List<ScheduledTask> list = scheduleService.getAllScheduledTask(vmUUID);
    	for(int i =0;i<list.size();i++){
    		if(list.get(i).getName().equals("createVmTask")) {
    			id = list.get(i).getId();
    			break;
    		}
    	}
        TaskIntermediateResult result = scheduleService.deleteScheduledTask(id);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println(result.getTaskId());
    }
    /**
     * Test GetAllScheduledTask.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetAllScheduledTask() {
        String definedUuid = vmUUID;
        List<ScheduledTask> list = scheduleService.getAllScheduledTask(definedUuid);
        AssertJUnit.assertNotNull(list);
//        for (ScheduledTask task : list) {
//            System.out.println(task.getDescription());
//            System.out.println(task.getLastModifiedUser());
//            System.out.println(task.getDefinedUUID());
//            System.out.println(task.getName());
//            System.out.println(task.getTaskParams());
//            System.out.println(task.getId());
//        }
    }
    /**
     * Test GetScheduledTask.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void testGetScheduledTask() {
        int id = 3;
        List<ScheduledTask> list = scheduleService.getAllScheduledTask(vmUUID);
    	for(int i =0;i<list.size();i++){
    		if(list.get(i).getName().equals("createVmTask")) {
    			id = list.get(i).getId();
    			break;
    		}
    	}
        ScheduledTask task = scheduleService.getScheduledTask(id);
        AssertJUnit.assertNotNull(task);
//        System.out.println(task.getDescription());
//        System.out.println(task.getLastModifiedUser());
//        System.out.println(task.getDefinedUUID());
//        System.out.println(task.getName());
//        System.out.println(task.getTaskParams());
    }

}
