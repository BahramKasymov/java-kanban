

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();


        // Создаём задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", 2, Status.IN_PROGRESS);

        manager.createTask(task1);
        manager.createTask(task2);

        // Создаем эпики с подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 3, Status.NEW);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 4, Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 5, Status.IN_PROGRESS, epic1);

        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        // Печатаем все задачи, эпики и подзадачи
        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все подзадачи: " + manager.getAllSubtasks());

        // Изменяем статус задачи и проверяем статус эпика
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);
        System.out.println("Статус задачи 1 после обновления: " + task1.getStatus());
        epic1.updateEpicStatus(); // обновляем статус эпика
        manager.updateEpic(epic1); // теперь обновляем эпик

        // Удаляем задачи и эпик
        manager.deleteTaskById(1);
        manager.deleteEpicById(3);
        System.out.println("После удаления:");
        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
    }


}