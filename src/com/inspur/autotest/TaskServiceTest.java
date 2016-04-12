package com.inspur.autotest;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.task.CustomAndPageTaskList;
import com.inspur.ics.pojo.task.TaskFilter;

/**
 * TaskService 测试类.
 * @author ychau
 */
public class TaskServiceTest {
    /**
     * TaskService.
     */
    private static TaskService service;

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 获取所有的任务日志.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getAllTasks() {
        List<TaskInfo> tasks = service.getAllTasks();
        AssertJUnit.assertNotNull(tasks);
//        System.out.println("The Size of All Tasks:" + tasks.size());
//        System.out.println("The first Task:" + FormatUtil.toJson(tasks.get(0)));
    }

    /**
     * 获取近期任务，10分钟内执行过的任务以及之前未完成的任务.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getRecentTasks() {
        List<TaskInfo> tasks = service.getRecentTasks();
        AssertJUnit.assertNotNull(tasks);
//        System.out.println("The Size of recent Tasks:" + tasks.size());
//        System.out.println("The first Task:" + FormatUtil.toJson(tasks.get(0)));
    }

    /**
     * 分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getCutomTasks() {
        int currentPage = 1;
        int pageSize = 7;
        int totalSize = 0;
        int totalPage = 0;
        CustomAndPageTaskList pageList = service.getCutomTasks(currentPage, totalPage, pageSize, totalSize);
        AssertJUnit.assertNotNull(pageList);
//        System.out.println(FormatUtil.toJson(pageList));
    }

    /**
     * 获取任务信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getTaskInfoTest() {
    	List<TaskInfo> tasks = service.getAllTasks();
        String taskId = "1302";
        taskId = tasks.get(tasks.size() / 2).getProcessId();
        TaskInfo taskInfo = service.getTaskInfo(taskId);
        AssertJUnit.assertNotNull(taskInfo.getId());
//        System.out.println("任务ID：" + taskInfo.getId());
//        AssertJUnit.assertEquals("1302", taskInfo.getId());
    }
    /**
     * 取消任务.
     */
    @Test(groups={"update"},alwaysRun=true, enabled=false)
    public void cancelTask() {
    	List<TaskInfo> tasks = service.getAllTasks();
        String taskId = "1302";
        taskId = tasks.get(tasks.size() / 2).getId().toString();
        try {
            TaskIntermediateResult<?> taskResult = service.cancelTask(taskId);
            AssertJUnit.assertNotNull(taskResult);
//            System.out.println("任务ID：" + taskResult.getTaskId());
        } catch (RemoteException e) {
//            System.out.println("取消任务" + taskId + "失败!");
        	AssertJUnit.fail(e.getMessage());
        }
    }

    /**
     * 根据过滤条获取任务，分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getTasksWithFilter() {
        int currentPage = 1;
        int pageSize = 7;
        int totalSize = 0;
        int totalPage = 0;
        TaskFilter filter = new TaskFilter();
        CustomAndPageTaskList pageList = service.getTasksWithFilter(
                currentPage, totalPage, pageSize, totalSize, filter);
        AssertJUnit.assertNotNull(pageList);
//        System.out.println(FormatUtil.toJson(pageList));
    }
}
