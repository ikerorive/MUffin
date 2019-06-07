/** @file PassHash.java
 *  @brief PassHash
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package controller
 */
package controller;

import java.security.SecureRandom;

public class PassHash {
	/*
	 * ! \brief generador de salt para la contraseña
	 */
	public String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes.toString();
	}

	/*
	 * ! \brief Hashea la contraseña con el pass y salt. Usamos sha256
	 */
	public String getHashedPass(String password, String salt) {
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password + salt);
	}

}
