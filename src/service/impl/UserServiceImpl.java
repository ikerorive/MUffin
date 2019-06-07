/** @file UserServiceImpl.java
 *  @brief UserService implementation
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package service.impl
 */
package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDAO;
import model.User;
import service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	/*
	 * ! \brief Llama al dao y dependiendo del exito del registro del usuario
	 * devuelve true o false
	 */
	@Override
	public boolean registerUser(User user) {
		boolean isRegister = false;
		boolean saveUser = getUserDAO().saveUser(user);
		if (saveUser)
			isRegister = true;
		return isRegister;
	}

	/*
	 * ! \brief Llama al dao y devuelve el usuario al que corresponden el username y
	 * la contraseña
	 */
	@Override
	public User validateUserCredential(String username, String password) {
		User user = getUserDAO().getUserDetailsByUsernameAndPassword(username, password);
		return user;
	}

	/*
	 * ! \brief Llama al dao mandandole el usuario a cambiar
	 */
	@Override
	public void addAvatar(User user) {
		// TODO Auto-generated method stub
		getUserDAO().addAvatar(user);
	}
}
