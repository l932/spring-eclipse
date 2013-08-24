package cn.com.cintel.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;

public class HttpSend {
	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doGet(String url, String queryString, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5);
		HttpMethod method = new GetMethod(url);
		try {
			if (queryString != null && !queryString.equals("")) {
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (URIException e) {
			response.append("timeout");
		} catch (IOException e) {
			response.append("timeout");
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5);
		HttpMethod method = new PostMethod(url);
		// 设置Http Post数据
		if (params != null) {
			HttpMethodParams p = new HttpMethodParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				p.setParameter(entry.getKey(), entry.getValue());
			}
			method.setParams(p);
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (IOException e) {
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public static void main(String[] args) {
		// String reHttp =
		// HttpSend.doGet("http://172.16.3.27/lrdata/datapage/FZRealData.aspx?pid=001",
		// null, "UTF-8", true);
		Map<String, String> params = new HashMap<String, String>();
		// params.put("id", "大家Aa1");
		// params.put("dtime", "2012-06-27 12:23:12");
		// params.put("msg",
		// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><id>足球</id><id>Abc123，！,4我</id></root>");
		// String reHttp =
		// HttpSend.doPost("http://127.0.0.1:8080/czjserver/test/test.do",
		// params, "UTF-8", true);
		String smsurl = "http://127.0.0.1:8080/spring/manager/loginj.do";
		params.put("msg", "{\"loginname\":\"admin\",\"pwd\":\"111\",\"username\":\"北京萨丁3sd\"}");
		String reHttp = HttpSend.doGet("http://127.0.0.1:8080/spring/manager/loginj.do","?msg={\"loginname\":\"admin\",\"pwd\":\"111\",\"username\":\"北京萨丁3sd\"}", "UTF-8", true);
		// String reHttp = HttpSend.doGet(smsurl,
		// "?name=dfhmt新&pwd=8200344&dst=13552178367&msg=Test1我", "ISO-8859-1",
		// true);

		System.out.println(reHttp.trim());
	}

}
