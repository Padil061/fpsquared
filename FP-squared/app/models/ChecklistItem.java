package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class ChecklistItem extends Model {

    @Id
    @Column( name = "checklistitemID")
    private long id;

    @Column( name = "checked")
    public boolean checked;

    @Column( name = "text")
    public String text;

    @ManyToOne(cascade = CascadeType.ALL)
    public Task task;

    public static Model.Finder<Long, ChecklistItem> find = new Model.Finder<Long, ChecklistItem>(ChecklistItem.class);

    public long getId() { return id; }
}
