package cn.com.cintel.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PublicUtil {

	public PublicUtil() {
		super();
	}

	/**
	 * 将明文进行Base64编码
	 * 
	 * @author 刘鹏
	 * @param plaintext
	 *            明文
	 * @return Base64编码后的密文
	 */
	public static String encodeBase64(String plaintext) {
		if (plaintext == null) {
			return null;
		} else {
			return new BASE64Encoder().encode(plaintext.getBytes());
		}
	}

	/**
	 * 将Base64编码后的密文解码成明文
	 * 
	 * @author 刘鹏
	 * @param ciphertext
	 *            Base编码后的密文
	 * @return 解码后的明文
	 */
	public static String decodeBase64(String ciphertext) {
		if (ciphertext == null) {
			return null;
		} else {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(ciphertext);
				return new String(b);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 字符串内双引号替换为单引号，主要是为了解决XML中双引号返回页面时，JS解析出错的问题
	 * 
	 * @param oldString
	 * @return
	 */
	public static String doubleQuotationToSingle(String oldString) {
		return oldString.replace('\"', '\'');
	}

	/**
	 * @author 刘鹏 2011-05-31 08:31
	 * @param pwd
	 *            明文密码
	 * @return 密文密码
	 */
	public static String md5Pwd(String pwd) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String pswdEncode = md5.encodePassword(pwd, Constants.PASSWORD_KEY);
		return pswdEncode;
	}

	/**
	 * 得到传入的Calendar日期的周一
	 * 
	 * @author 刘鹏
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(Calendar c) {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		return df2.format(c.getTime());
	}

	/**
	 * 得到传入的Calendar日期的周日
	 * 
	 * @author 刘鹏
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(Calendar c) {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 7);
		return df2.format(c.getTime());
	}

	/**
	 * 得到传入的Calendar的月份的1号
	 * 
	 * @author 刘鹏
	 * @param Calendar
	 * @return yyyy-MM-dd
	 */
	public static String getMonthFirst(Calendar c) {
		c.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd ");
		return simpleFormate.format(c.getTime());
	}

	/**
	 * 得到传入的Calendar月份的最后一天
	 * 
	 * @author 刘鹏
	 * @param Calendar
	 * @return yyyy-MM-dd
	 */
	public static String getMonthLast(Calendar c) {
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd ");
		return simpleFormate.format(c.getTime());

	}

	/**
	 * 通过传入的Date，返回周几
	 * 
	 * @author 刘鹏
	 * @param date
	 * @return 星期X
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}

		return weekDays[w];
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @author 刘鹏 2011-08-02 10:36
	 */

	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @author 刘鹏 2011-08-02 10:37
	 */

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 
	 * @param url
	 *            http://IP:端口/方法名
	 * @param content
	 *            ?name=admin&pwd=111
	 * @param strcode
	 *            utf-8,gbk
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, Map mapcontent, String strcode) throws IOException {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码

		Iterator iter = mapcontent.entrySet().iterator();

		String getURL = "";
		while (iter.hasNext()) {
			Map.Entry entry = (Entry) iter.next();
			getURL = getURL + entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue(), strcode) + "&";
		}
		URL getUrl = new URL(url + "?" + getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), strcode));// 设置编码,否则中文乱码
		String lines;
		String re = "";
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
			re = lines;
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return re;
	}

	/**
	 * 
	 * @param url
	 *            http://IP:端口/方法名
	 * @param content
	 *            ?name=admin&pwd=111
	 * @param strcode
	 *            utf-8,gbk
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, Map mapcontent, String strcode) throws IOException {
		// Post请求的url，与get不同的是不需要带参数
		URL postUrl = new URL(url);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		// 设置是否向connection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
		// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);
		// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
		// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		// 进行编码
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
		// 要注意的是connection.getOutputStream会隐含的进行connect。
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
		String content = "";
		Iterator iter = mapcontent.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Entry) iter.next();
			content = entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue(), strcode) + "&";
		}
		// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
		out.writeBytes(content);
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), strcode));// 设置编码,否则中文乱码
		String line = "";
		String re = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			re = line;
		}
		reader.close();
		connection.disconnect();
		return re;
	}

}
