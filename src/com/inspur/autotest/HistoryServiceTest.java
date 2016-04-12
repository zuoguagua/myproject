package com.inspur.autotest;



import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Date;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HistoryService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostPerf;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMPerf;
import com.inspur.ics.pojo.monitor.HistoryMonitorInfo;
/**
 * @author kangzhx
 */
public class HistoryServiceTest {
    /**
     * HistoryService.
     */
    private static HistoryService historyService;
    /**
     * HostServiceImpl.
     */
    private static HostService hostService;
    private static VMService vmService;
    private String hostUUID = "29b6e3ae-7804-4100-b120-47f5a992de33";
    private String vmUUID = "3f972923-a3e4-4ce5-a2a3-929875f9560f";
    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        historyService = factory.getHistoryService();
        hostService = factory.getHostService();
        vmService = factory.getVMService();
    }
    @Parameters({"host1ip"})
    @Test(groups={"querybefore"},alwaysRun=true)
    public void testGetParas(String host1ip) {
        List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if(host.getIp().equals(host1ip)) {
            	 this.hostUUID = host.getUuid(); // huoqu UUID
            	 break;
            }
        }
        
        List<VM> vmList = vmService.getAll();
        for(VM vm:vmList) {
        	if(vm.getName().equals("vmtest0001")){
        		 vmUUID = vm.getConfig().getUuid();
        		 break;
        	}
        }
    }
    /**
     * Test GetHostPerfs.
     */
    @SuppressWarnings("deprecation")
    @Test(groups={"query"},alwaysRun=true)
    public void testGetHostPerfs() {
//        String hostUUID = "29b6e3ae-7804-4100-b120-47f5a992de33";
//        List<HostPerf> list = historyService.getHostPerfs(hostUUID);
//        for (HostPerf p : list) {
//            System.out.println("CpuUsedPercent:" + p.getCpuUsedPercent());
//            System.out.println("UUID:" + p.getHostUuid());
//            System.out.println("MemoryUsedPercent:" + p.getMemoryUsedPercent());
//            System.out.println("RecordedTime:" + p.getRecordTime());
//        }
    }
    /**
     * Test GetVMPerfs.
     */
    @SuppressWarnings("deprecation")
    @Test(groups={"query"},alwaysRun=true)
    public void testGetVMPerfs() {
//        String vmUUID = "3f972923-a3e4-4ce5-a2a3-929875f9560f";
//        List<VMPerf> list = historyService.getVMPerfs(vmUUID);
//        for (VMPerf p : list) {
//            System.out.println("CpuPercent:" + p.getCpuPercent());
//            System.out.println("MemPercent:" + p.getMemPercent());
//            System.out.println("VmUUID:" + p.getVmUuid());
//        }
    }
    /**
     * Test GetHostCpuPerfsAmongTime.
     */
    @Test(groups={"query"},alwaysRun=true)
    @SuppressWarnings("deprecation")
    public void testGetHostCpuPerfsAmongTime() {
//        String hostUUID = "d79e99e1-0d45-4ce2-b165-889627d0dbf4";
        Date fromTime = new Date(115, 2, 10);
        fromTime.setHours(14);
        fromTime.setMinutes(20);
        fromTime.setSeconds(0);
        Date toTime = new Date(115, 2, 27);
        toTime.setHours(14);
        toTime.setMinutes(20);
        toTime.setSeconds(0);
//        List<HistoryMonitorInfo> list = historyService.getHostCpuPerfsAmongTime(hostUUID, fromTime, toTime);
//        for (HistoryMonitorInfo info : list) {
//            System.out.println(info.getValue());
//            System.out.println(info.getTime());
//        }
    }
    /**
     * Test GetHostMemPerfsAmongTime.
     */
    @Test(groups={"query"},alwaysRun=true)
    @SuppressWarnings("deprecation")
    public void testGetHostMemPerfsAmongTime() {
//        String hostUUID = "f5a6a056-b134-439f-8fbd-23072d4be020";
        Date fromTime = new Date(115, 2, 10);
        fromTime.setHours(14);
        fromTime.setMinutes(20);
        fromTime.setSeconds(0);
        Date toTime = new Date(115, 2, 27);
        toTime.setHours(14);
        toTime.setMinutes(20);
        toTime.setSeconds(0);
//        List<HistoryMonitorInfo> list = historyService.getHostMemPerfsAmongTime(hostUUID, fromTime, toTime);
//        for (HistoryMonitorInfo info : list) {
//            System.out.println("主机内存Value：" + info.getValue());
//            System.out.println("时间Time:" + info.getTime());
//        }
    }
    /**
     * test GetVmCpuPerfsAmongTime.
     */
    @SuppressWarnings("deprecation")
    @Test(groups={"query"},alwaysRun=true)
    public void testGetVmCpuPerfsAmongTime() {
//        String vmUUID = "3f972923-a3e4-4ce5-a2a3-929875f9560f";
        Date fromTime = new Date(115, 2, 10);
        fromTime.setHours(14);
        fromTime.setMinutes(20);
        fromTime.setSeconds(0);
        Date toTime = new Date(115, 2, 27);
        toTime.setHours(14);
        toTime.setMinutes(20);
        toTime.setSeconds(0);
        List<HistoryMonitorInfo> list = historyService.getVmCpuPerfsAmongTime(vmUUID, fromTime, toTime);
//        for (HistoryMonitorInfo info : list) {
//            System.out.println("VMCPU使用值：" + info.getValue());
//            System.out.println("时间：" + info.getTime());
//        }
    }
    /**
     * test GetVmMemPerfsAmongTime.
     */
    @SuppressWarnings("deprecation")
    @Test(groups={"query"},alwaysRun=true)
    public void testGetVmMemPerfsAmongTime() {
//        String vmUUID = "3f972923-a3e4-4ce5-a2a3-929875f9560f";
        Date fromTime = new Date(115, 2, 10);
        fromTime.setHours(14);
        fromTime.setMinutes(20);
        fromTime.setSeconds(0);
        Date toTime = new Date(115, 2, 27);
        toTime.setHours(14);
        toTime.setMinutes(20);
        toTime.setSeconds(0);
        List<HistoryMonitorInfo> list = historyService.getVmMemPerfsAmongTime(vmUUID, fromTime, toTime);
//        for (HistoryMonitorInfo info : list) {
//            System.out.println("对应MEN值：" + info.getValue());
//            System.out.println("对应的时间：" + info.getTime());
//        }
    }

}
