package test.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    TaskManager manager;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
    }

    @Test
    void shouldAddAndGetTaskById() {
        Task task = new Task("Task", "desc", 0, Status.NEW);
        manager.createTask(task);
        Task retrieved = manager.getTaskById(task.getId());
        assertEquals(task, retrieved, "Should find task by id");
    }

    @Test
    void shouldAddEpicAndSubtask() {
        Epic epic = new Epic("Epic", "desc", 0, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask", "desc", 0, Status.NEW, epic);
        manager.createSubtask(subtask);

        assertNotNull(manager.getEpicById(epic.getId()));
        assertNotNull(manager.getSubtaskById(subtask.getId()));
    }

    @Test
    void shouldAddTasksToHistory() {
        Task task = new Task("Task", "desc", 0, Status.NEW);
        manager.createTask(task);
        manager.getTaskById(task.getId());

        List<Task> history = manager.getHistory();
        assertFalse(history.isEmpty(), "History must contain viewed tasks");
    }

    @Test
    void shouldKeepTaskUnchangedAfterAdding() {
        Task task = new Task("Task", "desc", 0, Status.NEW);
        manager.createTask(task);
        Task retrieved = manager.getTaskById(task.getId());

        assertEquals(task.getTitle(), retrieved.getTitle());
        assertEquals(task.getDescription(), retrieved.getDescription());
        assertEquals(task.getStatus(), retrieved.getStatus());
    }
}
