package lab6.controller;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import Dao.CustomerDAO;
import jakarta.ws.rs.HeaderParam;
import model.Customer;
import model.Phonenumber;
import net.bytebuddy.asm.Advice.Return;
import response.model.CommonResponse;
import response.model.CustomerResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

@Path("/services")
public class CustomerService {
	String JWT_TOKEN_KEY = "ssss";
	CustomerDAO cusDao = new CustomerDAO();

	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> getUsers() {
		// cusDao.addCustomer(new Customer("S1", new HashSet<Phonenumber>()));
		return cusDao.getAllCustomers();
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String text() {
		String test = "kuy";
		return test;
	}

	private String generateToken(Customer c) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
			Date expirationDate = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
			Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
			return JWT.create()
					// Issue date.
					.withIssuedAt(issuedAt)
					// Expiration date.
					.withExpiresAt(expirationDate)
					// User id - here we can put
					// anything we want, but for the
					// example userId is
					// appropriate.
					.withClaim("username", c.getUsername())
					// Issuer of the token.
					.withIssuer("jwtauth")
					// And the signing algorithm.
					.sign(algorithm);
		} catch (JWTCreationException e) {
			// LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	private Customer validateToken(String token) {
		try {
			if (token != null) {
				Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("jwtauth").build(); // Reusable verifier
				// instance
				DecodedJWT jwt = verifier.verify(token);
				// Get the userId from token claim.
				Claim cus = jwt.getClaim("username");
				System.out.println(cus.asString());
				// Find user by token subject(id).
				// c userDao = new UserDao();
				return cusDao.findCustomerbyname(cus.asString());
			}
		} catch (JWTVerificationException e) {
			// LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Path("/authenticate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateCredentials(Customer c)
			throws JsonGenerationException, JsonMappingException, IOException {
		Customer cus = validUser(c);
		CommonResponse responsePojo = new CommonResponse();
		if (cus == null) {
			responsePojo.setMsg("Invalid Username or password");
			responsePojo.setStatus("401");
			return Response.status(401).entity(responsePojo).build();
		}
		responsePojo.setResult(generateToken(cus));
		responsePojo.setStatus("200");
		responsePojo.setMsg("OK");
		return Response.ok().entity(responsePojo).build();
	}

	private Customer validUser(Customer c) {
		return cusDao.findCustomer(c);
	}

	@Path("/customers/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomerByName(@HeaderParam("token") String token, @PathParam("name") String name)
			throws JsonGenerationException, JsonMappingException, IOException {
		Customer cus = validateToken(token);
		CustomerResponse responsePojo = new CustomerResponse();
		return Response.status(200).entity(token).build();
		
		/*if (cus != null) {
			responsePojo.setMsg("ok");
			responsePojo.setStatus("200");
			responsePojo.setResult(cusDao.findCustomerbyname(name));
			return Response.status(200).entity(responsePojo).build();
		} else {
			responsePojo.setMsg("Permission denied");
			responsePojo.setStatus("403");
			return Response.status(403).entity(responsePojo).build();
		}*/
	}

}
