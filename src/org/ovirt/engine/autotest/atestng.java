package org.ovirt.engine.autotest;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.ovirt.engine.sdk.Api;



import org.ovirt.engine.sdk.decorators.VM;
import org.ovirt.engine.sdk.decorators.VMs;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.exceptions.UnsecuredConnectionAttemptError;
import org.testng.annotations.Test;

public class atestng {

	
	@Test
	public void testGetTags() throws ClientProtocolException, ServerException, UnsecuredConnectionAttemptError, IOException{
		String url = "https://10.166.15.160:443/api";
		String username = "admin@internal";
		String password = "ovirt";
		@SuppressWarnings("resource")
		Api aaa = new Api(url,username,password,true);
		VMs vms = aaa.getVMs();
		List<VM> vmlist = vms.list();
		for(int i =0 ;i < vmlist.size();i++){
			System.out.println(vmlist.get(i));
			System.out.println(vmlist.get(i).getName());
			System.out.println(vmlist.get(i).getHost());
		}
	}
}
