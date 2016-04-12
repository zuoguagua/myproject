package com.inspur.ics.client.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.inspur.ics.common.Types.TaskTargetType;

/**
 * Rest service interface for Datastore module.
 * @author  jiangwt
 */
@Path("/")
public interface DataStoreRestService {

	/**
	 * 向主机附加存储
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuids datastore uuids
	 *        数据存储uuid
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_attachDataStoreToHost")
	String attachDataStoreToHost(@FormParam("token") String token, @FormParam("dsUuids") List<String> dsUuids, @FormParam("hostUuid") String hostUuid);

	/**
	 * 向存储附加主机
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuids host uuids
	 *        主机uuids
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_attachHostToDataStore")
	String attachHostToDataStore(@FormParam("token") String token, @FormParam("hostUuids") List<String> hostUuids, @FormParam("dsUuid") String dsUuid);

	/**
	 * 自动选择Vsan Osd
	 * @param token user token
	 *        用户认证令牌
	 * @param osdList vsan osd list
	 *        vsan osd列表
	 * @return result
	 */
	@POST
	@Path("datastore_autoSelectVsanOsd")
	String autoSelectVsanOsd(@FormParam("token") String token, @FormParam("osds") List<String> osdList);

	/**
	 * 创建外部VSAN存储
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanDataStore vasn datastore
	 *        vsan数据存储
	 * @return result
	 */
	@POST
	@Path("datastore_createExternalVsanDataStore")
	String createExternalVsanDataStore(@FormParam("token") String token, @FormParam("vsanDataStore") String vsanDataStore);

	/**
	 * 创建NFS存储
	 * @param token user token
	 *        用户认证令牌
	 * @param nfsDataStore nfs datastore
	 *        nfs数据存储
	 * @return result
	 */
	@POST
	@Path("datastore_createNfsDataStore")
	String createNfsDataStore(@FormParam("token") String token, @FormParam("nfsDataStore") String nfsDataStore);

	/**
	 * 创建OCSF存储
	 * @param token user token
	 *        用户认证令牌
	 * @param ocfsDataStore ocfs datastore
	 *        ocfs数据存储
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_createOcfsDataStore")
	String createOcfsDataStore(@FormParam("token") String token, @FormParam("ocfsDataStore") String  ocfsDataStore, @FormParam("hostUuid") String hostUuid);

	/**
	 * 创建VSAN存储
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanDataStore vasn datastore
	 *        vsan数据存储
	 * @return result
	 */
	@POST
	@Path("datastore_createVsanDataStore")
	String createVsanDataStore(@FormParam("token") String token, @FormParam("vsanDataStore") String vsanDataStore);

	/**
	 * 从主机分离存储
     * @param token user token
	 *        用户认证令牌
	 * @param dsUuids datastore uuids
	 *        数据存储uuid
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_detachDataStoreFromHost")
	String detachDataStoreFromHost(@FormParam("token") String token, @FormParam("dsUuids") List<String> dsUuids, @FormParam("hostUuid") String hostUuid);

	/**
	 * 从存储分离主机
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuids host uuids 
	 *        主机uuids
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_detachHostFromDataStore")
	String detachHostFromDataStore(@FormParam("token") String token, @FormParam("hostUuids") List<String> hostUuids, @FormParam("dsUuid") String dsUuid);

	/**
	 * 扩充OCFS存储
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_extendOcfs")
	String extendOcfs(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid, @FormParam("hostUuid") String hostUuid);

	/**
	 * 扩展vsan存储
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanUuid datastore uuid
	 *        数据存储uuid
	 * @param osd vsan osd info
	 *        vsan osd信息
	 * @return result
	 */
	@POST
	@Path("datastore_extendVsan")
	String extendVsan(@FormParam("token") String token, @FormParam("dsUuid") String vsanUuid, @FormParam("osd") String osd);

	/**
	 * 获取所有存储
	 * @param token user token
	 *        用户认证令牌
	 * @return result
	 */
	@POST
	@Path("datastore_getAll")
	String getAll(@FormParam("token") String token);

	/**
	 * 获取主机可用存储
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getAvailableDataStoreOnHost")
	String getAvailableDataStoreOnHost(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	/**
	 * 获取存储上可用的主机
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getAvailableHostOnDataStore")
	String getAvailableHostOnDataStore(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 获取主机上vsan可用端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getAvailablePortGroupForVsan")
	String getAvailablePortGroupForVsan(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	/**
	 * 获取存储目录树形结构
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getDataStoreDirectoryTree")
	String getDataStoreDirectoryTree(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 获取数据存储内目标文件夹内文件
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @param dir target dir to browse
	 *        浏览目标目录
	 * @return result
	 */
	@POST
	@Path("datastore_getDataStoreFile")
	String getDataStoreFile(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid, @FormParam("dir") String dir);

