package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author kangzhx
 */
@Path("/")
public interface DrsRestService {

    /**
     * drs 获取DRS是否开启.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_getStatus")
    String getStatus(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 开启/关闭DRS.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @param drsEnabled
     *            drs是否开启
     * @return result
     */
    @POST
    @Path("drs_configDrs")
    String configDrs(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID,
            @FormParam("drsEnabled") boolean drsEnabled);

    /**
     * 获取DRS策略.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_getDrsStrategy")
    String getDrsStrategy(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 配置DRS策略.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @param strategyJson
     *            drs策略
     * @return result
     */
    @POST
    @Path("drs_configStrategy")
    String configStrategy(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID,
            @FormParam("strategy") String strategyJson);

    /**
     * 创建drs组.
     * @param token
     *            用户认证令牌
     * @param drsGroup
     *            drs组
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_createDrsGroup")
    String createDrsGroup(@FormParam("token") String token,
            @FormParam("drsGroup") String drsGroup,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 改变drs组.
     * @param token
     *            用户认证令牌
     * @param drsGroup
     *            drs组
     * @return result
     */
    @POST
    @Path("drs_modifyDrsGroup")
    String modifyDrsGroup(@FormParam("token") String token,
            @FormParam("drsGroup") String drsGroup);

    /**
     * 删除drs组.
     * @param token
     *            用户认证令牌
     * @param id
     *            drsId
     * @return result
     */
    @POST
    @Path("drs_deleteDrsGroup")
    String deleteDrsGroup(@FormParam("token") String token,
            @FormParam("id") int id);

    /**
     * 获取在集群中的drs组.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_getDrsGroupInCluster")
    String getDrsGroupInCluster(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 获取drs组的信息.
     * @param token
     *            用户认证令牌
     * @param id
     *            drs组的id
     * @return result
     */
    @POST
    @Path("drs_getDrsGroupInfo")
    String getDrsGroupInfo(@FormParam("token") String token,
            @FormParam("id") int id);

    /**
     * 获取可以加入该drs组策略的虚拟机.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @param id
     *            drsId
     * @return result
     */
    @POST
    @Path("drs_getAddableVms")
    String getAddableVms(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID,
            @FormParam("id") int id);

    /**
     * 获取可加入该Drs组的主机.
     * @param token
     *           用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @param id
     *            drsId
     * @return result
     */
    @POST
    @Path("drs_getAddableHosts")
    String getAddableHosts(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID,
            @FormParam("id") int id);

    /**
     * 创建drs策略.
     * @param token
     *            用户认证令牌
     * @param drsRule
     *            drs规则
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_createDrsRule")
    String createDrsRule(@FormParam("token") String token,
            @FormParam("drsRule") String drsRule,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 改变的drs策略.
     * @param token
     *            用户认证令牌
     * @param drsRule
     *            drs规则
     * @return result
     */
    @POST
    @Path("drs_modifyDrsRule")
    String modifyDrsRule(@FormParam("token") String token,
            @FormParam("drsRule") String drsRule);

    /**
     * 删除drs策略.
     * @param token
     *            用户认证令牌
     * @param id
     *            drs规则Id
     * @return result
     */
    @POST
    @Path("drs_deleteDrsRule")
    String deleteDrsRule(@FormParam("token") String token,
            @FormParam("id") int id);

    /**
     * 获取在集群中的drs规则.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @return result
     */
    @POST
    @Path("drs_getDrsRuleInCluster")
    String getDrsRuleInCluster(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * 获取drs规则的信息.
     * @param token
     *            用户认证令牌
     * @param id
     *            drsId
     * @return result
     */
    @POST
    @Path("drs_getDrsRuleInfo")
    String getDrsRuleInfo(@FormParam("token") String token,
            @FormParam("id") int id);

    /**
     * 获得可加入该drs规则的虚拟机.
     * @param token
     *            用户认证令牌
     * @param clusterUUID
     *            集群UUID
     * @param id
     *            drs规则的Id
     * @return result
     */
    @POST
    @Path("drs_getAddableVmsForRule")
    String getAddableVmsForRule(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID,
            @FormParam("id") int id);
}
