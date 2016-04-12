package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.ClusterGroup;
import com.inspur.ics.pojo.DrsRule;
import com.inspur.ics.pojo.DrsStrategy;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.VM;

/**
 * DRS调试设置接口.
 * @author kangzhx
 */
public interface DrsService {

    /**
     * 获取drs是否开启.
     * @param clusterUUID
     *            集群的UUID
     * @return 返回状态
     */
    boolean isDrsEnabled(String clusterUUID);

    /**
     * 配置drs.
     * @param clusterUUID
     *            集群的UUID
     * @param drsEnabled
     *            drs是否开启
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult configDrs(String clusterUUID, boolean drsEnabled);

    /**
     * 获取DRS策略.
     * @param clusterUUID
     *            集群UUID
     * @return drs策略
     */
    DrsStrategy getDrsStrategy(String clusterUUID);

    /**
     * 配置DRS策略.
     * @param clusterUUID
     *            集群UUID
     * @param drsStrategy
     *            drs策略
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult configStrategy(String clusterUUID,
            DrsStrategy drsStrategy);

    /**
     * 创建DRS组.
     * @param clusterGroup
     *            集群组
     * @param clusterUUID
     *            集群UUID
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createDrsGroup(ClusterGroup clusterGroup,
            String clusterUUID);

    /**
     *创建DRS组,分为主机DRS组，虚拟机DRS组，在创建时必须选择主机或者虚拟机，否则会创建失败.
     * @param clusterGroup
     *            drs组对象
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult modifyDrsGroup(ClusterGroup clusterGroup);

    /**
     * 删除drs组.
     * @param id
     *            drs组对应的id
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult deleteDrsGroup(int id);

    /**
     * 获取在集群中的drs组.
     * @param clusterUUID
     *            集群UUID
     * @return drs组列表
     */
    List<ClusterGroup> getDrsGroupInCluster(String clusterUUID);

    /**
     * 获取drs组信息.
     * @param id
     *            drs组对应的id
     * @return drs组对象
     */
    ClusterGroup getDrsGroupInfo(int id);

    /**
     * 获取可以加入该drs组策略的虚拟机.
     * @param clusterUUID
     *            集群对应的UUID
     * @param id
     *            drs组对应的id
     * @return 虚拟机列表
     */
    List<VM> getAddableVms(String clusterUUID, int id);

    /**
     * 获取可以加入该drs组策略的主机.
     * @param clusterUUID
     *            集群UUID
     * @param id
     *            drs组id
     * @return 主机列表
     */
    List<Host> getAddableHosts(String clusterUUID, int id);

    /**
     * 创建drs规则.
     * @param drsRule
     *            drs规则对象
     * @param clusterUUID
     *            集群的UUID
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createDrsRule(DrsRule drsRule, String clusterUUID);

    /**
     * 改变drs规则.
     * @param drsRule
     *            drs规则对象
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult modifyDrsRule(DrsRule drsRule);

    /**
     * 删除drs规则.
     * @param id
     *            drs的id
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult deleteDrsRule(int id);

    /**
     * 获取在集群中的drs规则.
     * @param clusterUUID
     *            集群UUID
     * @return drs规则列表
     */
    List<DrsRule> getDrsRuleInCluster(String clusterUUID);

    /**
     * 获取drs规则.
     * @param id
     *            drs规则的id
     * @return drs规则对象
     */
    DrsRule getDrsRuleInfo(int id);

    /**
     * 获取可加入该drs规则的虚拟机列表.
     * @param clusterUUID
     *            集群UUID
     * @param id
     *            drs的id
     * @return 虚拟机列表
     */
    List<VM> getAddableVmsForRule(String clusterUUID, int id);
}
