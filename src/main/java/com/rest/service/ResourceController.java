package com.rest.service;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.service.database.DatabaseManager;
import com.service.pojo.UserDetails;

/**
 * @author raghavender.n
 *
 */
@Path("service")
public class ResourceController {

	/**
	 * @param userDetails
	 * @return
	 * @throws SQLException
	 */
	@Path("/addUser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(UserDetails userDetails) throws SQLException {

		final Gson gson = new Gson();
		DatabaseManager databaseManager = new DatabaseManager();
		boolean status = databaseManager.insertUser(userDetails);

		if (status) {
			return Response
					.ok()
					.entity(gson
							.toJson("User details inserted successfully!!!"))
					.build();
		} else {
			return Response.ok().entity(gson.toJson("Insertion failed!!!!"))
					.build();
		}

	}

	/**
	 * @param firstName
	 * @return
	 * @throws SQLException
	 */
	@Path("/getUser/{userEmail}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@PathParam("userEmail") String userEmail)
			throws SQLException {

		DatabaseManager databaseManager = new DatabaseManager();
		UserDetails user = databaseManager.getUser(userEmail);

		return Response.ok(200).entity(user).build();
	}

	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@Path("/updateUser")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(UserDetails user) throws SQLException {

		final Gson gson = new Gson();
		DatabaseManager databaseManager = new DatabaseManager();
		boolean status = databaseManager.updateUser(user);

		if (status) {
			return Response
					.ok()
					.entity(gson.toJson("User details updated successfully!!!"))
					.build();
		} else {
			return Response.ok().entity(gson.toJson("Updation failed!!!!"))
					.build();
		}

	}

	/**
	 * @param userEmail
	 * @return
	 * @throws SQLException
	 */
	@Path("/deleteUser/{userEmail}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userEmail") String userEmail)
			throws SQLException {

		final Gson gson = new Gson();
		DatabaseManager databaseManager = new DatabaseManager();
		boolean status = databaseManager.deleteUser(userEmail);

		if (status) {
			return Response
					.ok()
					.entity(gson.toJson("User details deleted successfully!!!"))
					.build();
		} else {
			return Response.ok().entity(gson.toJson("Deletion failed!!!!"))
					.build();
		}

	}

}
