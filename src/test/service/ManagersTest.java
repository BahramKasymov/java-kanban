package test.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.service.HistoryManager;
import ru.yandex.javacourse.service.Managers;
import ru.yandex.javacourse.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки класса Managers.
 */
@DisplayName("Тесты для класса Managers")
class ManagersTest {

    @Test
    @DisplayName("Должен возвращать не null TaskManager по умолчанию")
    void should_ReturnNonNullTaskManager_When_GetDefault() {
        // given: ничего не требуется

        // when: вызываем Managers.getDefault()
        TaskManager manager = Managers.getDefault();

        // then: результат не должен быть null
        assertNotNull(manager, "TaskManager не должен быть null");
    }

    @Test
    @DisplayName("Должен возвращать не null HistoryManager по умолчанию")
    void should_ReturnNonNullHistoryManager_When_GetDefaultHistory() {
        // given: ничего не требуется

        // when: вызываем Managers.getDefaultHistory()
        HistoryManager historyManager = Managers.getDefaultHistory();

        // then: результат не должен быть null
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }
}
