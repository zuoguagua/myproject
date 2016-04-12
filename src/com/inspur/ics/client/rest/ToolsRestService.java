/**
 * 
 */
package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author luo
 *
 */
@Path("/")
public interface ToolsRestService {
	
    /**
     * whether tools of router is available.
	 * @param token
	 *            user token.
	 * @param routerUUID
	 *            router uuid.
     * @return task result,only contains task id.
     */
	@POST
	@Path("tools_isRouterToolsAvailable")
    String isRouterToolsAvailable(@FormParam("token") String token, @FormParam("routerUUID") String routerUUID);

    /**
     * whether tools of vm is available.
	 * @param token
	 *            user token.
	 * @param vmUUID
	 *            vm uuid.
     * @return task result,only contains task id.
     */
	@POST
	@Path("tools_isVMToolsAvailable")
    String isVMToolsAvailable(@FormParam("token") String token, @FormParam("vmUUID") String vmUUID);

}
