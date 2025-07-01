package test.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*

class ManagersTest {

    @Test
    void defaultTaskManagerShouldNotBeNull() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "TaskManager must be initialized");
    }

    @Test
    void defaultHistoryManagerShouldNotBeNull() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager must be initialized");
    }
    @Test
    void managersShouldReturnNonNullTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Managers.getDefault() не должен возвращать null");
    }

    @Test
    void managersShouldReturnNonNullHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Managers.getDefaultHistory() не должен возвращать null");
    }
}
