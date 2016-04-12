package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.pojo.Event;
import com.inspur.ics.pojo.event.PageEventList;

/**
 * 系统事件管理服务接口.
 * @author ychau
 */
public interface SystemEventService {
    /**
     * 获取所有事件.
     * @return 所有事件列表
     */
    List<Event> getAllEvents();

    /**
     * 根据任务ID获取相关事件信息.
     * @param taskId
     *            任务标示ID
     * @return 任务相关事件列表
     */
    List<Event> getEventsByTaskId(int taskId);

    /**
     * 根据事件id获取相关事件信息.
     * @param eventId
     *            事件id
     * @return 相关事件列表.
     */
    List<Event> getRelatedEvents(int eventId);

    /**
     * 分页显示事件列表信息.
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
    PageEventList getEventsWithDesc(int currentPage,
            int totalPage,
            int pageSize,
            int totalSize);
}
