package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.SystemConfig;

/**
 * 系统配置服务接口.
 * @author ychau
 */
public interface SystemConfigService {

    /**
     * 获取系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @return 系统事件、任务记录保留配置信息
     */
    SystemConfig getSystemConfigInfo();

    /**
     * 设置系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @param systemConfig 更新系统事件、任务记录保留配置信息
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult setSystemConfigInfo(SystemConfig systemConfig);

    /**
     * 设置icenter管理节点NTP服务器 .
     * @param ntpServers 新的NTP服务器 列表
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult setNtpServers(String[] ntpServers);

    /**
     * 获取icenter管理节点NTP服务器 .
     * @return NTP服务器 列表
     */
    List<String> getNtpServers();

    /**
     * 获取虚拟平台版本.
     * @return 虚拟平台版本
     */
    String getIcsVersion();

}
