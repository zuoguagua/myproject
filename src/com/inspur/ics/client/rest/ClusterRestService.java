package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 集群模块的REST接口.
 * @author zhangjun
 */
@Path("/")
public interface ClusterRestService {

	@POST
	@Path("cluster_addHeartbeatDevice")
	String addHeartbeatDevice(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
			@FormParam("hostUUID") String hostUUID,
			@FormParam("hbDevices") String[] hbDevices);

    @POST
    @Path("cluster_addHostsOnline")
    String addHost(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
            @FormParam("hostUUIDs") String[] hostUUIDs);

    @POST
    @Path("cluster_create")
    String createCluster(@FormParam("token") String token, @FormParam("cluster") String cluster,
    		@FormParam("hostUUID") String hostUUID);

    @POST
    @Path("cluster_getAll")
    String getAllCluster(@FormParam("token") String token);

    @POST
    @Path("cluster_getHeartbeatDeviceInCluster")
    String getAllHeartbeatDevice(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID);

    @POST
    @Path("cluster_getAvailableHosts")
    String getAvailableHosts(@FormParam("token") String token);

    @POST
    @Path("cluster_getAvailableOcfsDataStoreForHeartbeatDevice")
    String getAvailableOcfsDataStoreForHeartbeatDevice(@FormParam("token") String token,
    		@FormParam("clusterUUID") String clusterUUID);

    @POST
    @Path("cluster_getInfo")
    String getClusterInfo(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID);

    @POST
    @Path("cluster_getO2cbConfig")
    String getO2cbConfig(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID);

    @POST
    @Path("cluster_modify")
    String modifyClusterName(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
    		@FormParam("clusterName") String newClusterName);

    @POST
    @Path("cluster_delete")
    String removeCluster(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID);

    @POST
    @Path("cluster_removeHearbeatDevice")
    String removeHeartbeatDevice(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
    		@FormParam("devs") String[] hbUUIDs);

    @POST
    @Path("cluster_removeHosts")
    String removeHost(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
    		@FormParam("hostUUIDs") String[] hostUUIDs);

    @POST
    @Path("cluster_updateO2cbConfig")
    String updateO2cbConfig(@FormParam("token") String token, @FormParam("clusterUUID") String clusterUUID,
    		@FormParam("config") String newO2cbConfig);

}
