package com.inspur.autotest;

import java.util.ArrayList;
import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.client.VMTemplateService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.common.Types;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DataStoreDir;
import com.inspur.ics.pojo.DataStoreFile;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.IscsiSoftwareAdapter;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMTemplate;
import com.inspur.ics.pojo.VSanDataStore;
import com.inspur.ics.pojo.VSanMon;
import com.inspur.ics.pojo.VSanOSD;

/**
 * Junit test of Datastore service.
 * @author jiangwt
 */

public class DataStoreServiceTest {
	/**
     * DataStore service.
     */
    private static DataStoreService dataStoreService;
    private static HostService hostService;
    private static StorageDeviceService storageDeviceService;
    private static ClusterService clusterService;
    private static VNetService vNetService;
    private static VMService vmService;
    private static VMTemplateService vmTmpService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private String hostUuid = null;
    private String ophostUuid = null;
    private String dsUuid = null;
    private String nfsdsUuid = null;
    private String clusterUuid = null;
    private String bdUuid = null;
    private String bdUuid1 = null;
    private String bdUuid2 = null;
    private String pgUuid = null;
    private String vsanUuid = null;
    private boolean removeflag = false;
    private boolean detachflag = false;
    
    
	/**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        dataStoreService = factory.getDataStoreService();
        hostService  =  factory.getHostService();
        storageDeviceService = factory.getStorageDeviceService();
        clusterService = factory.getClusterService();
        vNetService = factory.getVNetService();
        taskService = factory.getTaskService();
        vmService = factory.getVMService();
        vmTmpService = factory.getVMTemplateService();
    }
    
    @Parameters({"host1ip", "host2ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.StorageDeviceServiceTest.rescanAllStorageAdapter"},alwaysRun=true)
    public void initPara(String host1ip,String host2ip) {
    	//获取主机uuid 两个 
    	List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if(host.getIp().equals(host1ip)) {
            	 this.hostUuid = host.getUuid(); // huoqu UUID
            }
            if(host.getIp().equals(host2ip)) {
           	     this.ophostUuid = host.getUuid(); // huoqu UUID
           }
        }
        
        List<Cluster> clusterList = clusterService.getAllCluster();
//        AssertJUnit.assertNotNull(clusterList);
        if (clusterList.size() > 0){
            for(int i = 0; i < clusterList.size(); i++) {
            	if("sdk_cluster".equals(clusterList.get(i).getDisplayName())){
            		clusterUuid = clusterList.get(i).getUuid();;
            	}
            }
        }
        List<BlockDevice> blkDevices = storageDeviceService
				.getAvailableBlockDeviceForOcfsDataStore(hostUuid);
        if(blkDevices.size()>0){
        		bdUuid = blkDevices.get(blkDevices.size()-1).getUuid();
        }
        
        List<BlockDevice> blkDevices1 = storageDeviceService
				.getAvailableBlockDeviceForOsd(hostUuid);
        if(blkDevices1.size() > 1) {
        	bdUuid1 = blkDevices1.get(0).getUuid();
        	bdUuid2 = blkDevices1.get(1).getUuid();
        } else if(blkDevices1.size() == 1) {
        	bdUuid1 = blkDevices1.get(0).getUuid();
        	bdUuid2 = bdUuid1;
        }
        
        List<PortGroup> result = vNetService.listPortGroupByHostUuid(hostUuid);
        if(result.size() > 0) {
            pgUuid = result.get(0).getUuid();
        }
    }
    /**
     * 向主机附加存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"mountDataStore"},alwaysRun=true)
    public void  attachDataStoreToHost() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";

        List<String> addDsUuids = new ArrayList<String>();
        String addDsUuid1 = new String();
        addDsUuid1 = dsUuid;         //add a dsUuid
        addDsUuids.add(addDsUuid1);

        TaskIntermediateResult taskResult = dataStoreService.attachDataStoreToHost(addDsUuids, hostUuid);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        System.out.println(taskResult.getTaskId());
    }

    /**
     * 向主机附加存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"attachDataStoreToHost"},alwaysRun=true)
    public void  attachHostToDataStore() throws Exception {


        List<String> addHostUuids = new ArrayList<String>();
        String addHostUuid1 = new String();
        addHostUuid1 = hostUuid;         //add a hsotUuid
        addHostUuids.add(addHostUuid1);

        TaskIntermediateResult taskResult = dataStoreService.attachHostToDataStore(addHostUuids, dsUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 创建外部vsan数据存储.
     * checked.
     */

    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
//    @Test(groups={"create"},dependsOnMethods={"createNfsDataStore"},alwaysRun=true)
    public void  createExternalVsanDataStore() {


        VSanDataStore vsan = new VSanDataStore();

        Cluster cluster = new Cluster();
        cluster.setUuid(clusterUuid);
        vsan.setCluster(cluster);

        vsan.setName("vsan_test");
        vsan.setExternalVsanConfig("100.1.8.167;100.1.8.168;");

        TaskIntermediateResult taskResult = dataStoreService.createExternalVsanDataStore(vsan);
        System.out.println(taskResult.getTaskId());

    }

