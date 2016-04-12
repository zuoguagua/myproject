package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.SystemEventService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.pojo.Event;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.event.PageEventList;

/**
 * SystemEventService 测试类.
 * @author ychau
 */
public class SystemEventServiceTest {
    /**
     * SystemEventService.
     */
    private static SystemEventService service;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    int eventId;

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getSystemEventService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 获取所有事件.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getAllEvents() {
        List<Event> events = service.getAllEvents();
        AssertJUnit.assertNotNull(events);
        System.out.println("The Size of All Events :" + events.size());
    }

    /**
     * 根据任务ID获取相关事件信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getEventsByTaskId() {
    	int taskId = 1114;
    	List<TaskInfo> tasks = taskService.getAllTasks();
    	 for(TaskInfo task:tasks){
     		if(task.getName().equals("pattern=vm.iso.create.task.name#args=[]")) {
     			List<Event> events = service.getEventsByTaskId(task.getId());
     			eventId = events.get(0).getId();
     			System.out.println("The Size of Event's List :" + events.size());
     			AssertJUnit.assertNotNull(events.get(0).getUserName());
     			break;
     		}
     	}
    }

    /**
     * 根据事件id获取相关事件信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getRelatedEvents() {
    	List<Event> events = service.getRelatedEvents(eventId);
     	System.out.println("The Size of Event's List :" + events.size());
     	AssertJUnit.assertNotNull(events.get(0).getUserName());
    }

    /**
     * 分页显示事件列表信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getEventsWithDesc() {
        int currentPage = 1;
        int pageSize = 7;
        int totalSize = 0;
        int totalPage = 0;
        PageEventList pageList = service.getEventsWithDesc(currentPage, totalPage, pageSize, totalSize);
        AssertJUnit.assertNotNull(pageList.getEvents().get(0));
        System.out.println(FormatUtil.toJson(pageList));
    }
}
