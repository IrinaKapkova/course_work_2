package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Task {
    public static final DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter DateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static int counter=1;
    private final int id;
    private String name;
    private String description;
    private final boolean working;
    protected LocalDateTime dateTime;
    private boolean valid;

    public Task(String name, String description, boolean working, LocalDateTime date) {
        setName(name);
        setDescription(description);
        this.working = working;
        this.id = counter++;
        this.dateTime = date;
        valid = true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isValid() {
        return valid;
    }
    public static String validateString(String value, String value2) {
        return value == null || value.isBlank() || value.isEmpty() || value.trim().length() == 0 ? value2 : value;
    }
    public void setName(String name) {
        this.name = validateString (name, "Введите корректно заголовок задачи!");
    }

    public void setDescription(String description) {
        this.description = validateString ( description, "Введите корректно описание задачи!");
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && working == task.working && valid == task.valid && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(dateTime, task.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, working, dateTime, valid);
    }

    protected abstract String getType();

    @Override
    public String toString() {
        return String.format("Заголовок: %s,\nID: %d \nТип: %s,\nПовторяемость: %s,\n" +
                        "Первоначально запланированное время выполнения: %s\n" +
                        "Описание задачи: %s\n",
                name, id, working ? "Рабочая" : "Личная", getType(),
                dateTime.format(DateTimeFormat),
                description);
    }
}