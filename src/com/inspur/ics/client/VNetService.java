package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.dto.ClusterAndHostDTO;
import com.inspur.ics.pojo.DVPortGroup;
import com.inspur.ics.pojo.DVSwitch;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.PNic;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StandardSwitch;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VirtualSwitch;



/**
 * 网络服务接口
 * @author jingshsh
 */
@SuppressWarnings("rawtypes")
public interface VNetService{
	
    /**
     * 列出标准交换机列表
     * @return 标准交换机列表
     */
	public List<StandardSwitch> listStandardSwitchFromDB();
    
    /**
     * 列出指定交换机上的端口组
     * @param swUuid
     * 			 交换机的UUID
     * @return 端口组列表
     */
    List<StandardPortGroup> listStandardSwitchPortgroupFromDB(String swUuid);

    /**
     * 列出端口组（按名称）上的虚拟机列表
     * @param portgroup
     *            端口组名称
     * @return List<VM>
     */
    public List<VM> listStandardSwitchPortgroupVmFromDB(String portGroup);

    /**
     * 列出未使用的网卡
     * @return 未使用的网卡列表
     */
    List<PNic> listUnusedPNicFromDB();

    /**
     * 列出指定主机上的端口组
     * @param hostUuid
     * 			  主机的UUID
     * @return 端口组列表
     */
    List<PortGroup> listPortGroupByHostUuid(String hostUuid);

    /**
     * 增加@20150610 by wangyanjia
     * 根据端口组获取所在的交换机
     * @param pgUuid
     *            端口组的UUID
     * @return 端口组列表
     */
    VirtualSwitch getVswitchByPortGroupUuid(String pgUuid);

    /**
     * 列出主机上的虚拟交换机
     * @param hostUuid
     *            主机的UUID
     * @return 虚拟交换机列表
     */
    List<VirtualSwitch> listSwitchByHostUuid(String hostUuid);

    /**
     * 列出主机的未使用的网卡
     * @param hostUuid
     *            主机的UUID
     * @return 网卡列表
     */
    List<PNic> listHostUnusedPNic(String hostUuid);

    /**
     * 列出主机的所有网卡
     * @param hostUuid
     *            主机的UUID
     * @return 网卡列表
     */
    List<PNic> listHostPNics(String hostUuid);

    /**
     * 列出指定主机上所有数据网端口组（含管理网端口组）
     * @param hostUuid
     *            主机的UUID
     * @return 标准端口组列表
     */
    List<StandardPortGroup> listStandardPortGroupWithIPByHostUuid(
            String hostUuid);

    /**
     * 获取标准交换机的信息
     * @param swUuid
     *            交换机UUID
     * @return 交换机类
     */
    StandardSwitch getStandardSwitchInfo(String swUuid);

    /**
     * 获取所有的标准端口组
     * @return 标准端口组列表
     */
    List<StandardPortGroup> showStandardNetworksOverview();

    /**
     * 创建标准交换机
     * @param sw
     *            标准交换机
     * @param destHostUUID
     *            目的主机UUID
     * @return 任务的中间执行结果.
     */
	TaskIntermediateResult createStandardSwitch(StandardSwitch sw, String destHostUUID);

    /**
     * 给标准交换机添加网卡
     * @param sw
     *            标准交换机
     * @return 任务的中间执行结果.
     */
    TaskIntermediateResult modifyStandardSwitch(StandardSwitch sw);

    /**
     * 创建标准交换机端口组
     * @param swUuid
     *            标准交换机UUID
     * @param portgroup
     *            标准端口组
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult createStandardSwitchPortGroup(String swUuid,
            StandardPortGroup portgroup);

    /**
     * 删除标准交换机
     * @param swUuid
     *            标准交换机UUID
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult deleteStandardSwitch(String swUuid);

    /**
     * 删除标准交换机端口组
     * @param portGroupUuid
     *            标准端口组UUID
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult deleteStandardSwitchPortGroup(String portGroupUuid);

    /**
     * 列出所有的分布式交换机
     * @return 分布式交换机列表
     */
    List<DVSwitch> getAllDVSwitch();

