/** @file UserService.java
 *  @brief UserService
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 12/12/2018
 */

/** @brief package service
 */package service;

import model.User;

public interface UserService {
	public abstract User validateUserCredential(String username,	String password);
	public abstract boolean registerUser(User user);

}