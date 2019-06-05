package dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import controller.PassHash;
import dao.EventoDAO;
import dao.QuestionsDAO;
import model.Evento;
import model.Questions;
import model.User;

@Repository("questionsDAO")
public class QuestionsDAOImpl implements QuestionsDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}




	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getAllQuestions() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questions.class);
		// detachedCriteria.add(Restrictions.eq("name", "sebesa"));
		List<Questions> findByCriteria = (List<Questions>) hibernateTemplate.findByCriteria(detachedCriteria);
		if (findByCriteria != null && findByCriteria.size() > 0)
			return findByCriteria;
		else
			return null;
	}

}
