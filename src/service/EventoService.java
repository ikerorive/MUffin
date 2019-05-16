package service;

import java.util.List;

import model.Evento;


public interface EventoService {
	public abstract boolean registerEvento(Evento evento);
	public abstract List<Evento> getAllEventos();
}
