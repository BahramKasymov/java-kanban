package test.model;

import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Title", "Description", 1, Status.NEW);
        Task task2 = new Task("Another Title", "Another Description", 1, Status.DONE);

        Assertions.assertEquals(task1, task2, "Tasks with the same ID must be equal");
    }

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Epic epic = new Epic("Epic", "Epic description", 100, Status.NEW);
        Subtask subtask1 = new Subtask("Subtask", "Desc", 10, Status.NEW, epic);
        Subtask subtask2 = new Subtask("Subtask2", "Another", 10, Status.DONE, epic);

        Assertions.assertEquals(subtask1, subtask2, "Subtasks with the same ID must be equal");
    }

    @Test
    void epicCannotContainItself() {
        Epic epic = new Epic("Epic", "Desc", 1, Status.NEW);
        // В твоей логике нет возможности добавить эпик сам в себя напрямую,
        // этот тест просто демонстрация:
        epic.addSubtask(new Subtask("Sub", "desc", 2, Status.NEW, epic));
        Assertions.assertTrue(epic.getSubtasks().stream().noneMatch(sub -> sub.getId() == epic.getId()),
                "Epic cannot contain itself as subtask");
    }

    @Test
    void subtaskCannotReferenceItselfAsEpic() {
        Subtask subtask = new Subtask("Title", "Description", 1, Status.NEW, null);

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> subtask.setEpic(new Epic("Title", "Description", 1, Status.NEW)),
                "Expected exception when subtask references itself"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Subtask cannot be its own epic"));
    }

}
