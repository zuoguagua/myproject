package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.IscsiTarget;
import com.inspur.ics.pojo.StandardPortGroup;

/**
 * Iscsi服务接口.
 * @author jiangwt
 */

public interface IscsiService {

	/**
     * 获取iscsi适配器可用端口组.
     * @param isaUuid
     *            iscsi适配器的 uuid
     * @return 标准端口组列表
     */
    List<StandardPortGroup> getAvailablePortGroupForIscsiAdapter(String isaUuid);

    /**
     * 获取iscsi目标可用端口组.
     * @param isaUuid
     *            iscsi适配器的uuid
     * @param itUuid
     *            iscsi目标的uuid
     * @return 标准端口组列表
     */
    List<StandardPortGroup> getAvailablePortGroupForIscsiTarget(String isaUuid,
            String itUuid);

    /**
     * 获取找到的iscsi目标iqn.
     * @param isaUuid
     *            iscsi适配器uuid
     * @param ip
     *            目标ip
     * @param port
     *            目标 端口
     * @param spgUuids
     *            标准端口组uuid列表
     * @return iqn列表
     */
    List<String> getDiscoveredIscsiTargetIqn(String isaUuid, String ip,
            Integer port, List<String> spgUuids);

    /**
     * 获取iscsi适配器上的iscsi.
     * @param isaUuid
     *            iscsi适配器的uuid
     * @return iscsi的列表
     */
    List<IscsiTarget> getIscsiTargetOnIscsiAdapter(String isaUuid);

    /**
     * 获取iscsi适配器上的端口组
     * @param isaUuid
     *            iscsi适配器uuid
     * @return 标准端口组列表
     */
    List<StandardPortGroup> getPortGroupOnIscsiAdapter(String isaUuid);

    /**
     * 获取iscsi目标上的端口组.
     * @param itUuid
     *            iscsi目标的uuid
     * @return 端口组列表
     */
    List<StandardPortGroup> getPortGroupOnIscsiTarget(String itUuid);

    /**
     * 添加iscsi目标.
     * <pre>
     *  // 定义iscsi适配器的uuid、iscsi目标的ip、端口和iqn
        String isaUuid = "";
        String tgtIp = "";
        Integer tgtPort = 3260;
        String tgtIqn = "";

        IscsiSoftwareAdapter isa = new IscsiSoftwareAdapter();
        isa.setUuid(isaUuid);

        // 新建标准端口组并设置端口组的uuid
        StandardPortGroup spg1 = new StandardPortGroup();
        spg1.setUuid("");

        // 创建iscsi接口，并添加设置好的端口组
        IscsiIface ii1 = new IscsiIface();
        ii1.setPortGroup(spg1);
        List&lt;IscsiIface&gt; ifaces = new ArrayList&lt;IscsiIface&gt;();
        ifaces.add(ii1);

        // 创建target对象，并设置相关参数
        IscsiTarget target = new IscsiTarget();
        target.setIscsiAdapter(isa);
        target.setIp(tgtIp);
        target.setPort(tgtPort);
        target.setIqn(tgtIqn);
        target.setIfaces(ifaces);
        target.setChap(false);

        // 执行添加iscsi目标的操作
        iscsiService.addIscsiTarget(target);
     * </pre>
     * @param target
     *            iscsi目标
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult addIscsiTarget(IscsiTarget target);

    /**
     * 添加iscsi适配器端口组.
     * @param isaUuid
     *            iscsi适配器的 uuid
     * @param pgUuid
     *            端口组的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult addPortGroupToIscsiAdapter(
            String isaUuid, String pgUuid);

    /**
     * 添加iscsi目标的端口组.
     * @param itUuid
     *            iscsi目标的uuid
     * @param pgUuid
     *            端口组的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult addPortGroupToIscsiTarget(
            String itUuid, String pgUuid);

    /**
     * 移除iscsi目标.
     * @param itUuid
     *            iscsi目标的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removeIscsiTarget(String itUuid);

    /**
     * 移除iscsi适配器上的端口组.
     * @param isaUuid
     *            iscsi适配器的uuid
     * @param pgUuid
     *            端口组的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removePortGroupOnIscsiAdapter(
            String isaUuid, String pgUuid);

    /**
     * 移除iscsi目标上的端口组.
     * @param itUuid
     *            iscsi目标的uuid
     * @param pgUuid
     *            端口组的uuid
     * @return 任务结果
     */
    @SuppressWarnings("rawtypes")
	TaskIntermediateResult removePortGroupOnIscsiTarget(
            String itUuid, String pgUuid);
}
