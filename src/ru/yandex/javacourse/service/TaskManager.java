package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import java.util.List;

/**
 * Интерфейс TaskManager.
 * Определяет основные методы управления задачами, эпиками и подзадачами.
 */
public interface TaskManager {

    /** Создаёт новую задачу. */
    void createTask(Task task);

    /** Создаёт новый эпик. */
    void createEpic(Epic epic);

    /** Создаёт новую подзадачу. */
    void createSubtask(Subtask subtask);

    /** Удаляет все эпики. */
    void deleteAllEpics();

    /** Удаляет все подзадачи у эпика по ID. */
    void deleteAllSubtasksForEpic(int epicId);

    /** Возвращает историю просмотров задач. */
    List<Task> getHistory();

    /** Возвращает все задачи. */
    List<Task> getAllTasks();

    /** Возвращает все эпики. */
    List<Epic> getAllEpics();

    /** Возвращает все подзадачи. */
    List<Subtask> getAllSubtasks();

    /** Возвращает задачу по ID. */
    Task getTaskById(int id);

    /** Возвращает эпик по ID. */
    Epic getEpicById(int id);

    /** Возвращает подзадачу по ID. */
    Subtask getSubtaskById(int id);

    /** Обновляет задачу. */
    void updateTask(Task task);

    /** Обновляет эпик. */
    void updateEpic(Epic epic);

    /** Обновляет подзадачу. */
    void updateSubtask(Subtask subtask);

    /** Удаляет задачу по ID. */
    void deleteTaskById(int id);

    /** Удаляет эпик и его подзадачи по ID. */
    void deleteEpicById(int id);

    /** Возвращает все подзадачи для эпика. */
    List<Subtask> getSubtasksForEpic(int epicId);
}
