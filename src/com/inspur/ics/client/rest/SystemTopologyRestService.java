package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.inspur.ics.common.Types.TaskTargetType;

/**
 * 拓扑服务REST接口.
 * @author ychau
 */
@Path("/")
public interface SystemTopologyRestService {
    /**
     * 获取集群与主机信息的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 集群与主机信息数据集合
     */
    @POST
    @Path("system_topo_getClusterAndHostTopo")
    String getClusterAndHostTopo(@FormParam("token") String token);

    /**
     * 获取集群中通讯正常的主机信息的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 通讯正常状态的集群与主机数据集合
     */
    @POST
    @Path("system_topo_getClusterAndHostAvailableTopo")
    String getClusterAndHostAvailableTop(@FormParam("token") String token);

    /**
     * 获取虚拟机与虚拟机模板数据的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 虚拟机与虚拟机模板数据集合
     */
    @POST
    @Path("system_topo_getVMAndTemplateTopo")
    String getVMAndTemplateTopo(@FormParam("token") String token);

    /**
     * 获取虚拟交换机信息的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 虚拟交换机数据集合
     */
    @POST
    @Path("system_topo_getVirtualSwitchTopo")
    String getVirtualSwitchTopo(@FormParam("token") String token);

    /**
     * 获取系统内各种资源关联资源统计信息的拓扑接口.
     * @param token 操作用户会话令牌
     * @param targetType
     *            资源类型
     * @param targetUuid
     *            资源uuid标示
     * @return 资源及其相关项统计信息
     */
    @POST
    @Path("system_topo_getStatDataTopo")
    String getStatDataTopo(@FormParam("token") String token,
            @FormParam("targetType") TaskTargetType targetType,
            @FormParam("targetUuid") String targetUuid);

    /**
     * 获取系统综合资源信息的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 系统综合资源信息
     */
    @POST
    @Path("system_topo_getSystemTopo")
    String getSystemTopo(@FormParam("token") String token);

    /**
     * 获取系统资源统计信息.
     * @param token 操作用户会话令牌
     * @return 系统资源统计信息
     */
    @POST
    @Path("system_topo_getSystemOverview")
    String getSystemOverview(@FormParam("token") String token);

    /**
     * 获取系统硬件设备的拓扑接口.
     * @param token 操作用户会话令牌
     * @return 系统集群主机存储设备信息
     */
    @POST
    @Path("system_topo_getSystemPhysicalTopo")
    String getSystemPhysicalTopo(@FormParam("token") String token);

    /**
     * 获取虚拟设备拓扑数据.
     * @param token 操作用户会话令牌
     * @return 虚拟设备拓扑数据.
     */
    @POST
    @Path("system_topo_getSystemVirtualTopo")
    String getSystemVirtualTopo(@FormParam("token") String token);
}
