package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.HostService;
import com.inspur.ics.client.rest.HostRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.CdromDevice;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostIpmiInfo;
import com.inspur.ics.pojo.MultipathConfig;
import com.inspur.ics.pojo.UsbDevice;
import com.inspur.ics.pojo.monitor.HostMonitorInfo;
import com.inspur.ics.pojo.monitor.ServiceMonitorInfo;

/**
 * @author liuyi 2015/03/18
 */
public class HostServiceImpl implements HostService {

	/**
	 * 用户验证token.
	 */
	private String token = "";

	/**
	 * HostRestService 客户端对象.
	 */
	private HostRestService client;

	/**
	 * 构造函数，通过URL请求client对象.
	 * @param url
	 *            请求地址
	 * @param token
	 *            用户验证token
	 */
	public HostServiceImpl(String url, String token) {
		this.token = token;
		this.client = ProxyFactory.create(HostRestService.class, url,
		        ExecutorFactory.getDefaultClientExecutor());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult addHosts(List<Host> hosts) {
		// TODO Auto-generated method stub
		String resultStr = client.addHosts(token, FormatUtil.toJson(hosts));
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult deleteHost(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.deleteHost(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Host> getAllHostList() {
		// TODO Auto-generated method stub
		String resultStr = client.getAllHostList(token);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Host.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<Host>();
		} else {
			return (List<Host>) result.getData();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Host getHostInfo(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getInfo(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Host.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<Host>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ServiceMonitorInfo getManagerService() {
		// TODO Auto-generated method stub
		String resultStr = client.getManagerService(token);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, ServiceMonitorInfo.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<ServiceMonitorInfo>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public float getHostVirtual(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getHostVirtual(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Float.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<Float>) result.getData()).get(0);
		}
	}

	@Override
	public void configHostVirtual(String hostUUID, float scale) {
		// TODO Auto-generated method stub
		String resultStr = client.setHostVirtual(token, hostUUID, scale);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HostMonitorInfo getHostRuntimeInfo(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getHostRuntimeInfo(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, HostMonitorInfo.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<HostMonitorInfo>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean isRightPassword(Host host) {
		// TODO Auto-generated method stub
		String resultStr = client.isHostPwdRight(token, FormatUtil.toJson(host));
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Boolean.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<Boolean>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UsbDevice> getFreeUsbDevices(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getFreeUsbDevices(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, UsbDevice.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<UsbDevice>();
		} else {
			return (List<UsbDevice>) result.getData();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UsbDevice> getAllUsbDevices(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getHostAllUsbDevices(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, UsbDevice.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<UsbDevice>();
		} else {
			return (List<UsbDevice>) result.getData();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UsbDevice> getUsedUsbDevices(String hostUUID) {
		// TODO Auto-generated method stub
		String resultStr = client.getUsedUsbDevices(token, hostUUID);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, UsbDevice.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<UsbDevice>();
		} else {
			return (List<UsbDevice>) result.getData();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<String> scanHosts(String inet, int masknum) {
		// TODO Auto-generated method stub
		String resultStr = client.scanHosts(token, inet, masknum);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, String.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<String>();
		} else {
			return (List<String>) result.getData();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Host> getHostList(String type, String targetUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.getHostList(token, type, targetUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Host.class }); // 解包结果inputStream流，获取result对象
		System.out.println(resultStr);
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<Host>();
		} else {
			return (List<Host>) result.getData();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult modifyMultipathConfig(MultipathConfig multipathConfig) {
		// TODO Auto-generated method stub
		String resultStr = client.modifyMultipathConfig(token, FormatUtil.toJson(multipathConfig));
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public MultipathConfig getMultipathConfig(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.getMultipathConfigInfo(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, MultipathConfig.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new MultipathConfig();
		} else {
			return ((List<MultipathConfig>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public HostIpmiInfo getIpmiInfo(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.getIpmiInfo(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, HostIpmiInfo.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new HostIpmiInfo();
		} else {
			return ((List<HostIpmiInfo>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult updateIpmi(String hostUuid,
			HostIpmiInfo ipmi) {
		// TODO Auto-generated method stub
		String resultStr = client.updateIpmi(token, hostUuid, FormatUtil.toJson(ipmi));
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult powerOnHost(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.powerOn(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult powerOffHost(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.powerOff(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean isPowerOn(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.isPowerOn(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Boolean.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<Boolean>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CdromDevice> getFreeCdromDevices(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.getFreeCdromDevices(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, CdromDevice.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<CdromDevice>();
		} else {
			return (List<CdromDevice>) result.getData();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult setHostMaintenance(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.setHostMaintenance(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult exitMaintenanceMode(String hostUuid) {
		// TODO Auto-generated method stub
		String resultStr = client.exitMaintenanceMode(token, hostUuid);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TaskIntermediateResult modifyMasterIp(String masterIp,
			String mask) {
		// TODO Auto-generated method stub
		String resultStr = client.modifyMasterIp(token, masterIp, mask);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new TaskIntermediateResult<String>("0");
		} else {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String getHostIpNetmask() {
		// TODO Auto-generated method stub
		String resultStr = client.getHostIpNetmask(token);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, String.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else {
			return ((List<String>) result.getData()).get(0);
		}
	}

	/**
	 * getters & setters.
	 * @return HostRestService
	 */
	public HostRestService getClient() {
		return client;
	}

	/**
	 * getters & setters.
	 * @param client
	 *            client
	 */
	public void setClient(HostRestService client) {
		this.client = client;
	}

}
