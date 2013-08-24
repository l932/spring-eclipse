package cn.com.cintel.common.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class HttpTest {

	public void testLoc() {
		HttpRequester request = new HttpRequester();
		Map<String, String> params = new HashMap<String, String>();
		params.put("carids", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root command=\"rtlocation\"><carid>3</carid><carid>6</carid></root>");
		try {
			System.out.println(request.sendPost("http://192.168.2.245:8864/carclientservice/loc/realtimelocation.do", params).getContent());
			;
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	public void testSetTime() {
		HttpRequester request = new HttpRequester();
		Map<String, String> params = new HashMap<String, String>();
		params.put("carids",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root command=\"timing\"><set time=\"300\" item=\"5\"><carid>11</carid><carid>22</carid></root>");
		try {
			System.out.println(request.sendPost("http://127.0.0.1:8080/carclientservice/set/timing.do", params).getContent());
			;
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	public void testTrace() {
		HttpRequester request = new HttpRequester();
		Map<String, String> params = new HashMap<String, String>();
		params.put("carid", "999");
		try {
			System.out.println(request.sendPost("http://127.0.0.1:8080/carclientservice/loc/trace.do", params).getContent());
			;
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	public void testTraceRecap() {
		HttpRequester request = new HttpRequester();
		Map<String, String> params = new HashMap<String, String>();
		params.put("carid", "33333333333333333331");
		params.put("stime", "2012-07-23 11:37:59");
		params.put("etime", "2012-07-23 20:38:03");
		try {
			System.out.println(request.sendPost("http://192.168.2.245:8865/carclientservice/loc/tracerecap.do", params).getContent());
			;
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	public void testPoi() {
		HttpRequester request = new HttpRequester();
		Map<String, String> params = new HashMap<String, String>();
		params.put("managerid", "1000000020");
		params.put("pageno", "1");
		try {
			System.out.println(request.sendPost("http://192.168.2.245:8865/carclientservice/poi/poi.do", params).getContent());
			;
		} catch (IOException e) {
			System.err.println("IOException");
		}

	}

	public void testAlarmMSG() {
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		Map<String, String> params = new HashMap<String, String>();
		params.put("licensePlate", "京A829N3");
		params.put("alarmType", "超速报警1");
		params.put("intime", "2012-07-25 20:12:32");
		try {
			System.out.println(request.sendPost("http://192.168.2.245:8865/clw/alarmmsg/addalarmmsg.do", params).getContent());
			// System.out.println(request.sendPost("http://127.0.0.1:8080/clw/alarmmsg/findalarmmsg.do",
			// params).getContent());
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}
	
	public void testJson(){
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("UTF-8");
		
		try {
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", "{\"loginname\":\"admin\",\"pwd\":\"111\",\"username\":\"北京萨丁3sd\"}");
			System.out.println(request.sendGet("http://127.0.0.1:8080/spring/manager/loginj.do",params).getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		HttpTest t = new HttpTest();
		t.testJson();
	}

	public void test() {
		try {
			HttpRequester request = new HttpRequester();
			request.setDefaultContentEncoding("UTF-8");
			// 设置超时时间，以便捕获超时异常
			request.setTimeOut(10);
			new HashMap<String, String>();
			// params.put("managerid", "2");
			// params.put("pageno", "1");
			// params.put("title", "标题1");
			// params.put(
			// "poiinfo",
			// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><managerid>2</managerid><title>标题3</title><remark>备注3</remark><latitude type=\"1\">44.44444</latitude><longitude type=\"1\">124.55555</longitude></root>");
			// HttpRespons hr =
			// request.sendPost("http://127.0.0.1:8080/czjserver/poi/addpoi.do",
			// params);
			// params.put("msg",
			// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root command=\"rtlocation\"><carid>11111</carid><carid>22222</carid></root>");
			HttpRespons hr = request.sendPost2SCF("http://192.168.2.245:3721/busyservice/realtimelocation.do",
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root command=\"rtlocation\"><carid>11111</carid><carid>22222</carid></root>");

			// System.out.println(hr.getUrlString());
			// System.out.println(hr.getProtocol());
			// System.out.println(hr.getHost());
			// System.out.println(hr.getPort());
			// System.out.println(hr.getContentEncoding());
			// System.out.println(hr.getMethod());

			// Document doc = DocumentHelper.parseText(hr.getContent());
			// Element root = doc.getRootElement();
			// List sylist = root.selectNodes("/response/ans");//
			// 获得devarea节点下的属性值（num）方法
			// Iterator sylistit = sylist.iterator();// 遍历devarea节点下的所有属性
			// String dnum = "";// 定义属性变量
			//
			// /*
			// * 获得devarea底下的num属性来判断是否有测点区域的选择
			// */
			// while (sylistit.hasNext()) {
			// Element sylistelmt = (Element) sylistit.next();// 把每个devarea的属性
			// // 存储成一个Element对象
			// System.out.println(sylistelmt.getText());
			// }

			// 获取响应的内容，一般应该是XML
			System.out.println(hr.getContent());

		} catch (SocketTimeoutException e) {
			// 超时异常必须处理，以便呈现，追踪
			System.out.println("超时");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