    /**
     * 创建nfs数据存储.
     * checked.
     * @throws Exception 
     */

    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"createOcfsDataStore"},alwaysRun=true)
    public void  createNfsDataStore() throws Exception {

//        NfsDataStore nfs = new NfsDataStore();
//       // nfs.setName("nfs");
//        nfs.setId(1);
//        nfs.setServerIp("100.1.8.100");
//        nfs.setExportDir("/nfs");

//        List<Host> mountHosts = new ArrayList<Host>();
//        Host host1 = new Host();
//        host1.setUuid("");
//        host1.setHostName("");
//        host1.setCluster(null);
//        mountHosts.add(host1);
        NfsDataStore nfs = new NfsDataStore();
        nfs.setName("sdk_nfs_datastore");
        nfs.setServerIp("100.1.8.100");
        nfs.setExportDir("/nfs");

        Cluster cluster = new Cluster();
//        cluster.setUuid("f5835d60-b12d-4baf-8383-027023582079");
        cluster.setUuid(clusterUuid);
        nfs.setCluster(cluster);

        Host host1 = new Host();
        host1.setUuid(hostUuid);

        List<Host> hostOnNds = new  ArrayList<Host>();
        hostOnNds.add(host1);
        nfs.setHosts(hostOnNds);


        TaskIntermediateResult taskResult = dataStoreService.createNfsDataStore(nfs);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());

    }

    /**
     * 创建ocfs数据存储.
     * checked.
     * @throws Exception 
     */

    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"initPara"},alwaysRun=true)
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

        OcfsDataStore ocfs = new OcfsDataStore();
        ocfs.setName("ocfs_datastore111");
        ocfs.setLvmBased(false);
        ocfs.setLvmLabel(null);
        ocfs.setMaxSlots(1);

        Cluster cluster = new Cluster();
//        cluster.setUuid("f5835d60-b12d-4baf-8383-027023582079");
        cluster.setUuid(clusterUuid);
        ocfs.setCluster(cluster);

        Host host1 = new Host();
        host1.setUuid(hostUuid); //8.9
 //       ocfs.setHost(host1);

        List<Host> hostOnOds = new ArrayList<Host>();
        hostOnOds.add(host1);
        ocfs.setHosts(hostOnOds);

        List<BlockDevice> blockDevices = new ArrayList<BlockDevice>();
        BlockDevice bd = new BlockDevice();
        bd.setUuid(bdUuid);
        blockDevices.add(bd);
        ocfs.setBlockDevices(blockDevices);


//
//        TaskIntermediateResult taskResult = dataStoreService.createOcfsDataStore(ocfs, hostUuid);
//        System.out.println(taskResult.getTaskId());
//        Thread.currentThread().sleep(60000);
//        TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
//        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());


    }

    /**
     * 创建vsan数据存储.
     * checked.
     */

    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
