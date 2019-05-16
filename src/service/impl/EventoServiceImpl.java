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

	@Override
	public boolean registerEvento(Evento evento) {
		boolean isRegister=false;
		boolean saveEvento = getEventoDAO().saveEvento(evento);
		if(saveEvento)
			isRegister=true;
		return isRegister;
	}

	@Override
	public List<Evento> getAllEventos() {
		// TODO Auto-generated method stub
		return getEventoDAO().getAllEventos();
	}

}
