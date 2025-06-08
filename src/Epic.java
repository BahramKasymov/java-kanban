import java.util.ArrayList;
import java.util.List;

class Epic extends Task {

    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String title, String description, int id, Status status) {
        super(title, description, id, status);
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        subtask.setEpic(this);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void updateEpicStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);
        } else if (subtasks.stream().allMatch(subtask -> subtask.getStatus() == Status.DONE)) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }


}
