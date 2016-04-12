package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author zuolh
 * 
 */
// server-side path
@Path("/")
public interface VmTemplateRestService {

	/**
	 * @param token
	 *            User Authentication
	 * @param vmUUID
	 *            src vm
	 * @param templateName
	 *            str template name
	 * @param tmplDesp
	 *            template descripotion
	 * @return result
	 */
	@POST
	@Path("vmTemplate_createByVM")
	String createTemplateByVm(@FormParam("token") String token,
			@FormParam("vmUUID") String vmUUID,
			@FormParam("templateName") String templateName,
			@FormParam("tmplDesp") String tmplDesp);

	/**
	 * @param token
	 *            User Authentication
	 * @param tmplUUID
	 *            str
	 * @param templateName
	 *            str
	 * @param tmplDesp
	 *            str
	 * @return result
	 */
	@POST
	@Path("vmTemplate_createByTemplate")
	String createByTemplate(@FormParam("token") String token,
			@FormParam("tmplUUID") String tmplUUID,
			@FormParam("templateName") String templateName,
			@FormParam("tmplDesp") String tmplDesp);

	/**
	 * @param token
	 *            User Authentication
	 * @param clusterUUID
	 *            cluster UUID
	 * @return result
	 */
	@POST
	@Path("vmTemplate_getAllInCluster")
	String getVmTemplateByCluster(@FormParam("token") String token,
			@FormParam("clusterUUID") String clusterUUID);

	/**
	 * get special vm template list.
	 * @param token
	 *        user token
	 * @param targetType
	 *            target Type
	 * @param targetUuid
	 *            target Uuid
	 * @return result
	 */
	@POST
	@Path("vmTemplate_getVMTemplateList")
	String getVMTemplateList(@QueryParam("token") String token,
			@FormParam("targetType") String targetType,
			@FormParam("targetUuid") String targetUuid);

	/**
	 * @param token
	 *            User Authentication
	 * @param templateName
	 *            string
	 * @return result
	 */
	@POST
	@Path("vmTemplate_getVMTemplate")
	String getVmTemplateByName(@FormParam("token") String token,
			@FormParam("templateName") String templateName);

	/**
	 * modify Vm Template.
	 * @param token
	 *            user token
	 * @param vm
	 *        vm info(json format)
	 * @return result
	 */
	@POST
	@Path("vmTemplate_modify")
	String modifyVmTemplate(@FormParam("token") String token, @FormParam("vm") String vm);

	/**
	 * @param token
	 *            User Authentication
	 * @param tmplUUID
	 *            str
	 * @return result
	 */
	@POST
	@Path("vmTemplate_delete")
	String deleteVmTemplate(@FormParam("token") String token,
			@FormParam("tmplUUID") String tmplUUID);

	/**
	 * import VM Template.
	 * @param token
	 *            User token
	 * @param template
	 *            template
	 * @param filePath
	 *            file Path
	 * @return result
	 */
	@POST
	@Path("vmTemplate_importVMTemplate")
	String importVMTemplate(@FormParam("token") String token,
			@FormParam("template") String template,
	        @FormParam("filePath") String filePath);

	/**
	 * export VM Template.
	 * @param token
	 *            User token
	 * @param tmplUUID
	 *            vm template UUID
	 * @param filePath
	 *            file Path
	 * @return result
	 */
	@POST
	@Path("vmTemplate_exportVMTemplate")
	String exportVMTemplate(@FormParam("token") String token,
			@FormParam("tmplUUID") String tmplUUID,
	        @FormParam("filePath") String filePath);

	/**
	 * get vm template info.
	 * @param token
	 *            user token
	 * @param tmplUUID
	 *            template uuid
	 * @return result
	 */
	@POST
	@Path("vmTemplate_getInfo")
	String getVmTemplateInfo(@FormParam("token") String token,
			@FormParam("tmplUUID") String tmplUUID);
}
