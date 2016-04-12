/**
 * 
 */
package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.client.impl.VNetServiceImpl;
import com.inspur.ics.common.Types.PortGroupServiceType;
import com.inspur.ics.common.Types.PortsBondMode;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.DVPortGroup;
import com.inspur.ics.pojo.DVSwitch;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.PNic;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StandardSwitch;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VirtualSwitch;
import com.thoughtworks.xstream.io.json.JsonWriter.Format;


/**
 * @author jingshsh
 * 
 */

@SuppressWarnings({"rawtypes","unused"})
public class VNetServiceTest {
	
	static VNetService vNetService;
	private static HostService hostService;
    private static StorageDeviceService storageDeviceService;
    private static ClusterService clusterService;
    private static TaskService taskService;
	private String swUuid = null;
	private String hostUuid = null;
	private String hostUuid1 = null;	
	private String pgUuid = null;
	private String pnicUuid = null;
	private String pnicUuid1 = null;
	private String pnicUuid2 = null;
	private String pnicUuid3 = null;
	private String dvswUuid = null;
	private String clusterUuid = null;
	private PortGroup portGroup =null;
	/**
	 * factory for network service.
	 */
	private static ClientFactory factory;
	
	/**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
	    vNetService = factory.getVNetService();
	    hostService  =  factory.getHostService();
        storageDeviceService = factory.getStorageDeviceService();
        clusterService = factory.getClusterService();
        taskService = factory.getTaskService();
//		System.out.println("Init Junit Test");
	}
    @Parameters({"host1ip", "host2ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.DataStoreServiceTest.mountDataStore"},alwaysRun=true)
    public void initPara(String host1ip,String host2ip) {
    	//获取主机uuid 两个 
    	List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if(host.getIp().equals(host1ip)) {
            	 this.hostUuid = host.getUuid(); // huoqu UUID
            	 if(host.getpNic().size()>1) {
            	    	pnicUuid2 = host.getpNic().get(0).getUuid();
            	    	pnicUuid3 = host.getpNic().get(1).getUuid();
            	     } 
            	     else if(host.getpNic().size()==1) {
            	    	pnicUuid2 = host.getpNic().get(0).getUuid();
            	    	pnicUuid3 = pnicUuid;
            	     }
            	 
            }
            if(host.getIp().equals(host2ip)) {
           	     this.hostUuid1 = host.getUuid(); // huoqu UUID
           	     if(host.getpNic().size()>1) {
           	    	pnicUuid = host.getpNic().get(0).getUuid();
           	    	pnicUuid1 = host.getpNic().get(1).getUuid();
           	     } 
           	     else if(host.getpNic().size()==1) {
           	    	pnicUuid = host.getpNic().get(0).getUuid();
           	    	pnicUuid1 = pnicUuid;
           	     }
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
        
    }

//	@Test
//	public void Sysout(){
//		System.out.println("hello world");
//	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listStandardSwitchFromDB() {
		 
		List<StandardSwitch> result = vNetService.listStandardSwitchFromDB();
		
		
//		for (StandardSwitch itresult : result) {
//			System.out.println("交换机UUID："+itresult.getUuid());
//			System.out.println("交换机名称："+itresult.getName());
//		}
		 
	}
	
	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listStandardSwitchPortgroupFromDB(
			) {
//		String swUuid = "802c1d27-3e74-4058-b12f-0e6f4db2588d";
		List<StandardPortGroup> result = vNetService.listStandardSwitchPortgroupFromDB(swUuid);
//		for (StandardPortGroup itresult : result) {
//			System.out.println("标准端口组UUID："+itresult.getUuid());
//			System.out.println("标准端口组的名称："+itresult.getName());
//		}
		 
	}

	/**
	 *  失败***********************************************************************待议
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listStandardSwitchPortgroupVmFromDB() {
		String portGroupUuid = "manageNetwork";
		List<VM> result = vNetService.listStandardSwitchPortgroupVmFromDB(portGroupUuid);
//		System.out.println(result.size());
//		for (VM itresult : result) {
//			System.out.println("虚拟机名称："+itresult.getName());
//		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listUnusedPNicFromDB() {
		List<PNic> result = vNetService.listUnusedPNicFromDB();
//		for (PNic itresult : result) {
//			System.out.println("未使用的网卡名称："+itresult.getName());
//		}
		 
	}

	/**
	 * 测试成功. 
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listPortGroupByHostUuid() {
//		String hostUuid = "29b6e3ae-7804-4100-b120-47f5a992de33";
		List<PortGroup> result = vNetService.listPortGroupByHostUuid(hostUuid);
//		for (PortGroup itresult : result) {
//			System.out.println("端口组的UUID："+itresult.getUuid());
//			System.out.println("端口组名称："+itresult.getName());
//		}
		 
	}

	/**
     * 增加@20150610 by wangyanjia
     * 测试成功.
     */
	@Test(groups={"query"},alwaysRun=true)
    public void getVswitchByPortGroupUuid() {
//        String pgUuid = "b3546842-5eb4-47c4-a444-28daec417761";
		if(pgUuid != null) {
            VirtualSwitch result = vNetService.getVswitchByPortGroupUuid(pgUuid);
		}
//        System.out.println("交换机UUID：" + result.getUuid());
//        System.out.println("交换机名称：" + result.getName());

    }

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listSwitchByHostUuid() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		List<VirtualSwitch> result = vNetService.listSwitchByHostUuid(hostUuid1);
//		for (VirtualSwitch itresult : result) {
//			System.out.println("虚拟交换机UUID："+itresult.getUuid());
//			System.out.println("虚拟交换机名称："+itresult.getName());
//		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listHostUnusedPNic() {
//		String hostUuid = "29b6e3ae-7804-4100-b120-47f5a992de33";
		List<PNic> result = vNetService.listHostUnusedPNic(hostUuid);
//		System.out.println(result.size());
//		for (PNic itresult : result) {
//			System.out.println("未使用的网卡UUID："+itresult.getUuid());
//			System.out.println("未使用的网卡名称："+itresult.getName());
//		}
		 
	}
	
	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listHostPNics() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		List<PNic> result = vNetService.listHostPNics(hostUuid1);
