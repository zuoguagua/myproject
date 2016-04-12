//package com.inspur.ics.client.rest;
//
//import javax.ws.rs.FormParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//
///**
// * Rest service interface for system module.
// * @author zhangjun
// */
//@Path("/")
//public interface SystemRestService {
//
//    /**
//     * @param token user token
//     * @param language language to set
//     * @return result
//     */
//    @POST
//    @Path("system_configLocale")
//    String configLocale(@FormParam("token") String token, @FormParam("language") String language);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getClusterTree")
//    String getClusterTree(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @param vnetUUID vnet uuid
//     * @return result
//     */
//    @POST
//    @Path("system_getCustomVNet")
//    String getCustomVNet(@FormParam("token") String token, @FormParam("vnetUUID") String vnetUUID);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getHostTree")
//    String getHostTree(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getLicenceInfo")
//    String getLicenceInfo(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getSimpleInfo")
//    String getSimpleInfo(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getVMTopo")
//    String getVMTopo(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_getVResourceTree")
//    String getVResourceTree(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_isLicenceOutOfDate")
//    String isLicenceOutOfDate(@FormParam("token") String token);
//
//    /**
//     * @param userName user name
//     * @param password user password
//     * @param userLocale user locale
//     * @return result
//     */
//    @POST
//    @Path("system_security_userLogin")
//    String login(@FormParam("username") String userName,
//                 @FormParam("passwd") String password,
//                 @FormParam("userLocale") String userLocale);
//
//    /**
//     * @param token user token
//     * @return result
//     */
//    @POST
//    @Path("system_logout")
//    String logout(@FormParam("token") String token);
//
//    /**
//     * @param token user token
//     * @param licence licence to modify
//     * @return result
//     */
//    @POST
//    @Path("system_modifyLicence")
//    String modifyLicence(@FormParam("token") String token, @FormParam("licence") String licence);
//
//    /**
//     * @param token user token
//     * @param password user password
//     * @return result
//     */
//    @POST
//    @Path("system_modifyPassword")
//    String modifyPassword(@FormParam("token") String token, @FormParam("password") String password);
//
//}
