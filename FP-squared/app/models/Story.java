package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Story {
public Timeline timeline;

@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
public List<Task> tasks;

@OneToMany
public List<Account> accounts;
}
