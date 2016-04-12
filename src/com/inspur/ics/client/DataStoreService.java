package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DataStoreDir;
import com.inspur.ics.pojo.DataStoreFile;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.VSanDataStore;
import com.inspur.ics.pojo.VSanMon;
import com.inspur.ics.pojo.VSanOSD;

/**
 * 数据存储服务接口.
 * @author jiangwt
 */
public interface DataStoreService {

     /**
     * 向主机附加存储.
     * @param addDsUuids
     *            数据存储的uuids.
     * @param hostUuid
     *            主机的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult attachDataStoreToHost(
            List<String> addDsUuids, String hostUuid);

    /**
     * 向数据存储附加主机.
     * @param addHostUuids
     *            主机的uuids.
     * @param dsUuid
     *            数据存储uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult attachHostToDataStore(
            List<String> addHostUuids, String dsUuid);

    /**
     * 创建外部 vsan数据存储.
     * <pre>
     *  // 创建外部vsan对象
        VSanDataStore vsan = new VSanDataStore();

        // 设置vsan所在的集群的uuid
        Cluster cluster = new Cluster();
        cluster.setUuid("");
        vsan.setCluster(cluster);

        // 设置vsan的名称
        vsan.setName("vsan_test");
        // 设置外部vsan信息
        vsan.setExternalVsanConfig("100.1.8.167, 100.1.8.168")
     * </pre>
     * @param vsanDataStore
     *            vsan数据存储info
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult createExternalVsanDataStore(
            VSanDataStore vsanDataStore);

    /**
     * 创建nfs数据存储.
     * <pre>
     *  // 创建nfs对象，设置nfs的名称，服务器IP以及导出目录。
        NfsDataStore nfs = new NfsDataStore();
        nfs.setName("");
        nfs.setServerIp("");
        nfs.setExportDir("");

        // 设置nfs所在的集群的uuid
        Cluster cluster = new Cluster();
        cluster.setUuid("");
        nfs.setCluster(cluster);

        // 设置nfs所在主机的uuid
        Host host1 = new Host();
        host1.setUuid("");

        List&lt;Host&gt; hostOnNds = new  ArrayList&lt;Host&gt;();
        hostOnNds.add(host1);
        nfs.setHosts(hostOnNds);

        // 执行创建nfs数据存储的操作
        dataStoreService.createNfsDataStore(nfs);
     * </pre>
     * @param nfsDataStore
     *            nfs数据存储信息
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult createNfsDataStore(
            NfsDataStore nfsDataStore);

    /**
     * 创建ocfs数据存储.
     * <pre>
     *  String hostUuid = "";
        // 创建ocfs对象，设置名称、最大挂载点等。
        OcfsDataStore ocfs = new OcfsDataStore();
        ocfs.setName("");
        ocfs.setLvmBased(false);
        ocfs.setLvmLabel(null);
        ocfs.setMaxSlots(1);

        // 指定ocfs创建所在的集群
        Cluster cluster = new Cluster();
        cluster.setUuid("");
        ocfs.setCluster(cluster);

        // 指定ocfs创建所在的主机
        Host host1 = new Host();
        host1.setUuid("");

        List&lt;Host&gt; hostOnOds = new ArrayList&lt;Host&gt;();
        hostOnOds.add(host1);
        ocfs.setHosts(hostOnOds);

        // 设置创建ocfs所用的块设备的uuid
        List&lt;BlockDevice&gt; blockDevices = new ArrayList&lt;BlockDevice&gt;();
        BlockDevice bd = new BlockDevice();
        bd.setUuid("");
        blockDevices.add(bd);
        ocfs.setBlockDevices(blockDevices);

        // 执行创建ocfs数据存储的操作
        dataStoreService.createOcfsDataStore(ocfs, hostUuid);
     * </pre>
     * @param ocfsDataStore
     *            ocfs数据存储对象
     * @param hostUuid
     *            主机uuid
     * @return task result
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult createOcfsDataStore(
            OcfsDataStore ocfsDataStore,
            String hostUuid);

    /**
     * 创建 vsan数据存储.
     * <pre>
     *  // 创建vsan对象
        VSanDataStore vsan = new VSanDataStore();

        // 设置vsan所在的集群的uuid
        Cluster cluster = new Cluster();
        cluster.setUuid("");
        vsan.setCluster(cluster);

        // 设置vsan的名称
        vsan.setName("vsan_test");

        // vsan的创建至少需要三台主机，每台主机上至少需要一块磁盘
        List&lt;VSanOSD&gt; vsanosd = new ArrayList&lt;VSanOSD&gt;();

        //设置主机1的uuid以及块设备1的uuid
        VSanOSD vsanosd1 = new VSanOSD();
        BlockDevice bd = new BlockDevice();
        bd.setUuid("");
        vsanosd1.setBlockDevice(bd);
        Host host1 = new Host();
        host1.setUuid("");
        vsanosd1.setHost(host1);
        vsanosd.add(vsanosd1);

        //设置主机2的uuid以及块设备2的uuid
        VSanOSD vsanosd2 = new VSanOSD();
        BlockDevice bc = new BlockDevice();
        bc.setUuid("");
        vsanosd2.setBlockDevice(bc);
        Host host2 = new Host();
        host2.setUuid("");
        vsanosd2.setHost(host2);
        vsanosd.add(vsanosd2);

        //设置主机3的uuid以及块设备3的uuid
        VSanOSD vsanosd3 = new VSanOSD();
        BlockDevice bb = new BlockDevice();
        bb.setUuid("");
        vsanosd3.setBlockDevice(bb);
        Host host3 = new Host();
        host3.setUuid(""); 
        vsanosd3.setHost(host3);
        vsanosd.add(vsanosd3);

        vsan.setOsds(vsanosd);

        // 执行创建vsan的操作
        dataStoreService.createVsanDataStore(vsan);
     * </pre>
     * @param vsanDataStore
     *            vsan数据存储info
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult createVsanDataStore(
            VSanDataStore vsanDataStore);

    /**
     * 从主机中分离数据存储.
     * @param removeDsUuids
     *            移除的数据存储的uuids
     * @param hostUuid
     *            主机uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult detachDataStoreFromHost(
            List<String> removeDsUuids, String hostUuid);

    /**
     * 从数据存储中分离主机.
     * @param removeHostUuids
     *            分离的主机的uuids.
     * @param dsUuid
     *            数据存储uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult detachHostFromDataStore(
            List<String> removeHostUuids, String dsUuid);

    /**
     * 扩展ocfs数据存储.
     * @param dsUuid
     *            数据存储uuid
     * @param hostUuid
     *            主机uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult extendOcfsDataStore(String dsUuid,
            String hostUuid);

    /**
     * 扩容 vsan数据存储.
     * <pre>
     *  String vsanUuid = "";

        // 设置要添加的磁盘所在的主机uuid和磁盘uuid
        VSanOSD osd = new VSanOSD();
        BlockDevice bd = new BlockDevice();
        bd.setUuid("");
        osd.setBlockDevice(bd);
        Host host1 = new Host();
        host1.setUuid("");
        osd.setHost(host1);

       //执行vsan的扩容操作
       dataStoreService.extendVsanDataStore(vsanUuid, osd);
     * </pre>
     * @param vsanUuid
     *            要扩容的vsan的uuid
     * @param osd
     *            新添加的vsan osd
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult extendVsanDataStore(String vsanUuid,
            VSanOSD osd);

    /**
     * 获取所有的数据存储.
     * @return 数据存储列表
     */
    List<DataStore> getAllDataStore();

