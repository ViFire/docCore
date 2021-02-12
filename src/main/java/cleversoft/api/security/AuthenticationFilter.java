package cleversoft.api.security;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

//https://stackoverflow.com/questions/19863311/jax-rs-service-rolesallowed-annotation-throwing-exception

@Provider
@Priority(1000)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        // whitelist resources like AuthenticationService for login otherweise token is needed
        if(resourceInfo.getResourceMethod().getAnnotation(PermitAll.class) != null) {
            context.setProperty("executeAuthorizationFilter", false);
        } else {
            //check if token is valid
            String jwtToken = context.getHeaderString(JWTTokenHelper.tokenName);
            boolean isTokenValid = false;

            try {
                // TODO check for validation and exp
                isTokenValid = JWTTokenHelper.validateUserToken(jwtToken);
            } catch (NullPointerException exception) {
                context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            if(!isTokenValid) {
                context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        }

    }

}




