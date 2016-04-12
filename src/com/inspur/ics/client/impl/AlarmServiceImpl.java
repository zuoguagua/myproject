package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.AlarmService;
import com.inspur.ics.client.rest.AlarmRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.AlarmEvent;
import com.inspur.ics.pojo.alarm.DataStoreAlarmStrategy;
import com.inspur.ics.pojo.alarm.HostAlarmStrategy;
import com.inspur.ics.pojo.alarm.VmAlarmStrategy;

/**
 * @author kangzhx
 */
public class AlarmServiceImpl implements AlarmService {

    /**
     * 用户认证令牌.
     */
    private String token = "";

    /**
     * 告警的rest代理对象.
     */
    private AlarmRestService client;

    /**
     * 构造函数 构造rest的代理对象.
     * @param url
     *            rest服务对应的url.
     * @param token
     *            用户认证令牌.
     */
    public AlarmServiceImpl(String url, String token) {
        client = ProxyFactory.create(AlarmRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlarmEvent> getAllEvents() {
        String str = client.getAllEvents(token);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, AlarmEvent.class });
        if (result.getData() != null) {
            return (List<AlarmEvent>) result.getData();
        } else if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return new ArrayList<AlarmEvent>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public TaskIntermediateResult updateHostAlarmStrategy(
            HostAlarmStrategy alarmStrategy) {
        String alarmStrategyJson = FormatUtil.toJson(alarmStrategy);
        String str = client.updateHostAlarmStrategy(token, alarmStrategyJson);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public TaskIntermediateResult updateAlarmStrategy(
            VmAlarmStrategy alarmStrategy) {
        String alarmStrategyJson = FormatUtil.toJson(alarmStrategy);
        String str = client.updateAlarmStrategy(token, alarmStrategyJson);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public TaskIntermediateResult updateDataStoreAlarmStrategy(
            DataStoreAlarmStrategy alarmStrategy) {
        String alarmStrategyJson = FormatUtil.toJson(alarmStrategy);
        String str = client.updateDataStoreAlarmStrategy(token,
                alarmStrategyJson);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public HostAlarmStrategy getHostAlaramStrategy(String uuid) {
        String str = client.getHostAlaramStrategy(token, uuid);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, HostAlarmStrategy.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return (HostAlarmStrategy) ((List) result.getData()).get(0);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public DataStoreAlarmStrategy getDataStoreAlarmStrategy(String uuid) {
        String str = client.getDataStoreAlarmStrategy(token, uuid);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, DataStoreAlarmStrategy.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return (DataStoreAlarmStrategy) ((List) result.getData()).get(0);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public VmAlarmStrategy getVmAlaramStrategy(String uuid) {
        String str = client.getVmAlarmStrategy(token, uuid);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, VmAlarmStrategy.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return (VmAlarmStrategy) ((List) result.getData()).get(0);
        }
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the client
     */
    public AlarmRestService getClient() {
        return client;
    }

    /**
     * @param client
     *            the client to set
     */
    public void setClient(AlarmRestService client) {
        this.client = client;
    }

}
