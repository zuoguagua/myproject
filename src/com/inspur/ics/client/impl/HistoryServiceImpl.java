package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.HistoryService;
import com.inspur.ics.client.rest.HistoryRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.HostPerf;
import com.inspur.ics.pojo.VMPerf;
import com.inspur.ics.pojo.monitor.HistoryMonitorInfo;

/**
 * Implementation of history service.
 * @author kangzhx
 */
public class HistoryServiceImpl implements HistoryService {

	/**
	 * History rest service代理对象.
	 */
	private HistoryRestService restService;
	/**
	 * 用户令牌.
	 */
	private String token;

	/**
	 * 构造函数.
	 * @param url
	 *            远程url地址
	 * @param token
	 *            用户认证令牌
	 */
	public HistoryServiceImpl(String url, String token) {
		restService = ProxyFactory.create(HistoryRestService.class, url, ExecutorFactory.getDefaultClientExecutor());
		this.token = token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryMonitorInfo> getVmCpuPerfsAmongTime(String vmUuid, Date fromTime, Date toTime) {
		// TODO Auto-generated method stub
		String fromTimeStr = FormatUtil.toJson(fromTime);
		String toTimeStr = FormatUtil.toJson(toTime);
		String resultXML = restService.getVmCpuPerfsAmongTime(token, vmUuid, fromTimeStr, toTimeStr);
		Result result = (Result) FormatUtil.fromXML(resultXML, new Class[] { Result.class, HistoryMonitorInfo.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<HistoryMonitorInfo>();
		} else {
			return (List<HistoryMonitorInfo>) result.getData();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryMonitorInfo> getVmMemPerfsAmongTime(String vmUuid, Date fromTime, Date toTime) {
		// TODO Auto-generated method stub
		String fromTimeStr = FormatUtil.toJson(fromTime);
		String toTimeStr = FormatUtil.toJson(toTime);
		String resultXML = restService.getVmMemPerfsAmongTime(token, vmUuid, fromTimeStr, toTimeStr);
		Result result = (Result) FormatUtil.fromXML(resultXML, new Class[] { Result.class, HistoryMonitorInfo.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<HistoryMonitorInfo>();
		} else {
			return (List<HistoryMonitorInfo>) result.getData();
		}
	}

	@Override
	public String getVmPerfInfo(String vmUuid, String type, Date fromTime, Date toTime, String resolution) {
		// TODO Auto-generated method stub
		String fromTimeStr = FormatUtil.toJson(fromTime);
		String toTimeStr = FormatUtil.toJson(toTime);
		String resultXML = restService.getVmPerfInfo(token, vmUuid, type, fromTimeStr, toTimeStr, resolution);
		Document document;
		try {
			document = DocumentHelper.parseText(resultXML);
			Element root = document.getRootElement();
			Element elementResult = root.element("result");
			Element elementData = root.element("data");
			if (elementResult.attribute("type").equals("success")) {
				return elementData.getText();
			}
		} catch (Exception e) {
			throw new RemoteException("无法获取虚拟机监控性能信息");
		}
		Result result = (Result) FormatUtil.fromXML(resultXML, new Class[] { Result.class});
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new String();
		} else {
			return (String) result.getData();
		}
	}

	@Override
	public String getHostPerfInfo(String hostUUID, String type, Date fromTime, Date toTime, String resolution) {
		// TODO Auto-generated method stub
		String fromTimeStr = FormatUtil.toJson(fromTime);
		String toTimeStr = FormatUtil.toJson(toTime);
		String resultXML = restService.getHostPerfInfo(token, hostUUID, type, fromTimeStr, toTimeStr, resolution);
		Document document;
		try {
			document = DocumentHelper.parseText(resultXML);
			Element root = document.getRootElement();
			Element elementData = root.element("data");
			Element elementPoints = elementData.element("points");
			if (root.attribute("type").getValue().equals("success")) {
				return elementPoints.asXML();
			}
		} catch (Exception e) {
			throw new RemoteException("无法获取主机监控性能信息");
		}
		Result result = (Result) FormatUtil.fromXML(resultXML, new Class[] { Result.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new String();
		} else {
			return (String) result.getData();
		}
	}
}
