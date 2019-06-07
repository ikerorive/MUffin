/** @file UserDAO.java
 *  @brief UserDAO
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package dao
 */
package dao;

import java.sql.Blob;

import model.User;

public interface UserDAO {
	public abstract boolean saveUser(User user);
	public User getUserDetailsByUsernameAndPassword(String username,String password);
	public abstract void addAvatar(User user);
}