    /**
     * 列出分布式交换机上的主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    List<Host> listDVSwitchHostFromDB(String dvswUuid);

    /**
     * 列出指定分布式端口上的虚拟机列表
     * @param dvPortGroupUuid
     *            分布式端口组UUID
     * @return 虚拟机
     */
    List<VM> getVmsOnPortGroup(String dvPortGroupUuid);

    @Deprecated
    /**
     * 使用getHostInDVSwitch，该函数获取的结果不准确
     * 列出分布式交换机上的所有主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    List<Host> getHostsOnDVSwitch(String dvswUuid);

    /**
     * 获取分布式交换机拓扑结构
     * @param dvSwUuid
     *            分布式交换机UUID
     * @return 分布式交换机对象
     */
    DVSwitch getDVSwitchInfo(String dvSwUuid);

    /**
     * 列举系统中所有的分布式端口组
     * @return 分布式端口组列表
     */
    List<DVPortGroup> showDistributeNetworkOverview();


    /**
     * 获取能添加到分布式交换的主机
     * @param dvswUuid
     *             分布式交换机UUID
     * @return 主机列表
     */
    List<Host> getAvailableHostForDVSwitch(String dvswUuid);

    /**
     * 列出分布式交换机上的主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    List<Host> getHostInDVSwitch(String dvswUuid);

    /**
     * 获取在指定主机上被交换机使用的网卡
     * @param hostUuid
     *            主机UUID
     * @param swUuid
     *            交换机UUID
     * @return 网卡列表
     */
    List<PNic> getPNicUsedBySwitch(String hostUuid, String swUuid);

    /**
     * 创建分布式虚拟交换机
     * @param clusterUuid
     *            集群UUID
     * @param dvsw
     *            分布式虚拟交换机
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult createDVSwitch(String clusterUuid,
            DVSwitch dvsw);


    /**
     * 删除分布式虚拟交换机
     * @param dvswUuid
     *            分布式虚拟交换机UUID
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult deleteDVSwitch(String dvswUuid);

    /**
     * 配置分布式虚拟交换机：添加或删除主机
     * @param dvsw
     *            分布式虚拟交换机
     * @param flag
     *            true:添加主机
     * @return 任务的中间执行结果
     */
	TaskIntermediateResult configDVSwitch(DVSwitch dvsw,
            boolean flag);

    /**
     * 配置分布式交换机中指定主机的物理网卡与该交换机关系
     * @param dvswUuid
     *            分布式交换机UUID
     * @param host
     * 			  包含指定网卡的主机
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult configNicOnDVSwitch(String dvswUuid,
            Host host);

    /**
     * 创建分布式端口组
     * @param dvswUuid
     *            分布式虚拟交换机UUID
     * @param portGroup
     *            端口组
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult createDVPortGroup(String dvswUuid, DVPortGroup portGroup);

    /**
     * 删除分布式端口组
     * @param dvpgUuid
     *            分布式虚拟交换机UUID
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult deleteDVPortGroup(String dvpgUuid);

    /**
     * 获取指定目标的相关联的端口组
     * @param targetType
     *            任务目标类型
     * @param targetUuid
     *            目标UUID
     * @return 端口组列表
     */
    List<PortGroup> getPortGroupList(TaskTargetType targetType,
            String targetUuid);

    /**
     * 获取指定目标的相关联的交换机列表
     * @param targetType
     *            任务目标类型
     * @param targetUuid
     *            目标UUID
     * @return 分布式交换机列表
     */
    List<DVSwitch> getDVSwitchList(TaskTargetType targetType, String targetUuid);

    /**
     * 获取主机上的管理端口组
     * @param hostUuid
     *            主机UUID
     * @return 标准端口组
     */
    StandardPortGroup getManagementPortgroupOnHost(String hostUuid);
    
    /**
     * 扫描物理适配器
     * @param hostUuid
     *            主机UUID
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult scanEthernetAdapters(String hostUuid);
    
    /**
     * 配置端口组
     * @param portGroup
     *            端口组
     * @return 任务的中间执行结果
     */
    TaskIntermediateResult configPortgroup(PortGroup portGroup);
}
