/**
 * Created by Sebastian Walz
 * 26.04.2013 17:37:39
 */
package de.hska.awp.palaver2.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util
{
	private static final String		ENCODING 	= "UTF-8";
	private static final String		ALGORITHM 	= "MD5";
	
	/**
	 * Konverter fuer DB
	 * @param bool
	 * @return
	 */
	public static Integer convertBoolean(Boolean bool)
	{
		return (bool) ? 1 : 0;
	}
	
	/**
	 * Passwordverschluesselung
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getMD5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		byte[] bytesOfMessage = password.getBytes(ENCODING);
		MessageDigest md = MessageDigest.getInstance(ALGORITHM);
		return md.digest(bytesOfMessage);
	}
}
