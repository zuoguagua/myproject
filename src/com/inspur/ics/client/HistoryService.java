package com.inspur.ics.client;

import java.util.Date;
import java.util.List;

import com.inspur.ics.pojo.HostPerf;
import com.inspur.ics.pojo.VMPerf;
import com.inspur.ics.pojo.monitor.HistoryMonitorInfo;

/**
 * 查看历史性能.
 * 
 * @author kangzhx
 */
public interface HistoryService {
	/**
	 * 获取虚拟机指定时间间隔的CPU性能监控信息.
	 * 
	 * @param vmUuid
	 *            虚拟机UUID
	 * @param fromTime
	 *            起始时间
	 * @param toTime
	 *            终止时间
	 * @return
	 */
	List<HistoryMonitorInfo> getVmCpuPerfsAmongTime(String vmUuid, Date fromTime, Date toTime);

	/**
	 * 获取虚拟机指定时间间隔的内存性能监控信息.
	 * 
	 * @param vmUuid
	 *            虚拟机UUID
	 * @param fromTime
	 *            起始时间
	 * @param toTime
	 *            终止时间
	 * @return
	 */
	List<HistoryMonitorInfo> getVmMemPerfsAmongTime(String vmUuid, Date fromTime, Date toTime);

	/**
	 * 
	 * @param vmUUID
	 *            虚拟机UUID
	 * @param type
	 *            性能参数类型（"cpu"或"mem"）
	 * @param fromTime
	 *            起始时间
	 * @param toTime
	 *            截止时间
	 * @param resolution
	 *            粒度，默认为"10"
	 * @return
	 */
	@Deprecated
	String getVmPerfInfo(String vmUUID, String type, Date fromTime, Date toTime, String resolution);

	/**
	 * 获取主机指定时间间隔的性能参数(包括：CPU和内存)
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @param type
	 *            要获取的性能参数类型（"cpu","mem"）
	 * @param fromTime
	 *            起始时间
	 * @param toTime
	 *            截止时间
	 * @param resolution
	 *            粒度，默认为"10"
	 * @return
	 */
	String getHostPerfInfo(String hostUUID, String type, Date fromTime, Date toTime, String resolution);
}
