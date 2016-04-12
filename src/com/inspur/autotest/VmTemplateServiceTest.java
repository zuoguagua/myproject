package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.client.VMTemplateService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMConfigInfo;
import com.inspur.ics.pojo.VMTemplate;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VirtualDisk;

/**
 * @author zuolanhai vm template test class.
 */
public class VmTemplateServiceTest {

//	private String vmUUID = "211000af-1ead-4601-8bc7-332c482abf1d"; // 202
	private String tmplUUID = "4b72038a-0776-44db-bb6e-75aabd03e828";
	private String hostUUID = "0fe940bc-8366-4cfa-9418-5dcb43f5a2ef"; // 202
	private String clusterUUID = "bf3fb7a5-a328-460e-900e-e4538dafa437";
	 private static ClusterService clusterService;
	/**
     *
     */
	private static VMTemplateService vmTemplateClient;
	/**
     * HostServiceImpl.
     */
    private static HostService hostService;
    private static VMService vmService;
    private static TaskService taskService;
    private static DataStoreService dsService;
    private static VNetService vNetService;
	
	/**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
		vmTemplateClient = factory.getVMTemplateService();
		hostService = factory.getHostService();
		clusterService = factory.getClusterService();
		vmService = factory.getVMService();
		taskService = factory.getTaskService();
		dsService = factory.getDataStoreService();
		vNetService = factory.getVNetService();
		System.out.println("Init Junit Test:" + factory.getToken());
	}
    @Parameters({"host1ip"})
    @Test(groups={"create"},dependsOnMethods={"com.inspur.ics.client.autotest.VMServiceTest.attachCdrom"},alwaysRun=true)
    public void testGetParas(String host1ip) {
        List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if(host.getIp().equals(host1ip)) {
            	 this.hostUUID = host.getUuid(); // huoqu UUID
            	 break;
            }
        }
        
       List<Cluster> clusterList = clusterService.getAllCluster();
//      AssertJUnit.assertNotNull(clusterList);
      if (clusterList.size() > 0){
          for(int i = 0; i < clusterList.size(); i++) {
          	if("sdk_cluster".equals(clusterList.get(i).getDisplayName())){
          		clusterUUID = clusterList.get(i).getUuid();
          	}
          }
      }
//      VM vm = vmService.getVMInfoByName("vmtest0001");
//      AssertJUnit.assertEquals(vm.getName(), "vmtest0001");
//      if(vm != null) {
//      	vmUUID = vm.getConfig().getUuid();
//      }
    }
	/**
     *
     */
    @Test(groups={"query"},alwaysRun=true)
	public void getVmtemplateList() {
		List<VM> vcTempList = vmTemplateClient
				.getVmTemplateByCluster(clusterUUID);
		System.out.println("result : \n" + vcTempList.size());
	}

    @Test(groups={"query"},alwaysRun=true)
	public void getVMTemplateList() {
		List<VM> vcTempList = vmTemplateClient.getVMTemplateList(TaskTargetType.HOST,
				hostUUID);
		System.out.println(vcTempList.size());
	}
	/**
	 * @throws Exception 
     *
     */
    @Test(groups={"create"},dependsOnMethods={"getVmTemplateByName"},alwaysRun=true)
	public void cpTemplate() throws Exception {
//		String tmplUUID = "4b72038a-0776-44db-bb6e-75aabd03e828";
		String templateName = "test_sdk_tmpl_from_tmpl";
		String tmplDesp = "test sdk--create template from template";
		TaskIntermediateResult result = vmTemplateClient.createVMTemplateByTemplate(
				tmplUUID, templateName, tmplDesp);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//		System.out.print("copy template taskid :" + result.getTaskId()
//				+ "\nresut:" + result.getResult().toString());
	}

//	/**
//    *
//    */
//    @Test(groups={"create"},dependsOnMethods={"testGetParas"},alwaysRun=true)
//	public void createVmTemplate() {
//		String templateName = "test_sdk_create_vmTemplate";
//		String tmplDesp = "test sdk create vmTemplate";
//		vmTemplateClient.createVMTemplateByVM(
//				vmUUID, templateName, tmplDesp);
////		System.out.print("create template taskid :" + result.getTaskId()
////				+ "\nresut:" + result.getResult().toString());
//	}

