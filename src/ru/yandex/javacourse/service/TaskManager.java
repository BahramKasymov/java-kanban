package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import java.util.*;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int idCounter = 1;

    public void createTask(Task task) {
        task.setId(idCounter++);
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(idCounter++);
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(idCounter++);
        subtasks.put(subtask.getId(), subtask);
        subtask.getEpic().addSubtask(subtask); // Добавляем подзадачу в эпик
    }

    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            deleteAllSubtasksForEpic(epic.getId());
        }
        epics.clear();
    }

    public void deleteAllSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<Subtask> subtasksForEpic = epic.getSubtasks();
            for (Subtask subtask : subtasksForEpic) {
                subtasks.remove(subtask.getId()); // Удаляем из карты подзадач
            }
            epic.getSubtasks().clear(); // Очищаем список подзадач эпика
            epic.updateEpicStatus(); // Обновляем статус эпика
            updateEpic(epic); // Обновляем эпик в карте
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.updateEpicStatus();
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = subtask.getEpic();
        epic.updateEpicStatus();
        updateEpic(epic);
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Удаляем эпик по ID и подзадачи
    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            deleteAllSubtasksForEpic(id); // Удаляем связанные подзадачи
        }
    }

    public List<Subtask> getSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();
    }
}