    /**
     * 获取主机上可用的数据存储.
     * @param hostUuid
     *            主机uuid
     * @return 数据存储列表
     */
    List<DataStore> getAvailableDataStoreOnHost(String hostUuid);

    /**
     * 获取数据存储上可用的主机.
     * @param dsUuid
     *            数据存储uuid
     * @return 主机列表
     */
    List<Host> getAvailableHostOnDataStore(String dsUuid);

    /**
     * 获取主机上的vsan可用的端口组
     * @param hostUuid
     *            目标主机uuid
     * @return 端口组列表
     */
    List<StandardPortGroup> getAvailablePortGroupForVsan(String hostUuid);

    /**
     * 获取数据存储的目录树.
     * @param dsUuid
     *            数据存储uuid
     * @return 目录树
     */
    DataStoreDir getDataStoreDirectoryTree(String dsUuid);

    /**
     * 获取数据存储的文件列表.
     * @param dsUuid
     *            数据存储uuid
     * @param dir
     *            目标目录
     * @return 文件列表
     */
    List<DataStoreFile> getDataStoreFile(String dsUuid, String dir);

    /**
     * 获取数据存储的信息.
     * @param dsUuid
     *            数据存储uuid
     * @return 数据存储对象
     */
    DataStore getDataStoreInfo(String dsUuid);

    /**
     * 获取数据存储列表.
     * @param targetType
     *            目标类型
     * @param targetUuid
     *            目标数据存储的uuid
     * @return 任务结果
     */
    List<DataStore> getDataStoreList(TaskTargetType targetType,
            String targetUuid);

