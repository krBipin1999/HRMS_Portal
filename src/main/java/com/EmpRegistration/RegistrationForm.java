package com.EmpRegistration;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/chatRegister")
public class RegistrationForm {

	@POST
	@Path("/submit")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response submitRegistrationForm(@FormParam("name") String name, @FormParam("number") String number,
			@FormParam("email") String email, @FormParam("password") String password) {
		String success = RegistrationHelper.saveRegistration(name, number, email, password);

		if (success.equals("insert")) {
			return Response.status(Response.Status.OK)
					.entity("<h1>Registration Successfully Completed</h1><p>Welcome, " + name + "!</p>").build();
		}
		else if (success.equals("exists")) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("<h1>Entered email id or number is Already Registered, Please check again!</p>").build();
		}
		else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("<h1>Registration Failed</h1><p>Please check the details and register again.</p>")
					.build();
		}
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it again!";
	}
}
