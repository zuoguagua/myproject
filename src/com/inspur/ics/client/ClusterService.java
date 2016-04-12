package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.HeartbeatDevice;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.O2cbConfig;
import com.inspur.ics.pojo.OcfsDataStore;

/**
 * 集群服务接口.
 * @author zhangjun
 */
public interface ClusterService {

    /**
     * 向集群添加心跳设备.
     * @param clusterUuid 集群的UUID
     * @param hostUuid 提供心跳设备的主机的UUID
     * @param hbDevices 待添加的心跳设备
     * @return 返回任务消息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult addHeartbeatDevice(String clusterUuid, String hostUuid,
			List<HeartbeatDevice> hbDevices);

    /**
     * 向集群添加计算节点.
     * @param clusterUuid 集群的UUID
     * @param addHostUuids 待添加计算节点的UUID
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult addHost(String clusterUuid, String[] addHostUuids);

    /**
     * 创建集群.
     * <pre>
     * 	Cluster cluster = new Cluster();

    	// 配置集群的配置信息.
    	O2cbConfig o2cbConfig = new O2cbConfig();
    	o2cbConfig.setHeartbeatThreshold(61);
    	o2cbConfig.setIdleTimeout(30000);
    	o2cbConfig.setKeepaliveDelay(2000);
    	o2cbConfig.setReconnectDelay(2000);
    	cluster.setO2cbConfig(o2cbConfig);

    	// 添加的物理主机.
    	List&lt;Host&gt; clusterNodes = new ArrayList&lt;Host&gt;();
    	Host host1 = new Host();
    	Host host2 = new Host();
    	host1.setUuid("");
    	host2.setUuid("");
    	clusterNodes.add(host1);
    	clusterNodes.add(host2);
    	cluster.setHosts(clusterNodes);

    	// 执行格式化心跳设备或CFS数据存储的物理主机.
    	String opHostUuid = "";

    	// 添加的心跳设备.
    	List&lt;HeartbeatDevice&gt; hbDevices = new ArrayList&lt;HeartbeatDevice&gt;();
    	// 使用块设备作为心跳设备，可以有多个.
    	HeartbeatDevice hb1 = new HeartbeatDevice();
    	BlockDevice bd1 = new BlockDevice();
    	bd1.setUuid("");
    	hb1.setBlockDevice(bd1);
    	hbDevices.add(hb1);
    	// 使用CFS数据存储作为心跳设备, 可以有多个.
    	HeartbeatDevice hb2 = new HeartbeatDevice();
    	OcfsDataStore ods = new OcfsDataStore();
    	ods.setName("TEST_CFS");
    	ods.setMaxSlots(16);
    	// CFS数据存储使用的块设备.
    	List&lt;BlockDevice&gt; bdOnOds = new ArrayList&lt;BlockDevice&gt;();
    	BlockDevice bd2 = new BlockDevice();
    	bd2.setUuid("");
    	bdOnOds.add(bd2);
    	ods.setBlockDevices(bdOnOds);
    	// 挂载CFS数据存储的物理节点.
    	List&lt;Host&gt; mountHosts = new ArrayList&lt;Host&gt;();
    	mountHosts.add(host1);
    	mountHosts.add(host2);
    	ods.setHosts(mountHosts);
    	hb2.setOcfsDataStore(ods);
    	hbDevices.add(hb2);
    	cluster.setHbDevices(hbDevices);

    	clusterService.createCluster(cluster, opHostUuid);
     * </pre>
     * @param cluster 待创建集群（需包含必要的集群信息）
     * @param hostUuid 执行格式化心跳设备的主机的UUID
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult createCluster(Cluster cluster, String hostUuid);

    /**
     * 获取系统中所有已创建的集群及其相关信息.
     * @return 返回集群列表
     */
    List<Cluster> getAllCluster();

    /**
     * 获取集群中所使用的心跳设备.
     * @param clusterUuid 集群的UUID
     * @return 返回心跳设备列表
     */
    List<HeartbeatDevice> getAllHeartbeatDevice(String clusterUuid);

    /**
     * 获取系统中所有可用于集群操作的计算节点.
     * @return 返回可用计算节点列表
     */
    List<Host> getAvailableHosts();

    /**
     * 获取能用做心跳设备的CFS数据存储.
     * @param clusterUuid 集群的UUID
     * @return 返回可用的CFS数据存储
     */
    List<OcfsDataStore> getAvailableOcfsDataStoreForHeartbeatDevice(String clusterUuid);

    /**
     * 获取特定集群的相关信息.
     * @param clusterUuid 集群的UUID
     * @return 返回集群对象
     */
    Cluster getClusterInfo(String clusterUuid);

    /**
     * 获取集群运行配置信息.
     * @param clusterUuid 集群的UUID
     * @return 返回集群运行配置信息
     */
    O2cbConfig getO2cbConfig(String clusterUuid);

    /**
     * 修改集群名称.
     * @param clusterUuid 集群的UUID
     * @param newName 集群新的名称
     * @return 返回任务消息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult modifyClusterName(String clusterUuid, String newName);

    /**
     * 移除集群.
     * @param clusterUuid 集群的UUID
     * @return 返回任务消息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeCluster(String clusterUuid);

    /**
     * 从集群中移除心跳设备.
     * @param clusterUuid 集群的UUID
     * @param hbUuids 待移除心跳设备的UUID
     * @return 返回任务消息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeHeartbeatDevice(String clusterUuid, String[] hbUuids);

    /**
     * 从集群中移除计算节点.
     * @param clusterUuid 集群的UUID
     * @param removeHostUuids 待移除计算节点的UUID
     * @return 返回任务消息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeHost(String clusterUuid, String[] removeHostUuids);

    /**
     * 更新集群的运行配置信息.
     * @param clusterUuid 集群的UUID
     * @param newConfig 新的运行配置信息
     * @return 返回任务信息
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult updateO2cbConfig(String clusterUuid, O2cbConfig newConfig);

}
