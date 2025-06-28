package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.InMemoryTaskManager;
import ru.yandex.javacourse.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        // Создание задач, связанных с покупкой машины
        Task task1 = new Task("Исследовать модели автомобилей", "Сравните популярные модели автомобилей", 0, Status.NEW);
        Task task2 = new Task("Проверить бюджет", "Подсчитать, сколько можно потратить на машину", 0, Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Процесс покупки автомобиля", "Шаги по покупке автомобиля", 0, Status.NEW);
        Subtask subtask1 = new Subtask("Поиск автосалонов", "Найти ближайшие автосалоны", 0, Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Тест-драйв автомобиля", "Запланировать тест-драйв выбранной модели", 0, Status.NEW, epic1);

        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Оформление документов", "Документы для покупки и регистрации автомобиля", 0, Status.NEW);
        Subtask subtask3 = new Subtask("Сбор документов", "Собрать необходимые документы для покупки", 0, Status.NEW, epic2);
        taskManager.createEpic(epic2);
        taskManager.createSubtask(subtask3);

        // Отображение всех задач, подзадач и эпиков
        System.out.println("Все задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());

        // Изменение статусов
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);

        // Обновляем задачи и подзадачи в менеджере
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        // Печать статусов задач и эпиков
        System.out.println("Задача 1: " + task1);
        System.out.println("Задача 2: " + task2);
        System.out.println("Эпик 1: " + epic1 + " (Статус обновлен: " + epic1.getStatus() + ")");
        System.out.println("Эпик 2: " + epic2 + " (Статус обновлен: " + epic2.getStatus() + ")");

        // Удаление одной задачи и одного эпика
        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());

        // Проверка состояний после удаления
        System.out.println("Все задачи после удаления: " + taskManager.getAllTasks());
        System.out.println("Все эпики после удаления: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи после удаления: " + taskManager.getAllSubtasks());

        // Новый блок — проверка истории просмотров
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getTaskById(task2.getId()); // повторный просмотр

        System.out.println("История просмотров:");
        for (Task t : taskManager.getHistory()) {
            System.out.println(t);
        }
    }
}
