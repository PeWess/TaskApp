package com.example.springboottaskmanager.TestData;

import com.example.springboottaskmanager.model.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {
    static public Task fullWrittenTask = new Task(1L, "Cleaning", "Administration", "Completed", "Sergey", "Sergey");
    final static public Task smallNameTask = new Task(2L, "cleaning", "Administration", "Completed", "Sergey", "Sergey");
    final static public Task wrongStatusTask = new Task(3L, "Cleaning", "Administration", "Complete", "Sergey", "Sergey");
    final static public Task noOwnerTask = new Task(4L, "Cleaning", "Administration", "Completed", "Sergey", "");
    final static public Task noNameTask = new Task(5L, "cleaning", "Administration", "Complete", "Sergey", "");
    final static public Task onlyImportantTask = new Task(6L, "Cleaning", "", "In completion", "", "Sergey");
    final static public Task emptyTask = new Task();

    final static public List<Task> goodTasks = new ArrayList<>(Arrays.asList(fullWrittenTask, onlyImportantTask));
    final static public List<Task> badTasks = new ArrayList<>(){{add(smallNameTask); add(wrongStatusTask); add(noOwnerTask); add(noNameTask); add(emptyTask);}};
}
