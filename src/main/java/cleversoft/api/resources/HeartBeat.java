package cleversoft.api.resources;

import cleversoft.api.security.PermitRoles;
import cleversoft.entities.UserRole;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

@Path("heartbeat")
public class HeartBeat {

    @GET
    @Produces("text/plain")
    @PermitRoles({UserRole.HEARTBEAT})
    public String hello() {
        return new Date().toString();
    }

}