package org.inspur.ics.client.autotest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;

public class ClusterServiceTest {
	private static ClusterService clusterService;
	
	@BeforeClass
	@Test()
	@Parameters({"url","username","password","userLocale"})
	public static void init(String url, String username, String password, String userLocale){
		ClientFactory factory = new ClientFactory(url,username,password,userLocale);
		clusterService = factory.getClusterService();
		System.out.println("Init for ClusterService.");
	}
	
	@Test(enabled = false)
	public void addHeartbeatDevice(){
		
	}
	
	@Test(enabled = false)
	public void addHost(){
		
	}
	
	@Test
	public void createCluster(){
		
	}
	
	@Test
	public void getAllCluster(){
		
	}
	
	@Test
	public void getAllHeartbeatDevice(){
		
	}
}
