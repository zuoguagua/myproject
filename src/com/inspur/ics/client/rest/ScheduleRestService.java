package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author kangzhx
 */
@Path("/")
public interface ScheduleRestService {

    /**
     * 创建调度任务.
     * @param token
     *            用户认证令牌
     * @param task
     *            调度任务
     * @return result
     */
    @POST
    @Path("scheduler_createScheduledTask")
    String createScheduledTask(@FormParam("token") String token,
            @FormParam("task") String task);

    /**
     * 改变调度任务.
     * @param token
     *            用户认证令牌
     * @param task
     *            调度任务
     * @return result
     */
    @POST
    @Path("scheduler_modifyScheduledTask")
    String modifyScheduledTask(@FormParam("token") String token,
            @FormParam("task") String task);

    /**
     * 删除调度任务.
     * @param token
     *            用户认证令牌
     * @param id
     *            调度任务的Id
     * @return result
     */
    @POST
    @Path("scheduler_deleteScheduledTask")
    String deleteScheduledTask(@FormParam("token") String token,
            @FormParam("id") int id);

    /**
     * 获取所有调度任务.
     * @param token
     *            用户认证令牌
     * @param definedUuid
     *            任务对象的唯一标识UUID
     * @return result
     */
    @POST
    @Path("scheduler_getAllScheduledTask")
    String getAllScheduledTask(@FormParam("token") String token,
            @FormParam("definedUuid") String definedUuid);

    /**
     * 获取调度任务的信息.
     * @param token
     *            用户认证令牌
     * @param id
     *            调度任务的id
     * @return result
     */
    @POST
    @Path("scheduler_getScheduledTaskInfo")
    String getScheduledTaskInfo(@FormParam("token") String token,
            @FormParam("id") int id);

}
