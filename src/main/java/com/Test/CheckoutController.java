package com.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/checkout")
public class CheckoutController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCheckoutData(CheckoutData data) {
        try {
            // Save data to the database using DAO
            CheckoutDAO.saveData(data);
            return Response.status(Response.Status.CREATED).entity("Checkout data saved successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to save data").build();
        }
    }
}