//		for (PNic itresult : result) {
//			System.out.println("网卡名称："+itresult.getName());
//			System.out.println("网卡UUID："+itresult.getUuid());
//		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listStandardPortGroupWithIPByHostUuid(
			) {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		List<StandardPortGroup> result = vNetService.listStandardPortGroupWithIPByHostUuid(hostUuid1);
//		for (StandardPortGroup itresult : result) {
//			System.out.println("标准端口组UUID："+itresult.getUuid());
//			System.out.println("标准端口组名称："+itresult.getName());
//		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getStandardSwitchInfo() {
//		String swUuid1 = "8fa896a7-6b76-43d0-ade6-4c76b2e3b75b";
		List<StandardSwitch> result = vNetService.listStandardSwitchFromDB();
		swUuid = result.get(0).getUuid();
		StandardSwitch ss = vNetService.getStandardSwitchInfo(swUuid);
//		System.out.println("交换机UUID："+result.getUuid());
//		System.out.println("交换机名称："+result.getName());
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void showStandardNetworksOverview() {
		List<StandardPortGroup> result = vNetService.showStandardNetworksOverview();
//		for (StandardPortGroup itresult : result) {
//			System.out.println("标准端口组UUID："+itresult.getUuid());
//			System.out.println("标准端口组的名称："+itresult.getName());
//		}
		 
	}

//	/**
//	 * 测试成功.
//	 */
//	@Test(groups={"create"},dependsOnMethods={"createDVPortGroup"},alwaysRun=true)
//	public void createStandardSwitch() {
//		StandardSwitch sw = new StandardSwitch();
//		sw.setName("sw");
//		sw.setPnics(null);
//		String destHostUUID = hostUuid1;
//		TaskIntermediateResult result = vNetService.createStandardSwitch(sw,destHostUUID);
//		StandardSwitch sw1 = new StandardSwitch();
//		sw1.setName("sw1");
//		sw1.setPnics(null);
//		String destHostUUID1 = hostUuid1;
//		vNetService.createStandardSwitch(sw1,destHostUUID1);
//		StandardSwitch sw2 = new StandardSwitch();
//		sw2.setName("sw2");
//		sw2.setPnics(null);
//		String destHostUUID2 = hostUuid1;
//		vNetService.createStandardSwitch(sw2,destHostUUID2);
////		System.out.println(result.getTaskId());
//	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Test(groups={"update"},dependsOnMethods={"initPara","getStandardSwitchInfo"},alwaysRun=true)
	public void modifyStandardSwitch() throws Exception {
		StandardSwitch sw = new StandardSwitch();
		sw.setUuid(swUuid);
		PNic pnic = new PNic();
		pnic.setUuid(pnicUuid);
		List pnics = new ArrayList<PNic>();
		pnics.add(pnic);
		sw.setPnics(pnics);
		TaskIntermediateResult result = vNetService.modifyStandardSwitch(sw);
	    TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		System.out.println(result);
		
	}

	/**
	 * 测试成功.
	 */
//	@Test(groups={"create"},dependsOnMethods={"listStandardSwitchFromDB"},alwaysRun=true)
//	public void createStandardSwitchPortGroup() {
////		String swUuid = "802c1d27-3e74-4058-b12f-0e6f4db2588d";
//		StandardPortGroup pg = new StandardPortGroup();
//		/**
//		 * 这块使用UserName，PortGroup的name属性是用来后台使用的界
//		 */
//		pg.setUserName("haha");
//		
//		pg.setVlan(88);
////		System.out.println(FormatUtil.toJson(pg));
//		TaskIntermediateResult result1 = vNetService.createStandardSwitchPortGroup(swUuid,pg);
////		System.out.println(result1.getTaskId()); 
//	}

//	/**
//	 * 测试成功.
//	 */
//	@Test(groups={"delete"},alwaysRun=true)
//	public void deleteStandardSwitch() {
////		String swUuid2 = "10902e9e-75e8-4cd0-872c-49d4da0bfa08";
//		TaskIntermediateResult result = vNetService.deleteStandardSwitch(swUuid2);
//		 System.out.println(result.getTaskId());
//	}
//
//	/**
//	 * 测试成功.
//	 */
//	@Test(groups={"delete"},alwaysRun=true)
//	public void deleteStandardSwitchPortGroup() {
////		String token = "";
////		String portGroupUuid = "7e90c6ba-5900-4614-b09b-ae0b20a02054";
//		TaskIntermediateResult result = vNetService.deleteStandardSwitchPortGroup(pgUuid);
////		System.out.println(result.getTaskId());
//		 
//	}
	
	/**
	 * 测试成功.
	 */
	@Test(groups={"create"},dependsOnMethods={"createDVSwitch"},alwaysRun=true)
	public void getAllDVSwitch() {
		List<DVSwitch> result = vNetService.getAllDVSwitch();
		for (DVSwitch itresult : result) {
			if("dvsw".equals(itresult.getName())) {
				dvswUuid = itresult.getUuid();
			}
//			System.out.println(itresult.getName());
		}
//		for (DVSwitch itresult : result) {
//			System.out.println("分布式交换机UUID："+itresult.getUuid());
//			System.out.println("分布式交换机名称："+itresult.getName());
//		}
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void listDVSwitchHostFromDB() {
//		String dvswUuid = "dfb1f612-f34b-4503-bd72-64cdc5205aa3";
		List<Host> result = vNetService.listDVSwitchHostFromDB(dvswUuid);
//		for (Host itresult : result) {
//			System.out.println("主机UUID："+itresult.getUuid());
//			System.out.println("主机IP："+itresult.getIp());
//		}
		 
	}

	/**
	 * 失败***********************************************************************待议
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getVmsOnPortGroup() {
//		String pgUuid = "1744fdd3-b030-42f1-b11b-d4c3bf41da5c";
		List<VM> result = vNetService.getVmsOnPortGroup(pgUuid);
		for (VM itresult : result) {
			System.out.println(itresult.getName());
			System.out.println(itresult.getRuntime().getVncPort());
		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getHostsOnDVSwitch() {
//		String dvswUuid = "dfb1f612-f34b-4503-bd72-64cdc5205aa3";
		List<Host> result = vNetService.getHostsOnDVSwitch(dvswUuid);
		for (Host itresult : result) {
			System.out.println(itresult.getIp());
		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getDVSwitchInfo() {
//		String dvSwUuid = "dfb1f612-f34b-4503-bd72-64cdc5205aa3";
		DVSwitch result = vNetService.getDVSwitchInfo(dvswUuid);
		System.out.println(result.getName());
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void showDistributeNetworkOverview() {
		List<DVPortGroup> result = vNetService.showDistributeNetworkOverview();
		for (DVPortGroup itresult : result) {
			System.out.println("分布式端口组的UUID："+itresult.getUuid());
			System.out.println("分布式端口组的名称："+itresult.getName());
		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getAvailableHostForDVSwitch() {
//		String dvswUuid1 = "8a01a271-bb96-4e70-804c-4d8ba5396b80";
		List<Host> result = vNetService.getAvailableHostForDVSwitch(dvswUuid);
		for (Host itresult : result) {
			System.out.println(itresult.getIp());
		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getHostInDVSwitch() {
//		String dvswUuid = "dfb1f612-f34b-4503-bd72-64cdc5205aa3";
		List<Host> result = vNetService.getHostInDVSwitch(dvswUuid);
		for (Host itresult : result) {
			System.out.println(itresult.getIp());
		}
		 
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getPNicUsedBySwitch() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
//		String swUuid = "0cd18c6b-6582-4a53-8e09-01dd5da45d74";
		List<PNic> result = vNetService.getPNicUsedBySwitch(hostUuid1,swUuid);
		for (PNic itresult : result) {
			System.out.println(itresult.getName());
		}
		 
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Test(groups={"create"},dependsOnMethods={"initPara"},alwaysRun=true)
	public void createDVSwitch() throws Exception {
//		String clusterUuid = "c3c1e3fa-6a80-490b-bc8e-9145c878937e";
		DVSwitch dvsw = new DVSwitch();
		dvsw.setName("dvsw");
		dvsw.setBondmode(PortsBondMode.BALNCESLB);
		TaskIntermediateResult result = vNetService.createDVSwitch(clusterUuid,dvsw);
	    TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		 
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Test(groups={"delete"},dependsOnMethods={"deleteDVPortGroup"},alwaysRun=true)
	public void deleteDVSwitch() throws Exception {
//		String dvswUuid = "3eee0915-421b-4e33-8bd6-1c13019a5951";
		
		TaskIntermediateResult result = vNetService.deleteDVSwitch(dvswUuid);
	    TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		 
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Parameters({"host1ip"})
	@Test(groups={"update"},alwaysRun=true)
	public void configDVSwitch(String host1ip) throws Exception {
		TaskTargetType targetType = TaskTargetType.PORT_GROUP;
		String targetUuid = pgUuid;
		List<DVSwitch> result = vNetService.getDVSwitchList(targetType, targetUuid);
		DVSwitch sw = null;
		if (result != null) {
			for (DVSwitch itresult : result) {
				System.out.println(itresult.getName());
				if ((itresult.getName()).equals("dvsw")) {
					sw = itresult;
					break;
				}
			}
		}
		Host host = null;
		List<Host> result1 = vNetService.getAvailableHostForDVSwitch(sw.getUuid());
		if (result1 != null) {
			for (Host itresult : result1) {
				if ((itresult.getIp()).equals(host1ip)) {
					host = itresult;
					break;
				}
			}
		}
		ArrayList<Host> hosts = new ArrayList<Host>();
		host.setMultipathConfig(null);
		host.setVsanConfig(null);
		List<PNic> unusedPnics = vNetService.listHostUnusedPNic(host.getUuid());
		PNic pnic1 = new PNic();
		pnic1.setUuid(unusedPnics.get(0).getUuid());
		ArrayList<PNic> pnics = new ArrayList<PNic>();
		pnics.add(pnic1);
		host.setpNic(pnics);
		hosts.add(host);
		sw.setHosts(hosts);
		for (PNic pnic : sw.getPnics()) {
			pnic.setPciDevice(null);
		}
		System.out.println("&&&&&&" + sw.getHosts().get(0).getIp());
		TaskIntermediateResult result2 = vNetService.configDVSwitch(sw, true);
		TaskInfo taskInfo = TestUtil.waitFor(result2.getTaskId());
		AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
				TaskState.FINISHED, taskInfo.getState());
			System.out.println(result2.getTaskId());
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Test(groups={"update"},alwaysRun=true)
	public void configNicOnDVSwitch() throws Exception {
//		String dvswUuid = "dfb1f612-f34b-4503-bd72-64cdc5205aa3";
		Host host = new Host();
		host.setUuid(hostUuid1);
		PNic pnic = new PNic();
		pnic.setUuid(pnicUuid1);
		pnic.setUsed(true);
		ArrayList<PNic> pnics = new ArrayList<PNic>();
		pnics.add(pnic);
		host.setpNic(pnics);
		System.out.println(FormatUtil.toJson(host));
		TaskIntermediateResult result = vNetService.configNicOnDVSwitch(dvswUuid,host);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		 
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Test(groups={"create"},dependsOnMethods={"getAllDVSwitch"},alwaysRun=true)
	public void createDVPortGroup() throws Exception {
//		String dvswUuid1 = "8a01a271-bb96-4e70-804c-4d8ba5396b80";
		DVPortGroup portGroup = new DVPortGroup();
		portGroup.setUserName("sdkdvpg1");
		portGroup.setVlan(68);
		portGroup.setType(PortGroupServiceType.COMPORTGROUP);
		TaskIntermediateResult result = vNetService.createDVPortGroup(dvswUuid,portGroup);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
	}

	/**
	 * 测试成功.
	 * @throws Exception 
	 */
	@Test(groups={"delete"},dependsOnMethods={"com.inspur.ics.client.autotest.DataStoreServiceTest.removeDataStore"},alwaysRun=true)
	public void deleteDVPortGroup() throws Exception {
//		String pgUuid = "646fb853-6481-46c8-8bd0-b5a606dd2bf5";
		if(pgUuid != null) {
		    TaskIntermediateResult result = vNetService.deleteDVPortGroup(pgUuid); 
	        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	        AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		}
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"querybefore"},alwaysRun=true)
	public void getPortGroupList() {
		TaskTargetType targetType = TaskTargetType.CLUSTER;
		String targetUuid = clusterUuid;
		vNetService.getPortGroupList(targetType,targetUuid);
		targetType = TaskTargetType.DV_SWITCH;
		targetUuid = dvswUuid;
		List<PortGroup> result = vNetService.getPortGroupList(targetType,targetUuid);
		for (PortGroup itresult : result) {
			if("sdkdvpg1".equals(itresult.getUserName())) {
				pgUuid = itresult.getUuid();
				portGroup = itresult;
				break;
			}
//			System.out.println(itresult.getUuid());
//			System.out.println(itresult.getUserName());
//			System.out.println(itresult.getVswitch().getName());
		}
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getDVSwitchList() {
		TaskTargetType targetType = TaskTargetType.PORT_GROUP;
		String targetUuid = pgUuid;
		List<DVSwitch> result = vNetService.getDVSwitchList(targetType,targetUuid);
	}

	/**
	 * 测试成功.
	 */
	@Test(groups={"query"},alwaysRun=true)
	public void getManagementPortgroupOnHost() {
//		String hostUuid1 = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef";
		StandardPortGroup result = vNetService.getManagementPortgroupOnHost(hostUuid1);
//		System.out.println(result.getName());
	}
	
    /**
     * 测试成功.
     * @throws Exception 
     */
	@Test(groups={"query"},alwaysRun=true)
	public void scanEthernetAdapters() throws Exception{
//		String hostUuid = "560851c9-c057-4253-a178-92e68e9e3104";
		TaskIntermediateResult result = vNetService.scanEthernetAdapters(hostUuid);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//		System.out.println(result.getTaskId());
	}
    
    /**
     * 测试成功.
     * @throws Exception 
     */
	@Test(groups={"update"},alwaysRun=true)
	public void configPortgroup() throws Exception{
		portGroup.setVlan(8);
		TaskIntermediateResult result = vNetService.configPortgroup(portGroup);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//		System.out.println(result.getTaskId());

	}
}
