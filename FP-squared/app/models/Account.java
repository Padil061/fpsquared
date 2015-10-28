package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Account extends Model {
    private String userName;
    private String password;
}