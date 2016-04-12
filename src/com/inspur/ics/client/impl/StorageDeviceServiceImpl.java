package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.client.rest.StorageDeviceRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.FcHostBusAdapter;
import com.inspur.ics.pojo.HostScsiDisk;
import com.inspur.ics.pojo.IscsiSoftwareAdapter;
import com.inspur.ics.pojo.LocalStorageAdapter;
import com.inspur.ics.pojo.StorageAdapter;

public class StorageDeviceServiceImpl implements StorageDeviceService {

	/** 存储设备模块的REST接口. */
	private StorageDeviceRestService storageDeviceRestService;

	/** 用户认证令牌  */
	private String token;

	/**
	 * @param url request path
	 * @param token user token
	 */
	public StorageDeviceServiceImpl(String url, String token) {
		storageDeviceRestService = ProxyFactory.create(StorageDeviceRestService.class, url,
				ExecutorFactory.getDefaultClientExecutor());
		this.token = token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HostScsiDisk> getAllScsiDisk(String hostUuid) {

		String resultXml = storageDeviceRestService.getAllScsiDisk(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, HostScsiDisk.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<HostScsiDisk>();
		} else {
			return (List<HostScsiDisk>) result.getData();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlockDevice> getAvailableBlockDeviceForHeartbeatDevice(
			String hostUuid) {

		String resultXml = storageDeviceRestService
				.getAvailableBlockDeviceForHeartbeatDevice(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, BlockDevice.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<BlockDevice>();
		} else {
			return (List<BlockDevice>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlockDevice> getAvailableBlockDeviceForOcfsDataStore(
			String hostUuid) {

		String resultXml = storageDeviceRestService
				.getAvailableBlockDeviceForOcfsDataStore(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, BlockDevice.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<BlockDevice>();
		} else {
			return (List<BlockDevice>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlockDevice> getAvailableBlockDeviceForOsd(String hostUuid) {

		String resultXml = storageDeviceRestService
				.getAvailableBlockDeviceForOsd(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, BlockDevice.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<BlockDevice>();
		} else {
			return (List<BlockDevice>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlockDevice> getAvailableBlockDeviceForVirtualDisk(
			String hostUuid) {

		String resultXml = storageDeviceRestService
				.getAvailableBlockDeviceForVirtualDisk(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, BlockDevice.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<BlockDevice>();
		} else {
			return (List<BlockDevice>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StorageAdapter> getIscsiAndFcStorageAdapter(String hostUuid) {

		String resultXml = storageDeviceRestService
				.getIscsiAndFcStorageAdapter(token, hostUuid);
		System.out.println(resultXml);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class,
				StorageAdapter.class,
				IscsiSoftwareAdapter.class,
				FcHostBusAdapter.class}
		);

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<StorageAdapter>();
		} else {
			return (List<StorageAdapter>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HostScsiDisk getScsiDiskInfo(String hostUuid, String sdUuid) {

		String resultXml = storageDeviceRestService
				.getScsiDiskInfo(token, hostUuid, sdUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, HostScsiDisk.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return null;
		} else {
			return ((List<HostScsiDisk>) result.getData()).get(0); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HostScsiDisk> getScsiDiskOnIscsiTarget(String itUuid) {

		String resultXml = storageDeviceRestService.getScsiDiskOnIscsiTarget(token, itUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class, HostScsiDisk.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<HostScsiDisk>();
		} else {
			return (List<HostScsiDisk>) result.getData(); 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public StorageAdapter getStorageAdapterInfo(String hostUuid, String saUuid) {

		String resultXml = storageDeviceRestService.getStorageAdapterInfo(token, hostUuid, saUuid);
		Result result = (Result) FormatUtil.fromXML(resultXml,
				new Class[]{Result.class,LocalStorageAdapter.class, FcHostBusAdapter.class,StorageAdapter.class});

		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return null;
		} else {
			return ((List<StorageAdapter>) result.getData()).get(0); 
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult rescanAllStorageAdapter(String hostUuid) {

		String resultXML = storageDeviceRestService.rescanAllStorageAdapter(token, hostUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult rescanStorageAdapter(String hostUuid,
			String saUuid) {

		String resultXML = storageDeviceRestService.rescanStorageAdapter(token, hostUuid, saUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

}
