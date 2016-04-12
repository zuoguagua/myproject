package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.pojo.ISOFile;

/**
 * nfs存储操作：挂载状态查询、挂载操作、获取iso镜像列表等.
 * @author liuyi
 *
 */
public interface NfsService {
	/**
	 * 获取NFS服务器上所有ISO镜像文件.
	 * @return List <ISOFile>
	 * 			ISO文件类型列表
	 */
	List<ISOFile> getAllIsoFiles();
	/**
	 * IP标识的主机挂载NFS存储.
	 * @param hostIP
	 * 			主机IP
	 */
	void mountNfsStorage(String hostIP);
	/**
	 * 卸载IP标识的主机所挂载的NFS存储.
	 * @param hostIP
	 * 			主机IP
	 */
	void umountNfsStorage(String hostIP);
	/**
	 * 目标主机是否挂载NFS存储.
	 * @param hostIP
	 * 			主机IP
	 * @return boolean
	 * 			布尔类型，挂载为true，未挂载为false
	 */
	boolean isNfsStorageMounted(String hostIP);
	/**
	 * 获取NFS导出目录路径.
	 * @return result
	 * 			NFS路径，NFS服务器IP与具体路径的字串组合
	 */
	String getNfsExportPath();
	/**
	 * 变更NFS导出目录路径.
	 * @param nfsExportPath
	 * 			导出目录路径
	 */
	void modifyNfsExportPath(String nfsExportPath);
	/**
	 * 打开ISO目录.
	 * @param token
	 * @return String
	 * 			jnlp输出流
	 */
	String openIsoDir();
}
