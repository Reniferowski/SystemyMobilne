package com.example.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static TaskStorage taskStorage = new TaskStorage();
    private final List<Task> tasks;

    public static TaskStorage getInstance(){
        return taskStorage;
    }

    private TaskStorage() {
        tasks = new ArrayList<>();
        for(int i = 1; i <= 102; i++){
            Task task = new Task();
            if (i % 4 == 0)
                task.setName("Bardzo bardzo bardzo bardzo bardzo pilne zadanie numer " + i);
            else
                task.setName("Pilne zadanie numer " + i);
            task.setDone(i % 3 == 0);
            if (i % 3 == 0)
                task.setCategory(Category.STUDIES);
            else
                task.setCategory(Category.HOME);
            tasks.add(task);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(UUID position) {
        for(int i = 0 ; i<tasks.size(); i++){
            if(tasks.get(i).getId().equals(position))
                return tasks.get(i);
        }
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
