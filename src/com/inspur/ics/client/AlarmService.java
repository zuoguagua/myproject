package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.AlarmEvent;
import com.inspur.ics.pojo.alarm.DataStoreAlarmStrategy;
import com.inspur.ics.pojo.alarm.HostAlarmStrategy;
import com.inspur.ics.pojo.alarm.VmAlarmStrategy;

/**
 * 告警服务接口.
 * @author kangzhx
 */
public interface AlarmService {
    /**
     * 查看所有报警事件.
     * @return 报警事件列表
     */
    List<AlarmEvent> getAllEvents();

    /**
     * 更新主机的告警策略.
     * @param alarmStrategy
     *            告警策略
     * @return 任务执行结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult updateHostAlarmStrategy(HostAlarmStrategy alarmStrategy);

    /**
     * 更新虚拟机的告警策略.
     * @param alarmStrategy
     *            告警策略
     * @return 任务执行结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult updateAlarmStrategy(VmAlarmStrategy alarmStrategy);

    /**
     * 更新数据存储的告警策略.
     * @param alarmStrategy
     *            告警策略
     * @return 任务执行结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult updateDataStoreAlarmStrategy(
            DataStoreAlarmStrategy alarmStrategy);

    /**
     * 获取主机告警策略.
     * @param uuid
     *            主机唯一的标识UUID
     * @return 主机的告警策略
     */
    HostAlarmStrategy getHostAlaramStrategy(String uuid);

    /**
     * 获取虚拟机的告警策略.
     * @param uuid
     *            虚拟机唯一的标识UUID
     * @return 虚拟机的告警策略
     */
    VmAlarmStrategy getVmAlaramStrategy(String uuid);

    /**
     * 获取数据存储的告警策略.
     * @param uuid
     *            唯一的标识UUID
     * @return 数据存储的告警策略
     */
    DataStoreAlarmStrategy getDataStoreAlarmStrategy(String uuid);

}
