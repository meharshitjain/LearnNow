package org.newlearnings.learnnow.delegator;

import org.newlearnings.learnnow.model.UserDetails;

public interface AuthenticationDelegator {
	public boolean validateUser(UserDetails userDetails);
}
