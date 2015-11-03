package models;

import play.db.ebean.Model;
import java.io.*;
import javax.persistence.Entity;

@Entity
public class Task{

    String name;
    Long id;
    String status;

// This is the constructor of the class Task
    public Task(String name){
          this.name = name;
  }

// Assign the id of the Task to the variable id.     
     public void taskId(Long taskId){
             id = taskId;
   }

// Assign the status of the Task to the variable status.
       public void taskStatus(String taskStatus){
                status = taskStatus;
  }

//Print the Task details.
        public void printTask(){
             System.out.println("Name:" + name);
             System.out.println("Id:" + id);
             System.out.println("Status:" + status);
   }
}
