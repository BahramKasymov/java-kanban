package ru.yandex.javacourse.service;

// Утилитарный класс для создания менеджеров
public class Managers {

    // Возвращает реализацию менеджера задач
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Возвращает реализацию менеджера истории просмотров
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
