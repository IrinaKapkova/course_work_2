package task;


import java.time.LocalDate;

public interface Repeatable {
    boolean checkCompliance(LocalDate date);
}