package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.VNetService;
import com.inspur.ics.client.rest.VNetRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.PortGroupServiceType;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.DVPortGroup;
import com.inspur.ics.pojo.DVSwitch;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.PNic;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StandardSwitch;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VirtualSwitch;




/**
 * 
 * @author jingshsh
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class VNetServiceImpl implements VNetService{
	
	/**
	 * token值
	 */
	private String token = "";
	/**
	 * VNetVSwitchRestService.
	 */
	private VNetRestService client;

	/**
	 * Constructor.
	 * 
	 * @param url
	 *            request path
	 */
	public VNetServiceImpl(String url, String token) {
		client = ProxyFactory.create(VNetRestService.class, url, ExecutorFactory.getDefaultClientExecutor());
		this.token = token;
	}
	
    /**
     * 列出标准交换机列表
     * @return 标准交换机列表
     */
	public List<StandardSwitch> listStandardSwitchFromDB(){
		String str = client.listStandardSwitchFromDB(token);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<StandardSwitch>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<StandardSwitch>();
		}
	}
    
    /**
     * 列出指定交换机上的端口组
     * @param swUuid 交换机的UUID
     * @return 端口组列表
     */
    public List<StandardPortGroup> listStandardSwitchPortgroupFromDB(String swUuid){
		String str = client.listStandardSwitchPortgroupFromDB(token,swUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardPortGroup.class });
		if (result.getData() != null) {
			return (List<StandardPortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<StandardPortGroup>();
		}
    }

    /**
     * @Description 列出端口组（按名称）上的虚拟机列表
     * @param portgroup
     *            指定的端口组UUID
     * @return 虚拟机列表
     */
    public List<VM> listStandardSwitchPortgroupVmFromDB(String portgroup){
		String str = client.listStandardSwitchPortgroupVmFromDB(token,portgroup);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, VM.class });
		if (result.getData() != null) {
			return (List<VM>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<VM>();
		}
    }

    /**
     * 列出未使用的网卡
     * list unused pnics.
     * @return 未使用的网卡列表
     * @return pnic list.
     */
    public List<PNic> listUnusedPNicFromDB(){
		String str = client.listUnusedPNicFromDB(token);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, PNic.class });
		if (result.getData() != null) {
			return (List<PNic>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PNic>();
		}
    }

    /**
     * 列出指定主机上的端口组
     * list host's port group.
     * @param hostUuid 主机的UUID
     * @param hostUuid
     *            host uuid
     * @return 端口组列表
     */
    public List<PortGroup> listPortGroupByHostUuid(String hostUuid){
		String str = client.listPortGroupByHostUuid(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<PortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PortGroup>();
		}
    }
    
    /**
     * 增加@20150610 by wangyanjia
     * 根据端口组获取交换机
     * list host's port group.
     * @param pgUuid 端口组UUID
     * @return 交换机列表
     */
    public VirtualSwitch getVswitchByPortGroupUuid(String pgUuid){
        String str = client.getVswitchByPortGroupUuid(token, pgUuid);
        Result result = (Result) FormatUtil.fromXML(str, new Class[] {
                Result.class, StandardSwitch.class });
        if (result.getData() != null) {
            return ((List<VirtualSwitch>) result.getData()).get(0);
        } else if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return new VirtualSwitch();
        }
    }

    /**
     * 列出主机上的虚拟交换机
     * @param hostUuid
     *            主机的UUID
     * @return 虚拟交换机列表
     */
    public List<VirtualSwitch> listSwitchByHostUuid(String hostUuid){
		String str = client.listSwitchByHostUuid(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<VirtualSwitch>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<VirtualSwitch>();
		}
    }

    /**
     * 列出主机的未使用的网卡
     * @param hostUuid
     *            主机的UUID
     * @return 网卡列表
     */
    public List<PNic> listHostUnusedPNic(String hostUuid){
		String str = client.listHostUnusedPNic(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, PNic.class });
		if (result.getData() != null) {
			return (List<PNic>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PNic>();
		}
    }

    /**
     * 列出主机的所有网卡
     * @param hostUuid
     *            主机的UUID
     * @return 网卡列表
     */
    public List<PNic> listHostPNics(String hostUuid){
		String str = client.listHostPNics(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<PNic>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PNic>();
		}
    }

    /**
     * get standard port groups that contain ip by host uuid.
     * @param hostUuid
     *            主机的UUID
     * @return 标准端口组列表
     */
    public List<StandardPortGroup> listStandardPortGroupWithIPByHostUuid(
            String hostUuid){
		String str = client.listStandardPortGroupWithIPByHostUuid(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<StandardPortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<StandardPortGroup>();
		}
    }

    /**
     * 获取标准交换机的信息
     * @param swUuid
     *            交换机UUID
     * @return 交换机类
     */
    public StandardSwitch getStandardSwitchInfo(String swUuid){
		String str = client.getStandardSwitchInfo(token,swUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return ((List<StandardSwitch>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new StandardSwitch();
		}
    }

    /**
     * 获取所有的标准端口组
     * @return 标准端口组列表
     */
    public List<StandardPortGroup> showStandardNetworksOverview(){
		String str = client.showStandardNetworksOverview(token);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class});
		if (result.getData() != null) {
			return (List<StandardPortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<StandardPortGroup>();
		}
    }

    /**
     * 创建标准交换机
     * @param token
     *            token值
     * @param sw
     *            标准交换机
     * @param destHostUUID
     *            目的主机UUID
     * @return 任务的中间执行结果.
     */
    public TaskIntermediateResult createStandardSwitch(StandardSwitch sw, String destHostUUID){
		String swJson = FormatUtil.toJson(sw);
		String str = client.createStandardSwitch(token,swJson,destHostUUID);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
	}

    /**
     * 给标准交换机添加网卡
     * @param token
     *            token值
     * @param sw
     *            标准交换机
     * @return 任务的中间执行结果.
     */

    public TaskIntermediateResult modifyStandardSwitch(StandardSwitch sw){
    	String swJson = FormatUtil.toJson(sw);
		String str = client.modifyStandardSwitch(token,swJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 创建标准交换机端口组
     * @param token
     *            token值
     * @param swUuid
     *            标准交换机UUID
     * @param portgroup
     *            标准端口组
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult createStandardSwitchPortGroup(String swUuid,
            StandardPortGroup portgroup){
    	if(portgroup.getType()==null){
    		portgroup.setType(PortGroupServiceType.COMPORTGROUP);
    	}
    	String portgroupJson = FormatUtil.toJson(portgroup);
		String str = client.createStandardSwitchPortGroup(token,swUuid,portgroupJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 删除标准交换机
     * @param token
     *            token值
     * @param swUuid
     *            标准交换机UUID
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult deleteStandardSwitch(String swUuid){
		String str = client.deleteStandardSwitch(token,swUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 删除标准交换机端口组
     * @param token
     *            token值
     * @param portGroupUuid
     *            标准端口组UUID
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult deleteStandardSwitchPortGroup(String portGroupUuid){
		String str = client.deleteStandardSwitchPortGroup(token,portGroupUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 列出所有的分布式交换机
     * @return 分布式交换机列表
     */
    public List<DVSwitch> getAllDVSwitch(){
		String str = client.getAllDVSwitch(token);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, DVSwitch.class });
		if (result.getData() != null) {
			return (List<DVSwitch>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<DVSwitch>();
		}
    }

    /**
     * 列出分布式交换机上的主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    public List<Host> listDVSwitchHostFromDB(String dvswUuid){
		String str = client.listDVSwitchHostFromDB(token,dvswUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, Host.class });
		if (result.getData() != null) {
			return (List<Host>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<Host>();
		}
    }

    /**
     * 列出指定分布式端口上的虚拟机列表
     * @param dvPortGroupUuid
     *            分布式端口组UUID
     * @return 虚拟机
     */
    public List<VM> getVmsOnPortGroup(String dvPortGroupUuid){
		String str = client.getVmsOnPortGroup(token,dvPortGroupUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, VM.class });
		if (result.getData() != null) {
			return (List<VM>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<VM>();
		}
    }

    /**
     * 列出分布式交换机上的所有主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    public List<Host> getHostsOnDVSwitch(String dvswUuid){
		String str = client.getHostsOnDVSwitch(token,dvswUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, Host.class });
		if (result.getData() != null) {
			return (List<Host>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<Host>();
		}
    }

    /**
     * 获取分布式交换机拓扑结构
     * @param dvSwUuid
     *            分布式交换机UUID
     * @return 分布式交换机对象
     */
    public DVSwitch getDVSwitchInfo(String dvSwUuid){
		String str = client.getDVSwitchInfo(token,dvSwUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, DVSwitch.class });
		if (result.getData() != null) {
			return ((List<DVSwitch>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new DVSwitch();
		}
    }

    /**
     * 列举系统中所有的分布式端口组
     * @return 分布式端口组列表
     */
    public List<DVPortGroup> showDistributeNetworkOverview(){
		String str = client.showDistributeNetworkOverview(token);
		System.out.print(str);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, DVPortGroup.class });
		if (result.getData() != null) {
			return (List<DVPortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<DVPortGroup>();
		}
    }


    /**
     * 获取能添加到分布式交换的主机
     * @param dvswUuid
     *             分布式交换机UUID
     * @return 主机列表
     */
    public List<Host> getAvailableHostForDVSwitch(String dvswUuid){
		String str = client.getAvailableHostForDVSwitch(token,dvswUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, Host.class });
		if (result.getData() != null) {
			return (List<Host>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<Host>();
		}
    }

    /**
     * 列出分布式交换机上的主机
     * @param dvswUuid
     *            分布式交换机UUID
     * @return 主机列表
     */
    public List<Host> getHostInDVSwitch(String dvswUuid){
		String str = client.getHostInDVSwitch(token,dvswUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, Host.class });
		if (result.getData() != null) {
			return (List<Host>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<Host>();
		}
    }

    /**
     * 获取在指定主机上被交换机使用的网卡
     * @param hostUuid
     *            主机UUID
     * @param swUuid
     *            交换机UUID
     * @return 网卡列表
     */
    public List<PNic> getPNicUsedBySwitch(String hostUuid, String swUuid){
		String str = client.getPNicUsedBySwitch(token, hostUuid, swUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return (List<PNic>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PNic>();
		}
    }

    /**
     * 创建分布式虚拟交换机
     * @param token
     *            token值
     * @param clusterUuid
     *            集群UUID
     * @param dvsw
     *            分布式虚拟交换机
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult createDVSwitch(String clusterUuid,
            DVSwitch dvsw){
    	String dvswJson = FormatUtil.toJson(dvsw);
		String str = client.createDVSwitch(token, clusterUuid, dvswJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }


    /**
     * 删除分布式虚拟交换机
     * @param token
     *            token值
     * @param dvswUuid
     *            分布式虚拟交换机UUID
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult deleteDVSwitch(String dvswUuid){
		String str = client.deleteDVSwitch(token,dvswUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 配置分布式虚拟交换机：添加或删除主机
     * @param token
     *            token值
     * @param dvsw
     *            分布式交换机
     * @param flag
     *            true:添加主机
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult configDVSwitch(DVSwitch dvsw,
            boolean flag){
		for(DVPortGroup p : dvsw.getPgroups()) {
			p.setVswitch(null);
		}
    	String dvswJson = FormatUtil.toJson(dvsw);
		String str = client.configDVSwitch(token,dvswJson,flag);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 配置分布式交换机中指定主机的物理网卡与该交换机关系
     * @param token
     *            token值
     * @param dvswUuid
     *            分布式交换机UUID
     * @param host
     * 			  包含指定网卡的主机
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult configNicOnDVSwitch(String dvswUuid,
            Host host){
    	String hostJson = FormatUtil.toJson(host);
		String str = client.configNicOnDVSwitch(token,dvswUuid,hostJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 创建分布式端口组
     * @param token
     *            token值
     * @param dvswUuid
     *            分布式虚拟交换机UUID
     * @param portGroup
     *            端口组
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult createDVPortGroup(String dvswUuid, DVPortGroup portGroup){
    	if(portGroup.getType()==null){
    		portGroup.setType(PortGroupServiceType.COMPORTGROUP);
    	}
    	String portGroupJson = FormatUtil.toJson(portGroup);
		String str = client.createDVPortGroup(token,dvswUuid,portGroupJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 删除分布式端口组
     * @param token
     *            token
     * @param dvpgUuid
     *            分布式虚拟交换机UUID
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult deleteDVPortGroup(String dvpgUuid){
		String str = client.deleteDVPortGroup(token,dvpgUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }

    /**
     * 获取所有的端口组
     * @param targetType
     *            target type
     * @param targetUuid
     *            target uuid
     * @return group list
     */
    public List<PortGroup> getPortGroupList(TaskTargetType targetType,
            String targetUuid){
		String str = client.getPortGroupList(token,targetType.name(),targetUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, PortGroup.class });
		if (result.getData() != null) {
			return (List<PortGroup>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<PortGroup>();
		}
    }

    /**
     * 获取分布式交换机列表
     * @param targetType
     *            target type
     * @param targetUuid
     *            target uuid
     * @return 分布式交换机列表
     */
    public List<DVSwitch> getDVSwitchList(TaskTargetType targetType, String targetUuid){
		String str = client.getDVSwitchList(token,targetType.name(),targetUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, DVSwitch.class });
		if (result.getData() != null) {
			return (List<DVSwitch>) result.getData();
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new ArrayList<DVSwitch>();
		}
    }

    /**
     * 获取主机上的管理端口组
     * @param hostUuid
     *            主机UUID
     * @return 标准端口组
     */
    public StandardPortGroup getManagementPortgroupOnHost(String hostUuid){
		String str = client.getManagementPortgroupOnHost(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, StandardSwitch.class });
		if (result.getData() != null) {
			return ((List<StandardPortGroup>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new StandardPortGroup();
		}
    }
    
    /**
     * 扫描物理适配器
     * @param hostUuid
     *            主机UUID
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult scanEthernetAdapters(String hostUuid){
		String str = client.scanEthernetAdapters(token,hostUuid);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }
    
    /**
     * 配置端口组
     * @param portGroup
     *            端口组
     * @return 任务的中间执行结果
     */
    public TaskIntermediateResult configPortgroup(PortGroup portGroup){
		String portGroupJson = FormatUtil.toJson(portGroup);
		String str = client.configPortgroup(token,portGroupJson);
		Result result = (Result) FormatUtil.fromXML(str, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getData() != null) {
			return ((List<TaskIntermediateResult>) result.getData()).get(0);
		} else if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return new TaskIntermediateResult<String>("0");
		}
    }
}
