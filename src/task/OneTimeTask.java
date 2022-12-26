package task;

import java.time.LocalDateTime;

public class OneTimeTask extends Task {
    public OneTimeTask(String name, String description, boolean working, LocalDateTime date) {
        super(name, description, working, date);
    }
    @Override
    protected String getType() {
        return "Однократная";
    }


}
