/** @file UserService.java
 *  @brief UserService
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package service
 */
package service;

import model.User;

public interface UserService {
	public abstract User validateUserCredential(String username, String password);

	public abstract boolean registerUser(User user);

	public abstract void addAvatar(User user);
}