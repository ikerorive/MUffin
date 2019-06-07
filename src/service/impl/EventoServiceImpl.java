/** @file EventoServiceImpl.java
 *  @brief EventoServiceImpl
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package service.impl
 */
package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.EventoDAO;
import dao.UserDAO;
import model.Evento;
import service.EventoService;

@Service("eventoService")
public class EventoServiceImpl implements EventoService {
	@Autowired
	private EventoDAO eventoDAO;
	

	public EventoDAO getEventoDAO() {
		return eventoDAO;
	}

	public void setEventoDAO(EventoDAO eventoDAO) {
		this.eventoDAO = eventoDAO;
	}

	/*
	 * ! \brief Llama al dao y dependiendo del exito devuelve true o false
	 */
	@Override
	public boolean registerEvento(Evento evento) {
		boolean isRegister=false;
		boolean saveEvento = getEventoDAO().saveEvento(evento);
		if(saveEvento)
			isRegister=true;
		return isRegister;
	}

	/*
	 * ! \brief Llama al dao y devuelve la lista de eventos
	 */
	@Override
	public List<Evento> getAllEventos() {
		// TODO Auto-generated method stub
		return getEventoDAO().getAllEventos();
	}

	/*
	 * ! \brief Llama al dao y devuelve el evento
	 */
	@Override
	public Evento getEvento(int id) {
		// TODO Auto-generated method stub
		return getEventoDAO().getEvento(id);
	}

	@Override
	public List<Evento> getEventosByCreator(int idCreador) {
		// TODO Auto-generated method stub
		return getEventoDAO().getEventosByCreator(idCreador);
	}

}
