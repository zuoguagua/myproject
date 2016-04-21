package com.inspur.ics.client;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.inspur.ics.client.impl.AlarmServiceImpl;
import com.inspur.ics.client.impl.ClusterServiceImpl;
import com.inspur.ics.client.impl.DataStoreServiceImpl;
import com.inspur.ics.client.impl.DrsServiceImpl;
import com.inspur.ics.client.impl.HistoryServiceImpl;
import com.inspur.ics.client.impl.HostServiceImpl;
import com.inspur.ics.client.impl.IscsiServiceImpl;
import com.inspur.ics.client.impl.NfsServiceImpl;
import com.inspur.ics.client.impl.ScheduleServiceImpl;
import com.inspur.ics.client.impl.StorageDeviceServiceImpl;
import com.inspur.ics.client.impl.SystemConfigServiceImpl;
import com.inspur.ics.client.impl.SystemEventServiceImpl;
import com.inspur.ics.client.impl.SystemLicenseServiceImpl;
import com.inspur.ics.client.impl.SystemSecurityServiceImpl;
import com.inspur.ics.client.impl.SystemTopologyServiceImpl;
import com.inspur.ics.client.impl.TaskServiceImpl;
import com.inspur.ics.client.impl.ToolsServiceImpl;
import com.inspur.ics.client.impl.VClusterServiceImpl;
import com.inspur.ics.client.impl.VMServiceImpl;
import com.inspur.ics.client.impl.VNetServiceImpl;
import com.inspur.ics.client.impl.VcTemplateServiceImpl;
import com.inspur.ics.client.impl.VmTemplateServiceImpl;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.pojo.License;

/**
 * ELlen的客户端工厂.
 *  参照下面的语句，创建对InCloud Sphere的远程连接,然后获取具体的服务对象后调用服务对象的接口。
 *  <pre>
 *  ClientFactory factory = new ClientFactory(url, "admin", "admin@inspur", "zh");
    VClusterService vclusterService = factory.getVClusterService();
    TaskIntermediateResult taskResult = vclusterService.createVCluster(
            "vcv01", "43fc5811-c93d-4df1-bee0-7cef158dc7d9");
 *  </pre>
 * @author lilx
 */
public class ClientFactory {

    /**
     * 请求路径.
     */
    private String url;
    /**
     * 用户名.
     */
    private String username;
    /**
     * 密码.
     */
    private String password;
    /**
     * 用户令牌.
     */
    private String token;
    /**
     * 构造函数.
     * @param url
     *            请求路径
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param userLocale
     *            本地化（该系统目前仅支持中文和英文两种语言，其中中文参数值为"zh"，英文参数值为"en"）
     */
    public ClientFactory(String url, String username, String password, String userLocale) {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
        this.url = url;
        this.username = username;
        this.password = password;
        this.token = getSystemSecurityService().userLogin(username, password, userLocale);
        //check license
        if(getSystemLicenseService().getLicense() == null || getSystemLicenseService().getLicense().size()==0){
        	throw new RemoteException("there is no license");
        }
        boolean validlicense = false;
        for(License license : getSystemLicenseService().getLicense()){
        	if(!license.isOutDate()){
        		validlicense = true;
        		break;
        	}
        }
        if (!validlicense) {
            throw new RemoteException("license is out of date");
        }
    }

    /**
     * 获取系统安全服务对象实例.
     * @return 返回SystemSecurityService对象
     */
    public SystemSecurityService getSystemSecurityService() {
        return new SystemSecurityServiceImpl(url, token);
    }

    /**
     * 获取系统许可证书服务对象实例.
     * @return 返回SystemLicenseService对象
     */
    public SystemLicenseService getSystemLicenseService() {
        return new SystemLicenseServiceImpl(url, token);
    }

    /**
     * 获取系统配置服务对象实例.
     * @return 返回SystemConfigService对象
     */
    public SystemConfigService getSystemConfigService() {
        return new SystemConfigServiceImpl(url, token);
    }

    /**
     * 获取系统事件管理服务对象实例.
     * @return 返回SystemEventService对象
     */
    public SystemEventService getSystemEventService() {
        return new SystemEventServiceImpl(url, token);
    }

