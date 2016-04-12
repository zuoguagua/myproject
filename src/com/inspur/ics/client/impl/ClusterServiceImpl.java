package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.rest.ClusterRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.HeartbeatDevice;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.O2cbConfig;
import com.inspur.ics.pojo.OcfsDataStore;

/**
 * @author zhangjun
 */
public class ClusterServiceImpl implements ClusterService {

    /** 集群模块的REST接口. */
    private ClusterRestService clusterRestService;

    /** 用户认证令牌. */
    private String token;

    /**
     * @param url the path of cluster rest service.
     * @param token user token.
     */
    public ClusterServiceImpl(String url, String token) {
        clusterRestService = ProxyFactory.create(ClusterRestService.class, url,
        		ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult addHeartbeatDevice(String clusterUuid,
			String hostUuid, List<HeartbeatDevice> hbDevices) {

    	List<String> hbJasons = new ArrayList<String>();

    	for (HeartbeatDevice hb : hbDevices) {
    		System.out.println(FormatUtil.toJson(hb));
    		hbJasons.add(FormatUtil.toJson(hb));
    	}

    	System.out.println(hbJasons.toArray(new String[0])[0]);
        String resultXML = clusterRestService
        		.addHeartbeatDevice(token, clusterUuid, hostUuid, hbJasons.toArray(new String[0]));
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult addHost(String clusterUuid,
			String[] addHostUuids) {

    	String resultXML = clusterRestService.addHost(token, clusterUuid, addHostUuids);
    	Result result = (Result) FormatUtil.fromXML(resultXML,
    			new Class[]{Result.class, TaskIntermediateResult.class});

    	if (result.getError() != null) {
    		throw new RemoteException(result.getError().getMessage());
    	} else {
    		return (TaskIntermediateResult) ((List) result.getData()).get(0);
    	}
    }

    @SuppressWarnings("rawtypes")
    @Override
    public TaskIntermediateResult createCluster(Cluster cluster, String hostUuid) {

        String clusterJson = FormatUtil.toJson(cluster);
        String resultXML = clusterRestService.createCluster(token, clusterJson, hostUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Cluster> getAllCluster() {

        String resultXML = clusterRestService.getAllCluster(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, Cluster.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<Cluster>();
        }  else {
            return (List<Cluster>) result.getData();
        }
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<HeartbeatDevice> getAllHeartbeatDevice(String clusterUuid) {

    	String resultXML = clusterRestService.getAllHeartbeatDevice(token, clusterUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, HeartbeatDevice.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<HeartbeatDevice>();
        }  else {
            return (List<HeartbeatDevice>) result.getData();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Host> getAvailableHosts() {

        String resultXML = clusterRestService.getAvailableHosts(token);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, Host.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<Host>();
        }  else {
            return (List<Host>) result.getData();
        }
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<OcfsDataStore> getAvailableOcfsDataStoreForHeartbeatDevice(
			String clusterUuid) {

        String resultXML = clusterRestService
        		.getAvailableOcfsDataStoreForHeartbeatDevice(token, clusterUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, OcfsDataStore.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return new ArrayList<OcfsDataStore>();
        }  else {
            return (List<OcfsDataStore>) result.getData();
        }
	}

	@SuppressWarnings("unchecked")
    @Override
    public Cluster getClusterInfo(String clusterUuid) {

        String resultXML = clusterRestService.getClusterInfo(token, clusterUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, Cluster.class, NfsDataStore.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<Cluster>) result.getData()).get(0);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public O2cbConfig getO2cbConfig(String clusterUuid) {

        String resultXML = clusterRestService.getO2cbConfig(token, clusterUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, O2cbConfig.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }  else if (result.getData() == null) {
            return null;
        }  else {
            return ((List<O2cbConfig>) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult modifyClusterName(String clusterUuid, String newName) {

        String resultXML = clusterRestService.modifyClusterName(token, clusterUuid, newName);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult removeCluster(String clusterUuid) {

        String resultXML = clusterRestService.removeCluster(token, clusterUuid);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult removeHeartbeatDevice(String clusterUuid,
			String[] hbUuids) {

        String resultXML = clusterRestService.removeHeartbeatDevice(token, clusterUuid, hbUuids);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult removeHost(String clusterUuid,
			String[] removeHostUuids) {

        String resultXML = clusterRestService.removeHost(token, clusterUuid, removeHostUuids);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TaskIntermediateResult updateO2cbConfig(String clusterUuid,
			O2cbConfig newConfig) {

		String newConfigJson = FormatUtil.toJson(newConfig);
        String resultXML = clusterRestService.updateO2cbConfig(token, clusterUuid, newConfigJson);
        Result result = (Result) FormatUtil.fromXML(resultXML,
        		new Class[]{Result.class, TaskIntermediateResult.class});

        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
	}

}
