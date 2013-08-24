/*
 * Created on 2006-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.com.cintel.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author RubinRuler
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Md5PasswordEncoder extends BaseDigestPasswordEncoder implements PasswordEncoder {

	/**
	 * 
	 */
	public Md5PasswordEncoder() {
		super();
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);

		return pass1.equals(pass2);
	}

	@Override
	public String encodePassword(String rawPass, Object salt) {
		String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

		if (!getEncodeHashAsBase64()) {
			return DigestUtils.md5Hex(saltedPass);
		}

		byte[] encoded = Base64.encodeBase64(DigestUtils.md5(saltedPass));
		return new String(encoded);
	}
}
