package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task implements Repeatable {
    public WeeklyTask(String title, String description, boolean working, LocalDateTime dateTime) {
        super(title, description, working, dateTime);
    }

    @Override
    protected String getType() {
        return "Еженедельная";
    }

    @Override
    public boolean checkCompliance(LocalDate date) {
        return date.getDayOfWeek() == dateTime.getDayOfWeek();
    }
}