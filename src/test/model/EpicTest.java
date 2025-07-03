package test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.service.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для проверки статусов эпика в зависимости от подзадач.
 */
@DisplayName("Тесты статусов эпика")
class EpicTest {

    private static final String EPIC_TITLE = "Epic";
    private static final String EPIC_DESC = "Описание эпика";
    private static final String SUBTASK_TITLE = "Subtask";
    private static final String SUBTASK_DESC = "Описание подзадачи";

    @Test
    @DisplayName("Эпик должен иметь статус NEW, если нет подзадач")
    void should_BeNew_When_NoSubtasks() {
        // given: создаём менеджер и эпик без подзадач
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESC, 1, Status.NEW);
        manager.createEpic(epic);

        // when: обновляем статус эпика
        manager.updateEpicStatus(epic);

        // then: статус эпика должен быть NEW
        assertEquals(Status.NEW, epic.getStatus(), "Эпик должен быть NEW, если нет подзадач");
    }

    @Test
    @DisplayName("Эпик должен иметь статус DONE, если все подзадачи DONE")
    void should_BeDone_When_AllSubtasksDone() {
        // given: создаём эпик и две подзадачи со статусом DONE
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESC, 1, Status.NEW);
        manager.createEpic(epic);

        Subtask s1 = new Subtask(SUBTASK_TITLE, SUBTASK_DESC, 2, Status.DONE, epic);
        Subtask s2 = new Subtask(SUBTASK_TITLE, SUBTASK_DESC, 3, Status.DONE, epic);
        manager.createSubtask(s1);
        manager.createSubtask(s2);

        // when: обновляем статус эпика
        manager.updateEpicStatus(epic);

        // then: статус эпика должен быть DONE
        assertEquals(Status.DONE, epic.getStatus(), "Эпик должен быть DONE, если все подзадачи DONE");
    }

    @Test
    @DisplayName("Эпик должен иметь статус IN_PROGRESS, если подзадачи смешаны")
    void should_BeInProgress_When_SubtasksMixed() {
        // given: создаём эпик и две подзадачи, одна NEW, другая DONE
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESC, 1, Status.NEW);
        manager.createEpic(epic);

        Subtask s1 = new Subtask(SUBTASK_TITLE, SUBTASK_DESC, 2, Status.NEW, epic);
        Subtask s2 = new Subtask(SUBTASK_TITLE, SUBTASK_DESC, 3, Status.DONE, epic);
        manager.createSubtask(s1);
        manager.createSubtask(s2);

        // when: обновляем статус эпика
        manager.updateEpicStatus(epic);

        // then: статус эпика должен быть IN_PROGRESS
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Эпик должен быть IN_PROGRESS, если подзадачи смешаны");
    }
}
