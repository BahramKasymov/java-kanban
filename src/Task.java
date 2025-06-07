

public class Task {
    private String title;
    private String description;
    private int id;
    private Status status;


    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }
}
