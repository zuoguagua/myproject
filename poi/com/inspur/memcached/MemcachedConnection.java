package com.inspur.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class MemcachedConnection {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("10.166.15.153",11211));
		
		System.out.println("Connection to server sucessfully");
		
		System.out.println("Set to cache:"+mcc.set("aaa", 900,"memcached"));
		
		System.out.println("Get from cache :"+mcc.get("aaa"));
	}

}
