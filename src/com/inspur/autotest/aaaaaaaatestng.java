package com.inspur.autotest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.DrsService;
import com.inspur.ics.client.HistoryService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.IscsiService;
import com.inspur.ics.client.NfsService;
import com.inspur.ics.client.ScheduleService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.SystemEventService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.ToolsService;
import com.inspur.ics.client.VClusterService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.client.VMTemplateService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.BootDevice;
import com.inspur.ics.common.Types.PortGroupServiceType;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.Types.VirtualDeviceConfigFileOperation;
import com.inspur.ics.common.Types.VirtualDeviceConfigOperation;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.ClusterGroup;
import com.inspur.ics.pojo.DVPortGroup;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DeviceSection;
import com.inspur.ics.pojo.Event;
import com.inspur.ics.pojo.FcHostBusAdapter;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostScsiDisk;
import com.inspur.ics.pojo.ISOFile;
import com.inspur.ics.pojo.IscsiIface;
import com.inspur.ics.pojo.IscsiSoftwareAdapter;
import com.inspur.ics.pojo.IscsiTarget;
import com.inspur.ics.pojo.MultipathConfig;
import com.inspur.ics.pojo.MultipathSection;
import com.inspur.ics.pojo.O2cbConfig;
import com.inspur.ics.pojo.PNic;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.ScheduledTask;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StorageAdapter;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMBackup;
import com.inspur.ics.pojo.VMConfigInfo;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VirtualCdrom;
import com.inspur.ics.pojo.VirtualDisk;
import com.inspur.ics.pojo.VirtualSwitch;
import com.inspur.ics.pojo.monitor.HistoryMonitorInfo;

@Test
public class aaaaaaaatestng {
	/** cluster service. */
    private static ClusterService clusterService;
    private static VMService vmService;
    private static VMTemplateService vmTemplateClient;
    private static ScheduleService scheduleService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private static StorageDeviceService storageDeviceService;
    private static DataStoreService dataStoreService;
    /**
     * VCluster service.
     */
    private static VClusterService vclusterService;
    /**
     * DrsService.
     */
    private static DrsService drsService;
    /**
     * Iscsi service.
     */
    private static IscsiService iscsiService;
    /**
     * HostServiceImpl.
     */
    private static HostService hostService;
    static VNetService vNetService;
    /**
     * HistoryService.
     */
    private static HistoryService historyService;
    /**
     * SystemEventService.
     */
    private static SystemEventService systemEventService;
    /**
    *
    */
   private static NfsService client;
 
    private static ToolsService toolsService;

