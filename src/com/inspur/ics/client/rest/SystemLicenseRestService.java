package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 系统许可证书配置REST接口.
 * @author ychau
 *
 */
@Path("/")
public interface SystemLicenseRestService {

    /**
     * 获取许可证相关信息.
     * @param token 用户会话令牌
     * @return 许可证书信息
     */
    @POST
    @Path("system_license_getLicense")
    String getLicense(@FormParam("token") String token);
//
//    /**
//     * 导入或更新许可证书.
//     * @param token 用户会话令牌
//     * @param licenseInfo 许可证书信息
//     * @return 返回任务信息
//     */
//    @POST
//    @Path("system_license_modifyLicense")
//    String modifyLicence(@FormParam("token") String token, @FormParam("licenseInfo") String licenseInfo);
    
    /**
     * 添加新许可证.
     * @param token 用户会话令牌
     * @param licenseInfo 许可证书信息
     * @return 返回任务信息
     */
    @POST
    @Path("system_license_addLicense")
    String addLicense(@FormParam("token") String token, @FormParam("licenseInfo") String licenseInfo);
    
    /**
     * 移除许可证证书.
     * @param token 用户会话令牌
     * @param licenseInfo 许可证书信息
     * @return 返回任务信息
     */
    @POST
    @Path("system_license_removeLicense")
    String removeLicense(@FormParam("token") String token, @FormParam("licenseInfo") String licenseInfo);

}
