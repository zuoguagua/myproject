package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.VCTemplateService;
import com.inspur.ics.client.rest.VClusterRestService;
import com.inspur.ics.client.rest.VcTemplateRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.VClusterTemplate;


/**
 * @author zuolanhai
 *
 */
public class VcTemplateServiceImpl implements VCTemplateService {

    /**
     * vcluster template service.
     */
    private VcTemplateRestService vcTemplateService;

    /**
     * @param url
     *            api服务端地址
     * @param token
     *            验证合法性
     */
    public VcTemplateServiceImpl(String url, String token) {
        this.token = token;
        this.url = url;
        vcTemplateService = ProxyFactory.create(VcTemplateRestService.class,
                url,
                ExecutorFactory.getDefaultClientExecutor());
    }

    /**
     *
     */
    String url = "127.0.0.1:8080";

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            str
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     */
    String token = "";

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            str
     */
    public void setToken(String token) {
        this.token = token;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult createVclusterTemplate(String vclusterUUID, VClusterTemplate template) {
        String stringXml = vcTemplateService.createTemplate(token, vclusterUUID,
                FormatUtil.toJson(template));
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
        }
    }

//    @Override
//    public String createVclusterTmeplateByVcluster(String vClusterUuid,
//            String name, String description) {
//        VClusterTemplate template = new VClusterTemplate();
//        template.setName(name);
//        template.setDescription(description);
//        return vcTemplateService.createVclusterTmeplateByVcluster(token,
//                vClusterUuid, name, description);
//    }

    @Override
    public List<VClusterTemplate> getVClusterTemplateList() {
        String stringXml = vcTemplateService.getAllVclusterTemplate(token);
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, VClusterTemplate.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VClusterTemplate>();
        } else {
            return (List<VClusterTemplate>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult deleteVclusterTemplate(String templateUUID) {
        String stringXml = vcTemplateService.deleteVclusterTemplate(token, templateUUID);
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
        }
    }

    @Override
    public TaskIntermediateResult renameVClusterTemplate(String templateUUID, String templateName) {
        String stringXml = vcTemplateService.renameVclusterTmeplateName(token, templateUUID, templateName);
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((TaskIntermediateResult) ((List) result.getData()).get(0));
        }
    }
}
