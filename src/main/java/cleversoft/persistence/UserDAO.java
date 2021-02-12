package cleversoft.persistence;

import cleversoft.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    /* attribute equals table name */

    public User findUserByName(String username) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", username);

        List<User> results = findEntitiesByAttributes(attributes);

        if(results != null) {
            return results.get(0);
        }
        return null;
    }
}
