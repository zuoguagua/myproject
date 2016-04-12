package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.rest.TaskRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.task.CustomAndPageTaskList;
import com.inspur.ics.pojo.task.TaskFilter;

/**
 * 查看任务相关信息接口实现类.
 * @author ychau
 */
public class TaskServiceImpl implements TaskService {

    /**
     * 查看任务相关信息REST接口服务类.
     */
    private TaskRestService restService;

    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 查看任务相关信息接口实现类构造方法.
     * @param url 请求Action路径
     * @param token 用户会话令牌.
     */
    public TaskServiceImpl(String url, String token) {
        restService = ProxyFactory.create(TaskRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取任务日志信息.
     * @param taskId 任务processId
     * @return 任务日志
     */
    @SuppressWarnings("unchecked")
    @Override
    public TaskInfo getTaskInfo(String taskId) {
        String resultXML = restService.getTaskInfo(token, taskId);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskInfo.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<TaskInfo>) result.getData()).get(0);
        }
    }

    /**
     * 获取所有的任务日志.
     * @return 任务日志列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TaskInfo> getAllTasks() {
        String resultStr = restService.getAllTasks(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskInfo.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<TaskInfo>();
        } else {
            return (List<TaskInfo>) result.getData();
        }
    }

    /**
     * 获取近期任务，10分钟内执行过的任务以及之前未完成的任务.
     * @return 任务日志列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TaskInfo> getRecentTasks() {
        String resultStr = restService.getRecentTasks(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskInfo.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<TaskInfo>();
        } else {
            return (List<TaskInfo>) result.getData();
        }
    }

    /**
     * 分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    @SuppressWarnings("unchecked")
    @Override
    public CustomAndPageTaskList getCutomTasks(int currentPage,
            int totalPage, int pageSize,
            int totalSize) {
        String resultXML = restService.getCutomTasks(token, currentPage,
                totalPage, pageSize, totalSize);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, CustomAndPageTaskList.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<CustomAndPageTaskList>) result.getData()).get(0);
        }
    }

    /**
     * 取消任务.
     * @param taskId 任务processId
     * @return 返回任务执行结果
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult cancelTask(String taskId) {
        String resultStr = restService.cancelTask(token, taskId);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 根据过滤条获取任务，分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @param filter 任务过滤器
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    @SuppressWarnings("unchecked")
    @Override
    public CustomAndPageTaskList getTasksWithFilter(int currentPage,
            int totalPage, int pageSize, int totalSize, TaskFilter filter) {
        String resultXML = restService.getTasksWithFilter(token, currentPage,
                totalPage, pageSize, totalSize, FormatUtil.toJson(filter));
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, CustomAndPageTaskList.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<CustomAndPageTaskList>) result.getData()).get(0);
        }
    }
}
