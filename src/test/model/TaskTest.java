package test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки базовых свойств задач и подзадач.
 */
@DisplayName("Тесты для проверки сущностей Task и Subtask")
class TaskTest {

    // Константы для переиспользования в тестах
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final int TASK_ID = 1;
    private static final int SUBTASK_ID = 10;
    private static final int EPIC_ID = 100;

    @Test
    @DisplayName("Должны быть равны задачи с одинаковым ID")
    void should_TasksBeEqual_When_SameId() {
        // создаём две задачи с одинаковым ID, но разными полями
        Task task1 = new Task(TITLE, DESCRIPTION, TASK_ID, Status.NEW);
        Task task2 = new Task("Another Title", "Another Description", TASK_ID, Status.DONE);

        // проверяем, что они равны по ID
        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }

    @Test
    @DisplayName("Должны быть равны подзадачи с одинаковым ID")
    void should_SubtasksBeEqual_When_SameId() {
        // создаём эпик и две подзадачи с одинаковым ID, но разными полями
        Epic epic = new Epic("Epic", "Desc", EPIC_ID, Status.NEW);
        Subtask subtask1 = new Subtask("Subtask", "Desc", SUBTASK_ID, Status.NEW, epic);
        Subtask subtask2 = new Subtask("Subtask2", "Another", SUBTASK_ID, Status.DONE, epic);

        // проверяем, что подзадачи равны по ID
        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }

    @Test
    @DisplayName("Эпик не может содержать сам себя")
    void should_ThrowException_When_EpicContainsItself() {
        // создаём эпик
        Epic epic = new Epic("Epic", "Desc", TASK_ID, Status.NEW);

        // пытаемся добавить ID самого эпика в список подзадач -> должно выбросить исключение
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> epic.addSubtaskId(TASK_ID)
        );

        // Проверяем, что сообщение исключения содержит ожидаемый текст
        assertTrue(thrown.getMessage().contains("Epic cannot contain itself"));
    }

    @Test
    @DisplayName("Подзадача не может ссылаться на саму себя как на эпик")
    void should_ThrowException_When_SubtaskReferencesItselfAsEpic() {
        // создаём эпик с тем же ID, что и подзадача
        Epic epic = new Epic("Epic", DESCRIPTION, TASK_ID, Status.NEW);

        // пытаемся создать подзадачу, у которой эпик с таким же ID -> должно выбросить исключение
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Subtask(TITLE, DESCRIPTION, TASK_ID, Status.NEW, epic)
        );

        // Проверяем текст исключения
        assertTrue(thrown.getMessage().contains("Subtask cannot be its own epic"));
    }
}
