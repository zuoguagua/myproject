package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.DrsService;
import com.inspur.ics.client.rest.DrsRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.AlarmEvent;
import com.inspur.ics.pojo.ClusterGroup;
import com.inspur.ics.pojo.DrsRule;
import com.inspur.ics.pojo.DrsStrategy;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.VM;


/**
 * @author kangzhx
 */
public class DrsServiceImpl implements DrsService {

    /**
     * token.用户认证令牌.
     */
    private String token = "";
    /**
     * DrsRestService.rest代理端.
     */
    private DrsRestService client;

    /**
     * 构造函数.
     * @param token
     *            用户认证令牌
     * @param url
     *            远程url地址
     */
    public DrsServiceImpl(String url, String token) {
        client = ProxyFactory.create(DrsRestService.class, url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the client
     */
    public DrsRestService getClient() {
        return client;
    }

    /**
     * @param client
     *            the client to set
     */
    public void setClient(DrsRestService client) {
        this.client = client;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isDrsEnabled(String clusterUUID) {
        String str = client.getStatus(token, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, AlarmEvent.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return false;
        } else {
            return (Boolean) ((List) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TaskIntermediateResult configDrs(String clusterUUID,
            boolean drsEnabled) {
        String resultStr = client.configDrs(token, clusterUUID, drsEnabled);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public DrsStrategy getDrsStrategy(String clusterUUID) {
        String str = client.getDrsStrategy(token, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, DrsStrategy.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return (DrsStrategy) ((List) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TaskIntermediateResult configStrategy(String clusterUUID,
            DrsStrategy drsStrategy) {
        String str = FormatUtil.toJson(drsStrategy);
        String resultStr = client.configStrategy(token, clusterUUID, str);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TaskIntermediateResult createDrsGroup(ClusterGroup clusterGroup,
            String clusterUUID) {
        String str = FormatUtil.toJson(clusterGroup);
        String resultStr = client.createDrsGroup(token, str, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TaskIntermediateResult modifyDrsGroup(ClusterGroup clusterGroup) {
        String str = FormatUtil.toJson(clusterGroup);
        String resultStr = client.modifyDrsGroup(token, str);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TaskIntermediateResult deleteDrsGroup(int id) {
        String resultStr = client.deleteDrsGroup(token, id);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ClusterGroup> getDrsGroupInCluster(String clusterUUID) {
        String str = client.getDrsGroupInCluster(token, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, ClusterGroup.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new ArrayList<ClusterGroup>();
        } else {
            return ((List<ClusterGroup>) result.getData());
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ClusterGroup getDrsGroupInfo(int id) {
        String str = client.getDrsGroupInfo(token, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, ClusterGroup.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return (ClusterGroup) ((List) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VM> getAddableVms(String clusterUUID, int id) {
        String str = client.getAddableVms(token, clusterUUID, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, VM.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new ArrayList<VM>();
        } else {
            return ((List<VM>) result.getData());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Host> getAddableHosts(String clusterUUID, int id) {
        String str = client.getAddableHosts(token, clusterUUID, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, Host.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new ArrayList<Host>();
        } else {
            return ((List<Host>) result.getData());
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public TaskIntermediateResult createDrsRule(DrsRule drsRule,
            String clusterUUID) {
        String str = FormatUtil.toJson(drsRule);
        String resultStr = client.createDrsRule(token, str, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public TaskIntermediateResult modifyDrsRule(DrsRule drsRule) {
        String str = FormatUtil.toJson(drsRule);
        String resultStr = client.modifyDrsRule(token, str);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public TaskIntermediateResult deleteDrsRule(int id) {
        String resultStr = client.deleteDrsRule(token, id);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RuntimeException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new TaskIntermediateResult<String>("0");
        } else {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DrsRule> getDrsRuleInCluster(String clusterUUID) {
        String str = client.getDrsRuleInCluster(token, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, DrsRule.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new ArrayList<DrsRule>();
        } else {
            return ((List<DrsRule>) result.getData());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DrsRule getDrsRuleInfo(int id) {
        String str = client.getDrsRuleInfo(token, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, DrsRule.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return null;
        } else {
            return ((List<DrsRule>) result.getData()).get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VM> getAddableVmsForRule(String clusterUUID, int id) {
        String str = client.getAddableVmsForRule(token, clusterUUID, id);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, VM.class });
        if (result.getError() != null) { // 出现错误，抛出异常
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) { // 无数据返回空列表
            return new ArrayList<VM>();
        } else {
            return (List<VM>) result.getData();
        }
    }

}
