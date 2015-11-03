package models;

import java.util.Date;

import com.avaje.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Timeline {
    public Date startDate;
    public Date endDate;
}
