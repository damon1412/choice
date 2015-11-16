package com.choice.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.choice.model.Rzrq;
import com.choice.model.RzrqRequestHand;
import com.choice.service.IRzrqRequestHandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Scope("prototype")
@Service
public class WebRequestUtil {
	private static Logger log = LoggerFactory.getLogger(WebRequestUtil.class);
	@Resource(name="RzrqRequestHandServiceImpl")
	private IRzrqRequestHandService rzrqRequestHandService;
	/**
	 * get request body not formated
	 * use method formatRZRQ(String webString) to format
	 * @param startDay 
	 * @param endDay 
	 */
	public String getRequest(Date startDay,Date endDay){
		log.debug("get web data between date: "+DateUtils.formatDate(startDay, "yyyy-MM-dd")+" to "+DateUtils.formatDate(endDay, "yyyy-MM-dd"));
		StringBuffer result = new StringBuffer();
		String strStartDay = FormatUtil.formatDateToString(startDay);
		String strEndDay = FormatUtil.formatDateToString(endDay);
		RzrqRequestHand rzrqRequestHand = rzrqRequestHandService.selectByPrimaryKey(1);
		String scheme = rzrqRequestHand.getScheme();
		String host = rzrqRequestHand.getHost();
		String path = rzrqRequestHand.getPath();
		String Referer = rzrqRequestHand.getReferer();
		String Accept = rzrqRequestHand.getAccept();
		String Content_Type = rzrqRequestHand.getContentType();
		String Connection = rzrqRequestHand.getConnection();
		String User_Agent = rzrqRequestHand.getUserAgent();
		String Accept_Encoding = rzrqRequestHand.getAcceptEncoding();
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme(scheme)
					.setHost(host)
					.setPath(path)
					.setParameter("pageHelp.pageSize", rzrqRequestHand.getPagesize().toString())
					.setParameter("tabType", rzrqRequestHand.getTabtype())
					.setParameter("isPagination", rzrqRequestHand.getIspagination())
					.setParameter("jsonCallBack", rzrqRequestHand.getJsoncallback())
					.setParameter("_", rzrqRequestHand.getUnderLineValue())
					.setParameter("beginDate", strStartDay)
					.setParameter("endDate", strEndDay)
					.build();
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
	public List<Rzrq> formatRZRQ(String webString){
		List<Rzrq> rzrqList = new ArrayList<Rzrq>();
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
				
				Rzrq rzrq = new Rzrq();
				rzrq.setOpdate(DateUtils.parseDate(strOpDate.substring(0, 4)+"/"+strOpDate.substring(4, 6)+"/"+strOpDate.substring(6, 8),new String[]{"yyyy/MM/dd"}));
				if(null == strRqchl
						|| strRqchl.equalsIgnoreCase("null")
						|| strRqchl.equalsIgnoreCase("")){
					rzrq.setRqchl(new BigDecimal("0.0"));
				}else{
					rzrq.setRqchl(new BigDecimal(strRqchl));
				}
				
				if(null == strRqmcl
						|| strRqmcl.equalsIgnoreCase("null")
						|| strRqmcl.equalsIgnoreCase("")){
					rzrq.setRqmcl(new BigDecimal("0.0"));
				}else{
					rzrq.setRqmcl(new BigDecimal(strRqmcl));
				}
				
				if(null == strRqyl
						|| strRqyl.equalsIgnoreCase("null")
						|| strRqyl.equalsIgnoreCase("")){
					rzrq.setRqyl(new BigDecimal("0.0"));
				}else{
					rzrq.setRqyl(new BigDecimal(strRqyl));
				}
				
				if(null == strRqylje
						|| strRqylje.equalsIgnoreCase("null")
						|| strRqylje.equalsIgnoreCase("")){
					rzrq.setRqylje(new BigDecimal("0.0"));
				}else{
					rzrq.setRqylje(new BigDecimal(strRqylje));
				}
				
				if(null == strRw
						|| strRw.equalsIgnoreCase("null")
						|| strRw.equalsIgnoreCase("")){
					rzrq.setRw(new BigDecimal("0.0"));
				}else{
					rzrq.setRw(new BigDecimal(strRw));
				}
				
				if(null == strRzche
						|| strRzche.equalsIgnoreCase("null")
						|| strRzche.equalsIgnoreCase("")){
					rzrq.setRzche(new BigDecimal("0.0"));
				}else{
					rzrq.setRzche(new BigDecimal(strRzche));
				}
				
				if(null == strRzmre
						|| strRzmre.equalsIgnoreCase("null")
						|| strRzmre.equalsIgnoreCase("")){
					rzrq.setRzmre(new BigDecimal("0.0"));
				}else{
					rzrq.setRzmre(new BigDecimal(strRzmre));
				}
				
				if(null == strRzrqjyzl
						|| strRzrqjyzl.equalsIgnoreCase("null")
						|| strRzrqjyzl.equalsIgnoreCase("")){
					rzrq.setRzrqjyzl(new BigDecimal("0.0"));
				}else{
					rzrq.setRzrqjyzl(new BigDecimal(strRzrqjyzl));
				}
				
				if(null == strRzye
						|| strRzye.equalsIgnoreCase("null")
						|| strRzye.equalsIgnoreCase("")){
					rzrq.setRzye(new BigDecimal("0.0"));
				}else{
					rzrq.setRzye(new BigDecimal(strRzye));
				}
				
				if(null == strSecurityAbbr
						|| strSecurityAbbr.equalsIgnoreCase("null")
						|| strSecurityAbbr.equalsIgnoreCase("")){
					rzrq.setSecurityabbr("-1");
				}else{
					rzrq.setSecurityabbr(strSecurityAbbr);
				}
				
				if(null == strStockCode
						|| strStockCode.equalsIgnoreCase("null")
						|| strStockCode.equalsIgnoreCase("")){
					rzrq.setStockcode("1");
				}else{
					rzrq.setStockcode(strStockCode);
				}
				log.debug("get rzrq data: "+ToStringBuilder.reflectionToString(rzrq));
				rzrqList.add(rzrq);
			}
			log.debug("get rzrq data size: "+rzrqList.size());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return rzrqList;
	}
}
