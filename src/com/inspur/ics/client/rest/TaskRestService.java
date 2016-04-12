package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Rest service of task module.
 */
@Path("/")
public interface TaskRestService {
    /**
     * 获取任务日志信息.
     * @param token 操作用户会话令牌
     * @param taskId 任务processId
     * @return 任务日志
     */
    @POST
    @Path("system_task_getTaskInfo")
    String getTaskInfo(@FormParam("token") String token,
            @FormParam("taskId") String taskId);

    /**
     * 获取所有的任务日志.
     * @param token 操作用户会话令牌
     * @return 任务日志列表
     */
    @POST
    @Path("system_task_getAll")
    String getAllTasks(@FormParam("token") String token);

    /**
     * 获取近期任务，10分钟内执行过的任务以及之前未完成的任务.
     * @param token 操作用户会话令牌
     * @return 任务日志列表
     */
    @POST
    @Path("system_task_getRecent")
    String getRecentTasks(@FormParam("token") String token);

    /**
     * 分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param token 操作用户会话令牌
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    @POST
    @Path("system_task_getCustomTasksWithPaging")
    String getCutomTasks(@FormParam("token") String token,
            @FormParam("currentPage") int currentPage,
            @FormParam("totalPage") int totalPage,
            @FormParam("pageSize") int pageSize,
            @FormParam("totalSize") int totalSize);

    /**
     * 取消任务.
     * @param token 操作用户会话令牌
     * @param taskId 任务唯一标示
     * @return 任务返回结果
     */
    @POST
    @Path("system_task_cancelTask")
    String cancelTask(@FormParam("token") String token, @FormParam("taskId") String taskId);

    /**
     * 根据过滤条获取任务，分页显示任务日志，该日志列表是降序排列的，第一次请求时，当前页数为1，总页数和总数量必须指定为0,每次都会返回总页数和总数量，用于后续的请求.
     * @param token 操作用户会话令牌
     * @param currentPage 当前页数
     * @param totalPage 总页数
     * @param pageSize 每页显示的日志数量
     * @param totalSize 日志总数量
     * @param taskFilter 任务过滤器
     * @return CustomAndPageTaskList对象，该对象包含任务日志列表、总页数、总日志数量
     */
    @POST
    @Path("system_task_getTasksWithFilter")
    String getTasksWithFilter(@FormParam("token") String token,
            @FormParam("currentPage") int currentPage,
            @FormParam("totalPage") int totalPage,
            @FormParam("pageSize") int pageSize,
            @FormParam("totalSize") int totalSize,
            @FormParam("taskFilter") String taskFilter);
}
