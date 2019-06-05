package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.EventoDAO;
import dao.QuestionsDAO;
import dao.UserDAO;
import model.Evento;
import model.Questions;
import service.EventoService;
import service.QuestionsService;

@Service("questionsService")
public class QuestionsServiceImpl implements QuestionsService {
	@Autowired
	private QuestionsDAO questionsDAO;

	
	public QuestionsDAO getQuestionsDAO() {
		return questionsDAO;
	}


	public void setQuestionsDAO(QuestionsDAO questionsDAO) {
		this.questionsDAO = questionsDAO;
	}




	@Override
	public List<Questions> getAllQuestions() {
		// TODO Auto-generated method stub
		return getQuestionsDAO().getAllQuestions();
	}
	

}
