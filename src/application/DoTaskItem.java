package application;

import application.MyController.TaskType;

abstract class Task {
	  
	protected String name;
	protected String description;
	protected Integer minutesRequired;
	protected TaskType taskType;
	
	public Task(String name, String description, Integer minutesRequired, TaskType taskType) {
		if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (minutesRequired == null || minutesRequired <= 0) {
            throw new IllegalArgumentException("Minutes required must be positive");
        }
		this.name = name; 
		this.description = description == null || description.isEmpty() ? "No description provided" : description;
		this.minutesRequired = minutesRequired;
		this.taskType = taskType;
	}
	

	public String getName() {
		return name; 
	}
	
	public String getDescription() {
		return description;
	}
	
	public Integer getMinutesRequired() {
		return minutesRequired;
	}
	
	public TaskType getTaskType() {
		return taskType;
	}
	
	@Override
	public String toString() {
	    return String.format("Task{name='%s', description='%s', minutesRequired=%d, taskType=%s}", 
	                         name, description, minutesRequired, taskType);
	}


}

class DoTaskItem extends Task{

	public DoTaskItem(String name, String description , Integer minutesRequired, TaskType taskType ) {
		super(name, description, minutesRequired, taskType);	
	}
}

class ScheduleTaskItem extends Task {

	public ScheduleTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}

class DelegateTaskItem extends Task {

	public DelegateTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}

class DeleteTaskItem extends Task{

	public DeleteTaskItem(String name, String description, Integer minutesRequired, TaskType taskType) {
		super(name, description, minutesRequired, taskType);
	}
	
}

