/** @file QuestionsService.java
 *  @brief QuestionsService
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

import model.Questions;

public interface QuestionsService {

	public abstract List<Questions> getAllQuestions();

}
