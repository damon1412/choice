package com.choice.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.choice.model.RZRQ;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebRequestUtil {
	private static Logger log = LoggerFactory.getLogger(WebRequestUtil.class);
	public String getRequest(){
		StringBuffer result = new StringBuffer();
		Properties pps = new Properties();
		try {
			pps.load(getClass().getResourceAsStream("http_rzrq_total.properties"));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
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
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
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
			response1 = httpclient.execute(httpGet);
			log.info(response1.getStatusLine().toString());
			HttpEntity entity1 = response1.getEntity();
			br = new BufferedReader(new InputStreamReader(entity1.getContent()));
			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			EntityUtils.consume(entity1);
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			try {
				response1.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		return result.toString();
	
	}
	public List<RZRQ> formatRZRQ(String webString){
		List<RZRQ> rzrqList = new ArrayList<RZRQ>();
		String webStringJson = StringUtils.substring(webString, 19,webString.length()-1);
		log.debug(webStringJson);
		ObjectMapper mapper = new ObjectMapper();  
		try {
			JsonNode json = mapper.readTree(webStringJson);
			JsonNode result = json.get("result");
			Iterator<JsonNode> itor = result.elements();
			while(itor.hasNext()){
				JsonNode element = itor.next();
				String strOpDate = element.get("opDate").asText();
				String strRqchl = element.get("rqchl").asText();
				String strRqmcl = element.get("rqmcl").asText();
				String strRqyl = element.get("rqyl").asText();
				String strRqylje = element.get("rqylje").asText();
				String strRw = element.get("rw").asText();
				String strRzche = element.get("rzche").asText();
				String strRzmre = element.get("rzmre").asText();
				String strRzrqjyzl = element.get("rzrqjyzl").asText();
				String strRzye = element.get("rzye").asText();
				String strSecurityAbbr = element.get("securityAbbr").asText();
				String strStockCode = element.get("stockCode").asText();
				
				RZRQ rzrq = new RZRQ();
				rzrq.setOpDate(DateUtils.parseDate(strOpDate.substring(0, 4)+"/"+strOpDate.substring(4, 6)+"/"+strOpDate.substring(6, 8),new String[]{"yyyy/MM/dd"}));
				if(null == strRqchl
						|| strRqchl.equalsIgnoreCase("null")
						|| strRqchl.equalsIgnoreCase("")){
					rzrq.setRqchl(0.0);
				}else{
					rzrq.setRqchl(Double.parseDouble(strRqchl));
				}
				
				if(null == strRqmcl
						|| strRqmcl.equalsIgnoreCase("null")
						|| strRqmcl.equalsIgnoreCase("")){
					rzrq.setRqmcl(0.0);
				}else{
					rzrq.setRqmcl(Double.parseDouble(strRqmcl));
				}
				
				if(null == strRqyl
						|| strRqyl.equalsIgnoreCase("null")
						|| strRqyl.equalsIgnoreCase("")){
					rzrq.setRqyl(0.0);
				}else{
					rzrq.setRqyl(Double.parseDouble(strRqyl));
				}
				
				if(null == strRqylje
						|| strRqylje.equalsIgnoreCase("null")
						|| strRqylje.equalsIgnoreCase("")){
					rzrq.setRqylje(0.0);
				}else{
					rzrq.setRqylje(Double.parseDouble(strRqylje));
				}
				
				if(null == strRw
						|| strRw.equalsIgnoreCase("null")
						|| strRw.equalsIgnoreCase("")){
					rzrq.setRw(0.0);
				}else{
					rzrq.setRw(Double.parseDouble(strRw));
				}
				
				if(null == strRzche
						|| strRzche.equalsIgnoreCase("null")
						|| strRzche.equalsIgnoreCase("")){
					rzrq.setRzche(0.0);
				}else{
					rzrq.setRzche(Double.parseDouble(strRzche));
				}
				
				if(null == strRzmre
						|| strRzmre.equalsIgnoreCase("null")
						|| strRzmre.equalsIgnoreCase("")){
					rzrq.setRzmre(0.0);
				}else{
					rzrq.setRzmre(Double.parseDouble(strRzmre));
				}
				
				if(null == strRzrqjyzl
						|| strRzrqjyzl.equalsIgnoreCase("null")
						|| strRzrqjyzl.equalsIgnoreCase("")){
					rzrq.setRzrqjyzl(0.0);
				}else{
					rzrq.setRzrqjyzl(Double.parseDouble(strRzrqjyzl));
				}
				
				if(null == strRzye
						|| strRzye.equalsIgnoreCase("null")
						|| strRzye.equalsIgnoreCase("")){
					rzrq.setRzye(0.0);
				}else{
					rzrq.setRzye(Double.parseDouble(strRzye));
				}
				
				if(null == strSecurityAbbr
						|| strSecurityAbbr.equalsIgnoreCase("null")
						|| strSecurityAbbr.equalsIgnoreCase("")){
					rzrq.setSecurityAbbr("");
				}else{
					rzrq.setSecurityAbbr(strSecurityAbbr);
				}
				
				if(null == strStockCode
						|| strStockCode.equalsIgnoreCase("null")
						|| strStockCode.equalsIgnoreCase("")){
					rzrq.setStockCode("");
				}else{
					rzrq.setStockCode(strStockCode);
				}
				
				rzrqList.add(rzrq);
			}
			
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return rzrqList;
	}
}