    private static String url = "https://100.1.8.5";
    private static String username = "admin";
    private static String password = "admin@inspur";
    private static String userLocale = "zh";
	/**
     * init.
     */
    @BeforeClass
    public static void init() {
        ClientFactory factory = new ClientFactory(
                                url, username, password, userLocale);
        clusterService = factory.getClusterService();
        taskService = factory.getTaskService();
        drsService = factory.getDrsService();
        storageDeviceService = factory.getStorageDeviceService();
        vmService = factory.getVMService();
        vmTemplateClient = factory.getVMTemplateService();
        scheduleService = factory.getScheduleService();
        vclusterService = factory.getVClusterService();
        iscsiService = factory.getIscsiService();
        vNetService = factory.getVNetService();
        hostService = factory.getHostService();
        historyService = factory.getHistoryService();
        systemEventService = factory.getSystemEventService();

        toolsService = factory.getToolsService();
        dataStoreService = factory.getDataStoreService();

    }
    public void modifyClusterName() throws Exception {
    	String newName = "zjtest";
    	TaskIntermediateResult taskResult = clusterService.modifyClusterName("7bd15424-68b0-4719-831d-b4fb5adf16cc", newName);
    	Thread.currentThread().sleep(10000);
    	TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        
    }
    public void createCluster() throws Exception {
    	Cluster cluster = new Cluster();
    	cluster.setDisplayName("sdk_cluster11111");

    	// 配置集群的配置信息.
    	O2cbConfig o2cbConfig = new O2cbConfig();
    	o2cbConfig.setHeartbeatThreshold(61);
    	o2cbConfig.setIdleTimeout(30000);
    	o2cbConfig.setKeepaliveDelay(2000);
    	o2cbConfig.setReconnectDelay(2000);
    	cluster.setO2cbConfig(o2cbConfig);


    	TaskIntermediateResult taskResult = clusterService.createCluster(cluster, null);
    	Thread.currentThread().sleep(10000);
    	TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    public void testGetDrsGroupInCluster() {
//        String clusterUUID = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
        List<ClusterGroup> group = drsService.getDrsGroupInCluster("7bd15424-68b0-4719-831d-b4fb5adf16cc");
        for (ClusterGroup s : group) {
            System.out.println("name:" + s.getName());
            System.out.println("id:" + s.getId());
            System.out.println("clusterDisplayName :" + s.getCluster().getDisplayName());
        }
    }
    public void getIscsiAndFcStorageAdapter() {
		List<StorageAdapter> storageAdapters = storageDeviceService
				.getIscsiAndFcStorageAdapter("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
		for (StorageAdapter sa : storageAdapters) {
			System.out.println(sa.getUuid());
			System.out.println(sa.getName());
			if(sa instanceof IscsiSoftwareAdapter){
		    	if(((IscsiSoftwareAdapter) sa).getIscsiTargets().size() > 0) {
			        System.out.println("iiiiiiiiii");
			        System.out.println(((IscsiSoftwareAdapter) sa).getIscsiTargets().get(0).getUuid());
		    	} 
		    }
//			System.out.println(sa.getDisks().get(0).getScsiId());
		}
//		    if(sa instanceof IscsiSoftwareAdapter){
//		    	if(((IscsiSoftwareAdapter) sa).getIscsiTargets().size() > 0) {
//			        itUuid =((IscsiSoftwareAdapter) sa).getIscsiTargets().get(0).getUuid();
//			        return;
//		    	}
//		    }
	    }
    public void getScsiDiskOnIscsiTarget() {
		try {
			List<HostScsiDisk> scsiDisks = storageDeviceService.getScsiDiskOnIscsiTarget("");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			AssertJUnit.fail(e.getMessage());
		}

//		if (scsiDisks != null) {
//			for (HostScsiDisk hsd : scsiDisks) {
//				System.out.println(hsd.getUuid());
//				System.out.println(hsd.getScsiId());
//				System.out.println(hsd.getWwid());
//				System.out.println(hsd.getCapacity());
//				System.out.println(hsd.getDisplayCapacity());
//				System.out.println(hsd.getProductModel());
//				System.out.println(hsd.getVendor());
//				System.out.println(hsd.getTransport());
//			}
//		}
	}
    public void getAllScsiDisk() {
    	String saUuid = "";
		List<HostScsiDisk> scsiDisks = storageDeviceService.getAllScsiDisk("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
		if(scsiDisks != null) {
		    System.out.println( scsiDisks.get(0).getUuid());
		}
	}
    public void getStorageAdapterInfo() {
   		StorageAdapter sa = storageDeviceService.getStorageAdapterInfo("e49817d7-040a-4b69-a0a4-6b28e9ea9b61", "f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95");
//   		System.out.println(((FcHostBusAdapter)sa).getUuid());
//   		System.out.println(((FcHostBusAdapter)sa).getName());
//   		System.out.println(((FcHostBusAdapter)sa).getIdentifier());
//   		System.out.println(((FcHostBusAdapter)sa).getModel());
   	}
    public void getScsiDiskInfo() {
   		HostScsiDisk hsd = storageDeviceService.getScsiDiskInfo("e49817d7-040a-4b69-a0a4-6b28e9ea9b61", "e7a19331-240c-41f7-909f-7fdaa5ab4d74");
		System.out.println(hsd.getScsiId());
		System.out.println(hsd.getWwid());
		System.out.println(hsd.getUuid());
		System.out.println(hsd.getCapacity());
		System.out.println(hsd.getDisplayCapacity());
		System.out.println(hsd.getTransport());
		System.out.println(hsd.getVendor());
    }
    /**
     * 导出虚拟机.关机状态下都可以进行
     * @throws Exception 
     */
    public void exportVM() throws Exception {
    	TaskIntermediateResult taskResult = vmService.exportVM("3be27c95-77f6-451a-8cf3-92cfa1fd175e", "/storage/nfs/");
    	Thread.currentThread().sleep(10000);
 	    TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
 	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    public void exportVMTemplate() throws Exception {
   		TaskIntermediateResult result = vmTemplateClient.exportVMTemplate("f79ed903-3cf3-4cfe-968e-49c4ed7651d1", "/storage/nfs/");
   		Thread.currentThread().sleep(10000);
   	    TaskInfo taskInfo = taskService.getTaskInfo(result.getTaskId());
   	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
   		System.out.print("Export Vm template taskid :" + result.getTaskId()
   				+ "\nresut:" + result.getResult().toString());
   	}
    public void getInfo() {
		VM vmInfo = vmTemplateClient.getVMTemplateInfo("f79ed903-3cf3-4cfe-968e-49c4ed7651d1");
		System.out.println("name:" + vmInfo.getName());
		System.out.println("desc:" + vmInfo.getDescription());
		System.out.println("belonged host ip:" + vmInfo.getHost().getIp());
	}
    public void getVmTemplateByName() {
		List<VM> templates = vmTemplateClient.getVmTemplateByName("dc_model");
		AssertJUnit.assertEquals(templates.get(0).getName(), "dc_model");
//		if(templates != null) {
//			tmplUUID = templates.get(0).getConfig().getUuid();
//		}
//		for (VM vm : templates) {
//			System.out.println(vm.getDescription());
//		}
		System.out.println(templates.size());
	}
    public void modifyVmTemplate() throws Exception {
    	VM vmInfo = vmTemplateClient.getVMTemplateInfo("f79ed903-3cf3-4cfe-968e-49c4ed7651d1");
  		
    	vmInfo.getConfig().setMaxMem(4096);
    	vmInfo.getConfig().setMaxVcpus(8);
  		TaskIntermediateResult result = vmTemplateClient.modifyVmTemplate(vmInfo);
  		Thread.currentThread().sleep(10000);
  	    TaskInfo taskInfo = taskService.getTaskInfo(result.getTaskId());
  	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//  		System.out.print("modify template taskid :" + result.getTaskId()
//  				+ "\nresut:" + result.getResult().toString());
  	}
    public void getTaskInfoTest() {
    	 List<TaskInfo> tasks = taskService.getAllTasks();
         String taskId = "1302";
         taskId = tasks.get(tasks.size() / 2).getProcessId();
         TaskInfo taskInfo = taskService.getTaskInfo(taskId);
         AssertJUnit.assertNotNull(taskInfo.getId());
        
//         AssertJUnit.assertEquals("1302", taskInfo.getId());
    }
    @Test
    public void testModifyScheduledTask() throws Exception {
    	int id = 4;
    	ScheduledTask scheduledTask = null;
    	List<ScheduledTask> list = scheduleService.getAllScheduledTask("b922321a-8f4c-4979-95c7-a1bb80f4be4b");
    	for(int i =0;i<list.size();i++){
    		if(list.get(i).getName().equals("kangzhx4")) {
    			scheduledTask  = list.get(i);
    			break;
    		}
    	}
//        ScheduledTask scheduledTask = scheduleService.getScheduledTask(id);
        scheduledTask.setNextRunTime(new Date(2016, 3, 31));
        //调度任务名字
        scheduledTask.setName("createVmTaskNew");
        scheduledTask.setTaskParams("aaaaa123");
        //虚拟机的UUID
        TaskIntermediateResult result = scheduleService.modifyScheduledTask(scheduledTask);
        Thread.currentThread().sleep(10000);
        TaskInfo taskInfo = taskService.getTaskInfo(result.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        scheduledTask = scheduleService.getScheduledTask(id);
        AssertJUnit.assertEquals("createVmTaskNew", scheduledTask.getName());
    }
    @Test
    public void createVMByIsoInVCluster() throws Exception {
    	VMConfigInfo config = new VMConfigInfo();
        config.setName("sdk_vminvclster_test");
        config.setBoot(BootDevice.CDROM);
        config.setGuestOsLabel("xinXP");
        config.setMaxMem(1000);
        //config.setNicType("e1000");
        config.setVcpus(2);

        List<VirtualDisk> disks = new ArrayList<VirtualDisk>();
        VirtualDisk disk = new VirtualDisk();
        disk.setSize(20);
        disk.setFileName("1111111111.img");
        DataStore ds = new DataStore();
        ds.setUuid("15468aae-8916-4bf4-b912-2ec55426888e");
        disk.setDataStore(ds);
        disks.add(disk);

        config.setDisks(disks);

        List<VNic> nics = new ArrayList<VNic>();
        VNic nic = new VNic();
        nic.setAutoGenerated(true);
        PortGroup pgVM = new PortGroup();
        pgVM.setUuid("");
//        PortGroup pg = netService.getManagementPortgroupOnHost("759c447d-4843-46f2-81ab-ed9d40af2c89");
        nic.setPortGroup(pgVM);
//        if (pg instanceof StandardPortGroup) {
//        	StandardPortGroup pgVM = (StandardPortGroup)pg;
//        	pgVM.setIscsiIface(null);
//        	nic.setPortGroup(pgVM);
//        } else {
//        	nic.setPortGroup(pgVM);
//        }

        nics.add(nic);

//        nic = new VNic();
//        PNic pnic = new PNic();
//        pnic.setUuid("b8cc6193-e558-4dae-b450-1a6c2a7e00f3");
//        nic.setPnic(pnic);
//
//        nics.add(nic);

        config.setNics(nics);
        TaskIntermediateResult taskResult = vclusterService
                .createVMByIsoInVCluster(
                		"204a6c9d-14bb-4803-810b-f19e8524681e", config);
        TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        Thread.currentThread().sleep(10000);
        System.out.println("任务ID：" + taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    @Test
    public void getBackupList() {
    	List<VMBackup> backupLst = vmService.getBackupsOfVm("11aef914-e1ec-4ef8-9778-969a4f943a98");
    	for(VMBackup vmb:backupLst){
    		if(vmb.getName().equals("pppp")) 
    		{
    			System.out.println(vmb.getUuid());
    			break;
    		}
    	}
//    	System.out.println(backupLst.size());
    }
    @Test
    public void getHostsMovedIn() {
        List<Host> hosts = vmService.getAvailableHostsToMigrateVM("11aef914-e1ec-4ef8-9778-969a4f943a98");
        System.out.println(hosts.size());
        for (Host host : hosts) {
    		System.out.println(host.getIp());
    	}
    }
    @Test
    public void getOvaFiles() {
    	List<File> ovas = vmService.getOvaFiles();
//    	ovaFile = ovas.get(0).getPath();
    	for (File f : ovas) {
    		System.out.println(f.getName());
    		System.out.println(f.getPath());
    	}
    }
    @Test
    public void getVMInfo() {
        VM vm = vmService.getVMInfo("11aef914-e1ec-4ef8-9778-969a4f943a98");
        System.out.println(vm.getName());
        System.out.println(vm.getConfig().getNics().get(0).getMacAddress());
        System.out.println(vm.getConfig().getDisks().get(0).getFileName());
        System.out.println(vm.getvCluster() == null ? "" : vm.getvCluster().getName());
    }
    @Test
    public void getAvailablePortGroupForIscsiAdapter() {  //200测   9de6ac7d-fde9-45aa-9db6-3864ce784d25 8.5

//        String isaUuid = "e67a7165-bd64-4bad-af73-682e4cd064d8";

        List<StandardPortGroup> standportgroupList = iscsiService.getAvailablePortGroupForIscsiAdapter("f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95");

        for (StandardPortGroup standportgroup : standportgroupList) {
            System.out.println(standportgroup.getUuid());
            System.out.println(standportgroup.getName());
            System.out.println(standportgroup.getIp());
            System.out.println(standportgroup.getDatapgType());
         }
    }
    @Test
	public void showStandardNetworksOverview() {
		List<StandardPortGroup> result = vNetService.showStandardNetworksOverview();
		for (StandardPortGroup itresult : result) {
			System.out.println("标准端口组UUID："+itresult.getUuid());
			System.out.println("标准端口组的名称："+itresult.getName());
		}
		 
	}
    @Test
	public void showDistributeNetworkOverview() {
		List<DVPortGroup> result = vNetService.showDistributeNetworkOverview();
		for (DVPortGroup itresult : result) {
			System.out.println("分布式端口组的UUID："+itresult.getUuid());
			System.out.println("分布式端口组的名称："+itresult.getName());
		}
		 
	}
    @Test
    public void getIscsiTargetOnIscsiAdapter() {
        List<IscsiTarget> iscsiTargerList = iscsiService.getIscsiTargetOnIscsiAdapter("f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95");
        for (IscsiTarget iscsiTarget : iscsiTargerList) {
            System.out.println(iscsiTarget.getUuid());
         }
    }
    @Test
    public void  addIscsiTarget() throws Exception {

//        IscsiTarget target = new IscsiTarget();
//        target.setUuid("30f0768d-e7fd-4b29-89b7-90eee525ea32");
//        target.setIp("100.1.8.100");
//        target.setPort(3260);
//        target.setIqn("iqn.2003-01.org.linux-iscsi.master.x8664:sn.2f29a244197d");
        String tgtIp = "100.1.8.29";
        Integer tgtPort = 3260;
        String tgtIqn = "iqn.2000-01.storage.iscsi-target:878c4167";

        IscsiSoftwareAdapter isa = new IscsiSoftwareAdapter();
        isa.setUuid("f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95");

        StandardPortGroup spg1 = new StandardPortGroup();
        spg1.setUuid("3d178c22-b470-4e75-9385-1aefe1d0b52a");

        IscsiIface ii1 = new IscsiIface();
        ii1.setPortGroup(spg1);
        List<IscsiIface> ifaces = new ArrayList<IscsiIface>();
        ifaces.add(ii1);

        IscsiTarget target = new IscsiTarget();
        target.setIscsiAdapter(isa);
        target.setIp(tgtIp);
        target.setPort(tgtPort);
        target.setIqn(tgtIqn);
        target.setIfaces(ifaces);
        target.setChap(false);


        TaskIntermediateResult taskResult = iscsiService.addIscsiTarget(target);
        System.out.println(taskResult.getTaskId());
        Thread.currentThread().sleep(60000);
        TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());

    }
    @Test
    public void  addPortGroupToIscsiAdapter() throws Exception {

//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";

        TaskIntermediateResult taskResult = iscsiService.addPortGroupToIscsiAdapter("f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95", "7c405553-b12e-44d2-8953-8e0342ca0504");
        System.out.println(taskResult.getTaskId());
        Thread.currentThread().sleep(60000);
        TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    @Test(groups={"delete"},alwaysRun=true)
    public void  removeIscsiTarget() throws Exception {

            TaskIntermediateResult taskResult = iscsiService.removeIscsiTarget("95aead4b-d024-4ec3-b0d9-9884edb282e5");
            System.out.println(taskResult.getTaskId());
            Thread.currentThread().sleep(30000);
            TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());

    }
    @Test
    public void  addPortGroupToIscsiTarget() throws Exception {


        TaskIntermediateResult taskResult = iscsiService.addPortGroupToIscsiTarget("3621cec0-77c8-4cb1-96fa-58f6af4cc4cc", "3d178c22-b470-4e75-9385-1aefe1d0b52a");
        System.out.println(taskResult.getTaskId());
        Thread.currentThread().sleep(60000);
        TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    @Test
	public void listPortGroupByHostUuid() {
//		String hostUuid = "29b6e3ae-7804-4100-b120-47f5a992de33";
		List<PortGroup> result = vNetService.listPortGroupByHostUuid("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
		for (PortGroup itresult : result) {
			System.out.println("端口组的UUID："+itresult.getUuid());
			System.out.println("端口组名称："+itresult.getName());
		}
		 
	}
    @Test
    public void getAvailablePortGroupForIscsiTarget() {

        String isaUuid = "f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95";
        String itUuid = "3621cec0-77c8-4cb1-96fa-58f6af4cc4cc";

        List<StandardPortGroup> standportgroupList = iscsiService.getAvailablePortGroupForIscsiTarget(isaUuid, itUuid);

        for (StandardPortGroup standportgroup : standportgroupList) {
            System.out.println(standportgroup.getUuid());
            System.out.println(standportgroup.getName());

         }
    }
    @Test
    public void getDiscoveredIscsiTargetIqn() {

        String ip = "100.1.8.29";
        Integer port = 3260;

        List<String> spgUuids = new ArrayList<String>();
        String string1 = "3d178c22-b470-4e75-9385-1aefe1d0b52a";
        spgUuids.add(string1);


        //List<String> spgUuidsJson = FormatUtil.toJson(spgUuids);
        List<String> stringList = iscsiService.getDiscoveredIscsiTargetIqn("f8351455-5e6b-49d1-b8ba-c0f3d4d5ef95", ip, port, spgUuids);
        for (String string : stringList) {
            System.out.println(string);
         }
    }
    @Test
    public void getPortGroupOnIscsiTarget() {
        List<StandardPortGroup> standportgroupList = iscsiService.getPortGroupOnIscsiTarget("3621cec0-77c8-4cb1-96fa-58f6af4cc4cc");

        for (StandardPortGroup standportgroup : standportgroupList) {
            System.out.println(standportgroup.getUuid());
            System.out.println(standportgroup.getName());
            System.out.println(standportgroup.getNetmask());
         }
    }
    @Test
    public void  removePortGroupOnIscsiTarget() throws Exception {

//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";
            TaskIntermediateResult taskResult = iscsiService.removePortGroupOnIscsiTarget("3621cec0-77c8-4cb1-96fa-58f6af4cc4cc", "3d178c22-b470-4e75-9385-1aefe1d0b52a");
            System.out.println(taskResult.getTaskId());
            Thread.currentThread().sleep(30000);
            TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
    @Test
    public void getMutipathConfig() {
        MultipathConfig is = hostService.getMultipathConfig("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
        AssertJUnit.assertEquals(is.getPollingInterval(), "5");
//        s=is;
        System.out.println("15 获取多通道配置" + is.getUuid());
    }
    @Test
    public void modifyMultipathConfig() throws Exception {
    	MultipathConfig mc = hostService.getMultipathConfig("ee5a1adb-3069-4dc1-b181-ee677a48026d");
    	mc.setPollingInterval("6");
        TaskIntermediateResult is = hostService
                .modifyMultipathConfig(mc);
        Thread.currentThread().sleep(30000);
        TaskInfo taskInfo = taskService.getTaskInfo(is.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
        
//        System.out.println("14 修改多通道路径配置信息" + is.getTaskId());
    }
    @Test
    public void modifyMultipathConfig444() {
        Host host = new Host();
        host.setUuid("ee5a1adb-3069-4dc1-b181-ee677a48026d");
        MultipathConfig mc = new MultipathConfig();
        mc.setHost(host);
        mc.setUserFriendlyNames("yes");
        MultipathSection ms = new MultipathSection();
//        ms.setUuid("04b9c02a-c696-47cf-b0fd-676812d38fc7");
        ms.setFailback("immediate");
        ms.setFlushOnLastDel("yes");
        ms.setNoPathRetry("queue");
        ms.setPathGroupingPolicy("multibus");
        ms.setPathSelector("round-robin 0");
        ms.setPrio("const");
        ms.setRrMinIoRq("1");
        ms.setRrWeight("uniform");
        ms.setUserFriendlyNames("yes");
        ms.setWwid("34233423452345xxxxxxxxxxxx");
        List<MultipathSection> mss = new ArrayList<MultipathSection>();
        mss.add(ms);
        mc.setMultipathSections(mss);
        DeviceSection ds = new DeviceSection();
        ds.setDetectPrio("yes");
        ds.setDevLossTmo("infinity");
        ds.setFailback("immediate");
        ds.setFastIoFailTmo("5");
        ds.setFeatures("3 queue_if_no_path pg_init_retries 50");
        ds.setFlushOnLastDel("yes");
        ds.setGetuidCallout("/lib/udev/scsi_id --whitelisted --device=/dev/%n");
        ds.setHardwareHandler("0");
        ds.setNoPathRetry("queue");
        ds.setPathChecker("tur");
        ds.setPathGroupingPolicy("group_by_prio");
        ds.setPathSelector("round-robin 0");
        ds.setPrio("rdac");
        ds.setProduct("TP9[45]00 ");
        ds.setProductBlacklist("Universal Xport");
        ds.setRetainAttachedHwHandler("yes");
        ds.setRevision("2");
        ds.setRrMinIoRq("1");
        ds.setRrWeight("uniform");
        ds.setUserFriendlyNames("no");
        ds.setVendor("SGI");
        List<DeviceSection> dss = new ArrayList<DeviceSection>();
        dss.add(ds);
        mc.setDeviceSections(dss);
        
        TaskIntermediateResult is = hostService
                .modifyMultipathConfig(mc);
        System.out.println("14 修改多通道路径配置信息" + is.getTaskId());
    }
    @Test
    public void getEventsByTaskId() {
    	int taskId = 1114;
    	List<TaskInfo> tasks = taskService.getAllTasks();
        for(TaskInfo task:tasks){
    		if(task.getName().equals("pattern=vm.iso.create.task.name#args=[]")) {
    			List<Event> events = systemEventService.getEventsByTaskId(task.getId());
    			System.out.println("The Size of Event's List :" + events.size());
    			AssertJUnit.assertNotNull(events.get(0).getUserName());
    			break;
    		}
    	}
    }
    @Test
    public void getRelatedEvents() {
    	int taskId = 1114;
    	int eventId = 0;
    	List<TaskInfo> tasks = taskService.getAllTasks();
        for(TaskInfo task:tasks){
    		if(task.getName().equals("pattern=vm.iso.create.task.name#args=[]")) {
    			List<Event> events1 = systemEventService.getEventsByTaskId(task.getId());
    			eventId = events1.get(0).getId();
    			System.out.println("eventId===" + eventId);
    			System.out.println("The Size of Event's List :" + events1.size());
    			AssertJUnit.assertNotNull(events1.get(0).getUserName());
    			break;
    		}
    	}
     	List<Event> events = systemEventService.getRelatedEvents(eventId);
     	System.out.println("The Size of Event's List :" + events.size());
     	AssertJUnit.assertNotNull(events.get(0).getUserName());
    }
    @Test
	public void getPortGroupList() {
		TaskTargetType targetType = TaskTargetType.CLUSTER;
		String targetUuid = "461c1ae7-bf68-4eb5-8a9c-d6d07cb1a590";
		List<PortGroup> result = vNetService.getPortGroupList(targetType,targetUuid);
		for (PortGroup itresult : result) {
//			if("haha".equals(itresult.getName())) {
//				pgUuid = itresult.getUuid();
//			} else if("pg1".equals(itresult.getName())) {
//				dvPortGroupUuid = itresult.getUuid();
//			} else if("pg2".equals(itresult.getName())) {
//				dvpgUuid = itresult.getUuid();
//			}
			System.out.println(itresult.getUuid());
			System.out.println(itresult.getUserName());
		}
	}
    @Test
	public void createDVPortGroup() throws Exception {
//		String dvswUuid = "8a01a271-bb96-4e70-804c-4d8ba5396b80";
		DVPortGroup portGroup = new DVPortGroup();
		portGroup.setUserName("ddpg1");
		portGroup.setVlan(68);
		portGroup.setType(PortGroupServiceType.COMPORTGROUP);
		TaskIntermediateResult result = vNetService.createDVPortGroup("a3e9775b-62b3-4ca5-8316-972dc5dad3ad",portGroup);
		Thread.currentThread().sleep(10000);
	    TaskInfo taskInfo = taskService.getTaskInfo(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
	}
    @Test
    public void getVswitchByPortGroupUuid() {
    	TaskTargetType targetType = TaskTargetType.CLUSTER;
		String targetUuid = "461c1ae7-bf68-4eb5-8a9c-d6d07cb1a590";
		String pgUuid = "";
		List<PortGroup> result = vNetService.getPortGroupList(targetType,targetUuid);
		for (PortGroup itresult : result) {
			if("dvpg1".equals(itresult.getName())) {
				pgUuid = itresult.getUuid();
			} 
//			else if("pg1".equals(itresult.getName())) {
//				dvPortGroupUuid = itresult.getUuid();
//			} else if("pg2".equals(itresult.getName())) {
//				dvpgUuid = itresult.getUuid();
//			}
//			System.out.println(itresult.getUuid());
//			System.out.println(itresult.getUserName());
		}
//        String pgUuid = "b3546842-5eb4-47c4-a444-28daec417761";
        VirtualSwitch result1 = vNetService.getVswitchByPortGroupUuid(pgUuid);
        System.out.println("交换机UUID：" + result1.getUuid());
        System.out.println("交换机名称：" + result1.getName());

    }
    @Test
	public void listHostPNics() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		List<PNic> result = vNetService.listHostPNics("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
		for (PNic itresult : result) {
			System.out.println("网卡名称："+itresult.getName());
			System.out.println("网卡UUID："+itresult.getUuid());
		}
		 
	}
    @Test
	public void listSwitchByHostUuid() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		List<VirtualSwitch> result = vNetService.listSwitchByHostUuid("e49817d7-040a-4b69-a0a4-6b28e9ea9b61");
		for (VirtualSwitch itresult : result) {
			System.out.println("虚拟交换机UUID："+itresult.getUuid());
			System.out.println("虚拟交换机名称："+itresult.getName());
		}
	}

    @Test
    public void getNfsExportPath() {
        String path = client.getNfsExportPath();
        
        System.out.println("path : " + path);
    }
    /**
     * 导入虚拟机.
     * @throws Exception 
     */
    @Test
    public void importVM() throws Exception {
	    VM vm = new VM();
	    vm.setName("deployVM_From_SDK");
		TaskIntermediateResult taskResult = vmService.importVM(vm, "/storage/nfs/ffd24732-1ea6-4fb4-98e5-122dc28ceb57.ova");
		TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
//    	service.importVM(vm, "/storage/nfs/a7e7a3e0-bee7-4e4a-99b6-3a79b298cee9.ova");
    }
    @Test
	public void importVMTemplate() throws Exception {
		VM template = new VM();
		template.setName("test-import");
		 VMConfigInfo config = new VMConfigInfo();
		 template.setConfig(config);

		TaskIntermediateResult result = vmTemplateClient.importVMTemplate(template, "/storage/nfs/ffd24732-1ea6-4fb4-98e5-122dc28ceb57.ova");
		System.out.print("import Vm template taskid :" + result.getTaskId()
				+ "\nresut:" + result.toString());
	}
    @Test
    public void createVMByTemplate() throws Exception {
         
    	List<VirtualDisk> diskList = new ArrayList<VirtualDisk>();
    	DataStore ds = dataStoreService.getDataStoreInfo("e697ae4b-b5a6-4aa6-98a2-055887d464a9");
    	VirtualDisk disk = new VirtualDisk();
    	disk.setDataStore(ds);
    	diskList.add(disk);
    	TaskIntermediateResult taskResult = vmService.createVMByTemplate("4803f613-0d6b-41a9-a128-cf3d80b815f4",
        		"sdkvm_from_Template", "4120efbb-1297-4c86-8047-5a463812c697", diskList);
        Thread.currentThread();
		Thread.sleep(60000);
 	    TaskInfo taskInfo = taskService.getTaskInfo(taskResult.getTaskId());
 	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
}
