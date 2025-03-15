package application;

import java.time.LocalDateTime;


abstract class task {
	private LocalDateTime dateEntered; 
	private String name;
	private String description;
	private Integer minutesRequired;
	
	public task(LocalDateTime dateEntered, String name, String description, Integer minutesRequired) {
		this.dateEntered = dateEntered;
		this.name = name; 
		this.description = name; 
		this.minutesRequired = minutesRequired;
	}
}

class DoTaskItem extends task{

	public DoTaskItem(LocalDateTime dateEntered, String name, String description , Integer minutesRequired) {
		super(dateEntered, name, description, minutesRequired);
		// TODO Auto-generated constructor stub
	}

}

class ScheduleTaskItem extends task {

	public ScheduleTaskItem(LocalDateTime dateEntered, String name, String description, Integer minutesRequired) {
		super(dateEntered, name, description, minutesRequired);
		// TODO Auto-generated constructor stub
	}
	
}

class DelegateTaskItem extends task {

	public DelegateTaskItem(LocalDateTime dateEntered, String name, String description, Integer minutesRequired) {
		super(dateEntered, name, description, minutesRequired);
		// TODO Auto-generated constructor stub
	}
	
}

class DeleteTaskItem extends task{

	public DeleteTaskItem(LocalDateTime dateEntered, String name, String description, Integer minutesRequired) {
		super(dateEntered, name, description, minutesRequired);
		// TODO Auto-generated constructor stub
	}
	
}
