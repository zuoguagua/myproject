package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.ScheduleService;
import com.inspur.ics.client.rest.ScheduleRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.OnceTaskScheduler;
import com.inspur.ics.pojo.RecurrentTaskScheduler;
import com.inspur.ics.pojo.ScheduledTask;
/**
 * @author kangzhx
 */
public class ScheduleServiceImpl implements ScheduleService {
    /**
     * token 用户认证令牌.
     */
    private String token = "";
    /**
     * 告警的rest代理对象.
     */
    private ScheduleRestService client;

    /**
     * 构造函数 构造rest的代理对象.
     * @param url
     *            rest服务对应的url
     * @param token
     *            用户认证令牌
     */
    public ScheduleServiceImpl(String url, String token) {
        client = ProxyFactory.create(ScheduleRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public TaskIntermediateResult createScheduledTask(
            ScheduledTask scheduledTask) {
        String task = FormatUtil.toJson(scheduledTask);
        String str = client.createScheduledTask(token, task);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public TaskIntermediateResult modifyScheduledTask(
            ScheduledTask scheduledTask) {
    	if(scheduledTask.getScheduler() != null) {
    		scheduledTask.getScheduler().setScheduledTask(null);
    	}
        String task = FormatUtil.toJson(scheduledTask);
        String str = client.modifyScheduledTask(token, task);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public TaskIntermediateResult deleteScheduledTask(int id) {
        String str = client.deleteScheduledTask(token, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ScheduledTask> getAllScheduledTask(String definedUuid) {
        String str = client.getAllScheduledTask(token, definedUuid);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, ScheduledTask.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new ArrayList<ScheduledTask>();
        } else {
            return ((List<ScheduledTask>) result.getData());
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ScheduledTask getScheduledTask(int id) {
        String str = client.getScheduledTaskInfo(token, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, ScheduledTask.class, OnceTaskScheduler.class, RecurrentTaskScheduler.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return null;
        } else {
            return (ScheduledTask) ((List) result.getData()).get(0);
        }
    }

}
