package com.example.springboottaskmanager.TestData;

import com.example.springboottaskmanager.model.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestTaskData {
    static public Task fullWrittenTask = new Task(1L, "Cleaning", "Administration", "Completed", TestWorkersData.worker1, TestWorkersData.worker1);
    static public Task onlyImportantParams = new Task(2L, "Cleaning", "", "", null, TestWorkersData.worker1);
    static public Task smallTaskNameTask = new Task(3L, "cleaning", "Administration", "Completed", TestWorkersData.worker1, TestWorkersData.worker1);
    static public Task emptyTask = new Task();

    static public List<Task> goodTasks = new ArrayList<Task>(Arrays.asList(fullWrittenTask, onlyImportantParams));
    static public List<Task> badTasks = new ArrayList<Task>(Arrays.asList(smallTaskNameTask, emptyTask));
}
