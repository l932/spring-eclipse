package cn.com.cintel.common.util.mailtools;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送器
 */
public class MailSender {
	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MTAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MTAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(getAddressStr(mailInfo.getToAddress()));
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 创建邮件的抄送者地址，并设置到邮件消息中
			if (mailInfo.getCcAddress() != null) {
				Address cc = new InternetAddress(getAddressStr(mailInfo.getCcAddress()));
				mailMessage.setRecipient(Message.RecipientType.CC, cc);
			}
			// 创建邮件的秘密抄送者地址，并设置到邮件消息中
			if (mailInfo.getBccAddress() != null) {
				Address bcc = new InternetAddress(getAddressStr(mailInfo.getBccAddress()));
				mailMessage.setRecipient(Message.RecipientType.BCC, bcc);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpContent = new MimeBodyPart();
			// 向Multipart添加正文
			mbpContent.setContent(mailInfo.getContent(), "text/html;charset=gb2312");
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);

			// 向MimeMessage添加附件
			Enumeration<String> efile = mailInfo.getFile().elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				String filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(fds.getName());
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);
			}
			mailInfo.getFile().removeAllElements();
			// 向Multipart添加MimeMessage
			mailMessage.setContent(mp);

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MTAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MTAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(getAddressStr(mailInfo.getToAddress()));
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 创建邮件的抄送者地址，并设置到邮件消息中
			if (mailInfo.getCcAddress() != null) {
				Address cc = new InternetAddress(getAddressStr(mailInfo.getCcAddress()));
				mailMessage.setRecipient(Message.RecipientType.CC, cc);
			}
			// 创建邮件的秘密抄送者地址，并设置到邮件消息中
			if (mailInfo.getBccAddress() != null) {
				Address bcc = new InternetAddress(getAddressStr(mailInfo.getBccAddress()));
				mailMessage.setRecipient(Message.RecipientType.BCC, bcc);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);

			// 向MimeMessage添加附件
			Enumeration<String> efile = mailInfo.getFile().elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				String filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(fds.getName());
				// 向MimeMessage添加（Multipart代表附件）
				mainPart.addBodyPart(mbpFile);
			}
			mailInfo.getFile().removeAllElements();

			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 把地址组转换成地址字符串
	 * 
	 * @param mailInfo
	 *            地址信息
	 */
	private static String getAddressStr(String[] addr) {
		StringBuilder sbd = new StringBuilder();
		if (null != addr) {
			int listSize = addr.length;
			for (int i = 0; i < listSize; i++) {
				if (0 != i) {
					sbd.append(";");
				}
				sbd.append("<").append(addr[i]).append(">");
			}
		}
		return sbd.toString();

	}
}