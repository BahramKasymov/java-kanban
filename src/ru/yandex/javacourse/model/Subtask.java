package ru.yandex.javacourse.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int id, Status status, Epic epic) {
        super(title, description, id, status);
        this.epicId = epic.getId();
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
    }
}
