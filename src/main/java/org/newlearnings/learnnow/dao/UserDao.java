package org.newlearnings.learnnow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.newlearnings.learnnow.delegator.UserDelegator;
import org.newlearnings.learnnow.model.UserDetails;
import org.newlearnings.learnnow.util.HttpUrlConnection;

public class UserDao extends HttpUrlConnection implements UserDelegator {
	
	public UserDetails getUserDetails(String userName) {
		UserDetails userDetails = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getHttpUrlConnection();
			String selectQuery = "select user_id, first_name, last_name, email from user_details where username = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				userDetails = new UserDetails();
				userDetails.setUserId(resultSet.getInt("USER_ID"));
				userDetails.setUserName(userName);
				userDetails.setFirstName(resultSet.getString("FIRST_NAME"));
				userDetails.setLastName(resultSet.getString("LAST_NAME"));
				userDetails.setEmail(resultSet.getString("EMAIL"));
			}
			
			if (userDetails != null) {
				userDetails.setLanguagesSelected(getPreferredLanguages(userDetails.getUserId()));
			}
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return userDetails;
	}
	
	public List<String> getPreferredLanguages(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> languagesSelected = new ArrayList<>();
		
		try {
			connection = getHttpUrlConnection();
			String selectQuery = "select PREF_LANGUAGE from PREFERRED_LANGUAGE WHERE USER_ID = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				languagesSelected.add(resultSet.getString("PREF_LANGUAGE"));
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		
		return languagesSelected;
	}
	
	private int countTotalUsers() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int totalUsers = 0;
		
		try {
			connection = getHttpUrlConnection();
			String selectQuery = "select count(user_id) as total_users from user_details";
			preparedStatement = connection.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				totalUsers = resultSet.getInt("total_users");
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return totalUsers;
	}
	
	public boolean registerUser(UserDetails userDetails) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int rowAddedIndicator = 0;
		
		try {
			int newUserId = countTotalUsers() + 1;
			connection = getHttpUrlConnection();
			String insertQuery = "INSERT INTO USER_DETAILS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, DT_CREATED, "
					+ "EMAIL, PASSPHRASE) VALUES (?, ?, ?, ?, now(), ?, ?)";
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, newUserId);
			preparedStatement.setString(2, userDetails.getUserName());
			preparedStatement.setString(3, userDetails.getFirstName());
			preparedStatement.setString(4, userDetails.getLastName());
			preparedStatement.setString(5, userDetails.getEmail());
			preparedStatement.setString(6, userDetails.getPassphrase());
			
			rowAddedIndicator = preparedStatement.executeUpdate();
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return ((rowAddedIndicator > 0) ? true : false);
		
	}
	
	public boolean deleteLanguages(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int rowDeletedIndicator = 0;
		boolean isRowDeleted = false;
		
		try {
			connection = getHttpUrlConnection();
			String deleteQuery = "DELETE FROM PREFERRED_LANGUAGE WHERE USER_ID = ?";
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, userId);
			
			rowDeletedIndicator = preparedStatement.executeUpdate();
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		isRowDeleted = ((rowDeletedIndicator > 0) ? true : false);
		
		return isRowDeleted;
		
	}
	
	public boolean modifyLanguage(String userName, List<String> languagesAdded) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int[] rowAddedIndicator = null;
		boolean isRowAdded = true;
		
		UserDetails userDetails = getUserDetails(userName);
		
		if (userDetails != null) {
			if (userDetails.getLanguagesSelected().size() > 0) {
				deleteLanguages(userDetails.getUserId());
			}
			
			try {
				if (languagesAdded.size() > 0) {
					connection = getHttpUrlConnection();
					String insertQuery = "INSERT INTO PREFERRED_LANGUAGE (USER_ID, PREF_LANGUAGE) VALUES (?, ?)";
					preparedStatement = connection.prepareStatement(insertQuery);
					
					for (String language : languagesAdded) {
						preparedStatement.setInt(1, userDetails.getUserId());
						preparedStatement.setString(2, language);
						
						preparedStatement.addBatch();
					}
					
					rowAddedIndicator = preparedStatement.executeBatch();
				} else {
					rowAddedIndicator = new int[1];
					rowAddedIndicator[0] = 1;
				}
				
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			for (int indicator: rowAddedIndicator) {
				if (indicator == 0) {
					isRowAdded = false;
					break;
				}
			}
			
		} else {
			isRowAdded = false;
		}
		
		return isRowAdded;
	}

}
