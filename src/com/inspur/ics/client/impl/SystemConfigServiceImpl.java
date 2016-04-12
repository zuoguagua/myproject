package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.SystemConfigService;
import com.inspur.ics.client.rest.SystemConfigRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.SystemConfig;

/**
 * 系统配置服务接口实现类.
 * @author ychau
 */
public class SystemConfigServiceImpl implements SystemConfigService {
    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 系统配置服务REST接口服务类.
     */
    private SystemConfigRestService restService;

    /**
     * 系统配置服务接口实现类构造方法.
     * @param url
     *            请求Action路径
     * @param token
     *            用户会话令牌.
     */
    public SystemConfigServiceImpl(String url, String token) {
        this.restService = ProxyFactory.create(SystemConfigRestService.class,
                url, ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @return 系统事件、任务记录保留配置信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public SystemConfig getSystemConfigInfo() {
        String resultXML = restService.getSystemConfigInfo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, SystemConfig.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SystemConfig>) result.getData()).get(0);
        }
    }

    /**
     * 设置系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @param systemConfig
     *            更新系统事件、任务记录保留配置信息
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult setSystemConfigInfo(SystemConfig systemConfig) {
        String resultStr = restService.setSystemConfigInfo(token, FormatUtil.toJson(systemConfig));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 设置icenter管理节点NTP服务器 .
     * @param ntpServers
     *            新的NTP服务器 列表
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult setNtpServers(String[] ntpServers) {
        String resultStr = restService.setNtpServers(token, ntpServers);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    /**
     * 获取icenter管理节点NTP服务器 .
     * @return NTP服务器 列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getNtpServers() {
        String resultStr = restService.getNtpServers(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, String.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<String>();
        } else {
            return (List<String>) result.getData();
        }
    }
    /**
     * 获取虚拟平台版本.
     * @return 虚拟平台版本
     */
    @Override
    public String getIcsVersion() {
        return "InCloud Sphere v4.0.1(β)";
    }
}
