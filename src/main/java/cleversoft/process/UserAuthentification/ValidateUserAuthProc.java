package cleversoft.process.UserAuthentification;

import cleversoft.api.security.PasswordHelper;
import cleversoft.entities.User;
import cleversoft.entities.UserRole;
import cleversoft.persistence.UserDAO;
import cleversoft.process.BasicProcessor;
import cleversoft.tools.Pair;

/**
 *  Chain of responsibility
 *  Check if user exists and has grands for login
 *  Input: Pair<Username,Password>
 *  Output
 */
public class ValidateUserAuthProc extends BasicProcessor {

    private UserDAO dao;

    public ValidateUserAuthProc(UserDAO dao) {
        super();
        this.dao = dao;
    }


    @Override
    protected Object doProcess(Object input) {
        Pair<String, String> credentials = (Pair<String, String>) input;

        if(input != null) {
            User user = dao.findUserByName(credentials.first());

            if(user == null || !PasswordHelper.validatePassword(credentials.second(), user.getPassword()))
                return null;

            if(user.getUserRoles().contains(UserRole.LOGIN) || user.getUserRoles().contains(UserRole.ADMIN)) {
                return user;
            }
        }
        return null;
    }
}
