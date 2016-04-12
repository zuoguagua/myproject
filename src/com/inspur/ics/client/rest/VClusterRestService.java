package com.inspur.ics.client.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
/**
 *VCluster REST API 接口定义.
 * @author lilx
 *
 */
@Path("/")
public interface VClusterRestService {
   /**
    * 获取vcluster的信息.
    * @param token
    *            用户令牌
    * @param vclusterUUID
    *           虚拟集群uuid
    * @return  结果字符串
    */
    @POST
    @Path("vcluster_getVClusterInfo")
    String getVClusterInfo(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);

    /**
     * get all vclusters.
     * @param token
     *        user token
     * @return result
     */
    @POST
    @Path("vcluster_getVClusterList")
    String getVClusterList(@FormParam("token") String token);

    /**
     * 获取虚拟集群中所有的虚拟机列表.
     * @param token
     *        user token
     * @param vclusterUUID 集群uuid
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_getVmInVCluster")
    String getVmInVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);

    /**
     * 创建虚拟集群.
     * @param token
     *        user token
     * @param vcluster
     *        vcluster info(json format)
     * @return  result
     */
    @POST
    @Path("vcluster_createVCluster")
    String createVCluster(@FormParam("token") String token,
            @FormParam("vcluster") String vcluster);
    /**
     * 虚拟集群从ISO新建虚拟机.
     * @param vclusterUUID 虚拟集群uuid
     * @param config 虚拟机配置信息
     * @param token 用户令牌
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_createVMByISO")
    String createVMByIsoInVCluster(@FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("config") String config,
            @FormParam("token") String token);
    /**
     * 模版创建虚拟集群.
     * @param token 用户令牌
     * @param vclusterName 新的虚拟集群名称
     * @param templateUUID 虚拟机模版uuid
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_createVClusterByTemplate")
    String createVClusterByTemplate(@FormParam("token") String token,
            @FormParam("vclusterName") String vclusterName,
            @FormParam("templateUUID") String templateUUID);
    /**
     * 模版创建虚拟集群.
     * @param vclusterUUID 虚拟集群uuid
     * @param templateUUID 虚拟机模版uuid
     * @param vmName 新的虚拟机名称
     * @param token 用户令牌
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_createVMByTemplate")
    String createVMByTemplateInVCluster(@FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("templateUUID") String templateUUID,
            @FormParam("vmName") String vmName,
            @FormParam("token") String token);
    /**
     * 虚拟集群中复制创建虚拟机.
     * @param vclusterUUID 虚拟集群uuid
     * @param vmUUID 虚拟机uuid
     * @param vmName 新的虚拟机名称
     * @param token 用户令牌
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_copyVM")
    String copyVMInVCluster(@FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("vmName") String vmName,
            @FormParam("token") String token);
    /**
     * 虚拟集群中复制批量创建虚拟机.
     * @param vclusterUUID 虚拟集群uuid
     * @param templateUUID 虚拟机模版uuid
     * @param vmName 新的虚拟机名称
     * @param num 批量创建数目
     * @param token 用户令牌
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_batchCreateVMByTemplate")
    String batchCreateVMByTemplateInVCluster(@FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("templateUUID") String templateUUID,
            @FormParam("vmName") String vmName,
            @FormParam("num") int num,
            @FormParam("token") String token);
    /**
     * 虚拟集群删除虚拟机.
     * @param vclusterUUID 虚拟集群uuid
     * @param vmUUID 虚拟机uuid
     * @param token 用户令牌
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_deleteVM")
    String deleteVMInVCluster(@FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("token") String token);
    /**
     * 复制虚拟集群.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @param vclusterName 虚拟集群名称
     * @return 结果字符串
     */
    @POST
    @Path("vcluster_copyVCluster")
    String copyVCluster(@FormParam("token") String token,
            @FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("vclusterName") String vclusterName);
    /**
     * 删除虚拟集群.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_deleteVCluster")
    String deleteVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);
    /**
     * 获取可移入虚拟机列表.
     * @param token 用户令牌
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_getVMsCanBeMovedIn")
    String getVMsCanBeMovedIn(@FormParam("token") String token);
    /**
     * 移入虚拟机.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @param vms 可选虚拟机uuid字符串数组
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_moveVMInVCluster")
    String moveVMInVCluster(@FormParam("token") String token,
            @FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("vms") String[] vms);

    /**
     *移出虚拟机.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @param vmUUID 虚拟机uuid
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_moveVMOutVCluster")
    String moveVMOutVCluster(@FormParam("token") String token,
            @FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("vmUUID") String vmUUID);

    /**
     *打开虚拟集群电源.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_powerOnVCluster")
    String powerOnVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);

    /**
     *关闭虚拟集群电源.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @return  字符串
     */
    @POST
    @Path("vcluster_powerOffVCluster")
    String powerOffVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);
    /**
     *强制重启虚拟集群.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_restartVCluster")
    String restartVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);
    /**
     *关闭虚拟集群.
     * @param token 用户令牌
     * @param vclusterUUID 虚拟集群uuid
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_shutdown")
    String shutdownVCluster(@FormParam("token") String token, @FormParam("vclusterUUID") String vclusterUUID);
    /**
     *修改虚拟集群信息.
     * @param token 用户令牌
     * @param vcluster 虚拟集群对象
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_modifyVCluster")
    String modifyVCluster(@FormParam("token") String token, @FormParam("vcluster") String vcluster
            );

    /**
     * 根据名称获取虚拟集群信息.
     * @param token 用户令牌
     * @param vclusterName 虚拟集群名称
     * @return  结果字符串
     */
    @POST
    @Path("vcluster_getVClusterInfoByName")
    String getVClusterInfoByName(@FormParam("token") String token, @FormParam("vclusterName") String vclusterName);
}

