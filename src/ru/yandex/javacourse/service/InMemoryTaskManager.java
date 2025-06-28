package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final List<Task> history = new ArrayList<>();

    private int idCounter = 1;

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    private void addToHistory(Task task) {
        if (task != null) {
            history.add(task);
            if (history.size() > 10) {
                history.remove(0); // удаляем самый старый просмотр
            }
        }
    }

    @Override
    public void createTask(Task task) {
        task.setId(idCounter++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(idCounter++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(idCounter++);
        subtasks.put(subtask.getId(), subtask);
        subtask.getEpic().addSubtask(subtask); // Добавляем подзадачу в эпик
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            deleteAllSubtasksForEpic(epic.getId());
        }
        epics.clear();
    }

    @Override
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

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        addToHistory(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        addToHistory(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        addToHistory(subtask);
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.updateEpicStatus();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = subtask.getEpic();
        epic.updateEpicStatus();
        updateEpic(epic);
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Исправлено: сначала удаляем подзадачи, потом эпик
    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            deleteAllSubtasksForEpic(id); // Сначала удаляем связанные подзадачи
            epics.remove(id); // Потом удаляем эпик
        }
    }

    @Override
    public List<Subtask> getSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();
    }
}