    /**
     * 获取该主机上的数据存储.
     * @param hostUuid
     *            主机uuid
     * @return 数据存储列表
     */
    List<DataStore> getDataStoreOnHost(String hostUuid);

    /**
     * 获取备份信息和挂载状态.
     * @param dsUuid
     *            数据存储uuid
     * @return 数据存储
     */
    DataStore getDeviceBackingAndMountStatus(String dsUuid);

//    /**
//     * 修改 ocfs数据存储的拥有者
//     * @param ods
//     *            ocfs数据存储
//     * @param opHostUuid
//     *            操作的主机uuid
//     * @return 任务结果
//     */
//    @SuppressWarnings("rawtypes")
//	TaskIntermediateResult modifyOcfsDataStoreOwner(
//            OcfsDataStore ods, String opHostUuid);
//
//    /**
//     * 修改 nfs数据存储的拥有者.
//     * @param nds
//     *            nfs数据存储
//     * @return 任务结果
//     */
//    @SuppressWarnings("rawtypes")
//	TaskIntermediateResult modifyNfsDataStoreOwner(
//            NfsDataStore nds);

    /**
     * 获取与该存储相关的主机.
     * @param dsUuid
     *            数据存储uuid
     * @return 主机列表
     */
    List<Host> getHostOnDataStore(String dsUuid);

    /**
     * 获取主机上的vsan端口组.
     * @param hostUuid
     *            主机uuid
     * @return 标准端口组
     */
    StandardPortGroup getHostVsanPortGroup(String hostUuid);

    /**
     * 获取vsan监控列表.
     * @param vsanUuid
     *            vsan数据存储的uuid
     * @return vsan监控列表
     */
    List<VSanMon> getVsanMonitorList(String vsanUuid);

    /**
     * 获取vsan osd列表.
     * @param vsanUuid
     *            vsan数据存储的uuid
     * @return VSanOSD列表
     */
    List<VSanOSD> getVsanOsdList(String vsanUuid);

//    /**
//     * 自动选择vsan osds.
//     * @param osdList
//     *            vsan osd列表
//     * @return 块设备列表
//     */
//    List<BlockDevice> autoSelectVsanOsds(List<VSanOSD> osdList);

//    /**
//     * @return 获取默认的vsan副本
//     */
//    int getDefautReplicas();

    /**
     * 将InCould Sphere 3.x里的数据存储存储迁移到4.0环境.
     */
    void immigrateDataStoreFromV3ToV4();

    /**
     * 修改数据存储的名称.
     * @param dsUuid
     *            数据存储 uuid
     * @param dsName
     *            数据存储的名字
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult modifyDataStoreName(String dsUuid,
            String dsName);

    /**
     * 修改 ocfs数据存储的最大挂载数.
     * @param odsUuid
     *            ocfs数据存储uuid
     * @param newNodeSlot
     *            数据存储的最大挂载数
     * @param opHostUuid
     *            操作主机的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult modifyOcfsDataStoreNodeSlot(
            String odsUuid,
            int newNodeSlot, String opHostUuid);

    /**
     * 挂载数据存储.
     * @param hostUuid
     *            主机uuid
     * @param dsUuid
     *            数据存储uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult mountDataStore(String hostUuid,
            String dsUuid);

    /**
     * 移除数据存储.
      * @param dsUuid
     *            数据存储 uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeDataStore(String dsUuid);

    /**
     * 移除主机上的vsan网络端口组.
     * @param hostUuid
     *            目标主机的uuid
     * @param pgUuid
     *            要移除的端口组的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeHostVsanPortGroup(
            String hostUuid, String pgUuid);

    /**
     * 在主机上设置vsan网络端口组.
     * @param hostUuid
     *            目标主机的uuid
     * @param pgUuid
     *            vsan使用的端口组uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult setHostVsanPortGroup(String hostUuid,
            String pgUuid);

    /**
     * 缩减vsan数据存储.
     * @param vsanUuid
     *            vsan uuid
     * @param osdUuid
     *            移除的 osd的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult shrinkVsanDataStore(String vsanUuid,
            String osdUuid);

    /**
     * 同步一主机上的所有的数据存储.
     * @param hostUuid
     *            主机uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult synchronizeAllDataStore(String hostUuid);

    /**
     * 同步集群内vsan配置.
     * @param vsanUuid
     *            vsan uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult syncVsanConfigInCluster(String vsanUuid);

    /**
     * 卸载数据存储.
     * @param hostUuid
     *            主机uuid
     * @param dsUuid
     *            数据存储uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult unmountDataStore(String hostUuid,
            String dsUuid);
}
