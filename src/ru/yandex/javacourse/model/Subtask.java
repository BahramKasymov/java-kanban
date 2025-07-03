package ru.yandex.javacourse.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int id, Status status, Epic epic) {
        super(title, description, id, status);
        setEpic(epic); // Используем единый метод с проверкой!
    }

    public int getEpicId() {
        return epicId;
    }

    /**
     * Приватный сеттер ID — никто извне не сможет обойти проверку.
     */
    private void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    /**
     * Правильный способ установить Epic с проверкой.
     */
    public void setEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic cannot be null");
        }
        if (epic.getId() == this.getId()) {
            throw new IllegalArgumentException("Subtask cannot be its own epic");
        }
        setEpicId(epic.getId()); // вызывем безопасный метод
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}
