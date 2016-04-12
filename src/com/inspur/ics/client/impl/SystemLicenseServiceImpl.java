package com.inspur.ics.client.impl;

import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.SystemLicenseService;
import com.inspur.ics.client.rest.SystemLicenseRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.License;

/**
 * 系统许可证书配置接口实现类.
 * @author ychau
 *
 */
public class SystemLicenseServiceImpl implements SystemLicenseService {
    /**
     * 用户会话令牌.
     */
    private String token;

    /**
     * 系统安全REST接口服务类.
     */
    private SystemLicenseRestService restService;

    /**
     * 系统许可证书配置实现类构造方法.
     * @param url 请求Action路径
     * @param token 用户会话令牌.
     */
    public SystemLicenseServiceImpl(String url, String token) {
        this.restService = ProxyFactory.create(SystemLicenseRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * 获取许可证相关信息.
     * @return 许可证书信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<License> getLicense() {
        String resultXML = restService.getLicense(token);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, License.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return (List<License>) result.getData();
        }
    }

//    /**
//     * 导入或更新许可证书.
//     * @param licence 许可证书信息
//     * @return 返回任务信息
//     */
//    @Deprecated
//    @SuppressWarnings("rawtypes")
//    @Override
//    public TaskIntermediateResult modifyLicence(License licence) {
//        String stringXml = restService.modifyLicence(token, FormatUtil.toJson(licence));
//        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
//                Result.class, TaskIntermediateResult.class });
//        if (result.getError() != null) {
//            throw new RemoteException(result.getError().getMessage());
//        } else {
//            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
//        }
//    }

    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult addLicense(License licence) {
        String stringXml = restService.addLicense(token, FormatUtil.toJson(licence));
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult removeLicense(License licence) {
        String stringXml = restService.removeLicense(token, FormatUtil.toJson(licence));
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
        }
    }

}
