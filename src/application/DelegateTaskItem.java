package application;

import application.MyController.TaskType;

public class DelegateTaskItem extends Task {
	public DelegateTaskItem() {
		super(); 
		setTaskType(TaskType.DELEGATE);
	}
	public DelegateTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}
