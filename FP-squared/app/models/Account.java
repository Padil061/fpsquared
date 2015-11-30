package models;

import com.avaje.ebean.Model;
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
    Team team;

    public static Model.Finder<Long, Account> find = new Model.Finder<Long, Account>(Account.class);
}