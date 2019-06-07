/** @file EventoService.java
 *  @brief EventoService
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package service
 */
package service;

import java.util.List;

import model.Evento;


public interface EventoService {
	public abstract boolean registerEvento(Evento evento);
	public abstract List<Evento> getAllEventos();
	public abstract List<Evento> getEventosByCreator(int idCreador);
	
	public abstract Evento getEvento(int id);
}
