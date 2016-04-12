package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;
import java.util.Locale;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VCTemplateService;
import com.inspur.ics.client.VClusterService;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VCluster;
import com.inspur.ics.pojo.VClusterTemplate;

/**
 * @author zuolanhai
 * vcluster template test class.
 */
public class VcTemplateServiceTest {

    /**
     *
     */
    private static VCTemplateService vcTemplateClient;
    /**
     * VCluster service.
     */
    private static VClusterService vclusterService;
    private static TaskService taskService;
    private String vcUUID = null;
    private String vctUUID = null;

    /**
     * init.
     */
    @Parameters({"url", "username", "password", "userLocale"})
    @Test(groups={"init"})
    public static void init(String url, String username,
                            String password, String userLocale) {
    	ClientFactory factory = TestUtil.getClientFactory();
        vcTemplateClient = factory.getVCTemplateService();
        vclusterService = factory.getVClusterService();
        taskService = factory.getTaskService();
//        System.out.println("Init Junit Test");
    }
    

    /**
     *
     */
    @Test(groups={"query"},alwaysRun=true)
    public void getVcTemplateList() {
        List<VClusterTemplate> vcTempList = vcTemplateClient.getVClusterTemplateList();
        AssertJUnit.assertNotNull(vcTempList);
        for(VClusterTemplate vct:vcTempList) {
        	if(vct.getName().equals("test_sdk_vclusterTemplate")){
        		vctUUID = vct.getUuid();
        		break;
        	}
        }
//        System.out.println("vcluster template num : " + vcTempList.size());
    }

    /**
     * @throws Exception 
    *
    */
    @Test(groups={"delete"},alwaysRun=true)
    public void deleteVcTemplate() throws Exception {
//        String uuid = "fe26d19c-1295-4692-b4ed-bb5dd3afaf5f";
		if (vctUUID != null) {
			TaskIntermediateResult result = vcTemplateClient.deleteVclusterTemplate(vctUUID);
	        TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
			AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
					TaskState.FINISHED, taskInfo.getState());
		}
    }

   
    @Test(groups={"update"},alwaysRun=true)
    public void reNameVcTemplate() throws Exception {
//    	VClusterTemplate vctmpl = new VClusterTemplate();
//    	vctmpl.setUuid("fe26d19c-1295-4692-b4ed-bb5dd3afaf5f");
//    	vctmpl.setName("test_sdk_rename_vctemplate");
    	 TaskIntermediateResult result = vcTemplateClient.renameVClusterTemplate(vctUUID, "after_rename_vctemplate");
         TaskInfo taskInfo = TestUtil.waitFor(result.getTaskId());
 	    AssertJUnit.assertEquals(taskInfo.getLocalizeState()+":"+taskInfo.getLocalizedError()+"!!!",TaskState.FINISHED, taskInfo.getState());
    }
}