	/**
	 * @throws Exception 
    *
    */
    @Test(groups={"delete"},dependsOnMethods={"getVmTemplateByName","com.inspur.ics.client.autotest.VClusterServiceTest.deleteVCluster"},alwaysRun=true)
	public void deleteVmTemplate() throws Exception {
//		String uuid = "79c27b6d-f194-4e84-8c1a-78cf5f99339b";
//		String templateName = "rest_test1";
//		String tmplDesp = "rest test";
    	String cpytmplUUID = null;
    	List<VM> templates = vmTemplateClient.getVmTemplateByName("test_sdk_create_vmTemplate");
		if(templates != null && templates.size() != 0) {
			cpytmplUUID = templates.get(0).getConfig().getUuid();
		}
		if(cpytmplUUID != null) {
		    vmTemplateClient.deleteVmTemplate(cpytmplUUID);
		}
		cpytmplUUID =null;
		templates = vmTemplateClient.getVmTemplateByName("test-import");
		if(templates != null && templates.size() != 0) {
			cpytmplUUID = templates.get(0).getConfig().getUuid();
		}
		if(cpytmplUUID != null) {
		    vmTemplateClient.deleteVmTemplate(cpytmplUUID);
		}
    	TaskIntermediateResult result = vmTemplateClient.deleteVmTemplate(tmplUUID);
    	
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());

	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
	}

	/**
	 * @throws Exception 
    *
    */
    @Test(groups={"update"},alwaysRun=true)
	public void modifyVmTemplate() throws Exception {
        VM vmInfo = vmTemplateClient.getVMTemplateInfo(tmplUUID);
  		
    	vmInfo.getConfig().setMaxMem(4096);
    	vmInfo.getConfig().setMaxVcpus(8);
  		TaskIntermediateResult result = vmTemplateClient.modifyVmTemplate(vmInfo);
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//		System.out.print("modify template taskid :" + result.getTaskId()
//				+ "\nresut:" + result.getResult().toString());
	}

	/**
	 * @throws Exception 
     *
     */
    @Test(groups={"update"},dependsOnMethods={"exportVMTemplate"},alwaysRun=true)
	public void importVMTemplate() throws Exception {
		VM template = vmService.getOvaFileConfig(hostUUID, "/storage/nfs/"+tmplUUID+".ova");
		template.setName("test-import");
		// VMConfigInfo config = new VMConfigInfo();
		// config.setNicType("e1000");
		// template.setConfig(config);
        Host host = new Host();
        host.setUuid(hostUUID);
        template.setHost(host);
        DataStore data = new DataStore();
		for (DataStore datatmp : dsService.getDataStoreOnHost(hostUUID)) {
			if (datatmp instanceof OcfsDataStore) {
				data.setUuid(datatmp.getUuid());
			}
		}
        for (VirtualDisk vds : template.getConfig().getDisks()) {
        	vds.setDataStore(data);
        }
        PortGroup portgroup = new PortGroup();
        portgroup.setUuid(vNetService.getPortGroupList(TaskTargetType.HOST, hostUUID).get(0).getUuid());
        for (VNic vnic : template.getConfig().getNics()) {
        	vnic.setPortGroup(portgroup);
        }
		TaskIntermediateResult result = vmTemplateClient.importVMTemplate(template, "/storage/nfs/"+tmplUUID+".ova");
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
		System.out.print("import Vm template taskid :" + result.getTaskId()
				+ "\nresut:" + result.toString());
	}

	/**
	 * .
	 * @throws Exception 
	 */
    @Test(groups={"update"},alwaysRun=true)
	public void exportVMTemplate() throws Exception {
		TaskIntermediateResult result = vmTemplateClient.exportVMTemplate(tmplUUID, "/storage/nfs/");
        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
//		System.out.print("Export Vm template taskid :" + result.getTaskId()
//				+ "\nresut:" + result.getResult().toString());
	}

	/**
	 * .
	 */
    @Test(groups={"create"},dependsOnMethods={"testGetParas"},alwaysRun=true)
	public void getVmTemplateByName() {
		List<VM> templates = vmTemplateClient.getVmTemplateByName("test_sdk_create_vmTemplate");
		AssertJUnit.assertEquals(templates.get(0).getName(), "test_sdk_create_vmTemplate");
		if(templates != null) {
			tmplUUID = templates.get(0).getConfig().getUuid();
		}
//		for (VM vm : templates) {
//			System.out.println(vm.getDescription());
//		}
//		System.out.println(templates.size());
	}
    @Test(groups={"query"},alwaysRun=true)
	public void getInfo() {
		VM vmInfo = vmTemplateClient.getVMTemplateInfo(tmplUUID);
//		System.out.println("name:" + vmInfo.getName());
//		System.out.println("desc:" + vmInfo.getDescription());
//		System.out.println("belonged host ip:" + vmInfo.getHost().getIp());
	}
}
