package kram.storage;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA256 {
	public static String getHash(String heslo) {
		return DigestUtils.sha256Hex(heslo);
	};
}
