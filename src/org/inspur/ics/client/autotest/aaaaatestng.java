package org.inspur.ics.client.autotest;



import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.HostService;
import com.inspur.ics.pojo.Host;

public class aaaaatestng {
	
	private static HostService hostService;
	
	private static String url = "https://10.166.15.245";
	private static String username = "admin";
	private static String password = "admin@inspur";
	private static String userLocale = "zh";
	
	private String hostuuid = "f9b08943-613d-4fcb-944c-715a651ed82c";
	
	/**
	 * 
	 * 
	 *
	 */
	@BeforeClass
	@Test
	public void init(){
		ClientFactory factory = new ClientFactory(url, username, password, userLocale);
		
		hostService = factory.getHostService();
		System.out.println("Init for HostService.");
		
	}
		
	@Test
	public void getHostInfo(){
		
		Host host = hostService.getHostInfo(hostuuid);
		//String uuid = host.getUuid();
		//System.out.println(uuid);
		Assert.assertEquals(hostuuid, host.getUuid());
	}
	
	
}
