package cleversoft.process.UserAuthentification;

import cleversoft.process.BasicProcessor;
import cleversoft.tools.Pair;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 *  Chain of responsibility
 *  Checks if http request has a valid basic auth with username and password
 *  If so the username and pass will be forwarded to next processor
 *  Input: String: HTTP request header
 *  Output: Pair<Username,Password>
 *  Output: null
 */
public class ValidateBasicAuthProc extends BasicProcessor {
    @Override
    protected Object doProcess(Object input) {
        String authorization = (String) input;
        String[] userCred;

        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            userCred = credentials.split(":", 2);

            if(isUserNameValid(userCred[0]) && isPasswortValid(userCred[1])) {
                Pair<String, String> user = new Pair<>(userCred[0], userCred[1]);
                return user;
            }
        }

        return null;
    }

    public boolean isUserNameValid(String username) {
        if(username.length() > 0)
            return true;
        return false;
    }

    public boolean isPasswortValid(String password) {
        if(password.length() > 0)
            return true;
        return false;
    }
}
