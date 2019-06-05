package dao;

import java.util.List;

import model.Evento;

public interface EventoDAO {
	public abstract boolean saveEvento(Evento evento);
	public List<Evento> getAllEventos();
	public Evento getEvento(int id);
	public List<Evento> getEventosByCreator(int idCreador);
}
