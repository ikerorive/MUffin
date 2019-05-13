package controller;

import java.security.SecureRandom;

public class PassHash {

	public String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes.toString();
	}
	
	public String getHashedPass(String password, String salt) {
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password+salt); 
	}
	

}
