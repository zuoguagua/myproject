package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.HostScsiDisk;
import com.inspur.ics.pojo.StorageAdapter;

/**
 * 存储设备接口.
 * @author ZhangJun
 */
public interface StorageDeviceService {

	/**
	 * 获取主机上所有的SCSI磁盘.
	 * @param hostUuid 主机的UUID
	 * @return 返回SCSI磁盘列表
	 */
	List<HostScsiDisk> getAllScsiDisk(String hostUuid);

	/**
	 * 获取主机上可用作心跳设备的块设备.
	 * @param hostUuid 主机的UUID
	 * @return 返回块设备列表
	 */
	List<BlockDevice> getAvailableBlockDeviceForHeartbeatDevice(String hostUuid);

	/**
	 * 获取主机上可用于创建CFS数据存储的块设备.
	 * @param hostUuid 主机的UUID
	 * @return 返回块设备列表
	 */
	List<BlockDevice> getAvailableBlockDeviceForOcfsDataStore(String hostUuid);

	/**
	 * 获取主机上可用作OSD的块设备.
	 * @param hostUuid 主机的UUID
	 * @return 返回块设备列表
	 */
	List<BlockDevice> getAvailableBlockDeviceForOsd(String hostUuid);

	/**
	 * 获取主机上可用于虚拟磁盘的块设备.
	 * @param hostUuid 主机的UUID
	 * @return 返回块设备列表
	 */
	List<BlockDevice> getAvailableBlockDeviceForVirtualDisk(String hostUuid);

	/**
	 * 获取主机上的iSCSI和FC存储适配器.
	 * @param hostUuid 主机的UUID
	 * @return 返回存储适配器列表
	 */
	List<StorageAdapter> getIscsiAndFcStorageAdapter(String hostUuid);

	/**
	 * 获取主机上指定SCSI磁盘的信息.
	 * @param hostUuid 主机的UUID
	 * @param sdUuid SCSI磁盘的UUID
	 * @return 返回SCSI磁盘列表
	 */
	HostScsiDisk getScsiDiskInfo(String hostUuid, String sdUuid);

	/**
	 * @param itUuid iSCSI目标uuid
	 * @return SCSI磁盘列表
	 */
	List<HostScsiDisk> getScsiDiskOnIscsiTarget(String itUuid);

	/**
	 * 获取主机上指定存储适配器的信息.
	 * @param hostUuid 主机的UUID
	 * @param saUuid 存储适配器的UUID
	 * @return 返回存储适配器
	 */
	StorageAdapter getStorageAdapterInfo(String hostUuid, String saUuid);

	/**
	 * 扫描主机上所有的存储适配器.
	 * @param hostUuid 主机的UUID
	 * @return 返回任务消息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult rescanAllStorageAdapter(String hostUuid);

	/**
	 * 扫描主机上指定的存储适配器.
	 * @param hostUuid 主机的UUID
	 * @param saUuid 存储适配器的UUID
	 * @return 返回任务消息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult rescanStorageAdapter(String hostUuid, String saUuid);
}
