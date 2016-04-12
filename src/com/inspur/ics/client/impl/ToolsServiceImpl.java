/**
 * 
 */
package com.inspur.ics.client.impl;

import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.ToolsService;
import com.inspur.ics.client.rest.ToolsRestService;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.framework.result.Result;

/**
 * @author luo
 */
public class ToolsServiceImpl implements ToolsService {

    /**
     * vmtools rest service.
     */
    private ToolsRestService toolsRestService;

    /**
     * token.
     */
    private String token;

    /**
     * constructor.
     * @param url
     *            url
     * @param token
     *            token
     */
    public ToolsServiceImpl(String url, String token) {
        toolsRestService = ProxyFactory.create(ToolsRestService.class, url);
        this.token = token;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String isRouterToolsAvailable(String routerUUID) {
        if (routerUUID == null || routerUUID.isEmpty()) {
            throw new RemoteException("UUID can not be null or empty");
        }
        String stringXml = toolsRestService.isRouterToolsAvailable(token,
                routerUUID);
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, String.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((List) result.getData()).get(0).toString();
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String isVMToolsAvailable(String vmUUID) {
        if (vmUUID == null || vmUUID.isEmpty()) {
            throw new RemoteException("UUID can not be null or empty");
        }
        String stringXml = toolsRestService.isVMToolsAvailable(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
                Result.class, String.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return ((List) result.getData()).get(0).toString();
        }
    }

}
