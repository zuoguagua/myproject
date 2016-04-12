package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.VClusterTemplate;


/**
 * 虚拟集群模板服务接口，主要完成添加、获取、删除、修改等操作.
 * @author zuolanhai
 *
 */
public interface VCTemplateService {

    /**
     * 通过虚拟集群创建虚拟集群模板.
     * <pre>
     * For example:
     *    VClusterTemplate template = new VClusterTemplate();
     *    template.setDescription("description:test");
     *    template.setName("vcluster template name : template1");
     * </pre>
     * @param vclusterUUID
     *            创建模板所需的源虚拟集群标示uuid
     * @param template
     *            模板描述信息，需要设置：名称和描述信息
     * @return result 返回结果
     */
    TaskIntermediateResult createVclusterTemplate(String vclusterUUID, VClusterTemplate template);

//    /**
//     * 创建虚拟集群.
//     *
//     * @param vClusterUuid
//     *            创建模板所需的源虚拟集群标示uuid
//     * @param name
//     *            新建虚拟集群模板的名称
//     * @param description
//     *            新建虚拟集群模板的描述
//     * @return result 返回结果
//     */
//    String createVclusterTmeplateByVcluster(String vClusterUuid, String name,
//            String description);

    /**
     * 获取虚拟集群模板的列表.
     * @return 虚拟集群列表
     */
    List<VClusterTemplate> getVClusterTemplateList();

    /**
     * 删除虚拟集群模板.
     *
     * @param templateUUID
     *            删除虚拟集群模板的标示uuid
     * @return result 返回结果
     */
    TaskIntermediateResult deleteVclusterTemplate(String templateUUID);

    /**
     * 重命名虚拟机集群模版.
     * @param templateUUID
     *        要操作的虚拟集群模版
     * @param templateName
     *        重命名后的模版名称
     * @return 任务信息
     */
    TaskIntermediateResult renameVClusterTemplate(String templateUUID, String templateName);
}
