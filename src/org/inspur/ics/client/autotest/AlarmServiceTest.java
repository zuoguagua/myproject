package org.inspur.ics.client.autotest;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.AlarmService;
import com.inspur.ics.client.ClientFactory;
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

public class AlarmServiceTest {
	private static AlarmService alarmService ;
	private static HostService hostService;
	private static TaskService taskService;
	private static VMService vmService;
	
	@BeforeClass
	@Test()
	@Parameters({"url","username","password","userLocale"})
	public static void init(String url, String username, String password, String userLocale){
		ClientFactory factory = new ClientFactory(url,username,password,userLocale);
		alarmService = factory.getAlarmService();
		hostService = factory.getHostService();
		taskService = factory.getTaskService();
		vmService = factory.getVMService();
		System.out.println("Init for AlarmService.");
	}
	
	@Test
	public void testGetAllEvents(){
		List<AlarmEvent> list = alarmService.getAllEvents();
		int event = list.size();
		System.out.println(event);
	}
	
	@Test
	public void testUpdateHostAlarmStrategy() throws Exception{
		
		HostAlarmStrategy alarmStrategy = null;
		List<Host> hostList = hostService.getAllHostList();
		if(hostList.size() > 0){
			for(Host host:hostList){
				alarmStrategy = alarmService.getHostAlaramStrategy(host.getUuid());
				if(alarmStrategy != null){
					alarmStrategy.setMemPercent(20.2f);
					@SuppressWarnings("rawtypes")
					TaskIntermediateResult result = alarmService.updateHostAlarmStrategy(alarmStrategy);
					Thread.sleep(120);
					TaskInfo taskinfo =  taskService.getTaskInfo(result.getTaskId());
					System.out.println(taskinfo);
					String localizeState = taskinfo.getLocalizeState();
					String localizeError = taskinfo.getLocalizedError();
					TaskState taskstate = TaskState.FINISHED;
					TaskState state = taskinfo.getState();
					System.out.println(localizeState);
					System.out.println(localizeError);
					System.out.println(taskstate.toString());
					System.out.println(state);
				}
			}
		}
	}
	
	@Test(enabled = false)
	public void testUpdateAlarmStrategy(){
		VmAlarmStrategy alarmStrategy = null;
		String tempuuid = null;
		alarmStrategy = alarmService.getVmAlaramStrategy(tempuuid);
		if(alarmStrategy != null){
			alarmStrategy.setMemPercent(50);
		}
		
		TaskIntermediateResult result = alarmService.updateAlarmStrategy(alarmStrategy);
	}
	
	@Test(enabled = false)
	public void updateDataStoreAlarmStrategy(){
		DataStoreAlarmStrategy alarmStrategy = null;
		TaskIntermediateResult result = alarmService.updateDataStoreAlarmStrategy(alarmStrategy);
	}
	
	@Test(enabled = false)
	public void getHostAlaramStrategy(){
		String uuid = null;
		HostAlarmStrategy list = alarmService.getHostAlaramStrategy(uuid);
	}
	
	@Test(enabled = false)
	public void getDataStoreAlarmStrategy(){
		
	}
	
	@Test(enabled = false)
	public void getVmAlaramStrategy(){
		
	}
	
	
}
