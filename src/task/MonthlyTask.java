package task;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task implements Repeatable {
    public MonthlyTask(String title, String description, boolean working, LocalDateTime dateTime) {
        super(title, description, working, dateTime);
    }

    @Override
    protected String getType() {
        return "Ежемесячная";
    }

    @Override
    public boolean checkCompliance(LocalDate date) {
    return date.getDayOfMonth()==dateTime.getDayOfMonth();
    }

}