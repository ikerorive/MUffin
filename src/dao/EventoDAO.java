package dao;

import java.util.List;

import model.Evento;

public interface EventoDAO {
	public abstract boolean saveEvento(Evento evento);
	public List<Evento> getAllEventos();
}
