/** @file UserDAOImpl.java
 *  @brief User DAO implementation
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 12/12/2018
 */

/** @brief package dao.impl
 */
package dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import controller.PassHash;
import dao.UserDAO;
import model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public boolean saveUser(User user) {
		int id = (Integer) hibernateTemplate.save(user);
		if (id > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserDetailsByUsernameAndPassword(String username, String password) {
		DetachedCriteria detachedCriteriaNoHash = DetachedCriteria.forClass(User.class);
		detachedCriteriaNoHash.add(Restrictions.eq("username", username));
		// detachedCriteria.add(Restrictions.eq("password", password));
		List<User> findByCriteriaNoHash = (List<User>) hibernateTemplate.findByCriteria(detachedCriteriaNoHash);
		if (findByCriteriaNoHash != null && findByCriteriaNoHash.size() > 0) {
			System.out.println("Username exists");
			String salt=findByCriteriaNoHash.get(0).getSalt();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
			detachedCriteria.add(Restrictions.eq("username", username));
			String sha256hex = new PassHash().getHashedPass(password, salt);
			System.out.println("SALTED PASS: "+sha256hex);
			detachedCriteria.add(Restrictions.eq("password", sha256hex));
			List<User> findByCriteria = (List<User>) hibernateTemplate.findByCriteria(detachedCriteria);
			if (findByCriteria != null && findByCriteria.size() > 0)
				return findByCriteria.get(0);
			else
				return null;

		} else {
			System.out.println("Username does not exist");
			return null;
		}

	}
}
