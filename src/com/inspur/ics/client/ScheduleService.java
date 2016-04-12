package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.ScheduledTask;

/**
 * @author kangzhx
 */
public interface ScheduleService {
    /**
     * 创建调度任务.
     * @param scheduledTask
     *            调度任务
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createScheduledTask(ScheduledTask scheduledTask);

    /**
     * 修改调度任务.
     * @param scheduledTask
     *            调度任务
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult modifyScheduledTask(ScheduledTask scheduledTask);

    /**
     * 删除调度任务.
     * @param id
     *            调度的任务的id
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult deleteScheduledTask(int id);

    /**
     * 获取所有的调度任务.
     * @param definedUuid
     *            目标的UUID
     * @return 调度任务列表
     */
    List<ScheduledTask> getAllScheduledTask(String definedUuid);

    /**
     * 获取对应的id的调度任务.
     * @param id
     *            调度任务的id
     * @return 调度任务
     */
    ScheduledTask getScheduledTask(int id);
}
