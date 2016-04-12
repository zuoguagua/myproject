package com.inspur.autotest;

import org.testng.annotations.Test;

import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.SystemTopologyService;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.dto.ClusterAndHostDTO;
import com.inspur.ics.dto.StatDataDTO;
import com.inspur.ics.dto.VClusterAndVMDTO;
import com.inspur.ics.dto.VMAndTemplateDTO;
import com.inspur.ics.dto.VirtualSwitchDTO;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.SystemOverviewInfo;

/**
 * SystemTopologyService 测试类.
 * @author ychau
 */
public class SystemTopologyServiceTest {
    /**
     * SystemTopologyService.
     */
    private static SystemTopologyService service;
    private static HostService hostService;
    private String hostUuid = "d79e99e1-0d45-4ce2-b165-889627d0dbf4";

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getSystemTopologyService();
        hostService = factory.getHostService();
        System.out.println("Init Junit Test");
    }
    @Parameters({"host1ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.ScheduleServiceTest.testCreateScheduledTask"},alwaysRun=true)
	public void initPara(String host1ip) {
    	 List<Host> is = hostService.getAllHostList();
         AssertJUnit.assertNotNull(is);
         for (Host host : is) {
             if(host.getIp().equals(host1ip)) {
             	 this.hostUuid = host.getUuid(); // huoqu UUID
             	 break;
             }
         }
	}

    /**
     * 获取集群与主机信息的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getClusterAndHostTopo() {
        ClusterAndHostDTO dto = service.getClusterAndHostTopo();
        AssertJUnit.assertNotNull(dto);
//        System.out.println("The Count of the Cluster in the System: " + dto.getClusters().size());
//        System.out.println("The Count of the Host Not in the Cluster: " + dto.getClusters().size());
    }

    /**
     * 获取集群中通讯正常的主机信息的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getClusterAndHostAvailableTop() {
        ClusterAndHostDTO dto = service.getClusterAndHostAvailableTop();
        AssertJUnit.assertNotNull(dto);
//        System.out.println("The Count of the Available Cluster in the System: " + dto.getClusters().size());
//        System.out.println("The Count of the Available Host Not in the Cluster: " + dto.getClusters().size());
    }

    /**
     * 获取虚拟机与虚拟机模板数据的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getVMAndTemplateTopo() {
        VMAndTemplateDTO dto = service.getVMAndTemplateTopo();
        AssertJUnit.assertNotNull(dto);
//        System.out.println("Count of the VCluster: " + dto.getvClusterDTOs().size());
//        System.out.println("Count of the VCluster Template: " + dto.getVcTemplateDTOs().size());
//        System.out.println("Count of the VM: " + dto.getVmdtos().size());
//        System.out.println("Count of the VM Template: " + dto.getVmTemplateDTOs().size());
    }

    /**
     * 获取虚拟交换机信息的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getVirtualSwitchTopo() {
        VirtualSwitchDTO dto = service.getVirtualSwitchTopo();
        AssertJUnit.assertNotNull(dto);
//        System.out.println(dto.getDvSwitchDTOs().size());
//        System.out.println(dto.getStandardPortGroupDTOs().size());
    }

    /**
     * 获取系统内各种资源关联资源统计信息的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getStatDataTopo() {
//        String targetUuid = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
    	String targetUuid = hostUuid;
        TaskTargetType targetType = TaskTargetType.HOST;
        StatDataDTO dto = service.getStatDataTopo(targetType, targetUuid);
        AssertJUnit.assertNotNull(dto);
//        System.out.println(FormatUtil.toJson(dto));
    }

    /**
     * 获取系统综合资源信息的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getSystemTopo() {
        ClusterAndHostDTO dto = service.getSystemTopo();
        AssertJUnit.assertNotNull(dto);
//        System.out.println(dto.getHosts());
    }

    /**
     * 获取系统资源统计信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getSystemOverview() {
        SystemOverviewInfo info = service.getSystemOverview();
        AssertJUnit.assertNotNull(info);
//        System.out.println(FormatUtil.toJson(info));
    }

    /**
     * 获取系统硬件设备的拓扑接口.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getSystemPhysicalTopo() {
        ClusterAndHostDTO dto = service.getSystemPhysicalTopo();
        AssertJUnit.assertNotNull(dto);
//        System.out.println(FormatUtil.toJson(dto.getClusters()));
//        System.out.println(FormatUtil.toJson(dto.getHosts()));
    }

    /**
     * 获取虚拟设备拓扑数据.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getSystemVirtualTopo() {
        VClusterAndVMDTO dto = service.getSystemVirtualTopo();
        AssertJUnit.assertNotNull(dto);
        //        System.out.println(dto.getVclusters().size());
//        System.out.println(dto.getVms().size());
    }

}
