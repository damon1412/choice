package com.choice.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
	private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	public String getRequest(){
		StringBuffer result = new StringBuffer();
		Properties pps = new Properties();
		try {
			pps.load(getClass().getResourceAsStream("http_rzrq_total.properties"));
		} catch (FileNotFoundException e3) {
			logger.error(e3.getMessage());
		} catch (IOException e3) {
			logger.error(e3.getMessage());
		}
		String nameValuePair = pps.getProperty("nameValuePair");
		String scheme = pps.getProperty("scheme");
		String host = pps.getProperty("host");
		String path = pps.getProperty("path");
		String Referer = pps.getProperty("Referer");
		String Accept = pps.getProperty("Accept");
		String Content_Type = pps.getProperty("Content_Type");
		String Connection = pps.getProperty("Connection");
		String User_Agent = pps.getProperty("User_Agent");
		String Accept_Encoding = pps.getProperty("Accept_Encoding");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String[] pairs = nameValuePair.split("&");
		for (String pair : pairs) {
			String[] nameValue = pair.split("=");
			String name = nameValue[0];
			String value = "";
			if (nameValue.length == 2) {
				value = nameValue[1];
			}
			NameValuePair p = new BasicNameValuePair(name, value);
			nvps.add(p);
		}
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme(scheme).setHost(host).setPath(path).addParameters(nvps).build();
		} catch (URISyntaxException e2) {
			logger.error(e2.getMessage());
		}
		CloseableHttpResponse response1 = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(uri);
		System.out.println(httpGet.getURI());
		httpGet.setHeader("Host", host);
		httpGet.setHeader("Referer", Referer);
		httpGet.addHeader("Accept", Accept);
		httpGet.setHeader("Content-Type", Content_Type);
		httpGet.setHeader("Connection", Connection);
		httpGet.setHeader("User-Agent", User_Agent);
		httpGet.setHeader("Accept-Encoding", Accept_Encoding);
		BufferedReader br = null;
		try {
			logger.info("url: "+httpGet.getURI().toString());
			response1 = httpclient.execute(httpGet);
			logger.info(response1.getStatusLine().toString());
			HttpEntity entity1 = response1.getEntity();
			br = new BufferedReader(new InputStreamReader(entity1.getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			EntityUtils.consume(entity1);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
			try {
				response1.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result.toString();
	
	}
}
