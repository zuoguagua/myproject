package com.inspur.ics.client.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.HostIpmiInfo;
import com.inspur.ics.pojo.MultipathConfig;

/**
 * 
 * @author liuyi
 * 
 * 
 */
@Path("/")
public interface HostRestService {
	/**
	 * 添加主机
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hosts
	 *            主机信息列表
	 * @return result
	 */
	@POST
	@Path("host_addHosts")
	String addHosts(@FormParam("token") String token,
			@FormParam("hosts") String hosts);

	/**
	 * 删除主机
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_deleteHost")
	String deleteHost(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 获取所有主机
	 * 
	 * @param token
	 *            用户认证令牌
	 * @return result
	 */
	@POST
	@Path("host_getAllHostList")
	String getAllHostList(@FormParam("token") String token);

	/**
	 * 获取主机信息
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getInfo")
	String getInfo(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 获取管理服务器信息
	 * 
	 * @return result
	 */
	@POST
	@Path("host_getManagerService")
	String getManagerService(@FormParam("token") String token);

	/**
	 * 获取主机虚拟化度
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getHostVirtual")
	String getHostVirtual(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 设置主机虚拟化度
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @param scale
	 *            虚拟化度值
	 * @return result
	 */
	@POST
	@Path("host_setHostVirtual")
	String setHostVirtual(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID,
			@FormParam("scale") float scale);

	/**
	 * 获取主机运行时信息
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getHostRuntimeInfo")
	String getHostRuntimeInfo(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 验证主机管理密码
	 * 
	 * @param host
	 *            主机对象
	 * @return result
	 */
	@POST
	@Path("host_isHostPwdRight")
	String isHostPwdRight(@FormParam("token") String token,
	        @FormParam("host") String host);

	/**
	 * 获取主机未使用USB设备
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getFreeUsbDevices")
	String getFreeUsbDevices(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 获取主机上所有的USB设备
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getHostAllUsbDevices")
	String getHostAllUsbDevices(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 获取主机上占用的USB设备
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getUsedUsbDevices")
	String getUsedUsbDevices(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 扫描所有可添加主机
	 * 
	 * @param inet
	 *            待扫描网络IP
	 * @param masknum
	 *            子网掩码位数
	 * @return result
	 */
	@POST
	@Path("host_scanHosts")
	String scanHosts(@FormParam("token") String token,
	        @FormParam("inet") String inet,
			@FormParam("masknum") int masknum);

	/**
	 * 获取主机列表
	 * 
	 * @param type
	 *            设备类型
	 * @param targetUuid
	 *            目标设备UUID
	 * @return result
	 */
	@POST
	@Path("host_getHostList")
	String getHostList(@FormParam("token") String token,
	        @FormParam("type") String type,
			@FormParam("targetUuid") String targetUuid);

	/**
	 * 变更多通道配置信息
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param multipathConfig
	 *            多通道配置
	 * @return result
	 */
	@POST
	@Path("host_modifyMultipathConfig")
	String modifyMultipathConfig(@FormParam("token") String token,
			@FormParam("multipathConfig") String multipathConfig);

	/**
	 * 获取多通道配置信息
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("/host_getMultipathConfigInfo")
	String getMultipathConfigInfo(@FormParam("token") String token,
	        @FormParam("hostUUID") String hostUUID);

	/**
	 * 更新主机ipmi信息
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @param ipmi
	 *            主机PIMI配置信息
	 * @return result
	 */
	@POST
	@Path("host_updateIpmi")
	String updateIpmi(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID,
			@FormParam("ipmi") String ipmi);

	/**
	 * 打开主机电源
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_powerOn")
	String powerOn(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 关闭主机电源
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_powerOff")
	String powerOff(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 获取主机ipmi信息
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getIpmiInfo")
	String getIpmiInfo(@FormParam("token") String token,
	        @FormParam("hostUUID") String hostUUID);

	/**
	 * 主机是否开机
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_isPowerOn")
	String isPowerOn(@FormParam("token") String token,
	        @FormParam("hostUUID") String hostUUID);

	/**
	 * 获取主机可用CDRom设备
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_getFreeCdromDevices")
	String getFreeCdromDevices(@FormParam("token") String token,
	        @FormParam("hostUUID") String hostUUID);

	/**
	 * 设置主机进入维护模式
	 * 
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_setHostMaintenance")
	String setHostMaintenance(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 主机退出维护模式
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostUUID
	 *            主机UUID
	 * @return result
	 */
	@POST
	@Path("host_exitMaintenanceMode")
	String exitMaintenanceMode(@FormParam("token") String token,
			@FormParam("hostUUID") String hostUUID);

	/**
	 * 变更管理节点IP
	 * 
	 * @param token
	 *            用户认证令牌
	 * @param hostIp
	 *            目标主机IP
	 * @param mask
	 *            目标主机子网掩码
	 * @return result
	 */
	@POST
	@Path("host_modifyMasterIp")
	String modifyMasterIp(@FormParam("token") String token,
			@FormParam("hostIp") String hostIp, @FormParam("mask") String mask);

	/**
	 * 获取主机ip和子网掩码
	 * 
	 * @return result
	 */
	@POST
	@Path("host_getHostIpNetmask")
	String getHostIpNetmask(@FormParam("token") String token);

}
