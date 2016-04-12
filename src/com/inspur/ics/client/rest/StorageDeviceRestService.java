package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 存储设备模块的REST接口.
 * @author zhangjun
 */
@Path("/")
public interface StorageDeviceRestService {

	@POST
	@Path("storage_disk_getAllScsiDisk")
	String getAllScsiDisk(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getAvailableBlockDeviceForHeartbeatDevice")
	String getAvailableBlockDeviceForHeartbeatDevice(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getAvailableBlockDeviceForOcfsDataStore")
	String getAvailableBlockDeviceForOcfsDataStore(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getAvailableBlockDeviceForOsd")
	String getAvailableBlockDeviceForOsd(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getAvailableBlockDeviceForVirtualDisk")
	String getAvailableBlockDeviceForVirtualDisk(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getIscsiAndFcStorageAdapter")
	String getIscsiAndFcStorageAdapter(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_getScsiDiskInfo")
	String getScsiDiskInfo(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid,
			@FormParam("scsiDiskUuid") String scsiDiskUuid);

	@POST
	@Path("storage_disk_getScsiDiskOnIscsiTarget")
	String getScsiDiskOnIscsiTarget(@FormParam("token") String token, @FormParam("itUuid") String itUuid);

	@POST
	@Path("storage_disk_getStorageAdapterInfo")
	String getStorageAdapterInfo(@FormParam("token") String token,
			@FormParam("hostUuid") String hostUuid, @FormParam("saUuid") String saUuid);

	@POST
	@Path("storage_disk_rescanAllStorageAdapter")
	String rescanAllStorageAdapter(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	@POST
	@Path("storage_disk_rescanStorageAdapter")
	String rescanStorageAdapter(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid,
			@FormParam("saUuid") String saUuid);
	
}