//    @Test(groups={"create"},dependsOnMethods={"createExternalVsanDataStore"},alwaysRun=true)
    public void  createVsanDataStore() {

        VSanDataStore vsan = new VSanDataStore();

        Cluster cluster = new Cluster();
        cluster.setUuid(clusterUuid);
        
        vsan.setCluster(cluster);

        vsan.setName("vsan_test");

        List<VSanOSD> vsanosd = new ArrayList<VSanOSD>();

//        VSanOSD vsanosd1 = new VSanOSD();
//        BlockDevice bd = new BlockDevice();
//        bd.setUuid("804e88c4-b301-40c0-846c-410d11cd945c");
//        vsanosd1.setBlockDevice(bd);
//        Host host1 = new Host();
//        host1.setUuid("0fe940bc-8366-4cfa-9418-5dcb43f5a2ef"); //8.6
//        vsanosd1.setHost(host1);
//        vsanosd.add(vsanosd1);

        VSanOSD vsanosd2 = new VSanOSD();
        BlockDevice bc = new BlockDevice();
        bc.setUuid(bdUuid1);
        vsanosd2.setBlockDevice(bc);
        Host host2 = new Host();
        host2.setUuid(hostUuid); //8.7
        vsanosd2.setHost(host2);
        vsanosd.add(vsanosd2);

//        VSanOSD vsanosd3 = new VSanOSD();
//        BlockDevice bb = new BlockDevice();
//        bb.setUuid("5db34f1c-778c-4301-a69a-dd7837842795");
//        vsanosd3.setBlockDevice(bb);
//        Host host3 = new Host();
//        host3.setUuid("d79e99e1-0d45-4ce2-b165-889627d0dbf4"); //8.5
//        vsanosd3.setHost(host3);
//        vsanosd.add(vsanosd3);

        vsan.setOsds(vsanosd);

        TaskIntermediateResult taskResult = dataStoreService.createVsanDataStore(vsan);
        System.out.println(taskResult.getTaskId());

    }
    /**
     * 从主机中分离数据存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"modifyOcfsDataStoreNodeSlot"},alwaysRun=true)
    public void  detachDataStoreFromHost() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
		if (removeflag) {
			List<String> removeDsUuids = new ArrayList<String>();
			String removeDsUuid1 = new String();
			removeDsUuid1 = dsUuid; // add a remove dsUuid
			removeDsUuids.add(removeDsUuid1);

			TaskIntermediateResult taskResult = dataStoreService.detachDataStoreFromHost(removeDsUuids, hostUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			if(taskInfo.getState().equals(TaskState.FINISHED)) {
				detachflag = true;
			}
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

    /**
     * 从数据存储中分离主机.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"detachDataStoreFromHost"},alwaysRun=true)
    public void  detachHostFromDataStore() throws Exception {

//        String dsUuid = dsUuid;
		if (removeflag && !detachflag) {
			List<String> removeHostUuids = new ArrayList<String>();
			String removeHostUuid1 = new String();
			removeHostUuid1 = hostUuid; // add a remove hsotUuid
			removeHostUuids.add(removeHostUuid1);

			TaskIntermediateResult taskResult = dataStoreService.detachHostFromDataStore(removeHostUuids, dsUuid);

			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			if(taskInfo.getState().equals(TaskState.FINISHED)) {
				detachflag = true;
			}
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

    /**
     * 扩展ocfs数据存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void  extendOcfsDataStore() throws Exception {

//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";
//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";

        TaskIntermediateResult taskResult = dataStoreService.extendOcfsDataStore(dsUuid, hostUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 扩展vsan数据存储.
     * checked.
     */
    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
    public void  extendVsanDataStore() {

//        String vsanUuid = "153624ef-62ad-454f-9ec9-3252324affe6";
        VSanOSD osd = new VSanOSD();
//        osd.setUuid("804e88c4-b301-40c0-846c-410d11cd945c");
        BlockDevice bd = new BlockDevice();
        bd.setUuid(bdUuid2);
        osd.setBlockDevice(bd);
        Host host1 = new Host();
        host1.setUuid(hostUuid); //8.7
        osd.setHost(host1);


        TaskIntermediateResult taskResult = dataStoreService.extendVsanDataStore(vsanUuid, osd);
        System.out.println(taskResult.getTaskId());
    }

    /**
     * 获取所有的数据存储.
     * checked
     */
    @Test(groups={"create"},dependsOnMethods={"createNfsDataStore"},alwaysRun=true)
    public void  getAllDataStore() {

         List<DataStore> dataStoreList = dataStoreService.getAllDataStore();
         if(dataStoreList.size()>0) {
        	 for(DataStore ds:dataStoreList) {
        		 if("sdk_ocfs_datastore".equals(ds.getName())) {
        			 dsUuid = ds.getUuid();
        		 } else if("vsan_test".equals(ds.getName())) {
        			 vsanUuid = ds.getUuid();
        		 } else if("sdk_nfs_datastore".equals(ds.getName())) {
        			 nfsdsUuid = ds.getUuid();
        		 }
        	 }
         }

//         for (DataStore datastore : dataStoreList) {
//             System.out.println(datastore.getUuid());
//
//          }

    }

    /**
     * 获取主机上可用的数据存储.
     * checked.
    */
    @Test(groups={"query"},alwaysRun=true)
    public void getAvailableDataStoreOnHost() {

//        String hostUUID = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";

        List<DataStore> datastoreList = dataStoreService
                .getAvailableDataStoreOnHost(hostUuid);
//        System.out.println(datastoreList.size());
    }

    /**
     * 获取数据存储上可用的主机.
     * checked.
    */
    @Test(groups={"query"},alwaysRun=true)
    public void getAvailableHostOnDataStore() {

//        String dsUuid = "41ab18d8-6cee-4f2d-935b-df6052e3fe1a";

        List<Host> hostList = dataStoreService
                .getAvailableHostOnDataStore(dsUuid);
        System.out.println(hostList.size());
    }

    /**
     * 获取主机上的vsan可用的端口组.
     * unchecked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void  getAvailablePortGroupForVsan() {

//        String hostUuid = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";

        List<StandardPortGroup> standarportgroupList = dataStoreService
                .getAvailablePortGroupForVsan(hostUuid);
        System.out.println(standarportgroupList.size());
    }

    /**
     * 获取数据存储的目录树.
     * checked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getDataStoreDirectoryTree() {

//        String dsUuid = "86b30d41-4bd5-4d5f-8138-4152b8304953";
        DataStoreDir datastoredir = dataStoreService.getDataStoreDirectoryTree(dsUuid);

        if (datastoredir != null) {
            System.out.println(datastoredir);
           }
    }

    /**
     * 获取数据存储的文件列表.
     * checked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void  getDataStoreFile() {

//        String dsUuid = "86b30d41-4bd5-4d5f-8138-4152b8304953";
        String dir = "/lost+found";

        List<DataStoreFile> blockdeviceList = dataStoreService
                .getDataStoreFile(dsUuid, dir);
        System.out.println(blockdeviceList.size());
    }

    /**
     * 获取数据存储的信息.
     * checked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getDataStoreInfo() {

//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";
        DataStore datastore = dataStoreService.getDataStoreInfo(dsUuid);

        if (datastore != null) {
            System.out.println(datastore.getUuid());
            System.out.println(datastore.getName());
        }
    }

    /**
     * 获取数据存储列表.
     * checked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void  getDataStoreList() {

        TaskTargetType targetType = TaskTargetType.DATA_STORE; //DATA_STORE
        String targetUuid = dsUuid;

        List<DataStore> datastoreList = dataStoreService.getDataStoreList(targetType, targetUuid);
        System.out.println(datastoreList.size());
    }

    /**
     * 获取主机上的数据存储.
     * checked.
    */
    @Test(groups={"query"},alwaysRun=true)
    public void getDataStoreOnHost() {

//        String hostUUID = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";

        List<DataStore> datastoreList = dataStoreService
                .getDataStoreOnHost(hostUuid);
        System.out.println(datastoreList.size());
    }

    /**
     * 获取备份信息和挂载状态.
     * checked.
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getDeviceBackingAndMountStatus() {

//        String dsUuid = "29cfd644-4760-4544-b7a1-da17919f90de";
        DataStore datastore = dataStoreService.getDeviceBackingAndMountStatus(dsUuid);

        if (datastore != null) {
            System.out.println(datastore.getUuid());
            System.out.println(datastore.getName());
        }
    }

//    /**屏蔽 对外不提供
//     * 修改 ocfs数据存储的拥有者.
//     * checked.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test
//    public void  modifyOcfsDataStoreOwner() {
//
////        OcfsDataStore ods = new OcfsDataStore();
////        ods.setUuid("c6c0c1ff-c1df-4cd7-ac69-d5324f44ef01");
////        ods.setName("test_datastore1");
//        OcfsDataStore ods = new OcfsDataStore();
//        ods.setUuid("29cfd644-4760-4544-b7a1-da17919f90de");
//
//        Host host = new Host();
//        host.setUuid("f5a6a056-b134-439f-8fbd-23072d4be020");
//        ods.setHost(host);
//        Cluster cluster = new Cluster();
//        cluster.setUuid("c3c1e3fa-6a80-490b-bc8e-9145c878937e");
//        ods.setCluster(cluster);
//        String ophostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//
//        TaskIntermediateResult taskResult = dataStoreService.modifyOcfsDataStoreOwner(ods, ophostUuid);
//        System.out.println(taskResult.getTaskId());
//    }
//
//    /**屏蔽 对外不提供
//     * 修改 nfs数据存储的拥有者.
//     * checked.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test
//    public void  modifyNfsDataStoreOwner() {
//
//        NfsDataStore nds = new NfsDataStore();
//        nds.setUuid("903ccffa-fff0-4835-b64d-7ecec69d9434");
//
////        Host host = new Host();
////        host.setUuid("5667ef4e-8096-4154-8f36-222bef2eb5cb");
////        nds.setHost(host);
////        nds.setCluster(null);
//
//        Cluster cluster = new Cluster();
//        cluster.setUuid("c3c1e3fa-6a80-490b-bc8e-9145c878937e");
//        nds.setCluster(cluster);
//        nds.setHost(null);
//
//        TaskIntermediateResult taskResult = dataStoreService.modifyNfsDataStoreOwner(nds);
//        System.out.println(taskResult.getTaskId());
//    }

    /**
     * 获取存储相关的主机.
     * checked.
    */
    @Test(groups={"query"},alwaysRun=true)
    public void getHostOnDataStore() {

//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";

        List<Host> hostList = dataStoreService
                .getHostOnDataStore(dsUuid);
        System.out.println(hostList.size());
    }

    /**
     * 获取主机上的vsan端口组.
     * unchecked.
     */
    @Test(enabled=false)
    public void  getHostVsanPortGroup() {

//        String hostUuid = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";  // 8.6  0fe940bc-8366-4cfa-9418-5dcb43f5a2ef
                                                                   // 8.5  d79e99e1-0d45-4ce2-b165-889627d0dbf4
        StandardPortGroup standarportgroup = dataStoreService
                .getHostVsanPortGroup(hostUuid);
        if (standarportgroup != null) {
            System.out.println(standarportgroup.getUuid());
            System.out.println(standarportgroup.getName());
            System.out.println(standarportgroup.getIp());
            System.out.println(standarportgroup.getUserName());
           }
    }

    /**
     * 获取数据存储的文件列表.
     * checked.
     */
    @Test(enabled=false)
    public void  getVsanMonitorList() {

//        String vsanUuid = "153624ef-62ad-454f-9ec9-3252324affe6";

        List<VSanMon> vsanmonList = dataStoreService
                .getVsanMonitorList(vsanUuid);
        System.out.println(vsanmonList.size());
    }

    /**
     * 获取vsan osd列表.
     * checked.
     */
    @Test(enabled=false)
    public void  getVsanOsdList() {

//        String vsanUuid = "153624ef-62ad-454f-9ec9-3252324affe6";

        List<VSanOSD> vsanosdList = dataStoreService
                .getVsanOsdList(vsanUuid);
        System.out.println(vsanosdList.size());

   }

