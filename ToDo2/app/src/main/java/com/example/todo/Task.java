package com.example.todo;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean done;
    private Category category;

    public Task() {
        id = UUID.randomUUID();
        date = new Date();
        category = Category.HOME;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone(){
        return done;
    }
    public void setDone(boolean check){
        done = check;
    }

    public UUID getId() {
        return id;
    }

    public void setCategory(Category categoryToSet) {
        category = categoryToSet;
    }
    public Category getCategory(){
        return category;
    }

    public void setDate(Date dateToSet)
    {
        date = dateToSet;
    }
}
