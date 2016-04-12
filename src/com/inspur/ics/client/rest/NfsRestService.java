package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author liuyi
 */
@Path("/")
public interface NfsRestService {
	/**
	 * 获取所有ISO镜像文件.
	 * @param token
	 *            用户认证令牌
	 * @return result
	 */
	@POST
	@Path("nfs_getIsoFiles")
	String getIsoFiles(@FormParam("token") String token);

	/**
	 * 目标主机挂载NFS存储.
	 * @param token
	 *            用户认证令牌
	 * @param hostIP
	 *            主机IP
	 * @return result
	 */
	@POST
	@Path("nfs_mount")
	String mount(@FormParam("token") String token,
			@FormParam("hostIP") String hostIP);

	/**
	 * 卸载目标主机NFS存储.
	 * @param hostIP
	 *            主机IP
	 * @return result
	 */
	@POST
	@Path("nfs_umount")
	String umount(@FormParam("token") String token,
	        @FormParam("hostIP") String hostIP);

	/**
	 * 主机存储是否挂载.
	 * @param token
	 *            用户认证令牌
	 * @param hostIP
	 *            主机IP
	 * @return result
	 */
	@POST
	@Path("nfs_isMounted")
	String isMounted(@FormParam("token") String token,
			@FormParam("hostIP") String hostIP);

	/**
	 * 获取NFS导出目录路径.
	 * @return result
	 */
	@POST
	@Path("nfs_getNfsExportPath")
	String getNfsExportPath(@FormParam("token") String token);

	/**
	 * 变更NFS导出目录路径.
	 * @param exportPath
	 *            导出目录路径
	 * @return result
	 */
	@POST
	@Path("nfs_modifyNfsExportPath")
	String modifyNfsExportPath(@FormParam("token") String token,
	        @FormParam("exportPath") String exportPath);

	/**
	 * 打开ISO目录.
	 * @param token
	 *         用户认证令牌
	 * @return resultStream 结果输出流
	 */
	@POST
	@Path("nfs_openIsoDir")
	String openIsoDir(@FormParam("token") String token);

}
