package cleversoft.process.UserAuthentification;

import cleversoft.api.security.JWTTokenHelper;
import cleversoft.entities.User;
import cleversoft.process.BasicProcessor;


/**
 *  Chain of responsibility
 *  Check if user exists and has grands for login
 *  Input: User Entity
 *  Output: Token or null
 */
public class TokenGenerationProc extends BasicProcessor {

    @Override
    protected Object doProcess(Object input) {
        String token;
        try {
            token = JWTTokenHelper.createUserToken((User) input);
            return token;
        } catch (Exception e) {
            return null;
        }
    }
}
