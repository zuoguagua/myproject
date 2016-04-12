package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.task.CustomAndPageTaskList;
import com.inspur.ics.pojo.task.TaskFilter;

/**
 * 查看任务相关信息.
 * @author ychau
 */
public interface TaskService {
    /**
     * 获取任务日志信息.
     * @param taskId 任务processId
     * @return 任务日志
     */
    TaskInfo getTaskInfo(String taskId);

    /**
     * 获取所有的任务日志.
     * @return 任务日志列表
     */
    List<TaskInfo> getAllTasks();

    /**
     * 获取近期任务，10分钟内执行过的任务以及之前未完成的任务.
     * @return 任务日志列表
     */
    List<TaskInfo> getRecentTasks();

    /**
     * 分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    CustomAndPageTaskList getCutomTasks(int currentPage,
            int totalPage, int pageSize,
            int totalSize);

    /**
     * 取消任务.
     * @param taskId 任务processId
     * @return 返回任务执行结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult cancelTask(String taskId);

    /**
     * 根据过滤条获取任务，分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @param filter 任务过滤器
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    CustomAndPageTaskList getTasksWithFilter(int currentPage,
            int totalPage, int pageSize, int totalSize, TaskFilter filter);
}
