package ru.yandex.javacourse.service;

public final class Managers {

    private Managers() {
        // Приватный конструктор: запретить создание экземпляра этого класса.
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
