package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.IscsiService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.FcHostBusAdapter;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostScsiDisk;
import com.inspur.ics.pojo.IscsiSoftwareAdapter;
import com.inspur.ics.pojo.IscsiTarget;
import com.inspur.ics.pojo.StorageAdapter;
import com.inspur.ics.pojo.TaskInfo;

public class StorageDeviceServiceTest {
	/**
     * HostServiceImpl.
     */
    private static HostService hostService;
	private static StorageDeviceService storageDeviceService;
	private static TaskService taskService;
	/**
     * Iscsi service.
     */
    private static IscsiService iscsiService;
	private String hostUuid = null;
	private String saUuid = null;
	private String sdUuid = null;
	private String itUuid = null;
	/**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        storageDeviceService = factory.getStorageDeviceService();
        hostService = factory.getHostService();
        taskService = factory.getTaskService();
        iscsiService = factory.getIscsiService();
        System.out.println("Init Junit Test");
	}
    @Parameters({"host1ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.ClusterServiceTest.addHeartbeatDevice"},alwaysRun=true)
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
    @Test(groups={"querybefore"},alwaysRun=true)
	public void getAllScsiDisk() {
		List<HostScsiDisk> scsiDisks = storageDeviceService.getAllScsiDisk(hostUuid);
		if(scsiDisks != null) {
		    sdUuid = scsiDisks.get(0).getUuid();
		}
		

//		for (HostScsiDisk hsd : scsiDisks) {
//			System.out.println(hsd.getScsiId());
//			System.out.println(hsd.getWwid());
//			System.out.println(hsd.getUuid());
//			System.out.println(hsd.getCapacity());
//			System.out.println(hsd.getDisplayCapacity());
//			System.out.println(hsd.getTransport());
//			System.out.println(hsd.getVendor());
//			System.out.println("*************************");
//		}
	}

    @Test(groups={"query"},alwaysRun=true)
	public void getAvailableBlockDeviceForHeartbeatDevice() {
		List<BlockDevice> blkDevices = storageDeviceService
				.getAvailableBlockDeviceForHeartbeatDevice(hostUuid);

//		for (BlockDevice bd : blkDevices) {
//			System.out.println(bd.getDisplayName()
//					+ " " + bd.getUuid() + " " + bd.getDisplayCapacity());
//		}
	}

    @Test(groups={"query"},alwaysRun=true)
	public void getAvailableBlockDeviceForOcfsDataStore() {
		List<BlockDevice> blkDevices = storageDeviceService
				.getAvailableBlockDeviceForOcfsDataStore(hostUuid);

//		for (BlockDevice bd : blkDevices) {
//			System.out.println(bd.getDisplayName()
//					+ " " + bd.getUuid() + " " + bd.getDisplayCapacity());
//		}
	}

    @Test(groups={"query"},alwaysRun=true)
	public void getAvailableBlockDeviceForOsd() {
		List<BlockDevice> blkDevices = storageDeviceService
				.getAvailableBlockDeviceForOsd(hostUuid);

//		for (BlockDevice bd : blkDevices) {
//			System.out.println(bd.getDisplayName()
//					+ " " + bd.getUuid() + " " + bd.getDisplayCapacity());
//		}
	}

    @Test(groups={"querybefore"},alwaysRun=true)
	public void getIscsiAndFcStorageAdapter() {
    	List<StorageAdapter> storageAdapters = storageDeviceService
				.getIscsiAndFcStorageAdapter(hostUuid);
		int flag1 = 0;
		int flag2 = 0;
		for (StorageAdapter sa : storageAdapters) {
		    if(sa instanceof IscsiSoftwareAdapter){
		    	List<IscsiTarget> iscsiTargerList = iscsiService.getIscsiTargetOnIscsiAdapter(sa.getUuid());
		    	if(iscsiTargerList != null) {
		    		if(iscsiTargerList.size() > 0) {
		                itUuid =iscsiTargerList.get(0).getUuid();
		                flag1 = 1;
		    		}
		    	}
		    } else if(sa instanceof FcHostBusAdapter){
		    	System.out.println(sa.getUuid());
		    	saUuid = sa.getUuid();
		    	flag2 = 1;
		    } 
		    if (flag1 == 1 && flag2 == 1){
		    	break;
		    }
	    }
		

//		for (StorageAdapter sa : storageAdapters) {
//			System.out.println(sa.getClass());
//			System.out.println(sa.getIdentifier());
//			System.out.println(sa.getName());
//			System.out.println(sa.getUuid());
//			System.out.println(sa.getModel());
//		}
	}
    @Test(groups={"query"},alwaysRun=true)
	public void getScsiDiskOnIscsiTarget() {
		try {
			if(itUuid != null) {
			    List<HostScsiDisk> scsiDisks = storageDeviceService.getScsiDiskOnIscsiTarget(itUuid);
			}
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
    @Test(groups={"query"},alwaysRun=true)
	public void getScsiDiskInfo() {
		HostScsiDisk hsd = storageDeviceService.getScsiDiskInfo(hostUuid, sdUuid);
//		System.out.println(hsd.getScsiId());
//		System.out.println(hsd.getWwid());
//		System.out.println(hsd.getUuid());
//		System.out.println(hsd.getCapacity());
//		System.out.println(hsd.getDisplayCapacity());
//		System.out.println(hsd.getTransport());
//		System.out.println(hsd.getVendor());
	}
   
    @Test(groups={"query"},alwaysRun=true)
	public void getStorageAdapterInfo() {
    	if(hostUuid != null && saUuid != null) {
		    StorageAdapter sa = storageDeviceService.getStorageAdapterInfo(hostUuid, saUuid);
    	}
//		System.out.println(sa.getUuid());
//		System.out.println(sa.getName());
//		System.out.println(sa.getIdentifier());
//		System.out.println(sa.getModel());
	}
	@Test(groups ={"create"},dependsOnMethods={"initPara"},alwaysRun=true)
	public void rescanAllStorageAdapter() throws Exception {
		if (hostUuid != null) {
			TaskIntermediateResult result = storageDeviceService.rescanAllStorageAdapter(hostUuid);
			TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
	}
	 
	@Test(groups ={"update"},alwaysRun=true)
	public void rescanStorageAdapter() throws Exception {
//		String saUuid = "9de6ac7d-fde9-45aa-9db6-3864ce784d25"; // iscsi adapter.
//		String saUuid = "91e6ad4a-18c6-4002-bcfd-63068c95d74e"; // fc adapter.
		if (hostUuid != null && saUuid != null) {
		    TaskIntermediateResult result = storageDeviceService.rescanStorageAdapter(hostUuid, saUuid);
	        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		}
	}
}
