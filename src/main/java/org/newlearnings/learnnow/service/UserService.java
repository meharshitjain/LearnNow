package org.newlearnings.learnnow.service;

import org.newlearnings.learnnow.dao.UserDao;
import org.newlearnings.learnnow.delegator.UserDelegator;
import org.newlearnings.learnnow.model.UserDetails;

import com.google.gson.Gson;

import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/user")
public class UserService {
	UserDelegator userDaoImpl = new UserDao();
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserDetails")
	public String getUserDetails(@QueryParam(value = "userName") String userName) {
		Gson gson = new Gson();
		UserDetails userDetails = userDaoImpl.getUserDetails(userName);
		String userData = gson.toJson(userDetails);
		
		return userData;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerUser")
	public String registerUser(UserDetails userDetails) {
		Gson gson = new Gson();
		return gson.toJson(userDaoImpl.registerUser(userDetails));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modifyLanguage")
	public Response modifyLanguage(@QueryParam(value = "userName") String userName, List<String> languagesAdded) {
		if (userDaoImpl.modifyLanguage(userName, languagesAdded)) {
			return Response.ok().entity("Languages added successfully.").build();
		} else {
			return Response.status(Status.NOT_FOUND).entity("User Name provided does not exist.").build();
		}
	}
}

