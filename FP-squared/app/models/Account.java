package models;

import com.avaje.ebean.Model;
import controllers.routes;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class Account extends Model {

    @Id
    @Column(name = "accountID")
    private Long id;

    @Constraints.Required
    @Column(name = "userName")
    public String userName;

    @Constraints.Required
    @Column(name = "password")
    public String password;

    @ManyToOne(cascade = CascadeType.ALL)
    public Team team;

    public static Model.Finder<Long, Account> find = new Model.Finder<Long, Account>(Account.class);

    public static boolean authenticate(String userName, String password) {
        Account matchingAccount = Account.find.where().eq("userName", userName).findUnique();
        
        if (matchingAccount == null || !password.equals(matchingAccount.password)) {
            return false;
        } else {
            return true;
        }
    }

}