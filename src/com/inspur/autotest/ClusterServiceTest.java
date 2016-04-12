package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;

import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DrsStrategy;
import com.inspur.ics.pojo.HeartbeatDevice;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.O2cbConfig;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.monitor.ClusterMonitorInfo;

/**
 * Junit test of cluster service.
 * @author zhangjun
 */
public class ClusterServiceTest {
	/** cluster service. */
    private static ClusterService clusterService;
    /**
     * DataStore service.
     */
    private static DataStoreService dataStoreService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private static HostService hostService;
    private static StorageDeviceService storageDeviceService;
    private String clusterUuid = "f5835d60-b12d-4baf-8383-027023582079";
    private String hostUuid1="0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
    private String hostUuid2="f5a6a056-b134-439f-8fbd-23072d4be020";
    private String odsUuid="41ab18d8-6cee-4f2d-935b-df6052e3fe1a";
    private String hbdUuid="834e47f1-3047-4f33-9d70-6b3265637c3e";
    private String bdUuid1="b6ded4ee-1e60-4cce-a002-90a22f3568bd";
	private String bdUuid2="932452b9-8259-4a2e-957b-b4aa4d3acb64";

	/**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        clusterService = factory.getClusterService();
        hostService  =  factory.getHostService();
        storageDeviceService = factory.getStorageDeviceService();
        dataStoreService = factory.getDataStoreService();
        taskService = factory.getTaskService();
    }
    @Parameters({"host1ip", "host2ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.HostServiceTest.addHosts"})
    public void initPara(String host1ip,String host2ip) {
    }
    /**
     * 创建ocfs数据存储.
     * checked.
     * @throws Exception 
     */

    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"addHeartbeatDevice"},alwaysRun=true)
    public void  createOcfsDataStore() throws Exception {



//        OcfsDataStore ocfs = new OcfsDataStore();
//        ocfs.setName("ocfs_test");
//        ocfs.setUuid("302e9366-0182-4069-b57e-3c4b05569111");
//        ocfs.setMaxSlots(1);
//        ocfs.setOcfsLabel("IVIRTUAL_CFS_302e9366-0182-4069-b57e-3c4b05569111");
//        ocfs.setMountPath("/etc/ivirtual/datastore");
////        ocfs.setHost(null);
////        ocfs.setCluster(null);
//
//        List<BlockDevice> block = new ArrayList<BlockDevice>();
//        BlockDevice blockDevice = new BlockDevice();
//        blockDevice.setUuid("302e9366-0182-4069-b57e-3c4b05569995");
//        blockDevice.setDisplayName("test_ocfs");
//        block.add(blockDevice);
//
//        List<Host> mountHosts = new ArrayList<Host>();
//        Host host1 = new Host();
//        host1.setUuid("");
//        host1.setHostName("");
//        host1.setCluster(null);
//        mountHosts.add(host1);
//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//    	 List<Cluster> clusterList = clusterService.getAllCluster();
//         AssertJUnit.assertNotNull(clusterList);
//         if (clusterList.size() > 0){
//             for(int i = 0; i < clusterList.size(); i++) {
//             	if("sdk_cluster".equals(clusterList.get(i).getDisplayName())){
//             		clusterUuid = clusterList.get(i).getDisplayName();
//             		break;
//             	}
//             }
//         }


        OcfsDataStore ocfs = new OcfsDataStore();
        ocfs.setName("sdk_ocfs_datastore");
        ocfs.setLvmBased(false);
        ocfs.setLvmLabel(null);
        ocfs.setMaxSlots(1);

        Cluster cluster = new Cluster();
//        cluster.setUuid("f5835d60-b12d-4baf-8383-027023582079");
        cluster.setUuid(clusterUuid);
        ocfs.setCluster(cluster);

        Host host1 = new Host();
        host1.setUuid(hostUuid1); //8.9
 //       ocfs.setHost(host1);

        List<Host> hostOnOds = new ArrayList<Host>();
        hostOnOds.add(host1);
        ocfs.setHosts(hostOnOds);

        List<BlockDevice> blockDevices = new ArrayList<BlockDevice>();
        BlockDevice bd = new BlockDevice();
        bd.setUuid(bdUuid2);
        blockDevices.add(bd);
        ocfs.setBlockDevices(blockDevices);



        TaskIntermediateResult taskResult = dataStoreService.createOcfsDataStore(ocfs, hostUuid1);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        System.out.println(taskResult.getTaskId());
    }


    @Test(groups={"create"},dependsOnMethods={"addHost"},alwaysRun=true)
    public void addHeartbeatDevice() throws Exception {
//    	 List<DataStore> dataStoreList = dataStoreService.getAllDataStore();
//         if(dataStoreList.size()>0) {
//        	 for(DataStore ds:dataStoreList) {
//        		 if("sdk_ocfs_datastore".equals(ds.getName())) {
//        			 odsUuid = ds.getUuid();
//        		 }
//        	 }
//         }
    	String opHostUuid = hostUuid1;
    	List<HeartbeatDevice> hbDevices = new ArrayList<HeartbeatDevice>();

    	HeartbeatDevice hb1 = new HeartbeatDevice();
    	BlockDevice bd1 = new BlockDevice();
    	bd1.setUuid(bdUuid1);
    	hb1.setBlockDevice(bd1);
    	hbDevices.add(hb1);

        // 使用CFS数据存储做心跳设备.
//        OcfsDataStore ods = new OcfsDataStore();
//        ods.setUuid(odsUuid);
//        HeartbeatDevice hd2 = new HeartbeatDevice();
//        hd2.setOcfsDataStore(ods);
//        hbDevices.add(hd2);

    	TaskIntermediateResult taskResult = clusterService.addHeartbeatDevice(clusterUuid, opHostUuid, hbDevices);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    @Parameters({"host1ip", "host2ip"})
    @Test(groups={"create"},dependsOnMethods={"updateO2cbConfig"},alwaysRun=true)
    public void addHost(String host1ip,String host2ip) throws InterruptedException {
    	//获取主机uuid 两个 
    	List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if(host.getIp().equals(host1ip)) {
            	 this.hostUuid1 = host.getUuid(); // huoqu UUID
            }
            if(host.getIp().equals(host2ip)) {
           	     this.hostUuid2 = host.getUuid(); // huoqu UUID
           }
        }
        
        List<BlockDevice> blkDevices1 = storageDeviceService
				.getAvailableBlockDeviceForHeartbeatDevice(hostUuid1);
        if(blkDevices1.size()>1){
        	bdUuid1 = blkDevices1.get(0).getUuid();
        	bdUuid2 = blkDevices1.get(1).getUuid();
        }
    	String[] addHostUuids = new String[] {hostUuid1,hostUuid2};
    	TaskIntermediateResult taskResult = clusterService.addHost(clusterUuid, addHostUuids);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups={"create"},dependsOnMethods={"initPara"},alwaysRun=true)
    public void createCluster() throws Exception {
    	Cluster cluster = new Cluster();
    	cluster.setDisplayName("sdk_cluster");

    	// 配置集群的配置信息.
    	O2cbConfig o2cbConfig = new O2cbConfig();
    	o2cbConfig.setHeartbeatThreshold(61);
    	o2cbConfig.setIdleTimeout(30000);
    	o2cbConfig.setKeepaliveDelay(2000);
    	o2cbConfig.setReconnectDelay(2000);
    	cluster.setO2cbConfig(o2cbConfig);
    	
    	DrsStrategy strategy = new DrsStrategy();
    	cluster.setDrsStrategy(strategy);
//
//    	// 添加的物理主机.
//    	List<Host> clusterNodes = new ArrayList<Host>();
//    	Host host1 = new Host();
//    	host1.setUuid(hostUuid1); // 8.7
//    	clusterNodes.add(host1);
//    	cluster.setHosts(clusterNodes);
//
//    	// 执行格式化心跳设备或CFS数据存储的物理主机.
////    	String opHostUuid = hostUuid1;
//
//    	// 添加的心跳设备.
//    	List<HeartbeatDevice> hbDevices = new ArrayList<HeartbeatDevice>();
//    	// 使用块设备作为心跳设备，可以有多个.
//    	HeartbeatDevice hb1 = new HeartbeatDevice();
//    	BlockDevice bd1 = new BlockDevice();
//    	bd1.setUuid(bdUuid1);
//    	hb1.setBlockDevice(bd1);
//    	hbDevices.add(hb1);
//    	System.out.println("hostUuid1=="+hostUuid1);
//    	System.out.println("bdUuid1=="+bdUuid1);
//    	cluster.setHbDevices(hbDevices);
//    	// 使用CFS数据存储作为心跳设备, 可以有多个.
//    	HeartbeatDevice hb2 = new HeartbeatDevice();
//    	OcfsDataStore ods = new OcfsDataStore();
//    	ods.setName("TEST_SHARE_CFS");
//    	ods.setMaxSlots(16);
//    	// CFS数据存储使用的块设备.
//    	List<BlockDevice> bdOnOds = new ArrayList<BlockDevice>();
//    	BlockDevice bd2 = new BlockDevice();
//    	bd2.setUuid(bdUuid2);
//    	bdOnOds.add(bd2);
//    	ods.setBlockDevices(bdOnOds);
    	// 挂载CFS数据存储的物理节点.
//    	List<Host> mountHosts = new ArrayList<Host>();
//    	mountHosts.add(host1);
//    	mountHosts.add(host2);
//    	ods.setHosts(mountHosts);
//    	hb2.setOcfsDataStore(ods);
//    	hbDevices.add(hb2);
//    	cluster.setHbDevices(hbDevices);

    	TaskIntermediateResult taskResult = clusterService.createCluster(cluster, null);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    	
    }

    @Test(groups={"querybefore"},alwaysRun=true)
    public void getAllCluster() {
        List<Cluster> clusterList = clusterService.getAllCluster();
        AssertJUnit.assertNotNull(clusterList);
//        for (Cluster cluster : clusterList) {
//            System.out.println(cluster.getUuid());
//            System.out.println(cluster.getName());
//            System.out.println(cluster.getHostNum());
//            System.out.println(cluster.getHbMode());
//
//            // 获取集群监控信息.
//            ClusterMonitorInfo cmi = cluster.getRuntimeInfo();
//            System.out.println(cmi.getRemainCpuFrequency());
//            System.out.println(cmi.getRemainMemory());
//            System.out.println(cmi.getRemainStorage());
//            System.out.println(cmi.getRemainVcpu());
//            System.out.println(cmi.getTotalCpu());
//            System.out.println(cmi.getTotalCpuFrequency());
//            System.out.println(cmi.getTotalMemory());
//            System.out.println(cmi.getTotalStorage());
//            System.out.println(cmi.getTotalVcpu());
//            System.out.println(cmi.getUsedCpuFrequency());
//            System.out.println(cmi.getUsedMemory());
//            System.out.println(cmi.getUsedStorage());
//            System.out.println(cmi.getUuid());
//        }
    }
  //需要数据存储卸载以后才能更新
    @Test(groups={"create"},dependsOnMethods={"createCluster"},alwaysRun=true) 
    public void updateO2cbConfig() throws Exception {
    	 List<Cluster> clusterList = clusterService.getAllCluster();
    	 if (clusterList.size() > 0){
             for(int i = 0; i < clusterList.size(); i++) {
             	if("sdk_cluster".equals(clusterList.get(i).getDisplayName())){
             		clusterUuid = clusterList.get(i).getUuid();
             		break;
             	}
             }
         }
    	 System.out.println("clusterUuid===="+clusterUuid);
    	O2cbConfig newConfig = new O2cbConfig();
    	newConfig.setHeartbeatThreshold(31);
    	newConfig.setIdleTimeout(40000);
    	newConfig.setKeepaliveDelay(3000);
    	newConfig.setReconnectDelay(3000);
    	TaskIntermediateResult taskResult = clusterService.updateO2cbConfig(clusterUuid, newConfig);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    	
    }

    @Test(groups={"querybefore"},alwaysRun=true)
    public void getAllHeartbeatDevice() {
    	List<HeartbeatDevice> hbDevices = clusterService.getAllHeartbeatDevice(clusterUuid);
    	if(hbDevices.size() > 0 ) {
    		hbdUuid = hbDevices.get(0).getUuid();
    	}
    	AssertJUnit.assertNotNull(hbDevices);
//    	for (HeartbeatDevice hb : hbDevices) {
//    		System.out.println(hb.getUuid() + " " + hb.getRegion());
//    	}
    }

    @Test(groups={"query"},alwaysRun=true)
    public void getAvailableHosts() {
        List<Host> availableHosts = clusterService.getAvailableHosts();
        AssertJUnit.assertNotNull(availableHosts);
//        System.out.println("可用的主机个数：" + availableHosts.size());
//
//        for (Host host : availableHosts) {
//            System.out.println(host.getUuid() + " " + host.getHostName()
//            		+ " " + host.getIp());
//        }
    }

    @Test(groups={"query"},alwaysRun=true)
    public void getAvailableOcfsDataStoreForHeartbeatDevice() {
    	List<OcfsDataStore> ocfsDataStores = clusterService
    			.getAvailableOcfsDataStoreForHeartbeatDevice(clusterUuid);
    	 AssertJUnit.assertNotNull(ocfsDataStores);
//    	System.out.println("可用于做心跳设备的CFS数据存储个数：" + ocfsDataStores.size());
//
//    	for (OcfsDataStore ods : ocfsDataStores) {
//    		System.out.println(ods.getName() + " " + ods.getUuid());
//    	}
    }

    @Test(groups={"query"},alwaysRun=true)
    public void getClusterInfo() {
        Cluster cluster = clusterService.getClusterInfo(clusterUuid);
//        System.out.println(cluster.getName());
//        System.out.println(cluster.getUuid());
//        System.out.println(cluster.getHostNum());
//        System.out.println(cluster.getRuntimeInfo().getUsedMemory());
//        System.out.println(cluster.getRuntimeInfo().getRemainVcpu());
//        System.out.println(cluster.getRuntimeInfo().getTotalCpuFrequency());
//        System.out.println(cluster.getRuntimeInfo().getUsedCpuFrequency());
//        System.out.println(cluster.getRuntimeInfo().getTotalVcpu());
    }

    @Test(groups={"query"},alwaysRun=true)
    public void getO2cbConfig() {
    	O2cbConfig o2cbConfig = clusterService.getO2cbConfig(clusterUuid);
//    	System.out.println(o2cbConfig.getHeartbeatThreshold());
//    	System.out.println(o2cbConfig.getIdleTimeout());
//    	System.out.println(o2cbConfig.getKeepaliveDelay());
//    	System.out.println(o2cbConfig.getPanic());
//    	System.out.println(o2cbConfig.getPanicOnOops());
//    	System.out.println(o2cbConfig.getReconnectDelay());
    }

    @Test(groups={"update"},alwaysRun=true)
    public void modifyClusterName() throws Exception {
    	String newName = "zjtest";
    	TaskIntermediateResult taskResult = clusterService.modifyClusterName(clusterUuid, newName);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups={"delete"},dependsOnMethods={"removeHost"},alwaysRun=true)
    public void removeCluster() throws Exception {
    	TaskIntermediateResult taskResult = clusterService.removeCluster(clusterUuid);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups={"delete"},dependsOnMethods={"com.inspur.ics.client.autotest.VNetServiceTest.deleteDVSwitch"},alwaysRun=true)
    public void removeHeartbeatDevice() throws Exception {
    	String[] hbUuids = new String[] {hbdUuid};
    	TaskIntermediateResult taskResult = clusterService.removeHeartbeatDevice(clusterUuid, hbUuids);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups={"delete"},dependsOnMethods={"removeHeartbeatDevice"},alwaysRun=true)
    public void removeHost() throws Exception {
    	String[] removeHostUuids = new String[] {
    			hostUuid1,
    			hostUuid2};
    	TaskIntermediateResult taskResult = clusterService.removeHost(clusterUuid, removeHostUuids);
    	TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

}