    /**
     * 获取拓扑服务对象实例.
     * @return 返回SystemTopologyService对象
     */
    public SystemTopologyService getSystemTopologyService() {
        return new SystemTopologyServiceImpl(url, token);
    }

    /**
     * 获取查看任务相关信息对象实例.
     * @return 返回TaskService对象
     */
    public TaskService getTaskService() {
        return new TaskServiceImpl(url, token);
    }

    /**
     * 获取集群服务对象实例.
     * @return 返回ClusterService对象
     */
    public ClusterService getClusterService() {
        return new ClusterServiceImpl(url, token);
    }

    /**
     * 获取存储服务对象实例.
     * @return 返回DataStoreService对象
     */
    public DataStoreService getDataStoreService() {
        return new DataStoreServiceImpl(url, token);
    }

    /**
     * 获取Drs服务对象实例.
     * @return 返回DrsService对象
     */
    public DrsService getDrsService() {
        return new DrsServiceImpl(url, token);
    }

    /**
     * 获取历史服务对象实例.
     * @return 返回HistoryService对象
     */
    public HistoryService getHistoryService() {
        return new HistoryServiceImpl(url, token);
    }

    /**
     * 获取主机服务对象实例.
     * @return 返回HostService对象
     */
    public HostService getHostService() {
        return new HostServiceImpl(url, token);
    }

    /**
     * 获取iSCSI服务对象实例.
     * @return 返回IscsiService对象
     */
    public IscsiService getIscsiService() {
    	return new IscsiServiceImpl(url, token);
    }

    /**
     * 获取NFS 服务管理对象实例.
     * @return 返回NfsService对象
     */
    public NfsService getNfsService() {
        return new NfsServiceImpl(url, token);
    }

    /**
     * 获取密码信息.
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取SDK版本号.
     * @return 返回SDK版本号的值
     */
    public String getSdkVersion() {
        return "1.0";
    }

    /**
     * 获取存储设备服务对象实例.
     * @return 返回StorageDeviceService对象
     */
    public StorageDeviceService getStorageDeviceService() {
    	return new StorageDeviceServiceImpl(url, token);
    }

    /**
     * 获取系统版本号.
     * @return 返回系统版本号的值
     */
    public String getSystemVersion() {
        return "3.0";
    }
    /**
     * 获取用户令牌.
     * @return 用户令牌
     */
    public String getToken() {
        return token;
    }
    /**
     * 获取Tools服务对象实例.
     * @return 返回ToolsService对象
     */
    public ToolsService getToolsService() {
         return new ToolsServiceImpl(url, token);
    }
    /**
     * 获取请求路径.
     * @return 请求路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取用户名.
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取虚拟集群服务对象实例.
     * @return 返回VClusterService对象
     */
    public VClusterService getVClusterService() {
        return new VClusterServiceImpl(url, token);
    }
    /**
     * 获取虚拟集群模板服务对象实例.
     * @return 返回VCTemplateService对象
     */
    public VCTemplateService getVCTemplateService() {
         return new VcTemplateServiceImpl(url, token);
    }

    /**
     * 获取调度服务对象实例.
     * @return 返回ScheduleService对象
     */
    public ScheduleService getScheduleService() {
        return new ScheduleServiceImpl(url, token);
   }
    /**
     * 获取虚拟机服务对象实例.
     * @return 返回VMService对象
     */
    public VMService getVMService() {
         return new VMServiceImpl(url, token);
    }

    /**
     * 获取虚拟机模板服务对象实例.
     * @return 返回VMTemplateService对象
     */
    public VMTemplateService getVMTemplateService() {
         return new VmTemplateServiceImpl(url, token);
    }

    /**
     * 获取虚拟网卡服务对象实例.
     * @return 返回VNetService对象
     */
    public VNetService getVNetService() {
         return new VNetServiceImpl(url, token);
    }

    /**
     * 设置密码password的值.
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置用户令牌token的值.
     * @param token 用户令牌.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 设置请求路径url的值.
     * @param url
     *           请求路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置用户名username的值.
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

	/**
	 * 获取告警服务对象实例.
	 * @return 返回AlarmService对象
	 */
	public AlarmService getAlarmService() {
	   return new AlarmServiceImpl(url, token);
	}

}
