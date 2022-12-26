import task.Repeatable;
import task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Calendar {
    private final Map<Integer, Task> tasks;

    public Calendar() {
        tasks = new HashMap<>();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public boolean addTask(Task task) {
        return tasks.put(task.getId(), task) == null;
    }

    public static boolean validateStringBoolean(String value) {
        return value != null && !value.isBlank();
    }

    public boolean editTask(int id, String title, String description) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            if (validateStringBoolean(title)) {
                task.setName(title);
            }
            if (validateStringBoolean(description)) {
                task.setDescription(description);
            }
            return true;
        }
        return false;
    }

    public boolean removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.get(id).setValid(false);
            return true;
        }
        return false;
    }
    public List<Task> getTasksForDay(LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Task task = entry.getValue();
            if (task instanceof Repeatable && ((Repeatable) task).checkCompliance(date)
                    || !(task instanceof Repeatable) && task.getDateTime().toLocalDate().equals(date)) {
                result.add(task);
            }
        }
        result.sort(Comparator.comparing(Task::getDateTime));
        return result;
    }


    public HashMap<LocalTime, Task> showDeletedTasks() {
        HashMap<LocalTime, Task> resume = new HashMap<>();
        for (Task task : tasks.values()) {
            if (!task.isValid()) {
                putTaskToMap(task, resume);
            }
        }
        return resume;
    }

    private void putTaskToMap(Task task, Map<LocalTime, Task> map) {
        Task prevTask = map.put(task.getDateTime().toLocalTime(), task);
        int nanosCounter = 0;
        while (prevTask != null) {
            prevTask = map.put(prevTask.getDateTime().toLocalTime().plusNanos(++nanosCounter), prevTask);
        }
    }
}