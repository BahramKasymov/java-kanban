package service;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.service.HistoryManager;
import ru.yandex.javacourse.service.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager historyManager;
    Task task;

    @BeforeEach
    void setup() {
        historyManager = Managers.getDefaultHistory();
        task = new Task("Task", "desc", 1, Status.NEW);
    }

    @Test
    void shouldAddToHistory() {
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    void shouldKeepPreviousTaskData() {
        historyManager.add(task);
        Task sameTask = historyManager.getHistory().get(0);
        assertEquals(task.getTitle(), sameTask.getTitle());
        assertEquals(task.getDescription(), sameTask.getDescription());
        assertEquals(task.getStatus(), sameTask.getStatus());
    }
}
