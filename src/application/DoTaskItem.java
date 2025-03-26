package application;

import application.MyController.TaskType;

public class DoTaskItem extends Task{
	public DoTaskItem() {
		super();
		setTaskType(TaskType.DO);
	}
	public DoTaskItem(String name, String description , Integer minutesRequired, TaskType taskType ) {
		super(name, description, minutesRequired, taskType);	
	}
}