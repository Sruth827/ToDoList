package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MyController {
	
	private TaskSelectionListener listener; 
	
	//Set the listener
	public void setTaskSelectionListener(TaskSelectionListener listener) {
		this.listener = listener; 
	}
	private int selectedTaskID;
	
	public int getSelectedTaskID() {
		return selectedTaskID; 
	}

	@FXML
	public void handleTaskSelection(ActionEvent event) {
		//Get the button that was selected
	    Button clickedButton = (Button) event.getSource(); 
	    
	    //switch case to assign taskID priority 
	    this.selectedTaskID = switch(clickedButton.getText()) {
	    case "Task1" -> 1; 
	    case "Task2" -> 2;
	    case "Task3" -> 3; 
	    case "Task4" -> 4; 
		default -> throw new IllegalArgumentException("Unexpected value: " + clickedButton.getText()); 
	    };
	    
	    task newTask = TaskFactory.createTask("Example Name", "Example Description", 60, selectTaskID);
	    System.out.println("Created: " + newTask.getClass().getSimpleName());
	}
}
