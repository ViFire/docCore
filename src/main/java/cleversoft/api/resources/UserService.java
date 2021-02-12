package cleversoft.api.resources;

import cleversoft.api.security.PasswordHelper;
import cleversoft.entities.User;
import cleversoft.persistence.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {

    @Context
    HttpServletRequest httpRequest;

    private UserDAO dao = new UserDAO();

    @POST
    @Path("getUser")
    @Produces("text/plain")
    public Response getUser() {
        return null;
    }


    @POST
    @Path("createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User newUser) {
        if(newUser == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();

            try {
                newUser.setPassword(PasswordHelper.generatePassword(newUser.getPassword()));
                dao.em.getTransaction().begin();
                dao.create(newUser);
                dao.em.getTransaction().commit();
                return Response.status(Response.Status.OK).entity(newUser).build();
            } catch (NullPointerException e) {
                System.out.println(e);
                return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
            }

    }

}



