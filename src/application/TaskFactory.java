package application;

import application.MyController.TaskType;

public class TaskFactory {

    public static Task createTask(String name, String description, Integer minutesRequired, TaskType taskType) {
        return switch (taskType) {
            case DO -> new DoTaskItem(name, description, minutesRequired, taskType);
            case SCHEDULE -> new ScheduleTaskItem(name, description, minutesRequired, taskType);
            case DELEGATE -> new DelegateTaskItem(name, description, minutesRequired, taskType);
            case DELETE -> new DeleteTaskItem(name, description, minutesRequired, taskType);
            default -> throw new IllegalArgumentException("Invalid Task ID");
        };
    }
} 

