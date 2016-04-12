package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.SystemEventService;
import com.inspur.ics.client.rest.SystemEventRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.Event;
import com.inspur.ics.pojo.event.PageEventList;

/**
 * 系统事件管理服务接口实现类.
 * @author ychau
 */
public class SystemEventServiceImpl implements SystemEventService {
    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 系统配置服务REST接口服务类.
     */
    private SystemEventRestService restService;

    /**
     * 系统事件管理服务接口实现类构造方法.
     * @param url
     *            请求Action路径
     * @param token
     *            用户会话令牌.
     */
    public SystemEventServiceImpl(String url, String token) {
        this.restService = ProxyFactory.create(SystemEventRestService.class,
                url, ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取所有事件.
     * @return 所有事件列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() {
        String resultStr = restService.getAllEvents(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, Event.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<Event>();
        } else {
            return (List<Event>) result.getData();
        }
    }

    /**
     * 根据任务ID获取相关事件信息.
     * @param taskId
     *            任务标示ID
     * @return 任务相关事件列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByTaskId(int taskId) {
        String resultStr = restService.getEventsByTaskId(token, taskId);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, Event.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<Event>();
        } else {
            return (List<Event>) result.getData();
        }
    }

    /**
     * 根据事件id获取相关事件信息.
     * @param eventId
     *            事件id
     * @return 相关事件列表.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getRelatedEvents(int eventId) {
        String resultStr = restService.getRelatedEvents(token, eventId);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, Event.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<Event>();
        } else {
            return (List<Event>) result.getData();
        }
    }

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
    @SuppressWarnings("unchecked")
    @Override
    public PageEventList getEventsWithDesc(int currentPage,
            int totalPage,
            int pageSize,
            int totalSize) {
        String resultXML = restService.getEventsWithDesc(token, currentPage,
                totalPage, pageSize, totalSize);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, PageEventList.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<PageEventList>) result.getData()).get(0);
        }
    }
}
