package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Sprint {
    public String name;
    public Timeline timeline;
}
