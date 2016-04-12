package com.inspur.ics.client;

/**
 * 用于使用vmtools工具的接口.
 * @author luo
 * 
 */
public interface ToolsService {

	/**
	 * 检查虚拟路由器中的tools工具是否可用.
	 * @param routerUUID
	 *            虚拟路由网络的UUID.
	 * @return 可用返回true，否则返回false.
	 */
	String isRouterToolsAvailable(String routerUUID);

	/**
	 * 检查虚拟机中的tools是否可用.
	 * @param vmUUID
	 *            虚拟机的uuid.
	 * @return 可用返回true，否则返回false.
	 */
	String isVMToolsAvailable(String vmUUID);

}
