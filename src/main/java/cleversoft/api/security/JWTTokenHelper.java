package cleversoft.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import cleversoft.entities.User;
import cleversoft.entities.UserRole;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JWTTokenHelper {

    private String token;
    private boolean isTokenValid = false;
    private Set<String> roles = new HashSet<>();

    public static String tokenName = "dc-token";

    private static String tokenSecret = "cs4ever!$$$";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(tokenSecret);

    private static String ISSUER_CLAIM = "docCore-backend";
    private static String USERID_CLAIM = "id";
    private static String USER_ROLE_CLAIM = "roles";


    public static String createUserToken(User logonUser) {
        List roles = new ArrayList<String>();
        for (UserRole role : logonUser.getUserRoles()) {
            roles.add(role.getRole().toUpperCase());
        }

        try {
            JWTCreator.Builder token = JWT
                    .create()
                    .withIssuer(ISSUER_CLAIM)
                    .withClaim(USERID_CLAIM, logonUser.getId())
                    .withClaim(USER_ROLE_CLAIM, roles);

            if(!logonUser.isAPIUser())
                token.withExpiresAt(new DateTime().plusMinutes(60).toDate());

            return token.sign(ALGORITHM);

        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    public static boolean validateUserToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER_CLAIM).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public static int getUserIdClaim(String token) {

        DecodedJWT jwt = JWT.decode(token);
        Claim claim = jwt.getClaim(USERID_CLAIM);

        return JWT.decode(token).getClaim(USERID_CLAIM).asInt();
    }



}
