/*
package com.example.springboottaskmanager.TestService;

import com.example.springboottaskmanager.TestData.TestData;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.service.TaskServicePost;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

//Тест валидации на сервисе, обрабатывающем POST запросы
@SpringBootTest
@AutoConfigureDataJpa
public class TaskServicePostTest {
    @Autowired
    TaskServicePost taskServicePost;
    @MockBean
    TaskRepo taskRepo;

    //Попытка сохранения валидных записей, ConstraintViolationException не выбрасывается, в поле result возвращается сохраненная запись
    @Test
    public void PostTaskValidationTest_success() throws Exception {
        for (int i = 0; i < TestData.goodTasks.size(); i++) {
            Task curTask = TestData.goodTasks.get(i);
            Task result = taskServicePost.createTask(curTask);
            Assertions.assertEquals(curTask.getId(), result.getId());
            Assertions.assertEquals(curTask.getTaskName(), result.getTaskName());
            Assertions.assertEquals(curTask.getTaskType(), result.getTaskType());
            Assertions.assertEquals(curTask.getStatus(), result.getStatus());
            Assertions.assertEquals(curTask.getExecutor(), result.getExecutor());
            Assertions.assertEquals(curTask.getOwnBy(), result.getOwnBy());
        }
    }

    //Попытка сохранения невалидных записей, ConstraintViolationException выбрасывается, запись не возвращается
    @Test
    public void PostTaskValidationTest_fail() throws Exception {
        for (int i = 0; i < TestData.badTasks.size(); i++) {
            Task curTask = TestData.badTasks.get(i);
            Assertions.assertThrows(ConstraintViolationException.class, () -> taskServicePost.createTask(curTask));
        }
    }
}
*/