//    /**
//     * 自动选择vsan osds.
//     * unchecked.
//     */
//    @Test
//    public void  autoSelectVsanOsds() {
//
//        List<VSanOSD> osdList = new ArrayList<VSanOSD>();
//        VSanOSD osdList1 = new VSanOSD();
//        osdList1.setUuid("f2282d7b-f81f-40be-87a2-86f555460d6a");         //add a oldlist
// //       osdList1.setName("2");
//        Host host1 = new Host();
//        host1.setUuid("29b6e3ae-7804-4100-b120-47f5a992de33"); //8.7
//        osdList1.setHost(host1);
//        osdList.add(osdList1);
//
//        List<BlockDevice> blockdeviceList = dataStoreService
//                .autoSelectVsanOsds(osdList);
//        System.out.println(blockdeviceList.size());
//    }

//    /**
//     * 获取默认的vsan副本.
//     * checked.
//     */
//    @Test
//    public void  getDefautReplicas() {
//
//       int getdefaultreplicas = dataStoreService.getDefautReplicas();
//       System.out.println(getdefaultreplicas);
//    }

    /**
     * 将InCloud Sphere 3.x中的数据存储迁移到4.0环境中.
     */
//    @Test
//    public void immigrateDataStoreFromV3ToV4() {
//    	dataStoreService.immigrateDataStoreFromV3ToV4();
//    }

    /**
     * 修改数据存储名称.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void  modifyDataStoreName() throws Exception {

//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";
        String dsName = "sdk_ocfs_datastore_new";

        TaskIntermediateResult taskResult = dataStoreService.modifyDataStoreName(dsUuid, dsName);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 修改 ocfs数据存储挂载节点.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"unmountDataStore"},alwaysRun=true)
    public void  modifyOcfsDataStoreNodeSlot() throws Exception {
		if (removeflag) {
			// String odsUuid = "ea3e0144-4baa-4563-b251-2b96e6d4c67c";
			int newNodeSlot = 16;
			// String ophostUuid = "29b6e3ae-7804-4100-b120-47f5a992de33";

			TaskIntermediateResult taskResult = dataStoreService.modifyOcfsDataStoreNodeSlot(dsUuid, newNodeSlot,
					hostUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

    /**
     * 数据存储挂载.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"getAllDataStore"},alwaysRun=true)
    public void  mountDataStore() throws Exception {
                 
//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";
        TaskIntermediateResult taskResult = dataStoreService.mountDataStore(hostUuid, dsUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 删除数据存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"detachHostFromDataStore"},alwaysRun=true)
    public void  removeDataStore() throws Exception {
//    	List<VM> vms = dataStoreService.get
//        String dsUuid = "16d64427-f71c-4d27-b8e0-fda7826339b1";
    	if(nfsdsUuid != null) {
    		TaskIntermediateResult taskResult1= dataStoreService.removeDataStore(nfsdsUuid);
    		TestUtil.waitFor(taskResult1.getTaskId());
		}
		if (removeflag || detachflag) {
			List<VM> vms = vmService.getVMList(TaskTargetType.DATA_STORE, dsUuid);
			for(VM vm : vms) {
	            if (!vm.getStatus().equals(Types.VmStatus.STOPPED)) {
	                TaskIntermediateResult taskResult = vmService.powerOffVM(vm.getConfig().getUuid());
	                TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
	            }
	            TaskIntermediateResult taskResult = vmService.deleteVM(vm.getConfig().getUuid());
	            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			}
			List<VM> vmtps = vmTmpService.getVMTemplateList(TaskTargetType.DATA_STORE, dsUuid);
			for(VM vm : vmtps) {
	            TaskIntermediateResult taskResult = vmTmpService.deleteVmTemplate(vm.getConfig().getUuid());;
	            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			}
			if (dsUuid != null) {
				TaskIntermediateResult taskResult = dataStoreService.removeDataStore(dsUuid);
				System.out.println(taskResult.getTaskId());
				TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
				AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
						TaskState.FINISHED, taskInfo.getState());
			}
		}
    }

    /**
     * 移除主机上的vsan端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
    public void  removeHostVsanPortGroup() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";

        TaskIntermediateResult taskResult = dataStoreService.removeHostVsanPortGroup(hostUuid, pgUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 在主机上设置vsan端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void  setHostVsanPortGroup() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";

        TaskIntermediateResult taskResult = dataStoreService.setHostVsanPortGroup(hostUuid, pgUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 缩减vsan数据存储..
     * checked.
     */
    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
    public void  shrinkVsanDataStore() {

//        String vsanUuid = "a9abe6f4-c436-45a4-a1ff-3f9e814e3421";
//        String osdUuid = bdUuid2;

        TaskIntermediateResult taskResult = dataStoreService.shrinkVsanDataStore(vsanUuid, bdUuid2);
        System.out.println(taskResult.getTaskId());
    }
    /**
     * 同步所有的数据存储.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"update"},alwaysRun=true)
    public void  synchronizeAllDataStore() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";

        TaskIntermediateResult taskResult = dataStoreService.synchronizeAllDataStore(hostUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 同步集群内vsan配置.
     * unchecked.
     */
    @SuppressWarnings("rawtypes")
    @Test(enabled=false)
    public void syncVsanConfigInCluster() {

//        String vsanUuid = "86b30d41-4bd5-4d5f-8138-4152b8304953";

        TaskIntermediateResult taskResult = dataStoreService.syncVsanConfigInCluster(vsanUuid);
        System.out.println(taskResult.getTaskId());
    }

    /**
     * 数据存储未挂载.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"com.inspur.ics.client.autotest.VMServiceTest.deleteVM"},alwaysRun=true)
    public void  unmountDataStore() throws Exception {

//        String hostUuid = "f5a6a056-b134-439f-8fbd-23072d4be020";
//        String dsUuid = "bcb6bcf5-7712-4d43-8e6a-fc601d8d6cd8";
        TaskIntermediateResult taskResult = dataStoreService.unmountDataStore(hostUuid, dsUuid);
        System.out.println(taskResult.getTaskId());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        if(taskInfo.getState().equals(TaskState.FINISHED)) {
        	removeflag = true;
        }
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }

}


