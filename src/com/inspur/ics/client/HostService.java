package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.CdromDevice;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostIpmiInfo;
import com.inspur.ics.pojo.MultipathConfig;
import com.inspur.ics.pojo.UsbDevice;
import com.inspur.ics.pojo.monitor.HostMonitorInfo;
import com.inspur.ics.pojo.monitor.ServiceMonitorInfo;

/**
 * 主机服务接口.
 * @author liuyi
 */
public interface HostService {

    /**
     * 添加主机列表.
     * @param hosts
     *            主机信息类型对象列表
     * @return TaskIntermediateResult 任务结果
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult addHosts(List<Host> hosts);

    /**
     * 删除UUID标识的主机.
     * @param hostUuid
     *            主机唯一标识UUID
	 * @return TaskIntermediateResult 任务结果
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult deleteHost(String hostUuid);

	/**
	 * 获取所有主机信息列表.
	 * @return List<Host> 主机信息列表
	 */
	List<Host> getAllHostList();

	/**
	 * 获取UUID标识的主机信息.
	 * @param hostUUID
	 *            主机唯一标识UUID
	 * @return Host Host类型信息
	 */
	Host getHostInfo(String hostUUID);

	/**
	 * 获取管理服务器服务信息.
	 * @return ServiceMonitorInfo 管理服务器信息
	 */
	ServiceMonitorInfo getManagerService();

	/**
	 * 获取主机UUID标识的主机虚拟化度.
	 * @param hostUUID
	 *            主机UUID
	 * @return float 1~3范围内的浮点数值
	 */
	float getHostVirtual(String hostUUID);

	/**
	 * 设置UUID标识的主机虚拟化度.
	 * @param hostUUID
	 *            主机UUID
	 * @param scale
	 *            虚拟化度值，取值范围1~3浮点数类型
	 */
	void configHostVirtual(String hostUUID, float scale);

	/**
	 * 获取主机运行时信息.
	 * @param hostUUID
	 *            主机UUID
	 * @return HostMonitorInfo 主机管理信息
	 */
	HostMonitorInfo getHostRuntimeInfo(String hostUUID);

	/**
	 * 验证目标主机的登录密码.
	 * @param host
	 *            主机对象
	 * @return boolean 布尔值，正确则true，错误false
	 */
	boolean isRightPassword(Host host);

	/**
	 * 获取UUID标识主机未被使用USB设备.
	 * @param hostUUID
	 *            主机UUID
	 * @return List<UsbDevice> USB设备类型列表
	 */
	List<UsbDevice> getFreeUsbDevices(String hostUUID);

	/**
	 * 获取UUID标识的主机上所有的USB设备.
	 * @param hostUUID
	 *            主机UUID
	 * @return List<UsbDevice> USB设备类型列表
	 */
	List<UsbDevice> getAllUsbDevices(String hostUUID);

	/**
	 * 获取UUID标识主机上正在被占用的USB设备.
	 * @param hostUUID
	 *            主机UUID
	 * @return List<UsbDevice> USB设备类型列表
	 */
	List<UsbDevice> getUsedUsbDevices(String hostUUID);

	/**
	 * 扫描IP段内所有可添加到管理端的主机.
	 * @param inet
	 *            待扫描网络IP
	 * @param masknum
	 *            子网掩码位数，取值在24~32之间，是32位2进制IP中1的个数
	 *            例:11111111.11111111.11111111.11111100 = 255.255.255.252
	 *            子网掩码位数为 30
	 * @return List<String> 可添加主机的IP信息列表
	 */
	List<String> scanHosts(String inet, int masknum);

	/**
	 * 获取设备相关的主机列表.
	 * @param type
	 *            设备类型 通过TargetType.DEVICENAME.name()获取
	 * @param targetUuid
	 *            目标设备UUID
	 * @return List<Host> 主机列表
	 */
	List<Host> getHostList(String type, String targetUuid);

	/**
	 * 变更多通道配置.
	 * @param multipathConfig
	 *            多通道配置
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult modifyMultipathConfig(MultipathConfig multipathConfig);

	/**
	 * 获取UUID标识主机的多通道配置信息.
	 * @param hostUuid
	 *            主机UUID
	 * @return MultipathConfig 多通道配置信息类型对象
	 */
	MultipathConfig getMultipathConfig(String hostUuid);

	/**
	 * 获取UUID标识的主机ipmi信息.
	 * @param hostUuid
	 *            主机UUID
	 * @return HostIpmiInfo 主机IPMI信息
	 */
	HostIpmiInfo getIpmiInfo(String hostUuid);

	/**
	 * 更新UUID标识主机的ipmi信息.
	 * @param hostUuid
	 *            主机UUID
	 * @param ipmi
	 *            主机PIMI配置信息
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult updateIpmi(String hostUuid,
			HostIpmiInfo ipmi);

	/**
	 * 打开UUID标识的主机电源.
	 * @param hostUuid
	 *            主机UUID
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult powerOnHost(String hostUuid);

	/**
	 * 关闭UUID标识的主机电源.
	 * @param hostUuid
	 *            主机UUID
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult powerOffHost(String hostUuid);

	/**
	 * UUID标识的主机是否开机.
	 * @param hostUuid
	 *            主机UUID
	 * @return boolean 布尔类型，开机为true，关机为false
	 */
	boolean isPowerOn(String hostUuid);

	/**
	 * 获取UUID标识主机可用CDRom设备.
	 * @param hostUuid
	 *            主机UUID
	 * @return List<CdromDevice> CDrom设备信息列表
	 */
	List<CdromDevice> getFreeCdromDevices(String hostUuid);

	/**
	 * 设置UUID标识的主机进入维护模式.
	 * @param hostUuid
	 *            主机UUID
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult setHostMaintenance(String hostUuid);

	/**
	 * 设置UUID标识的主机退出维护模式.
	 * @param hostUuid
	 *            主机UUID
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult exitMaintenanceMode(String hostUuid);

	/**
	 * 通过更改管理节点IP来变更管理主机.
	 * @param masterIp
	 *            目标主机IP地址
	 * @param mask
	 *            目标主机子网掩码
	 * @return TaskIntermediateResult 任务结果信息
	 */
	@SuppressWarnings("rawtypes")
	TaskIntermediateResult modifyMasterIp(String masterIp,
			String mask);

	/**
	 * 获取管理主机ip和子网掩码.
	 * @return String 管理主机的IP和子网掩码字串
	 */
	String getHostIpNetmask();
}
