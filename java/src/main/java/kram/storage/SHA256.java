package kram.storage;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;


public class SHA256 {
	public static String getHash(String password) {
		String salt = new BigInteger(180, new SecureRandom()).toString(32);
		password = DigestUtils.sha3_256Hex(password);
		String passFront = (String) password.subSequence(0, 20);
		String passEnd = (String) password.subSequence(20, password.length());
		String newPass = passFront + salt + passEnd;
		return newPass;
	};
	
	public static String getHashWithouSalt(String password) {
		password = DigestUtils.sha3_256Hex(password);
		return password;
	}
}
