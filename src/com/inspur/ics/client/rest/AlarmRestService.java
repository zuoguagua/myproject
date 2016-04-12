package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author kangzhx
 */
@Path("/")
public interface AlarmRestService {
    /**
     * 获取所有告警事件.
     * @param token
     *            用户认证令牌
     * @return result
     */
    @POST
    @Path("system_alarm_getAllEvents")
    String getAllEvents(@FormParam("token") String token);

    /**
     * 更新主机的告警策略.
     * @param token
     *            用户认证令牌
     * @param hostStrategy
     *            升级主机告警的策略
     * @return result
     */
    @POST
    @Path("system_alarm_updateHostAlarmStrategy")
    String updateHostAlarmStrategy(@FormParam("token") String token,
            @FormParam("hostStrategy") String hostStrategy);

    /**
     * 更新虚拟机的告警策略.
     * @param token
     *            用户认证令牌
     * @param vmStrategy
     *            虚拟机的告警策略
     * @return result
     */
    @POST
    @Path("system_alarm_updateVmAlarmStrategy")
    String updateAlarmStrategy(@FormParam("token") String token,
            @FormParam("vmStrategy") String vmStrategy);

    /**
     * 更新数据存储的告警策略.
     * @param token
     *            用户认证令牌
     * @param dsStrategy
     *            数据存储告警策略
     * @return result
     */
    @POST
    @Path("system_alarm_updateDataStoreAlarmStrategy")
    String updateDataStoreAlarmStrategy(@FormParam("token") String token,
            @FormParam("dsStrategy") String dsStrategy);

    /**
     * 获取主机的告警策略.
     * @param token
     *           用户认证令牌
     * @param uuid
     *            主机对应的uuid
     * @return result
     */
    @POST
    @Path("system_alarm_getHostAlaramStrategy")
    String getHostAlaramStrategy(@FormParam("token") String token,
            @FormParam("uuid") String uuid);

    /**
     * 获取虚拟机的告警策略.
     * @param token
     *           用户认证令牌
     * @param uuid
     *            虚拟机的uuid
     * @return result
     */
    @POST
    @Path("system_alarm_getVmAlarmStrategy")
    String getVmAlarmStrategy(@FormParam("token") String token,
            @FormParam("uuid") String uuid);

    /**
     * 获取数据存储的告警策略.
     * @param token
     *           用户认证令牌
     * @param uuid
     *            数据存储的uuid
     * @return result
     */
    @POST
    @Path("system_alarm_getDataStoreAlarmStrategy")
    String getDataStoreAlarmStrategy(@FormParam("token") String token,
            @FormParam("uuid") String uuid);
}
