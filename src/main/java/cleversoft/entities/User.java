package cleversoft.entities;

import cleversoft.api.security.PasswordHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    private String password;

    @ElementCollection( fetch = FetchType.EAGER)
    @CollectionTable(name="user_role")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRole> roles = new HashSet<>();

    @ManyToOne
    private Account relatedAccount = null;

    private boolean isAPIUser = false;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = PasswordHelper.generatePassword(password);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return roles;
    }

    public void setUserRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public boolean isAPIUser() {
        return isAPIUser;
    }

    public void setAPIUser(boolean APIUser) {
        isAPIUser = APIUser;
    }

    public Account getRelatedAccount() {
        return relatedAccount;
    }

    public void setRelatedAccount(Account relatedAccount) {
        this.relatedAccount = relatedAccount;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

}
