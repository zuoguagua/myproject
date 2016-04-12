package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 系统事件管理服务REST接口.
 * @author ychau
 */
@Path("/")
public interface SystemEventRestService {
    /**
     * 获取所有事件.
     * @param token 操作用户会话令牌
     * @return 所有事件列表
     */
    @POST
    @Path("system_event_getAll")
    String getAllEvents(@FormParam("token") String token);

    /**
     * 根据任务ID获取相关事件信息.
     * @param token 操作用户会话令牌
     * @param taskId
     *            任务标示ID
     * @return 任务相关事件列表
     */
    @POST
    @Path("system_event_getEventsByTaskId")
    String getEventsByTaskId(@FormParam("token") String token,
            @FormParam("taskId") int taskId);

    /**
     * 根据事件id获取相关事件信息.
     * @param token 操作用户会话令牌
     * @param eventId
     *            事件id
     * @return 相关事件列表.
     */
    @POST
    @Path("system_event_getRelatedEvents")
    String getRelatedEvents(@FormParam("token") String token,
            @FormParam("eventId") int eventId);

    /**
     * 分页显示事件列表信息.
     * @param token 操作用户会话令牌
     * @param currentPage
     *            当前页数
     * @param totalPage
     *            总页数
     * @param pageSize
     *            每页信息记录条数
     * @param totalSize
     *            总页数
     * @return 当前页事件记录信息列表.
     */
    @POST
    @Path("system_event_getEventsWithDesc")
    String getEventsWithDesc(@FormParam("token") String token,
            @FormParam("currentPage") int currentPage,
            @FormParam("totalPage") int totalPage,
            @FormParam("pageSize") int pageSize,
            @FormParam("totalSize") int totalSize);
}
