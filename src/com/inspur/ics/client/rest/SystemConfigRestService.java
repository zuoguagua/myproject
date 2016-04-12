package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 系统配置服务REST接口.
 * @author ychau
 */
@Path("/")
public interface SystemConfigRestService {
    /**
     * 获取系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @param token 操作用户会话令牌
     * @return 系统事件、任务记录保留配置信息
     */
    @POST
    @Path("system_config_getSystemConfigInfo")
    String getSystemConfigInfo(@FormParam("token") String token);

    /**
     * 设置系统在周期性删除任务事件信息时要保留多少天内的数据或多少条记录.
     * @param token 操作用户会话令牌
     * @param config 更新系统事件、任务记录保留配置信息
     * @return 返回任务信息
     */
    @POST
    @Path("system_config_setSystemConfigInfo")
    String setSystemConfigInfo(@FormParam("token") String token,
            @FormParam("config") String config);

    /**
     * 设置icenter管理节点NTP服务器 .
     * @param token 操作用户会话令牌
     * @param ntpServers 新的NTP服务器 列表
     * @return 返回任务信息
     */
    @POST
    @Path("system_config_setNtpServer")
    String setNtpServers(@FormParam("token") String token,
            @FormParam("ntpServers") String[] ntpServers);

    /**
     * 获取icenter管理节点NTP服务器 .
     * @param token 操作用户会话令牌
     * @return NTP服务器 列表
     */
    @POST
    @Path("system_config_getNtpServerList")
    String getNtpServers(@FormParam("token") String token);
}
