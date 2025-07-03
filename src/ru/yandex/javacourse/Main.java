package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.InMemoryTaskManager;
import ru.yandex.javacourse.service.Managers;
import ru.yandex.javacourse.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        // Инициализируем менеджер задач
        TaskManager taskManager = Managers.getDefault();

        // создаём задачи
        Task task1 = new Task("Task 1", "Описание Task 1", 0, Status.NEW);
        Task task2 = new Task("Task 2", "Описание Task 2", 0, Status.NEW);

        // создаём эпик и подзадачу
        Epic epic1 = new Epic("Epic 1", "Описание Epic 1", 0, Status.NEW);
        taskManager.createEpic(epic1); // важно создать эпик заранее — у него появится ID

        Subtask subtask1 = new Subtask("Subtask 1", "Описание Subtask 1", 0, Status.NEW, epic1);

        // сохраняем задачи
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createSubtask(subtask1);

        // обращаемся к задачам, чтобы сформировать историю
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());

        // выводим всё в консоль
        printAllTasks(taskManager);
    }

    /**
     * Печатает все задачи, эпики, подзадачи и историю просмотров.
     */
    private static void printAllTasks(TaskManager manager) {
        System.out.println("=== Задачи ===");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\n=== Эпики и их подзадачи ===");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Subtask subtask : manager.getSubtasksForEpic(epic.getId())) {
                System.out.println("   --> " + subtask);
            }
        }

        System.out.println("\n=== Все подзадачи ===");
        for (Subtask subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("\n=== История просмотров ===");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}