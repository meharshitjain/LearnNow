package org.newlearnings.learnnow.delegator;

import java.util.List;

import org.newlearnings.learnnow.model.QuizDetails;

public interface QuizDelegator {
	
	public List<QuizDetails> getQuizDetailsByLanguage(String language);
}
