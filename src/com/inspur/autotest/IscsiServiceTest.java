package com.inspur.autotest;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.IscsiService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.IscsiIface;
import com.inspur.ics.pojo.IscsiSoftwareAdapter;
import com.inspur.ics.pojo.IscsiTarget;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StorageAdapter;
import com.inspur.ics.pojo.TaskInfo;

/**
 * Junit test of Iscsi service.
 * @author jiangwt
 */

public class IscsiServiceTest {

     /**
     * Iscsi service.
     */
    private static IscsiService iscsiService;
    /**
     * HostServiceImpl.
     */
    private static HostService hostService;
    /**
     * TaskService.
     */
    private static TaskService taskService;
    private static StorageDeviceService storageDeviceService;
    static VNetService vNetService;
    private String hostUuid = null;
    private String isaUuid = null;
	private String itUuid= null;
	private String pgUuid= null;
	private String stiqn = null;
	private String tgtIp = null;
	private boolean pgtosaFlag = false;
	private boolean pgtoitFlag = false;

//    /**
//     * user token.
//     */
//    private static String token = "";
  

//    /**
//     * request path.
//     */
//    private static String url = "https://100.1.8.202:443";
    //private static String url1 = "http://100.1.8.200:80";

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();

//        token = factory.getToken();
        iscsiService = factory.getIscsiService();
        storageDeviceService = factory.getStorageDeviceService();
        hostService = factory.getHostService();
        vNetService = factory.getVNetService();
        taskService = factory.getTaskService();
        System.out.println("Init Junit Test");
//        IscsiTarget iscsiTarget = new IscsiTarget();
//
//        System.out.println(iscsiTarget.getIscsiAdapter().getUuid());

    }
    @Parameters({"host1ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.VNetServiceTest.createDVPortGroup"},alwaysRun=true)
	public void getAllScsiDisk(String host1ip) {
    	 List<Host> is = hostService.getAllHostList();
         for (Host host : is) {
             if(host.getIp().equals(host1ip)) {
             	 this.hostUuid = host.getUuid(); // huoqu UUID
             	 break;
             }
         }
         
        
//         pgUuid1 = result.get(1).getUuid();
         List<StorageAdapter> storageAdapters = storageDeviceService
 				.getIscsiAndFcStorageAdapter(hostUuid);
 		for (StorageAdapter sa : storageAdapters) {
 		    if(sa instanceof IscsiSoftwareAdapter){
 		    	isaUuid = sa.getUuid();
 		        break;
 		    }
 	    }
 		
 		 List<StandardPortGroup> result = iscsiService.getAvailablePortGroupForIscsiAdapter(isaUuid);
 		 if(result != null) {
             pgUuid = result.get(0).getUuid();
 		 }
    }
    /**
     * 添加iscsi适配器端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"getAllScsiDisk"},alwaysRun=true)
    public void  addPortGroupToIscsiAdapter() throws Exception {

//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";
		if (isaUuid!=null && pgUuid != null) {
			TaskIntermediateResult taskResult = iscsiService.addPortGroupToIscsiAdapter(isaUuid, pgUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			if(taskInfo.getState().equals(TaskState.FINISHED)) {
				pgtosaFlag = true;
			}
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }
    /**
     * 获取找到的iscsi目标iqn.
     * checked
     */
    @Parameters({"iscsiServerIp"})
    @Test(groups={"create"},dependsOnMethods={"addPortGroupToIscsiAdapter"},alwaysRun=true)
    public void getDiscoveredIscsiTargetIqn(String iscsiServerIp) {
		if (isaUuid != null && pgUuid != null) {
			String tgtIp = iscsiServerIp;
			Integer port = 3260;

			List<String> spgUuids = new ArrayList<String>();
			String string1 = pgUuid;
			spgUuids.add(string1);

			// List<String> spgUuidsJson = FormatUtil.toJson(spgUuids);
			List<String> stringList = iscsiService.getDiscoveredIscsiTargetIqn(isaUuid, tgtIp, port, spgUuids);
			if (stringList != null && stringList.size() > 0) {
				stiqn = stringList.get(stringList.size() / 2);
			}
			// for (String string : stringList) {
			// System.out.println(string);
			// }
		}
    }
    /**
     * 添加iscsi目标.
     * checked
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"getDiscoveredIscsiTargetIqn"},alwaysRun=true)
    public void  addIscsiTarget() throws Exception {

//        IscsiTarget target = new IscsiTarget();
//        target.setUuid("30f0768d-e7fd-4b29-89b7-90eee525ea32");
//        target.setIp("100.1.8.100");
//        target.setPort(3260);
//        target.setIqn("iqn.2003-01.org.linux-iscsi.master.x8664:sn.2f29a244197d");
		if (isaUuid!=null && pgUuid!=null && stiqn != null && tgtIp != null) {
			Integer tgtPort = 3260;

			IscsiSoftwareAdapter isa = new IscsiSoftwareAdapter();
			isa.setUuid(isaUuid);

			StandardPortGroup spg1 = new StandardPortGroup();
			spg1.setUuid(pgUuid);

			IscsiIface ii1 = new IscsiIface();
			ii1.setPortGroup(spg1);
			List<IscsiIface> ifaces = new ArrayList<IscsiIface>();
			ifaces.add(ii1);

			IscsiTarget target = new IscsiTarget();
			target.setIscsiAdapter(isa);
			target.setIp(tgtIp);
			target.setPort(tgtPort);
			target.setIqn(stiqn);
			target.setIfaces(ifaces);
			target.setChap(false);

			TaskIntermediateResult taskResult = iscsiService.addIscsiTarget(target);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}

    }

    /**
     * 添加iscsi目标的端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"create"},dependsOnMethods={"addIscsiTarget"},alwaysRun=true)
    public void  addPortGroupToIscsiTarget() throws Exception {
		if (isaUuid != null) {
			List<IscsiTarget> iscsiTargerList = iscsiService.getIscsiTargetOnIscsiAdapter(isaUuid);
			if(iscsiTargerList != null && iscsiTargerList.size() > 0) {
			    itUuid = iscsiTargerList.get(0).getUuid();
			}
			if (itUuid != null && pgUuid != null) {
				TaskIntermediateResult taskResult = iscsiService.addPortGroupToIscsiTarget(itUuid, pgUuid);
				System.out.println(taskResult.getTaskId());
				TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
				if(taskInfo.getState().equals(TaskState.FINISHED)) {
					pgtoitFlag = true;
				}
				AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
						TaskState.FINISHED, taskInfo.getState());
			}
		}
    }
    /**
     * 获取iscsi适配器可用端口组.
     * checked
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getAvailablePortGroupForIscsiAdapter() {  //200测   9de6ac7d-fde9-45aa-9db6-3864ce784d25 8.5

//        String isaUuid = "e67a7165-bd64-4bad-af73-682e4cd064d8";

        List<StandardPortGroup> standportgroupList = iscsiService.getAvailablePortGroupForIscsiAdapter(isaUuid);

        for (StandardPortGroup standportgroup : standportgroupList) {
            System.out.println(standportgroup.getUuid());
            System.out.println(standportgroup.getName());
            System.out.println(standportgroup.getIp());
            System.out.println(standportgroup.getDatapgType());
         }
    }

    /**
     * 获取iscsi目标可用端口组.
     * checked
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getAvailablePortGroupForIscsiTarget() {

//        String isaUuid = "e67a7165-bd64-4bad-af73-682e4cd064d8";
//        String itUuid = "30f0768d-e7fd-4b29-89b7-90eee525ea32";

    	if(isaUuid != null && itUuid != null) {
            List<StandardPortGroup> standportgroupList = iscsiService.getAvailablePortGroupForIscsiTarget(isaUuid, itUuid);

            for (StandardPortGroup standportgroup : standportgroupList) {
                System.out.println(standportgroup.getUuid());
                System.out.println(standportgroup.getName());

             }
         }
    }

   
    /**
     * 获取iscsi适配器上的iscsi.
     * checked
     */
    
    @Test(groups={"query"},alwaysRun=true)
    public void getIscsiTargetOnIscsiAdapter() {
        List<IscsiTarget> iscsiTargerList = iscsiService.getIscsiTargetOnIscsiAdapter(isaUuid);
        for (IscsiTarget iscsiTarget : iscsiTargerList) {
            System.out.println(iscsiTarget);
         }
    }

    /**
     * 获取iscsi适配器上的端口组.
     * checked
     */
    
    @Test(groups={"query"},alwaysRun=true)
    public void getPortGroupOnIscsiAdapter() {

        List<StandardPortGroup> standportgroupList = iscsiService.getPortGroupOnIscsiAdapter(isaUuid);

//        for (StandardPortGroup standportgroup : standportgroupList) {
//            System.out.println(standportgroup.getUuid());
//            System.out.println(standportgroup.getName());
//            System.out.println(standportgroup.getIp());
//         }
    }

    /**
     * 获取iscsi目标上的端口组.
     * checked
     */
   
    @Test(groups={"query"},alwaysRun=true)
    public void getPortGroupOnIscsiTarget() {
    	if(itUuid != null) {
            List<StandardPortGroup> standportgroupList = iscsiService.getPortGroupOnIscsiTarget(itUuid);
    	}

//        for (StandardPortGroup standportgroup : standportgroupList) {
//            System.out.println(standportgroup.getUuid());
//            System.out.println(standportgroup.getName());
//            System.out.println(standportgroup.getNetmask());
//
//
//         }
    }

    



    /**
     * 移除iscsi目标.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"removePortGroupOnIscsiAdapter"},alwaysRun=true)
    public void  removeIscsiTarget() throws Exception {

        
        List<StandardPortGroup> standportgroupList = iscsiService.getPortGroupOnIscsiAdapter(isaUuid);
        if (standportgroupList.get(0).getUuid()!=null){
            Writer writer =new FileWriter("removeIscsiTarget.txt");
            writer.close();
        }
        
        
        
		if (itUuid != null) {
			TaskIntermediateResult taskResult = iscsiService.removeIscsiTarget(itUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}

    }

    /**
     * 移除iscsi适配器上的端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"addPortGroupToIscsiAdapter"},alwaysRun=true)
    public void  removePortGroupOnIscsiAdapter() throws Exception {
        
//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";
		if (pgtosaFlag) {
		    Writer writer =new FileWriter("removePortGroupOnIscsiAdapter.txt");
		    System.out.println("isaUuid "+isaUuid);
		    System.out.println("pgUuid "+pgUuid);
		    writer.write("[removePortGroupOnIscsiAdapter]isaUuid "+isaUuid+"pgUuid "+pgUuid);
		    writer.close();
			TaskIntermediateResult taskResult = iscsiService.removePortGroupOnIscsiAdapter(isaUuid, pgUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			if(taskInfo.getState().equals(TaskState.FINISHED)) {
				pgtosaFlag = false;
			}
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}

    }

    /**
     * 移除iscsi目标上的端口组.
     * checked.
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @Test(groups={"delete"},dependsOnMethods={"removePortGroupOnIscsiAdapter"},alwaysRun=true)
    public void  removePortGroupOnIscsiTarget() throws Exception {
        
        
        List<StandardPortGroup> standportgroupList = iscsiService.getPortGroupOnIscsiAdapter(isaUuid);
        if (standportgroupList.get(0).getUuid()!=null){
            Writer writer =new FileWriter("removePortGroupOnIscsiTarget.txt");
            writer.close();
        }

//        String pgUuid = "e4fe70f0-bbbc-4afd-8e0f-4753273bbe1a";
		if (pgtoitFlag) {
			TaskIntermediateResult taskResult = iscsiService.removePortGroupOnIscsiTarget(itUuid, pgUuid);
			System.out.println(taskResult.getTaskId());
			TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
			if(taskInfo.getState().equals(TaskState.FINISHED)) {
				pgtoitFlag = false;
			}
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }


}
