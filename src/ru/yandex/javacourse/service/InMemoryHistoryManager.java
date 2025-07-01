package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Task;

import java.util.ArrayList;
import java.util.List;

// Реализация интерфейса HistoryManager
public class InMemoryHistoryManager implements HistoryManager {

    // Список для хранения истории просмотров
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        // Добавляем задачу в историю
        history.add(task);
        // Если превышает 10 элементов — удаляем самый старый
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history); // Возвращаем копию для безопасности
    }
}
