package ru.yandex.javacourse.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int id, Status status, Epic epic) {
        super(title, description, id, status);
        if (epic != null) {
            if (epic.getId() == id) {
                throw new IllegalArgumentException("Subtask cannot be its own epic");
            }
            this.epicId = epic.getId();
        }
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
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

    public void setEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic cannot be null");
        }
        if (epic.getId() == this.getId()) {
            throw new IllegalArgumentException("Subtask cannot be its own epic");
        }
        this.epicId = epic.getId();
    }
}
