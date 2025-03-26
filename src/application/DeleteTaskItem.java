package application;

import application.MyController.TaskType;

public class DeleteTaskItem extends Task{
	public DeleteTaskItem() {
		super();
		setTaskType(TaskType.DELETE);
	}
	public DeleteTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}

