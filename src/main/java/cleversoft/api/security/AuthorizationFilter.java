package cleversoft.api.security;

import cleversoft.entities.User;
import cleversoft.entities.UserRole;
import cleversoft.persistence.UserDAO;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(1100)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        if(context.getProperty("executeAuthorizationFilter") != null) {
            return;
        }

        User user = null;

        try {
            UserDAO dao = new UserDAO();
            int userId = JWTTokenHelper.getUserIdClaim(context.getHeaderString(JWTTokenHelper.tokenName));
            user = dao.find(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user == null) {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        PermitRoles annotation = resourceInfo.getResourceMethod().getAnnotation(PermitRoles.class);
        UserRole role = annotation.value()[0];

        if(!((user.hasRole(role) && user.hasRole(UserRole.LOGIN)) || user.hasRole(UserRole.ADMIN))) {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }

}
