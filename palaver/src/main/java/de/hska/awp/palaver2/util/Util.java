/**
 * Created by Sebastian Walz
 * 26.04.2013 17:37:39
 */
package de.hska.awp.palaver2.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Util {
	private static final String ENCODING = "UTF-8";
	private static final String ALGORITHM = "SHA-1";

	/**
	 * Konverter fuer DB
	 * 
	 * @param bool
	 * @return
	 */
	public static Integer convertBoolean(Boolean bool) {
		return (bool) ? 1 : 0;
	}

	/**
	 * Passwordverschluesselung
	 * 
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@Deprecated
	public static byte[] getMD5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytesOfMessage = password.getBytes(ENCODING);
		MessageDigest md = MessageDigest.getInstance(ALGORITHM);
		return md.digest(bytesOfMessage);
	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String sha1 = "";
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(password.getBytes("UTF-8"));
		sha1 = byteToHex(crypt.digest());

		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
