package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyTask extends Task implements Repeatable {
    public YearlyTask(String title, String description, boolean working, LocalDateTime dateTime) {
        super(title, description, working, dateTime);
    }

    @Override
    protected String getType() {
        return "Ежегодная";
    }

    @Override
    public boolean checkCompliance(LocalDate date) {
        return date.getDayOfMonth() == dateTime.getDayOfMonth() && date.getMonth() == dateTime.getMonth();
    }

}