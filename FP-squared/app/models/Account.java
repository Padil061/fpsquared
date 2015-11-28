package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Account extends Model {

    @Id
    @Column(name = "accountID")
    private Long id;

    @Column(name = "userName")
    public String userName;

    @Column(name = "password")
    public String password;

    @ManyToOne(cascade = CascadeType.ALL)
    Team team;
}