/** @file User.java
 *  @brief User class
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2018
 */

/** @brief package model
 */
package model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int idUser;
	@NotEmpty
	@Column(name = "username")
	private String username;

	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotEmpty
	@Column(name = "tipoUsuario")
	private String tipoUsuario;

	@NotEmpty
	@Column(name = "email")
	private String email;

	@Column(name = "salt")
	private String salt;

	@Column(name = "avatar")
	private Blob avatar;

	@Transient
	private String respuestaPreguntas;
	
	
	
	public String getRespuestaPreguntas() {
		return respuestaPreguntas;
	}

	public void setRespuestaPreguntas(String respuestaPreguntas) {
		this.respuestaPreguntas = respuestaPreguntas;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Blob getAvatar() {
		return avatar;
	}

	public void setAvatar(Blob avatar) {
		this.avatar = avatar;
	}

}
