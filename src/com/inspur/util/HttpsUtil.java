package com.inspur.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {
    /** 
     * �ƹ���֤ 
     *   
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     */  
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
        SSLContext sc = SSLContext.getInstance("SSLv3");  
      
        // ʵ��һ��X509TrustManager�ӿڣ������ƹ���֤�������޸�����ķ���  
        X509TrustManager trustManager = new X509TrustManager() {  
            @Override  
            public void checkClientTrusted(  
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
                    String paramString) throws CertificateException {  
            }  
      
            @Override  
            public void checkServerTrusted(  
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
                    String paramString) throws CertificateException {  
            }  
      
            @Override  
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                return null;  
            }  
        };  
      
        sc.init(null, new TrustManager[] { trustManager }, null);  
        return sc;  
    }  
    
    /** 
     * ģ������ 
     *  
     * @param url       ��Դ��ַ 
     * @param map   �����б� 
     * @param encoding  ���� 
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws IOException  
     * @throws ClientProtocolException  
     */  
    public static String send(String url, Map<String,String> map,String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
        String body = "";  
        //�����ƹ���֤�ķ�ʽ����https����  
        SSLContext sslcontext = createIgnoreVerifySSL();  
          
           // ����Э��http��https��Ӧ�Ĵ���socket���ӹ����Ķ���  
           Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
               .register("http", PlainConnectionSocketFactory.INSTANCE)  
               .register("https", new SSLConnectionSocketFactory(sslcontext))  
               .build();  
           PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
           HttpClients.custom().setConnectionManager(connManager);  
      
           //�����Զ����httpclient����  
        //CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
        CloseableHttpClient client = HttpClients.createDefault();  
          
        //����post��ʽ�������  
        HttpPost httpPost = new HttpPost(url);  
          
        //װ�����  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //���ò��������������  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
      
        System.out.println("�����ַ��"+url);  
        System.out.println("���������"+nvps.toString());  
          
        //����header��Ϣ  
        //ָ������ͷ��Content-type������User-Agent��  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //ִ��������������õ������ͬ��������  
        CloseableHttpResponse response = client.execute(httpPost);  
        //��ȡ���ʵ��  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //��ָ������ת�����ʵ��ΪString����  
            body = EntityUtils.toString(entity, encoding);  
        }  
        EntityUtils.consume(entity);  
        //�ͷ�����  
        response.close();  
           return body;  
    }  
    
    public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, HttpException {  
        String url = "https://10.166.15.160/api/";  
        String body = send(url, null, "utf-8");  
        System.out.println("������Ӧ�����");  
        System.out.println(body);  
        System.out.println("-----------------------------------");  
    }  
}
