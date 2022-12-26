import task.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Consumer;

public class Main {

    public static Calendar calendar = new Calendar();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                menu();
                System.out.println("Выберите пункт меню соответствующий необходимым действиям: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            getItemMenu(scanner, Main::inputTask);
                            break;
                        case 2:
                            getItemMenu(scanner, Main::editTask);
                            break;
                        case 3:
                            getItemMenu(scanner, Main::removeTask);
                            break;
                        case 4:
                            getItemMenu(scanner, Main::printTasksForTheDay);
                            break;
                        case 5:
                            getItemMenu(scanner, Main::printTasksForPeriod);
                            break;
                        case 6:
                            getItemMenu(scanner, Main::printDeletedTasks);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Неверный выбор! Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        System.out.println("Внесите имя задачи:");
        String name = scanner.nextLine();
        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine();
        boolean working;
        System.out.println("Это задача рабочая? \nЕсли утвержденние верно, внесите:\nДА,\nиначе: нажмите Enter");
        switch (scanner.nextLine()) {
            case "ДА":
            case "да":
            case "Да":
                working = true;
                break;
            default:
                working = false;
        }
        LocalDateTime date = null;
        System.out.println("Внесите дату и время задачи в формате ДД.ММ.ГГГГ ЧЧ:ММ");
        boolean shouldEnterAgain = true;
        while (shouldEnterAgain) {
            try {
                date = LocalDateTime.parse(scanner.nextLine(), Task.DateTimeFormat);
                shouldEnterAgain = false;
            } catch (DateTimeParseException e) {
                System.out.println("Формат ввода неверный, внесите данные еще раз:");
            }
        }
        Task task;
        System.out.println("Повторяемость задания:");
        System.out.println("\t ● 0 – однократная");
        System.out.println("\t ● 1 – ежедневная");
        System.out.println("\t ● 2 – еженедельная");
        System.out.println("\t ● 3 – ежемесячная");
        System.out.println("\t ● 4 – ежегодная");
        switch (scanner.next()) {
            case "1":
                task = new DailyTask(name, description, working, date) {
                    @Override
                    public boolean checkCompliance(LocalDate date) {
                        return false;
                    }
                };
                break;
            case "2":
                task = new WeeklyTask(name, description, working, date);
                break;
            case "3":
                task = new MonthlyTask(name, description, working, date);
                break;
            case "4":
                task = new YearlyTask(name, description, working, date);
                break;
            default:
                task = new OneTimeTask(name, description, working, date);
        }
        if (calendar.addTask(task)) {
            System.out.println("Добавлена задача: \n" + task + "\n");
            return;
        }
        throw new RuntimeException("Программа завершена из-за ошибки");
    }

    private static void editTask(Scanner scanner) {
        int id = enterId(scanner);
        Task task = calendar.getTask(id);
        if (task != null) {
            System.out.println("Прежний заголовок задачи:");
            System.out.println(task.getName());
            System.out.println("Введите новый заголовок. Если изменения заголовка не требуется нажмите Enter");
            String name = scanner.nextLine();
            System.out.println();
            System.out.println("Прежнее описание задачи:");
            System.out.println(task.getDescription());
            System.out.println("Внесите новое описание. Если изменений описаний не требуется нажмите Enter");
            String description = scanner.nextLine();
            System.out.println();
            calendar.editTask(id, name, description);
            System.out.println("Задача отредактирована и сохранена в еженедельнике со следующими показателями: \n" + task + "\n");
        } else {
            System.out.println("Задачи с указанным Вами ID пока еще нет в еженедельнике");
        }
    }

    private static void removeTask(Scanner scanner) {
        int id = enterId(scanner);
        if (calendar.removeTask(id)) {
            System.out.println("Задача с ID " + id + " была удалена из ежедневника");
        } else {
            System.out.println("Задачи с указанным ID пока еще нет");
        }
    }

    private static void printTasksForTheDay(Scanner scanner) {
        System.out.println("Введите дату в формате ДД.ММ.ГГГГ:");
        LocalDate date = enterDate(scanner);
        printTasksForThisDay(scanner);
    }

    private static void printTasksForPeriod(Scanner scanner) {
        System.out.println("Введите начало периода в формате ДД.ММ.ГГГГ:");
        LocalDate begin = enterDate(scanner);
        System.out.println("Введите конец периода в формате ДД.ММ.ГГГГ:");
        LocalDate end = enterDate(scanner);
        while (begin.isBefore(end.plusDays(1))) {
            printTasksForThisDay(scanner);
            begin = begin.plusDays(1);
        }
    }

    private static void printDeletedTasks(Scanner scanner) {
        System.out.println("Удаленные задания:");
        for (Task task : calendar.showDeletedTasks().values()) {
            System.out.println(task);
        }
    }

    private static void menu() {
        System.out.println("\n ● 1. Добавить задачу" +
                "\n ● 2. Редактировать задачу" +
                "\n ● 3. Удалить задачу" +
                "\n ● 4. Получить задачи на указанный день" +
                "\n ● 5. Показать задачи по дням" +
                "\n ● 6. Показать удаленные задачи" +
                "\n ● 0. Выход из меню");
    }

    private static void getItemMenu(Scanner scanner, Consumer<Scanner> consumer) {
        scanner.nextLine();
        consumer.accept(scanner);
    }

    private static int enterId(Scanner scanner) {
        System.out.println("Введите ID задачи:");
        boolean shouldEnterAgain = true;
        int id = -1;
        while (shouldEnterAgain) {
            try {
                id = Integer.parseInt(scanner.nextLine());
                shouldEnterAgain = false;
            } catch (NumberFormatException e) {
                System.out.println("Формат ввода неверный, внесите данные еще раз:");
            }
        }
        return id;
    }

    private static LocalDate enterDate(Scanner scanner) {
        boolean shouldEnterAgain = true;
        LocalDate date = null;
        while (shouldEnterAgain) {
            try {
                date = LocalDate.parse(scanner.nextLine(), Task.DateFormat);
                shouldEnterAgain = false;
            } catch (DateTimeParseException e) {
                System.out.println("Формат ввода неверный, внесите данные еще раз:");
            }
        }
        return date;
    }

    public static void printTasksForThisDay(Scanner scanner) {
        LocalDate localDate = enterDate(scanner);
        Collection<Task> taskForDate = calendar.getTasksForDay(localDate);
        System.out.println("Задачи на " + localDate.format(Task.DateFormat));
        for (Task task : taskForDate) {
            System.out.println(task.toString());
        }
    }
}