package cn.com.cintel.common.util.mailtools;

import java.util.Vector;

public class MailTest {

	public static void main(String[] args) {

		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.cintel.com.cn");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("peng.liu@cintel.com.cn");// 用哪个邮箱发送
		mailInfo.setPassword("xxxx");// 邮箱密码
		mailInfo.setFromAddress("peng.liu@cintel.com.cn");// 发件人
		mailInfo.setToAddress(new String[] { "peng.liu@cintel.com.cn" });// 收件人地址集
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("设置邮箱内容");

		// 添加附件
		Vector<String> file = new Vector<String>();
		file.add("d:/catalina.sh");

		mailInfo.setFile(file);
		// 这个类主要来发送邮件
		// MailSender.sendTextMail(mailInfo);//发送文体格式
		// 发送html格式
		if (MailSender.sendHtmlMail(mailInfo)) {
			System.out.println("发送成功");
		} else {
			System.out.println("发送失败");
		}
	}
}
