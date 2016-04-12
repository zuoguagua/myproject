package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.PortGroup;

/**
 * 
 * @author jingshsh
 */
@Path("/")
public interface VNetRestService{
	
	@POST
	@Path("vnet_vswitch_listStandardSwitchFromDB")
	String listStandardSwitchFromDB(@FormParam("token") String token);

	@POST
	@Path("vnet_vswitch_listStandardSwitchPortgroupFromDB")
	String listStandardSwitchPortgroupFromDB(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_listStandardSwitchPortgroupVmFromDB")
	String listStandardSwitchPortgroupVmFromDB(@FormParam("token") String token, @FormParam("pgUuid") String pgUuid);

	@POST
	@Path("vnet_vswitch_listUnusedPNicFromDB")
	String listUnusedPNicFromDB(@FormParam("token") String token);

	@POST
	@Path("vnet_vswitch_listPortGroupByHostUuid")
	String listPortGroupByHostUuid(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);
	
	//增加@20150610 by wangyanjia
	@POST
    @Path("vnet_vswitch_getVswitchByPortGroupUuid")
    String getVswitchByPortGroupUuid(@FormParam("token") String token, @FormParam("pgUuid") String pgUuid);

	@POST
	@Path("vnet_vswitch_listSwitchByHostUuid")
	String listSwitchByHostUuid(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	@POST
	@Path("vnet_vswitch_listHostUnusedPNic")
	String listHostUnusedPNic(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	@POST
	@Path("vnet_vswitch_listHostPNics")
	String listHostPNics(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	@POST
	@Path("vnet_vswitch_listStandardPortGroupWithIPByHostUuid")
	String listStandardPortGroupWithIPByHostUuid(@FormParam("token") String token, 
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("vnet_vswitch_getStandardSwitchInfo")
	String getStandardSwitchInfo(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_showStandardNetworksOverview")
	String showStandardNetworksOverview(@FormParam("token") String token);

	@POST
	@Path("vnet_vswitch_createStandardSwitch")
	String createStandardSwitch(@FormParam("token") String token,
			@FormParam("standardSwitch") String standardSwitch, @FormParam("destHostUUID") String destHostUUID);

	@POST
	@Path("vnet_vswitch_modifyStandardSwitch")
	String modifyStandardSwitch(@FormParam("token") String token,
			@FormParam("standardSwitch") String standardSwitch);

	@POST
	@Path("vnet_vswitch_createStandardSwitchPortGroup")
	String createStandardSwitchPortGroup(
			@FormParam("token") String token, @FormParam("swUuid") String swUuid,
			@FormParam("standardPortGroup") String pgUuid);

	@POST
	@Path("vnet_vswitch_deleteStandardSwitch")
	String deleteStandardSwitch(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_deleteStandardSwitchPortGroup")
	String deleteStandardSwitchPortGroup(
			@FormParam("token") String token, @FormParam("pgUuid") String pgUuid);

	@POST
	@Path("vnet_vswitch_getAllDVSwitch")
	String getAllDVSwitch(@FormParam("token") String token);

	@POST
	@Path("vnet_vswitch_getDVPortGroupBySwUuid")
	String listDVSwitchHostFromDB(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_getVmsOnPortGroup")
	String getVmsOnPortGroup(@FormParam("token") String token, @FormParam("pgUuid") String pgUuid);

	@POST
	@Path("vnet_vswitch_getHostsOnDVSwitch")
	String getHostsOnDVSwitch(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_getDVSwitchInfo")
	String getDVSwitchInfo(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_showDistributeNetworkOverview")
	String showDistributeNetworkOverview(@FormParam("token") String token);

	@POST
	@Path("vnet_vswitch_getAvailableHostForDVSwitch")
	String getAvailableHostForDVSwitch(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_getHostInDVSwitch")
	String getHostInDVSwitch(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_getPNicUsedBySwitch")
	String getPNicUsedBySwitch(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_createDVSwitch")
	String createDVSwitch(@FormParam("token") String token, @FormParam("clusterUuid") String clusterUuid,
			@FormParam("dvSwitch") String dvSwitch);

	@POST
	@Path("vnet_vswitch_deleteDVSwitch")
	String deleteDVSwitch(@FormParam("token") String token, @FormParam("swUuid") String swUuid);

	@POST
	@Path("vnet_vswitch_configDVSwitch")
	String configDVSwitch(@FormParam("token") String token, @FormParam("dvSwitch") String dvSwitch,
			@FormParam("action") boolean flag);

	@POST
	@Path("vnet_vswitch_configNicOnDVSwitch")
	String configNicOnDVSwitch(@FormParam("token") String token, @FormParam("swUuid") String dvswUuid,
			@FormParam("host") String host);

	@POST
	@Path("vnet_vswitch_createDVPortGroup")
	String createDVPortGroup(@FormParam("token") String token,
			@FormParam("swUuid") String swUuid, @FormParam("dvPortGroup") String dvPortGroup);

	@POST
	@Path("vnet_vswitch_deleteDVPortGroup")
	String deleteDVPortGroup(@FormParam("token") String token, @FormParam("pgUuid") String pgUuid);

	@POST
	@Path("vnet_vswitch_getPortGroupList")
	String getPortGroupList(@FormParam("token") String token, @FormParam("targetType") String targetType,
			@FormParam("targetUuid") String targetUuid);

	@POST
	@Path("vnet_vswitch_getDVSwitchList")
	String getDVSwitchList(@FormParam("token") String token, @FormParam("targetType") String targetType, @FormParam("targetUuid") String targetUuid);

	@POST
	@Path("vnet_vswitch_getManagementPortgroupOnHost")
    String getManagementPortgroupOnHost(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);
	
	@POST
	@Path("vnet_vswitch_scanEthernetAdapters")
    String scanEthernetAdapters(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);
    
	@POST
	@Path("vnet_vswitch_configPortgroup")
    String configPortgroup(@FormParam("token") String token, @FormParam("portGroup") String portGroup);
}
