package cleversoft.api.resources;

import cleversoft.persistence.UserDAO;
import cleversoft.process.UserAuthentification.TokenGenerationProc;
import cleversoft.process.UserAuthentification.ValidateBasicAuthProc;
import cleversoft.process.UserAuthentification.ValidateUserAuthProc;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("auth")
public class AuthenticationService {

    @Context
    HttpServletRequest httpRequest;

    private UserDAO dao = new UserDAO();

    @GET
    @Path("login")
    @Produces("text/plain")
    @PermitAll
    public Response login() {

        ValidateBasicAuthProc basicAuth = new ValidateBasicAuthProc();
        ValidateUserAuthProc userAuth = new ValidateUserAuthProc(dao);
        TokenGenerationProc tokenGen = new TokenGenerationProc();
        basicAuth.setNextProcessor(userAuth);
        userAuth.setNextProcessor(tokenGen);

        String token = (String) basicAuth.process(httpRequest.getHeader("Authorization"));

        if(token != null) {
            return Response.status(Response.Status.OK).entity(token).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("logout")
    @Produces("text/plain")
    public String logout() {
        // TODO create logout process
        return "logout";
    }
}



