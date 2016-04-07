package com.inspur.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class SslUtils {
	private static void trustAllHttpsCerticates() throws NoSuchAlgorithmException, KeyManagementException{
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
	
	static class miTM implements TrustManager,X509TrustManager{
		public boolean isServerTrusted(X509Certificate[] certs){
			return true;
		}
		
		public boolean isClientTrusted(X509Certificate[] certs,String authType){
			return true;
		}
		
		public void checkServerTrusted(X509Certificate[] certs,String authType) throws CertificateException{
			return ;
		}
		
		public void checkClientTrusted(X509Certificate[] certs,String authType)throws CertificateException{
			return ;
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static void ignoreSsl()throws Exception{
		HostnameVerifier hv = new HostnameVerifier(){
			public boolean verify(String urlHostName,SSLSession session){
				System.out.println("Warning:URL Host:"+urlHostName+" vs. "+session.getPeerHost());
				return true;
			}
		};
		trustAllHttpsCerticates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
}
