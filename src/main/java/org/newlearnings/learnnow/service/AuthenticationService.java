package org.newlearnings.learnnow.service;

import org.newlearnings.learnnow.dao.AuthenticationDao;
import org.newlearnings.learnnow.delegator.AuthenticationDelegator;
import org.newlearnings.learnnow.model.UserDetails;

import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/authenticate")
public class AuthenticationService {
	AuthenticationDelegator authenticationDelegatorImpl = new AuthenticationDao();
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validateUser")
	public String validateUser(UserDetails userDetails) {
		Gson gson = new Gson();
		return gson.toJson(authenticationDelegatorImpl.validateUser(userDetails));
	}
}
