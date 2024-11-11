package com.empLogin;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/resource")
public class LoginRes {
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static Response getLogin(@FormParam("email") String email, @FormParam("password") String passsword) {
		boolean isCorrect=LoginHelper.getData(email, passsword);
		if(isCorrect) {
			 return Response.seeOther(java.net.URI.create("https://www.youtube.com/")).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).entity("Wrong Credential!").build();
		}
	}
}