	/**
	 * 获取存储列表
	 * @param token user token
	 *        用户认证令牌
	 * @param targetType target type
	 *        目标类型
	 * @param targetUuid target uuid
	 *        目标uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getDataStoreList")
	String getDataStoreList(@FormParam("token") String token, @FormParam("targetType") TaskTargetType targetType, @FormParam("targetUuid") String targetUuid);

	/**
	 * 获取主机存储
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getDataStoreOnHost")
	String getDataStoreOnHost(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	/**
	 * 获取默认vsan副本
	 * @param token user token
	 *        用户认证令牌
	 * @return result
	 */
	@POST
	@Path("datastore_getDefautReplicas")
	String getDefautReplicas(@FormParam("token") String token);

	/**
	 * 获取设备备份和挂载状态
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getDeviceBackingAndMountStatus")
	String getDeviceBackingAndMountStatus(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 获取存储相关的主机
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getHostOnDataStore")
	String getHostOnDataStore(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 获取存储信息
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getInfo")
	String getInfo(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 获取vsan监听器列表
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanUuid vsan uuid
	 *        vasn存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getVsanMonitorList")
	String getVsanMonitorList(@FormParam("token") String token, @FormParam("dsUuid") String vsanUuid);

	/**
	 * 获取vsanOSD列表
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanUuid vsan uuid
	 *        vsan存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getVsanOsdList")
	String getVsanOsdList(@FormParam("token") String token, @FormParam("dsUuid") String vsanUuid);

	/**
	 * 获取主机vsan端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_getVsanPortGroup")
	String getVsanPortGroup(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	/**
	 * 将InCloud Sphere 3.x中的数据存储迁移到4.0环境中.
	 * @return result
	 */
	@POST
	@Path("datastore_immigrateDataStoreFromV3ToV4")
	String immigrateDataStoreFromV3ToV4(@FormParam("token") String token);

	/**
	 * 变更存储
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @param dsName datastore name
	 *        数据存储名称
	 * @return result
	 */
	@POST
	@Path("datastore_modify")
	String modify(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid, @FormParam("dsName") String dsName);

	/**
	 * 变更NFS存储拥有者
	 * @param token user token
	 *        用户认证令牌
	 * @param nds nfs datastore
	 *        nfs数据存储
	 * @return result
	 */
	@POST
	@Path("datastore_modifyNfsOwner")
	String modifyNfsOwner(@FormParam("token") String token, @FormParam("nfsDataStore") String nds);

	/**
	 * 变更OCFS存储节点位置
	 * @param token user token
	 *        用户认证令牌
	 * @param odsUuid ocfs datastore uuid
	 *        数据存储uuid
	 * @param newNodeSlot node slot number
	 *        节点槽数
	 * @param opHostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_modifyOcfsNodeSlot")
	String modifyOcfsNodeSlot(@FormParam("token") String token, @FormParam("dsUuid") String odsUuid, @FormParam("nodeSlot") int newNodeSlot, @FormParam("hostUuid") String opHostUuid);

	/**
	 * 变更OCFS存储拥有者
	 * @param token user token
	 *        用户认证令牌
	 * @param ods ocfs datastore
	 *        ocfs数据存储
	 * @param opHostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_modifyOcfsOwner")
	String modifyOcfsOwner(@FormParam("token") String token, @FormParam("ocfsDataStore") String ods, @FormParam("hostUuid") String opHostUuid);

	/**
	 * 挂载存储
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @param dsUuid  datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_mount")
	String mount(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid, @FormParam("dsUuid") String dsUuid);

	/**
	 * 移除存储
	 * @param token user token
	 *        用户认证令牌
	 * @param dsUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_remove")
	String remove(@FormParam("token") String token, @FormParam("dsUuid") String dsUuid);

	/**
	 * 移除vsan端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("datastore_removeVsanPortGroup")
	String removeVsanPortGroup(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid, @FormParam("pgUuid") String pgUuid);

	/**
	 * 设置vsan端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("datastore_setVsanPortGroup")
	String setVsanPortGroup(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid, @FormParam("pgUuid") String pgUuid);

	/**
	 * 缩减vsan存储大小
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanUuid datastore uuid
	 *        数据存储uuid
	 * @param osdUuid osd uuid
	 *        osd  uuid
	 * @return result
	 */
	@POST
	@Path("datastore_shrinkVsan")
	String shrinkVsan(@FormParam("token") String token, @FormParam("dsUuid") String vsanUuid, @FormParam("osdUuid") String osdUuid);

	/**
	 * 同步所有存储
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @return result
	 */
	@POST
	@Path("datastore_syncAllDataStore")
	String syncAllDataStore(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

	/**
	 * 同步集群内vsan配置
	 * @param token user token
	 *        用户认证令牌
	 * @param vsanUuid datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_syncVsanConfigInCluster")
	String syncVsanConfigInCluster(@FormParam("token") String token, @FormParam("dsUuid") String vsanUuid);

	/**
	 * 卸载存储
	 * @param token user token
	 *        用户认证令牌
	 * @param hostUuid host uuid
	 *        主机uuid
	 * @param dsUuid  datastore uuid
	 *        数据存储uuid
	 * @return result
	 */
	@POST
	@Path("datastore_unmount")
	String unmount(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid, @FormParam("dsUuid") String dsUuid);

}
