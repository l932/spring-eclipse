package cn.com.cintel.common.util.mailtools;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮箱认证器
 * */

public class MTAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MTAuthenticator() {
	}

	public MTAuthenticator(String username, String password) {
		userName = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
