package com.inspur.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.inspur.util.SslUtils;

@SuppressWarnings("deprecation")
public class hostList<GetMethod> {
	public void sendGetRequestHostList() throws Exception{
		//String url = "https://10.166.15.160/api/hosts";
		
		String url = "http://10.166.15.160/api/hosts";
		
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Username", "admin@internal");
		headers.put("Password", "ovirt");
		
		
		
		HttpClient httpClient = new DefaultHttpClient();
		
		//SslUtils su = new SslUtils();
		//String new_url = su.ignoreSsl(url);
		HttpGet httpgets = new HttpGet(url);
		//httpgets.setHeader("Username", "admin@internal");
		//httpgets.setHeader("Password", "ovirt");
		HttpResponse response = httpClient.execute(httpgets);
		
		HttpEntity entity = response.getEntity();
		if(entity != null){
			InputStream instreams = entity.getContent();
			String str = converStreamToString(instreams);
			System.out.println("Do Something");
			System.out.println(str);
			httpgets.abort();
		}
		
	}

	private String converStreamToString(InputStream instreams) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		
		try{
			while ((line = reader.readLine()) != null){
				sb.append(line+"\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				instreams.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
