package com.inspur.ics.client;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.dto.ClusterAndHostDTO;
import com.inspur.ics.dto.StatDataDTO;
import com.inspur.ics.dto.VClusterAndVMDTO;
import com.inspur.ics.dto.VMAndTemplateDTO;
import com.inspur.ics.dto.VirtualSwitchDTO;
import com.inspur.ics.pojo.SystemOverviewInfo;


/**
 * 拓扑服务接口.
 * @author ychau
 */
public interface SystemTopologyService {
    /**
     * 获取集群与主机信息的拓扑接口.
     * @return 集群与主机信息数据集合
     */
    ClusterAndHostDTO getClusterAndHostTopo();

    /**
     * 获取集群中通讯正常的主机信息的拓扑接口.
     * @return 通讯正常状态的集群与主机数据集合
     */
    ClusterAndHostDTO getClusterAndHostAvailableTop();

    /**
     * 获取虚拟机与虚拟机模板数据的拓扑接口.
     * @return 虚拟机与虚拟机模板数据集合
     */
    VMAndTemplateDTO getVMAndTemplateTopo();

    /**
     * 获取虚拟交换机信息的拓扑接口.
     * @return 虚拟交换机数据集合
     */
    VirtualSwitchDTO getVirtualSwitchTopo();

    /**
     * 获取系统内各种资源关联资源统计信息的拓扑接口.
     * @param targetType
     *            资源类型
     * @param targetUuid
     *            资源uuid标示
     * @return 资源及其相关项统计信息
     */
    StatDataDTO getStatDataTopo(TaskTargetType targetType, String targetUuid);

    /**
     * 获取系统综合资源信息的拓扑接口.
     * @return 系统综合资源信息
     */
    ClusterAndHostDTO getSystemTopo();

    /**
     * 获取系统资源统计信息.
     * @return 系统资源统计信息
     */
    SystemOverviewInfo getSystemOverview();

    /**
     * 获取系统硬件设备的拓扑接口.
     * @return 系统集群主机存储设备信息
     */
    ClusterAndHostDTO getSystemPhysicalTopo();

    /**
     * 获取虚拟设备拓扑数据.
     * @return 系统虚拟集群虚拟机虚拟交换机信息
     */
    VClusterAndVMDTO getSystemVirtualTopo();
}
