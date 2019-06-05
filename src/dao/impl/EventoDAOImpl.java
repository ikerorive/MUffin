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
import model.Evento;
import model.User;

@Repository("eventoDAO")
public class EventoDAOImpl implements EventoDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public boolean saveEvento(Evento evento) {
		int id = (Integer) hibernateTemplate.save(evento);
		if (id > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> getAllEventos() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Evento.class);
		// detachedCriteria.add(Restrictions.eq("name", "sebesa"));
		List<Evento> findByCriteria = (List<Evento>) hibernateTemplate.findByCriteria(detachedCriteria);
		if (findByCriteria != null && findByCriteria.size() > 0)
			return findByCriteria;
		else
			return null;
	}

	@Override
	public Evento getEvento(int id) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Evento.class);
		detachedCriteria.add(Restrictions.eq("id", id));
		List<Evento> findByCriteria = (List<Evento>) hibernateTemplate.findByCriteria(detachedCriteria);
		if (findByCriteria != null && findByCriteria.size() > 0)
			return findByCriteria.get(0);
		else
			return null;
	}

	@Override
	public List<Evento> getEventosByCreator(int idCreador) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Evento.class);
		detachedCriteria.add(Restrictions.eq("creador", Integer.toString(idCreador)));
		List<Evento> findByCriteria = (List<Evento>) hibernateTemplate.findByCriteria(detachedCriteria);
		if (findByCriteria != null && findByCriteria.size() > 0)
			return findByCriteria;
		else
			return null;
	}

}
