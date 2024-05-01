package org.newlearnings.learnnow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.newlearnings.learnnow.delegator.AuthenticationDelegator;
import org.newlearnings.learnnow.model.UserDetails;
import org.newlearnings.learnnow.util.HttpUrlConnection;

public class AuthenticationDao extends HttpUrlConnection implements AuthenticationDelegator {
	
	public boolean validateUser(UserDetails userDetails) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rowCount = 0;
		
		try {
			connection = getHttpUrlConnection();
			String selectQuery = "SELECT USER_ID FROM USER_DETAILS WHERE USERNAME = ? AND PASSPHRASE = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, userDetails.getUserName());
			preparedStatement.setString(2, userDetails.getPassphrase());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				rowCount++;
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		if (rowCount == 1) {
			return true;
		} else {
			return false;
		}
	}
}
