/** @file QuestionsDAO.java
 *  @brief QuestionsDAO
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

import model.Questions;

public interface QuestionsDAO {
	
	public List<Questions> getAllQuestions();

}
