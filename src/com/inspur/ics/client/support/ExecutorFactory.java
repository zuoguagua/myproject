package com.inspur.ics.client.support;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

/**
 * ClientExecutor的工厂方法，用于RestEasy Client进行HTTPS访问时ProxyFactory.create()的第三个参数的设定
 *
 */
public class ExecutorFactory {
	/**
	 * 用于返回可用的ClientExecutor
	 * @return
	 */
	public static ClientExecutor getDefaultClientExecutor() {
		ClientExecutor executor = null;
		try {
			executor = new ExecutorFactory().getProxyFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executor;
	}

	/**
	 * 用于生成回一个ClientExecutor,用来进行HTTPS访问
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@SuppressWarnings("deprecation")
	private ClientExecutor getProxyFactory() throws NoSuchAlgorithmException,
			KeyManagementException {
		/**
		 * 初始化SSLContext
		 */
		SSLContext sslcontext;
		/**
		 * 这块指明使用的协议TLS
		 */
		sslcontext = SSLContext.getInstance("TLS");
		/**
		 * 设置SSLContext的信任机制
		 * @Parameter TrustManager[],指定信任管理器，也就是信任策略
		 */
		sslcontext.init(null, new TrustManager[] { new TrustAny() }, null);
		/**
		 * 生成SSLSocketFactory
		 */
		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslcontext);
		/*{
		    @Override
		    public Socket createSocket(HttpParams params) throws IOException {
		        // TODO Auto-generated method stub
		        Socket s = super.createSocket(params);
		        if (s instanceof SSLSocket) {
		            ((SSLSocket) s).setEnabledCipherSuites(
		                    new String[]{"SSL_RSA_WITH_RC4_128_MD5",
		                                 "SSL_RSA_WITH_RC4_128_SHA",
		                                 "TLS_DHE_RSA_WITH_AES_128_CBC_SHA"});
		        }
		        return s;
		    }
		};*/
		
		/**
		 * SSLSocketFactory设定域名匹配警告策略，此处是忽略域名匹配警告
		 */
		sslSocketFactory.setHostnameVerifier(new MyHostnameVerifier());
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		ClientConnectionManager conManager = httpClient.getConnectionManager();
		
		SchemeRegistry schemeRegistry = conManager.getSchemeRegistry();
		
		schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));
		
		ClientExecutor executor = new ApacheHttpClient4Executor(httpClient);
		return executor;
	}

	/**
	 * 实现自己的X509TrustManager信任管理类，用户信任所有的服务器证书
	 * 
	 */
	private class TrustAny implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	/**
	 * 忽略域名匹配问题
	 */
	private class MyHostnameVerifier implements X509HostnameVerifier {

		@Override
		public void verify(String arg0, SSLSocket arg1) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void verify(String arg0, X509Certificate arg1)
				throws SSLException {
			// TODO Auto-generated method stub

		}

		@Override
		public void verify(String arg0, String[] arg1, String[] arg2)
				throws SSLException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean verify(String hostname, SSLSession session) {
			// TODO Auto-generated method stub
			return true;
		}

	}

}
