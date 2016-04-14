package org.inspur.ics.client.autotest;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.StorageDeviceService;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.DrsStrategy;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.O2cbConfig;

public class ClusterServiceTest {
	private static ClusterService clusterService;
	private static HostService hostService;
	private static StorageDeviceService storageDeviceService;

	
	private String hostUuid1;
	private String bdUuid1;
	@BeforeClass
	@Test()
	@Parameters({"url","username","password","userLocale"})
	public static void init(String url, String username, String password, String userLocale){
		ClientFactory factory = new ClientFactory(url,username,password,userLocale);
		clusterService = factory.getClusterService();
		hostService = factory.getHostService();
		storageDeviceService = factory.getStorageDeviceService();
		System.out.println("Init for ClusterService.");
	}
	
	@Test(enabled = false)
	public void addHeartbeatDevice(){
		
	}
	
	@Parameters({"host1ip"})
	@Test
	public void addHost(String host1ip){
		List<Host> is = hostService.getAllHostList();
		for(Host host:is){
			if(host.getIp().equals(host1ip)){
				this.hostUuid1 = host.getUuid();
				System.out.println(hostUuid1);
			}
		}
		
		List<BlockDevice> blkDevices1 = storageDeviceService.getAvailableBlockDeviceForHeartbeatDevice(hostUuid1);
		for(BlockDevice bd:blkDevices1){
			System.out.println(bd.getUuid());
		}
		if(blkDevices1.size()==0){
			//bdUuid1 = blkDevices1.get(0).getUuid();
			System.out.println("11");
			//System.out.println(bdUuid1);
			System.out.println("22");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Test(enabled = false)
	public void createCluster(){
		Cluster cluster = new Cluster();
		cluster.setDisplayName("sdk_cluster");
		
		O2cbConfig o2cbConfig = new O2cbConfig();
		o2cbConfig.setHeartbeatThreshold(61);
		o2cbConfig.setIdleTimeout(30000);
		o2cbConfig.setKeepaliveDelay(2000);
		o2cbConfig.setReconnectDelay(2000);
		cluster.setO2cbConfig(o2cbConfig);
		
		DrsStrategy drsStrategy = new DrsStrategy();
		drsStrategy.setCpuDiffThreshold(10);
		drsStrategy.setCpuThreshold(10);
		//drsStrategy.setNicDiffThreshold(10);
		//drsStrategy.setNicThreshold(4);
		drsStrategy.setDelBadCpuRecordNum(10);
		drsStrategy.setVmMigrationCount(2);
		
		cluster.setDrsStrategy(drsStrategy);
		
		TaskIntermediateResult taskResult = clusterService.createCluster(cluster, null);
		System.out.println(taskResult);
		
	}
	
	@Test
	public void getAllCluster(){
		List<Cluster> clusterList = clusterService.getAllCluster();
		for(int i = 0;i < clusterList.size();i++){
			System.out.println(clusterList.get(i).getDisplayName());
			System.out.println(clusterList.get(i).getName());
		}
		
	}
	
	@Test
	public void getAllHeartbeatDevice(){
		
	}
}
