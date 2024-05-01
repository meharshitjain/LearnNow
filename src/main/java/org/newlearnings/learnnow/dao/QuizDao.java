package org.newlearnings.learnnow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.newlearnings.learnnow.delegator.QuizDelegator;
import org.newlearnings.learnnow.model.QuizDetails;
import org.newlearnings.learnnow.util.HttpUrlConnection;

public class QuizDao extends HttpUrlConnection implements QuizDelegator {
	
	public List<QuizDetails> getQuizDetailsByLanguage(String language) {
		List<QuizDetails> quizDetails = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getHttpUrlConnection();
			String selectQuery = "select * from QUIZ_BANK where LANGUAGES = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, language);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				QuizDetails quizDetail = new QuizDetails();
				quizDetail.setQuizId(resultSet.getInt("QUIZ_ID"));
				quizDetail.setLanguages(resultSet.getString("LANGUAGES"));
				quizDetail.setQuestionId(resultSet.getInt("QUESTION_ID"));
				quizDetail.setQuestion(resultSet.getString("QUESTION"));
				quizDetail.setAnswer(resultSet.getString("ANSWER"));
				quizDetail.setOption1(resultSet.getString("OPTION_1"));
				quizDetail.setOption2(resultSet.getString("OPTION_2"));
				quizDetail.setOption3(resultSet.getString("OPTION_3"));
				quizDetail.setOption4(resultSet.getString("OPTION_4"));
				
				quizDetails.add(quizDetail);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return quizDetails;
	}
}
