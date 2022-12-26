package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class DailyTask extends Task implements Repeatable {
    public DailyTask(String name, String description, boolean working, LocalDateTime dateTime) {
        super(name, description, working, dateTime);
    }

    @Override
    protected String getType() {
        return "Ежедневная";
    }

    @Override
    public boolean checkCompliance(LocalDate dateTime) {
        return this.dateTime.toLocalDate().isBefore(ChronoLocalDate.from(dateTime))
                || this.dateTime.toLocalDate().equals(dateTime);
    }
    }
