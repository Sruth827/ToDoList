package application;

import java.time.LocalDateTime;

abstract class Task implements TaskSelectionListener {
	
	private String name;
	private String description;
	private Integer minutesRequired;
	private Integer taskPriority; 
	
	public task( String name, String description, Integer minutesRequired) {
		if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (minutesRequired == null || minutesRequired <= 0) {
            throw new IllegalArgumentException("Minutes required must be positive");
        }
		this.name = name; 
		this.description = description; 
		this.minutesRequired = minutesRequired;
	}
	
}

class DoTaskItem extends Task{

	public DoTaskItem(String name, String description , Integer minutesRequired) {
		super(name, description, minutesRequired);
	}


}

class ScheduleTaskItem extends Task {

	public ScheduleTaskItem(String name, String description, Integer minutesRequired) {
		super(name, description, minutesRequired);
	}
	
}

class DelegateTaskItem extends task {

	public DelegateTaskItem(String name, String description, Integer minutesRequired) {
		super(name, description, minutesRequired);
	}
	
}

class DeleteTaskItem extends task{

	public DeleteTaskItem(String name, String description, Integer minutesRequired) {
		super(name, description, minutesRequired);
	}
	
}

