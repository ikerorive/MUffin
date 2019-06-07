/** @file EventoDAO.java
 *  @brief EventoDAO
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package dao
 */
package dao;

import java.util.List;

import model.Evento;

public interface EventoDAO {
	public abstract boolean saveEvento(Evento evento);
	public List<Evento> getAllEventos();
	public Evento getEvento(int id);
	public List<Evento> getEventosByCreator(int idCreador);
}
