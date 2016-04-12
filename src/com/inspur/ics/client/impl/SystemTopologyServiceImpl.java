package com.inspur.ics.client.impl;

import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.SystemTopologyService;
import com.inspur.ics.client.rest.SystemTopologyRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.dto.ClusterAndHostDTO;
import com.inspur.ics.dto.StatDataDTO;
import com.inspur.ics.dto.VClusterAndVMDTO;
import com.inspur.ics.dto.VMAndTemplateDTO;
import com.inspur.ics.dto.VirtualSwitchDTO;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.SystemOverviewInfo;

/**
 * 拓扑服务接口实现类.
 * @author ychau
 */
public class SystemTopologyServiceImpl implements SystemTopologyService {
    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 拓扑服务REST接口服务类.
     */
    private SystemTopologyRestService restService;

    /**
     *  拓扑服务接口实现类构造方法.
     * @param url 请求Action路径
     * @param token 用户会话令牌.
     */
    public SystemTopologyServiceImpl(String url, String token) {
        this.restService = ProxyFactory.create(SystemTopologyRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取集群与主机信息的拓扑接口.
     * @return 集群与主机信息数据集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public ClusterAndHostDTO getClusterAndHostTopo() {
        String resultXML = restService.getClusterAndHostTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, ClusterAndHostDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<ClusterAndHostDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取集群中通讯正常的主机信息的拓扑接口.
     * @return 通讯正常状态的集群与主机数据集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public ClusterAndHostDTO getClusterAndHostAvailableTop() {
        String resultXML = restService.getClusterAndHostAvailableTop(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, ClusterAndHostDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<ClusterAndHostDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取虚拟机与虚拟机模板数据的拓扑接口.
     * @return 虚拟机与虚拟机模板数据集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public VMAndTemplateDTO getVMAndTemplateTopo() {
        String resultXML = restService.getVMAndTemplateTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, VMAndTemplateDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<VMAndTemplateDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取虚拟交换机信息的拓扑接口.
     * @return 虚拟交换机数据集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public VirtualSwitchDTO getVirtualSwitchTopo() {
        String resultXML = restService.getVirtualSwitchTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, VirtualSwitchDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<VirtualSwitchDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取系统内各种资源关联资源统计信息的拓扑接口.
     * @param targetType
     *            资源类型
     * @param targetUuid
     *            资源uuid标示
     * @return 资源及其相关项统计信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public StatDataDTO getStatDataTopo(TaskTargetType targetType, String targetUuid) {
        String resultXML = restService.getStatDataTopo(token, targetType, targetUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, StatDataDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<StatDataDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取系统综合资源信息的拓扑接口.
     * @return 系统综合资源信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public ClusterAndHostDTO getSystemTopo() {
        String resultXML = restService.getSystemTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, ClusterAndHostDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<ClusterAndHostDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取系统资源统计信息.
     * @return 系统资源统计信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public SystemOverviewInfo getSystemOverview() {
        String resultXML = restService.getSystemOverview(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, SystemOverviewInfo.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<SystemOverviewInfo>) result.getData()).get(0);
        }
    }

    /**
     * 获取系统硬件设备的拓扑接口.
     * @return 系统集群主机存储设备信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public ClusterAndHostDTO getSystemPhysicalTopo() {
        String resultXML = restService.getSystemPhysicalTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, ClusterAndHostDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<ClusterAndHostDTO>) result.getData()).get(0);
        }
    }

    /**
     * 获取虚拟设备拓扑数据.
     * @return 系统虚拟集群虚拟机虚拟交换机信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public VClusterAndVMDTO getSystemVirtualTopo() {
        String resultXML = restService.getSystemVirtualTopo(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
                new Class[]{Result.class, VClusterAndVMDTO.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<VClusterAndVMDTO>) result.getData()).get(0);
        }
    }

}
