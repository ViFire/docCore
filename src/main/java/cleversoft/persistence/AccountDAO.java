package cleversoft.persistence;

import cleversoft.entities.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO extends GenericDAO<Account> {

    public AccountDAO() {
        super(Account.class);
    }

    /* attribute equals table name */

    public Account findAccountByName(String name) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", name);

        List<Account> results = findEntitiesByAttributes(attributes);

        if(results != null) {
            return results.get(0);
        }
        return null;
    }
}
