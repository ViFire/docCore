package cleversoft.api.resources;

import cleversoft.api.ResponseHelper;
import cleversoft.api.security.PermitRoles;
import cleversoft.entities.Account;
import cleversoft.entities.UserRole;
import cleversoft.persistence.AccountDAO;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("account")
public class AccountService {

    @Context
    HttpServletRequest httpRequest;

    private AccountDAO dao = new AccountDAO();
    private ResponseHelper res = new ResponseHelper();

    @POST
    @Path("getAccount")
    @Produces("text/plain")
    public Response getAccount() {
        return null;
    }


    @POST
    @Path("createAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitRoles({UserRole.ADMIN})
    public Response createAccount(Account newAccount) {
        if (newAccount == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();

        if (dao.find(newAccount.getName(),"name") != null) {
            res.setPayload(newAccount);
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        Set<ConstraintViolation<Account>> constrains = dao.isValid(newAccount);

        if (!constrains.isEmpty() && false)
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();

        try {
            dao.create(newAccount);
            dao.closeTransaction();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Persist entity: "+newAccount+" already exist").build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Persist entity: "+newAccount+" already exist").build();
        }

        if (newAccount != null) {
            return Response.status(Response.Status.OK).entity(newAccount).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
    }

}



