package org.newlearnings.learnnow.delegator;

import org.newlearnings.learnnow.model.UserDetails;

import java.util.List;

public interface UserDelegator {

	public UserDetails getUserDetails(String userName);
	
	public boolean registerUser(UserDetails userDetails);
	
	public boolean modifyLanguage(String userName, List<String> languagesAdded);
}
