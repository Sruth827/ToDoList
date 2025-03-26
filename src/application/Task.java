package application;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import application.MyController.TaskType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "taskType")
@JsonSubTypes({
	@JsonSubTypes.Type(value = DoTaskItem.class, name = "DO"),
	@JsonSubTypes.Type(value = ScheduleTaskItem.class, name = "SCHEDULE"),
	@JsonSubTypes.Type(value = DelegateTaskItem.class, name = "DELEGATE"),
	@JsonSubTypes.Type(value = DeleteTaskItem.class, name = "DELETE")
})

public abstract class Task {
	  
	private String name;
	private String description;
	private Integer minutesRequired;
	private TaskType taskType;
	
	public Task() {}
	
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
	
	public void setName(String name) {
		this.name = name; 
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	public Integer getMinutesRequired() {
		return minutesRequired;
	}
	
	public void setMinutesRequired(Integer minutesRequired) {
		this.minutesRequired = minutesRequired; 
	}
	
	public TaskType getTaskType() {
		return taskType;
	}
	
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType; 
	}
	

}


