package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.CdromDevice;
import com.inspur.ics.pojo.DeviceSection;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostIpmiInfo;
import com.inspur.ics.pojo.MultipathConfig;
import com.inspur.ics.pojo.MultipathSection;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.UsbDevice;
import com.inspur.ics.pojo.monitor.HostMonitorInfo;
import com.inspur.ics.pojo.monitor.ServiceMonitorInfo;

/**
 * @author liuyi
 */
public class HostServiceTest {

    /**
     * HostServiceImpl.
     */
    private static HostService hostService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private String uuid = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
    private String uuid9 = "f5a6a056-b134-439f-8fbd-23072d4be020";

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        hostService = factory.getHostService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
    }

    /**
     * 扫描主机.
     */
    @Parameters({"inet", "masknum"})
    @Test(groups={"query"},alwaysRun=true)
    public void scanHosts(String inet,int masknum) {
    	 List<String> ips = hostService.scanHosts(inet, masknum);
        AssertJUnit.assertNotNull(ips);
//        for (String ip : ips) {
//            System.out.println("12 扫描主机:" + ip);
//        }
    }
    /**
     * 列表式添加主机测试.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Parameters({"host1ip", "host1password","host2ip","host2password"})
    @Test(groups={"create"},alwaysRun=true)
    public void addHosts(String host1ip,String host1password,String host2ip,String host2password) throws Exception {
        List<Host> hosts = new ArrayList<Host>();
        Host host = new Host();
        host.setIp(host1ip);
        host.setPassword(host1password);
        hosts.add(host);
        Host host1 = new Host();
        host1.setIp(host2ip);
        host1.setPassword(host2password);
        hosts.add(host1);
        TaskIntermediateResult task = hostService.addHosts(hosts);
        TaskInfo taskInfo = TestUtil.waitFor(task.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("1 添加主机测试:" + task.getTaskId());
    }
    /**
     * 获取所有主机列表测试.
     */
    @Parameters({"host1ip","host2ip"})
    @Test(groups={"query"},alwaysRun=true)
    public void getAllHostList(String host1ip,String host2ip) {
        List<Host> is = hostService.getAllHostList();
        AssertJUnit.assertNotNull(is);
        for (Host host : is) {
            System.out.println("3 获取所有主机列表测试:" + host.getIp() + "uuid:"
                    + host.getUuid());
            if(host.getIp().equals(host1ip)) {//8.5
            	 this.uuid = host.getUuid(); // huoqu UUID
            } else if(host.getIp().equals(host2ip)){
            	this.uuid9 = host.getUuid(); // huoqu UUID
            }
        }

    }
    /**
     * 判断目标主机密码测试.
     */
    @Parameters({"host1ip", "host1password"})
    @Test(groups={"query"},alwaysRun=true)
    public void isRightPassword(String host1ip,String host1password) {
        Host host = new Host();
        host.setIp(host1ip);
        host.setPassword(host1password);
        boolean is = hostService.isRightPassword(host);
        AssertJUnit.assertTrue(is);
//        System.out.println("9 判断目标主机密码测试:" + is);
    }
    /**
     * 设置主机虚拟度测试.
     */
    @Test(groups={"update"},alwaysRun=true)
    public void setHostVirtual() {
    	hostService.configHostVirtual(uuid, 2.0f);
    }
    /**
     * 获取主机虚拟度测试.
     */
    @Test(groups={"update"},dependsOnMethods={"setHostVirtual"},alwaysRun=true)
    public void getHostVirtual() {
        float scale = hostService.getHostVirtual(uuid);
        AssertJUnit.assertEquals(scale, 2.0f, 0.001f);
//        System.out.println("6 host VirtualSacle :" + scale);
    }
    /**
     * 获取主机信息测试.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getHostInfo() {
        Host host = hostService.getHostInfo(uuid);
        AssertJUnit.assertEquals(host.getUuid(), uuid);
//        System.out.println("4 获取主机信息测试:" + host.getIp());
    }
    /**
     * 获取管理节点服务信息测试.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getManagerService() {
        ServiceMonitorInfo manager = hostService.getManagerService();
        AssertJUnit.assertNotNull(manager);
//        System.out.println("5 获取管理节点服务信息测试" + manager.getManager());
    }
    /**
     * 获取主机运行时信息测试.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getHostRuntimeInfo() {
        HostMonitorInfo hostInfo = hostService.getHostRuntimeInfo(uuid);
        AssertJUnit.assertEquals(hostInfo.getUuid(), uuid);
//        System.out.println("8 获取主机运行时信息测试:" + hostInfo.getIp());
    }
    /**
     * 获取可用USB设备.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getFreeUsbDevices() {
        List<UsbDevice> devList = hostService.getFreeUsbDevices(uuid);
//        for (UsbDevice dev : devList) {
//            System.out.println("10 获取未使用USB设备：" + dev.getDeviceAddr());
//        }
    }
    /**
     * 获取所有USB设备.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getAllUsbDevices() {
        List<UsbDevice> devList = hostService.getAllUsbDevices(uuid);
//        for (UsbDevice dev : devList) {
//            System.out.println("11 获取所有USB设备：" + dev.getDeviceAddr());
//        }
    }
    /**
     * 获取所有可用USB设备.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getUsedUsbDevices() {
        List<UsbDevice> devList = hostService.getUsedUsbDevices(uuid);
//        for (UsbDevice dev : devList) {
//            System.out.println("获取所有可用USB设备：" + dev.getDeviceAddr());
//        }
    }
    /**
     * 获取目标相关主机信息.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getTargetRelatedHostInfo() {
        List<Host> hosts = hostService.getHostList(TaskTargetType.DATA_STORE.name(),
                "6e621c79-88ea-46b8-90b8-c938b8d66309");
//        for (Host host : hosts) {
//            System.out.println("13 获取目标相关主机信息: " + host.getUuid());
//        }
    }
    /**
     * 获取多通道配置.
     */
    @Test(groups={"update"},dependsOnMethods={"modifyMultipathConfig"},alwaysRun=true)
    public void getMutipathConfig() {
        MultipathConfig is = hostService.getMultipathConfig(uuid9);
        AssertJUnit.assertEquals(is.getPollingInterval(), "5");
//        s=is;
//        System.out.println("15 获取多通道配置" + is.getUuid());
    }
    /**
     * 修改多通道路径配置信息.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void modifyMultipathConfig() throws Exception {
    	MultipathConfig mc = hostService.getMultipathConfig(uuid);
    	Host host = hostService.getHostInfo(uuid);
    	mc.setHost(host);
    	mc.setPollingInterval("6");
        TaskIntermediateResult is = hostService
                .modifyMultipathConfig(mc);
        TaskInfo taskInfo = TestUtil.waitFor(is.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        
//        System.out.println("14 修改多通道路径配置信息" + is.getTaskId());
    }
    /**
     * 主机开机与否.
     */
    @Test(groups={"update"},dependsOnMethods={"getIpmiInfo"},alwaysRun=true)
    public void isHostPowerOn() {
        boolean is = hostService.isPowerOn(uuid);
//        System.out.println("20 主机开机与否:" + is);
    }
    /**
     * 获取ipmi信息.
     */
    @Parameters({"bmcip"})
    @Test(groups={"update"},dependsOnMethods={"updateIpmiInfo"},alwaysRun=true)
    public void getIpmiInfo(String bmcip) {
        HostIpmiInfo ipmiInfo = hostService.getIpmiInfo(uuid);
        AssertJUnit.assertNotNull(ipmiInfo);
        AssertJUnit.assertEquals(ipmiInfo.getBmcIp(), bmcip);
//        System.out.println("16 获取ipmi信息:" + ipmiInfo.getBmcIp());
    }
    /**
     * 更新ipmi信息.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Parameters({"bmcip"})
    @Test(groups={"update"},alwaysRun=true)
    public void updateIpmiInfo(String bmcip) throws Exception {
        HostIpmiInfo bmc = new HostIpmiInfo();
        bmc.setBmcIp(bmcip);
        bmc.setUserName("ADMIN");
        bmc.setPassword("ADMIN");
        TaskIntermediateResult is = hostService.updateIpmi(uuid, bmc);
        System.out.println("17 更新ipmi信息：" + is.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(is.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
//    /**
//     * 关闭主机.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test(groups={"delete"},alwaysRun=true)
//    public void powerOffHost() {
//        TaskIntermediateResult is = hostService.powerOffHost(uuid9);
//        AssertJUnit.assertNotNull(is.getTaskId());
////        System.out.println("19 关闭主机:" + is.getTaskId());
//    }
//    /**
//     * 打开主机.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test(groups={"update"},alwaysRun=true)
//    public void powerOnHost() {
//        TaskIntermediateResult is = hostService.powerOnHost(uuid9);
//        AssertJUnit.assertNotNull(is.getTaskId());
////        System.out.println("18 打开主机：" + is.getTaskId());
//    }
    
    /**
     * 获取可使用CDrom设备.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getFreeCDromDevices() {
        List<CdromDevice> devices = hostService.getFreeCdromDevices(uuid);
//        for (CdromDevice device : devices) {
//            System.out.println("21 获取可使用CDrom设备:" + device.getId());
//        }
    }
    /**
     * 进入维护.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"com.inspur.ics.client.autotest.ClusterServiceTest.removeCluster"},alwaysRun=true)
    public void setHostMaintenance() throws Exception {
        TaskIntermediateResult is = hostService.setHostMaintenance(uuid9);
        TaskInfo taskInfo = TestUtil.waitFor(is.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("22 进入维护:" + is.getTaskId());
    }
    /**
     * 退出维护.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},alwaysRun=true,dependsOnMethods={"setHostMaintenance"})
    public void exitMaintenanceMode() throws Exception {
        TaskIntermediateResult is = hostService.exitMaintenanceMode(uuid);
        TaskInfo taskInfo = TestUtil.waitFor(is.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("23 退出维护:" + is.getTaskId());
    }
    /**
     * 
     */
//     @SuppressWarnings("rawtypes")
//     @Test(groups={"update"})
//     public void modifyMasterIp() {
//     TaskIntermediateResult is = hostService.modifyMasterIp("100.1.8.202",
//     "255.255.255.0");
////     System.out.println(is.getTaskId());
//     }
    /**
     * ip&mask 
     */
     @Test(groups={"query"},alwaysRun=true)
    public void getHostIpNetmask() {
        String ip = hostService.getHostIpNetmask();
//        System.out.println("25 ip&mask :" + ip);
    }
    /**
     * 删除主机测试.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"exitMaintenanceMode"},alwaysRun=true)
    public void deletHost() throws Exception {
        TaskIntermediateResult task = hostService.deleteHost(uuid9);
        hostService.deleteHost(uuid);
        TaskInfo taskInfo = TestUtil.waitFor(task.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//        System.out.println("2 删除主机测试" + task.getTaskId());
    }
}
