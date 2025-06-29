package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.InMemoryTaskManager;
import ru.yandex.javacourse.service.Managers;
import ru.yandex.javacourse.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Task 1", "Описание Task 1", 0, Status.NEW);
        Task task2 = new Task("Task 2", "Описание Task 2", 0, Status.NEW);

        Epic epic1 = new Epic("Epic 1", "Описание Epic 1", 0, Status.NEW);
        taskManager.createEpic(epic1); // Сначала создаём эпик, чтобы получить ID

        Subtask subtask1 = new Subtask("Subtask 1", "Описание Subtask 1", 0, Status.NEW, epic1);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createSubtask(subtask1);

        // Вызываем методы, которые формируют историю
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());

        // Печатаем всё
        printAllTasks(taskManager);
    }


    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Subtask subtask : manager.getSubtasksForEpic(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История просмотров:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
