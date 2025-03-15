package application;

public class TaskFactory {

    public static task createTask(String name, String description, Integer minutesRequired, int selectTaskID) {
        return switch (selectTaskID) {
            case 1 -> new DoTaskItem(name, description, minutesRequired);
            case 2 -> new ScheduleTaskItem(name, description, minutesRequired);
            case 3 -> new DelegateTaskItem(name, description, minutesRequired);
            case 4 -> new DeleteTaskItem(name, description, minutesRequired);
            default -> throw new IllegalArgumentException("Invalid Task ID");
        };
    }
}
 