package test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.service.Managers;
import ru.yandex.javacourse.service.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки InMemoryTaskManager.
 */
@DisplayName("Тесты для InMemoryTaskManager")
class InMemoryTaskManagerTest {

    private static final String TASK_TITLE = "Task";
    private static final String TASK_DESC = "Description";
    private static final String EPIC_TITLE = "Epic";
    private static final String SUBTASK_TITLE = "Subtask";

    TaskManager manager;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
    }

    @Test
    @DisplayName("Должен добавлять задачу и находить её по ID")
    void should_AddAndGetTaskById_When_Created() {
        // given
        Task task = new Task(TASK_TITLE, TASK_DESC, 0, Status.NEW);

        // when
        manager.createTask(task);
        Task retrieved = manager.getTaskById(task.getId());

        // then
        assertEquals(task, retrieved, "Задача должна быть найдена по ID");
    }

    @Test
    @DisplayName("Должен добавлять эпик и подзадачу")
    void should_AddEpicAndSubtask_When_Created() {
        // given
        Epic epic = new Epic(EPIC_TITLE, TASK_DESC, 0, Status.NEW);

        // when
        manager.createEpic(epic);
        Subtask subtask = new Subtask(SUBTASK_TITLE, TASK_DESC, 0, Status.NEW, epic);
        manager.createSubtask(subtask);

        // then
        assertNotNull(manager.getEpicById(epic.getId()), "Эпик должен быть найден");
        assertNotNull(manager.getSubtaskById(subtask.getId()), "Подзадача должна быть найдена");
    }

    @Test
    @DisplayName("Должен добавлять задачи в историю")
    void should_AddTasksToHistory_When_Accessed() {
        // given
        Task task = new Task(TASK_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createTask(task);

        // when
        manager.getTaskById(task.getId());
        List<Task> history = manager.getHistory();

        // then
        assertFalse(history.isEmpty(), "История должна содержать просмотренные задачи");
    }

    @Test
    @DisplayName("Должен сохранять задачу без изменений после добавления")
    void should_KeepTaskUnchanged_When_Added() {
        // given
        Task task = new Task(TASK_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createTask(task);

        // when
        Task retrieved = manager.getTaskById(task.getId());

        // then
        assertEquals(task.getTitle(), retrieved.getTitle(), "Заголовок должен совпадать");
        assertEquals(task.getDescription(), retrieved.getDescription(), "Описание должно совпадать");
        assertEquals(task.getStatus(), retrieved.getStatus(), "Статус должен совпадать");
    }

    @Test
    @DisplayName("Должен обновлять задачу")
    void should_UpdateTask_When_Updated() {
        // given
        Task task = new Task(TASK_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createTask(task);

        // when
        task.setStatus(Status.DONE);
        manager.updateTask(task);

        // then
        Task updated = manager.getTaskById(task.getId());
        assertEquals(Status.DONE, updated.getStatus(), "Задача должна быть обновлена");
    }

    @Test
    @DisplayName("Должен обновлять подзадачу")
    void should_UpdateSubtask_When_Updated() {
        // given
        Epic epic = new Epic(EPIC_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createEpic(epic);
        Subtask subtask = new Subtask(SUBTASK_TITLE, TASK_DESC, 0, Status.NEW, epic);
        manager.createSubtask(subtask);

        // when
        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask);

        // then
        Subtask updated = manager.getSubtaskById(subtask.getId());
        assertEquals(Status.DONE, updated.getStatus(), "Подзадача должна быть обновлена");
    }

    @Test
    @DisplayName("Должен удалять задачу по ID")
    void should_DeleteTaskById_When_Deleted() {
        // given
        Task task = new Task(TASK_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createTask(task);

        // when
        manager.deleteTaskById(task.getId());

        // then
        assertNull(manager.getTaskById(task.getId()), "Задача должна быть удалена");
    }

    @Test
    @DisplayName("Должен удалять все подзадачи эпика")
    void should_DeleteAllSubtasksForEpic_When_Deleted() {
        // given
        Epic epic = new Epic(EPIC_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createEpic(epic);
        Subtask s1 = new Subtask("S1", TASK_DESC, 0, Status.NEW, epic);
        Subtask s2 = new Subtask("S2", TASK_DESC, 0, Status.NEW, epic);
        manager.createSubtask(s1);
        manager.createSubtask(s2);

        // when
        manager.deleteAllSubtasksForEpic(epic.getId());

        // then
        assertTrue(manager.getSubtasksForEpic(epic.getId()).isEmpty(), "Все подзадачи должны быть удалены");
    }

    @Test
    @DisplayName("Должен обновлять статус эпика на основе подзадач")
    void should_UpdateEpicStatus_When_SubtasksUpdated() {
        // given
        Epic epic = new Epic(EPIC_TITLE, TASK_DESC, 0, Status.NEW);
        manager.createEpic(epic);
        Subtask s1 = new Subtask("S1", TASK_DESC, 0, Status.NEW, epic);
        Subtask s2 = new Subtask("S2", TASK_DESC, 0, Status.NEW, epic);
        manager.createSubtask(s1);
        manager.createSubtask(s2);

        // then: изначально все подзадачи NEW → эпик тоже NEW
        assertEquals(Status.NEW, epic.getStatus(), "Эпик должен быть NEW, если все подзадачи NEW");

        // when: одна подзадача DONE - эпик IN_PROGRESS
        s1.setStatus(Status.DONE);
        manager.updateSubtask(s1);

        // then
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Эпик должен быть IN_PROGRESS, если подзадачи смешаны");

        // when: все DONE - эпик DONE
        s2.setStatus(Status.DONE);
        manager.updateSubtask(s2);

        // then
        assertEquals(Status.DONE, epic.getStatus(), "Эпик должен быть DONE, если все подзадачи DONE");
    }

}
