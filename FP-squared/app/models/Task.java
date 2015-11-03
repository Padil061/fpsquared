package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Task extends Model {
    public String name;
    public String status;
}
