package org.newlearnings.learnnow.delegator;

import java.util.List;

import org.newlearnings.learnnow.model.ShowResults;
import org.newlearnings.learnnow.model.UserAnswers;
import org.newlearnings.learnnow.model.UserResult;

public interface UserAnswersDelegator {
	
	public boolean submitQuizResponses(List<UserAnswers> userAnswers);
	
	public UserResult showResults(ShowResults showResults);
}
