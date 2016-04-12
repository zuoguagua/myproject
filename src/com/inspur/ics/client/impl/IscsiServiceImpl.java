package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.IscsiService;
import com.inspur.ics.client.rest.IscsiRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.IscsiTarget;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StandardSwitch;

/**
 * Implementation of ISCSI interface.
 * @author jiangwt
 */

public class IscsiServiceImpl implements IscsiService {
	
	/**
     * Iscsi rest service.
     */
    private IscsiRestService IscsiRestService;

    /**
     * user token.
     */
    private String token;

    /**
     * @param url the path of iscsi rest service.
     * @param token user token
     */
    public  IscsiServiceImpl(String url, String token) {
        IscsiRestService = ProxyFactory.create(IscsiRestService.class, url,  ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StandardPortGroup> getAvailablePortGroupForIscsiAdapter(String isaUuid) {

    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }

    	String resultXML = IscsiRestService.getAvailablePortGroupForIscsiAdapter(token, isaUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class, StandardSwitch.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<StandardPortGroup>();
        }  else {
            return (List<StandardPortGroup>) result.getData();
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StandardPortGroup> getAvailablePortGroupForIscsiTarget(String isaUuid, String itUuid) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	if (itUuid == null || itUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi target cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.getAvailablePortGroupForIscsiTarget(token, isaUuid, itUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class, StandardSwitch.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<StandardPortGroup>();
        }  else {
            return (List<StandardPortGroup>) result.getData();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getDiscoveredIscsiTargetIqn(String isaUuid, String ip, Integer port, List<String> spgUuids) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	
    	String resultXML = IscsiRestService.getDiscoveredIscsiTargetIqn(token, isaUuid, ip, port, spgUuids);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, String.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<String>();
        }  else {
            return (List<String>) result.getData();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<IscsiTarget> getIscsiTargetOnIscsiAdapter(String isaUuid) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	
    	String resultXML = IscsiRestService.getIscsiTargetOnIscsiAdapter(token, isaUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, IscsiTarget.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<IscsiTarget>();
        }  else {
            return (List<IscsiTarget>) result.getData();
        }
    }
	
    @SuppressWarnings("unchecked")
    @Override
    public List<StandardPortGroup> getPortGroupOnIscsiAdapter(String isaUuid) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.getPortGroupOnIscsiAdapter(token, isaUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<StandardPortGroup>();
        }  else {
            return (List<StandardPortGroup>) result.getData();
        }
    	
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<StandardPortGroup> getPortGroupOnIscsiTarget(String itUuid) {
    	  	
    	if (itUuid == null || itUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi target cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.getPortGroupOnIscsiTarget(token, itUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<StandardPortGroup>();
        }  else {
            return (List<StandardPortGroup>) result.getData();
        }
    }
    
    
    @SuppressWarnings({"rawtypes" })
    @Override
    public TaskIntermediateResult addIscsiTarget(IscsiTarget target) {
    	
    	String resultXML = IscsiRestService.addIscsiTarget(token, FormatUtil.toJson(target));
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    
    @SuppressWarnings({  "rawtypes" })
    @Override
    public TaskIntermediateResult addPortGroupToIscsiAdapter(String isaUuid, String pgUuid) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	if (pgUuid == null || pgUuid.isEmpty()) {
            throw new RemoteException("The uuid of port group cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.addPortGroupToIscsiAdapter(token, isaUuid, pgUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public TaskIntermediateResult  addPortGroupToIscsiTarget(String itUuid, String pgUuid) {
    	
    	if (itUuid == null || itUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	if (pgUuid == null || pgUuid.isEmpty()) {
            throw new RemoteException("The uuid of port group cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.addPortGroupToIscsiTarget(token, itUuid, pgUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public TaskIntermediateResult removeIscsiTarget(String itUuid) {
    	
    	if (itUuid == null || itUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi target cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.removeIscsiTarget(token, itUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    
    
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public TaskIntermediateResult removePortGroupOnIscsiAdapter(String isaUuid, String pgUuid) {
    	
    	if (isaUuid == null || isaUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	if (pgUuid == null || pgUuid.isEmpty()) {
            throw new RemoteException("The uuid of port group cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.removePortGroupOnIscsiAdapter(token, isaUuid, pgUuid);
    	System.out.println(resultXML);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public TaskIntermediateResult  removePortGroupOnIscsiTarget(String itUuid, String pgUuid) {
    	
    	if (itUuid == null || itUuid.isEmpty()) {
            throw new RemoteException("The uuid of iscsi adapter cannot be null or empty.");
        }
    	
    	if (pgUuid == null || pgUuid.isEmpty()) {
            throw new RemoteException("The uuid of port group cannot be null or empty.");
        }
    	
    	String resultXML = IscsiRestService.removePortGroupOnIscsiTarget(token, itUuid, pgUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        } 	
    }
    


}