package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author zuolanhai
 *
 */
// server-side path
@Path("/")
public interface VcTemplateRestService {

    /**
     * @param token
     *            User Authentication
     * @param vclusterUUID
     *            str
     * @param template
     *            str
     * @return result
     */
    @POST
    @Path("vcTemplate_createTemplate")
    String createTemplate(@FormParam("token") String token,
            @FormParam("vclusterUUID") String vclusterUUID,
            @FormParam("template") String template);

    /**
     * rename vcluster template name.
     * @param token
     *            user token
     * @param templateUUID
     *            template UUID
     * @param templateName
     *            new template Name
     * @return result
     */
    @POST
    @Path("vcTemplate_rename")
    String renameVclusterTmeplateName(@FormParam("token") String token,
            @FormParam("templateUUID") String templateUUID,
            @FormParam("templateName") String templateName);

    /**
     * get all vcluster templates.
     * @param token
     *        user token
     * @return result
     */
    @POST
    @Path("vcTemplate_getAll")
    String getAllVclusterTemplate(@FormParam("token") String token);

    /**
     * @param token
     *            User Authentication
     * @param templateUUID
     *            str
     * @return result
     */
    @POST
    @Path("vcTemplate_deleteTemplate")
    String deleteVclusterTemplate(@FormParam("token") String token,
            @FormParam("templateUUID") String templateUUID);
}
