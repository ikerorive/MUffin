/** @file Questions.java
 *  @brief Questions class
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 12/12/2018
 */

/** @brief package model
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idquestions")
	private int idQuestions;
	
	@Column(name = "questions")
	private String question;
	
	@Column(name = "categoriaPregunta")
	private String categoriaPregunta;

	public int getIdQuestions() {
		return idQuestions;
	}

	public void setIdQuestions(int idQuestions) {
		this.idQuestions = idQuestions;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCategoriaPregunta() {
		return categoriaPregunta;
	}

	public void setCategoriaPregunta(String categoriaPregunta) {
		this.categoriaPregunta = categoriaPregunta;
	}
}
