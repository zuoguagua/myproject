package com.inspur.ics.client.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.inspur.ics.pojo.IscsiTarget;

/**
 * Rest service interface for ISCSI  module.
 * @author  jiangwt
 */
@Path("/")
public interface IscsiRestService {

	/**
	 * 获取iscsi适配器可用端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getAvailablePortGroupForIscsiAdapter")
	String getAvailablePortGroupForIscsiAdapter(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid);

	/**
	 * 获取iscsi目标可用端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid  iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @param itUuid iscsi target uuid
	 *        iscsi 目标的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getAvailablePortGroupForIscsiTarget")
	String getAvailablePortGroupForIscsiTarget(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid, @FormParam("itUuid") String itUuid);

	/**
	 * 获取找到的iscsi目标iqn
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @param ip target ip
	 *        目标ip
	 * @param port target port
	 *        目标端口
	 * @param pgUuids port group uuids
	 *        端口组的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getDiscoveredIscsiTargetIqn")
	String getDiscoveredIscsiTargetIqn(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid, @FormParam("ip") String ip, @FormParam("port") int port, @FormParam("pgUuids") List<String> pgUuids);

	/**
	 * 获取iscsi适配器上的iscsi目标
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getIscsiTargetOnIscsiAdapter")
	String getIscsiTargetOnIscsiAdapter(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid);

	/**
	 * 获取iscsi适配器上的端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getPortGroupOnIscsiAdapter")
	String getPortGroupOnIscsiAdapter(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid);

	/**
	 * 获取iscsi目标上的端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param itUuid iscsi target uuid
	 *        iscsi 目标的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_getPortGroupOnIscsiTarget")
	String getPortGroupOnIscsiTarget(@FormParam("token") String token, @FormParam("itUuid") String itUuid);

	/**
	 * 添加iscsi目标
	 * @param token user token
	 *        用户认证令牌
	 * @param token user token
	 *        用户认证令牌
	 * @param target iscsi target
	 *        iscsi目标
	 * @return result
	 */
	@POST
	@Path("iscsi_addIscsiTarget")
	String addIscsiTarget(@FormParam("token") String token, @FormParam("target") String target);

	/**
	 * 添加iscsi适配器端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_addPortGroupToIscsiAdapter")
	String addPortGroupToIscsiAdapter(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid, @FormParam("pgUuid") String pgUuid);

	/**
	 * 添加iscsi目标的端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param itUuid iscsi target uuid
	 *        iscsi 目标的uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_addPortGroupToIscsiTarget")
	String addPortGroupToIscsiTarget(@FormParam("token") String token, @FormParam("itUuid") String itUuid, @FormParam("pgUuid") String pgUuid);

	/**
	 * 移除iscsi目标
	 * @param token user token
	 *        用户认证令牌
	 * @param itUuid iscsi target uuid
	 *        iscsi 目标的uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_removeIscsiTarget")
	String removeIscsiTarget(@FormParam("token") String token, @FormParam("itUuid") String itUuid);

	/**
	 * 移除iscsi适配器上的端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param isaUuid iscsi adapter uuid
	 *        iscsi 适配器的uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_removePortGroupOnIscsiAdapter")
	String removePortGroupOnIscsiAdapter(@FormParam("token") String token, @FormParam("isaUuid") String isaUuid, @FormParam("pgUuid") String pgUuid);

	/**
	 * 移除iscsi目标上的端口组
	 * @param token user token
	 *        用户认证令牌
	 * @param itUuid port target uuid
	 *        iscsi 目标的uuid
	 * @param pgUuid port group uuid
	 *        端口组uuid
	 * @return result
	 */
	@POST
	@Path("iscsi_removePortGroupOnIscsiTarget")
	String removePortGroupOnIscsiTarget(@FormParam("token") String token, @FormParam("itUuid") String itUuid, @FormParam("pgUuid") String pgUuid);
}
