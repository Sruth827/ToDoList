package application;

import application.MyController.TaskType;

public class ScheduleTaskItem extends Task {
	public ScheduleTaskItem() {
		super();
		setTaskType(TaskType.SCHEDULE);
	}
	public ScheduleTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}