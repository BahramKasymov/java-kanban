package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Task;

import java.util.List;


// Интерфейс для управления историей просмотров задач.
// история хранит последние 10 просмотренных задач.

public interface HistoryManager {
    //Добавляет задачу в историю. Добавляет задачу в историю просмотров.

    void add(Task task);
    //Возвращает список последних просмотренных задач.
    List<Task> getHistory();
}
