package com.bnade.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * htpp客户端程序，完成一些简单的http操作
 * 
 * @author liufeng0103
 *
 */
public class HttpClient {
	
	// 每次重新尝试中间等待时间，单位毫秒
	private static final long SLEEP_TIME_PER_TRY = 1000;
	// 当调用api失败时最多尝试的次数
	private static final int MAX_TRY_COUNT = 5;
	
	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);
	
	// http连接超时时间，单位是毫秒
	private int connectionTimeout = 5000;
	// http读取超时时间，单位是毫秒
	private int readTimeout = 5000;
	private int try_count = 0;
	private boolean isGzipSupported = false;	
	
	public void setGzipSupported(boolean isGzipSupported) {
		this.isGzipSupported = isGzipSupported;
	}

	/**
	 * 根据url参数，通过http的get方法获取url的内容
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String get(String url) throws IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection con = null;
		InputStream is = null;
		BufferedReader reader = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			con.setConnectTimeout(connectionTimeout);
			con.setReadTimeout(readTimeout);
			if (isGzipSupported) {
				con.setRequestProperty("Accept-Encoding", "gzip,deflate");
				logger.debug("接受{}文件", con.getContentEncoding());				
				is = new GZIPInputStream(con.getInputStream());
			} else {
				is = con.getInputStream();
			}
			reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (is != null) {
				is.close();
			}
			if (con != null) {
				con.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * 当通过GET方法获取内容失败时，将尝试多次获取内容，如果超过最大尝试次数还是失败将抛出异常
	 * 调用该方法前最好先调用resetTryCount()方法来重置尝试次数的初始值
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String reliableGet(String url) throws IOException {
		String content = null;	
		try {
			logger.debug("reliableGet获取{}内容", url);
			content = get(url);			
		} catch (Exception e) {
			try {
				if (try_count++ < MAX_TRY_COUNT) {
					if (try_count == 1) {
						logger.info("reliableGet获取{}内容失败", url);
					}
					logger.info("reliableGet失败，第{}次尝试，等待{}毫秒", try_count, SLEEP_TIME_PER_TRY);
					Thread.sleep(SLEEP_TIME_PER_TRY);					
					content = reliableGet(url);
				} else {
					throw e;
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}	
		return content;
	}
	
	public String reliableGet(String url, boolean isTrustedSSL) throws IOException {
		if (isTrustedSSL) {
			try {
				trustAllHttpsCertificates();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			} 
			HostnameVerifier hv = new HostnameVerifier() {  
		        public boolean verify(String urlHostName, SSLSession session) {  
		            System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
		                               + session.getPeerHost());  
		            return true;  
		        }  
		    };  
		    HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}
		return reliableGet(url);
	}

	public void resetTryCount() {
		try_count = 0;
	} 
	
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  
  
    static class miTM implements javax.net.ssl.TrustManager,  
            javax.net.ssl.X509TrustManager {  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
        public boolean isServerTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public boolean isClientTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
  
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
    }  
    
    public static void main(String[] args) throws IOException {
		HttpClient client = new HttpClient();		
		String url = "http://auction-api-cn.worldofwarcraft.com/auction-data/aaaff45cf244c3cdfecc06db745dcc30/auctions.json";
		long start = 0;
		String s = null;
		start = System.currentTimeMillis();
		s = client.get(url);
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(s.length());
		client.setGzipSupported(true);
		start = System.currentTimeMillis();
		s = client.get(url);
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(s.length());
		start = System.currentTimeMillis();
		s = client.reliableGet(url);
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(s.length());
	}
}
