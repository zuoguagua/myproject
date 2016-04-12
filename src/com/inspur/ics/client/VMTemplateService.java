package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.VM;

/**
 * 完成虚拟机模板添加、删除、修改等操作.
 * @author zuolanhai
 *
 */
public interface VMTemplateService {

    /**
     * 通过虚拟机，创建虚拟机模板.
     * @param vmUUID
     *            虚拟机唯一标示uuid
     * @param templateName
     *            新建的模板名称
     * @param tmplDesp
     *            新模板的描述
     * @return result 返回结果
     */
    TaskIntermediateResult createVMTemplateByVM(String vmUUID, String templateName,
            String tmplDesp);

    /**
     * 通过虚拟机模板创建虚拟机模板，即虚拟机模板复制功能.
     * @param tmplUUID
     *            源模板的唯一标示uuid
     * @param templateName
     *            新建模板的名称
     * @param tmplDesp
     *            新建模板的描述信息
     * @return result 返回结果
     */
    TaskIntermediateResult createVMTemplateByTemplate(String tmplUUID, String templateName,
            String tmplDesp);

    /**
     * 获取集群中虚拟机模板列表.
     * @param clusterUUID
     *            集群标示uuid
     * @return result 返回结果
     */
    List<VM> getVmTemplateByCluster(String clusterUUID);

    /**
     * 获取虚拟机模版列表.
     * @param targetType
     *        任务类型
     * @param targetUUID
     *        目标对象uuid（此处指要操作的虚拟机uuid）
     * @return 虚拟机模版列表
     */
    List<VM> getVMTemplateList(TaskTargetType targetType, String targetUUID);

    /**
     * 获取集群中虚拟机模板列表.
     * @param templateName
     *            模板名称name
     * @return 虚拟机模版列表
     */
    List<VM> getVmTemplateByName(String templateName);
    /**
     * 修改虚拟机模板配置.
     * @param vm
     *        虚拟机模版信息（同创建该模板时的虚拟机）
     * @return 任务信息
     */
    TaskIntermediateResult modifyVmTemplate(VM vm);

    /**
     * 删除虚拟机模板.
     * @param tmplUUID
     *            删除模板的标示uuid
     * @return 任务信息
     */
    TaskIntermediateResult deleteVmTemplate(String tmplUUID);

    /**
     * 导入虚拟机模版.
     * @param template
     *        虚拟机模版配置信息
     * @param filePath
     *        模版文件路径
     * @return 任务信息
     */
    TaskIntermediateResult importVMTemplate(VM template, String filePath);

    /**
     * 导出虚拟机模版.
     * @param templateUUID
     *        要操作的虚拟机模版uuid
     * @param filePath
     *        保存路径
     * @return 任务信息
     */
    TaskIntermediateResult exportVMTemplate(String templateUUID, String filePath);

    /**
     * 获取虚拟机模版信息.
     * @param tmplUUID
     *        要操作的虚拟机模版uuid
     * @return 虚拟机模版信息（同创建该模版的虚拟机信息）
     */
    VM getVMTemplateInfo(String tmplUUID);
}
