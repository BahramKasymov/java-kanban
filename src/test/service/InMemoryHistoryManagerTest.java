package test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.service.HistoryManager;
import ru.yandex.javacourse.service.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки InMemoryHistoryManager.
 */
@DisplayName("Тесты для InMemoryHistoryManager")
class InMemoryHistoryManagerTest {

    private static final String TASK_TITLE = "Task";
    private static final String TASK_DESC = "Description";
    private static final int TASK_ID = 1;

    HistoryManager historyManager;
    Task task;

    @BeforeEach
    void setup() {
        historyManager = Managers.getDefaultHistory();
        task = new Task(TASK_TITLE, TASK_DESC, TASK_ID, Status.NEW);
    }

    @Test
    @DisplayName("Должен добавлять задачу в историю")
    void should_AddTaskToHistory_When_Added() {
        // given: есть пустая история и задача

        // when: добавляем задачу
        historyManager.add(task);

        // then: задача должна быть в истории
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача должна совпадать");
    }

    @Test
    @DisplayName("Должен сохранять данные задачи без изменений")
    void should_KeepTaskData_When_AddedToHistory() {
        // given: добавляем задачу в историю
        historyManager.add(task);

        // when: получаем задачу из истории
        Task sameTask = historyManager.getHistory().get(0);

        // then: проверяем совпадение полей
        assertEquals(task.getTitle(), sameTask.getTitle(), "Заголовок должен совпадать");
        assertEquals(task.getDescription(), sameTask.getDescription(), "Описание должно совпадать");
        assertEquals(task.getStatus(), sameTask.getStatus(), "Статус должен совпадать");
    }
}
