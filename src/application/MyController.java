package application;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class MyController {
	
	private String inputName;
	private String inputDescript;
	private Integer minutes;
	private Integer taskID; 
	private Task newTask;
	@FXML
	final ToggleGroup MatrixButtons = new ToggleGroup(); 
	@FXML
	TextField TaskName = new TextField(); 
	@FXML 
	TextArea TaskDescription = new TextArea(); 
	@FXML 
	TextField MinsReq = new TextField(); 
	@FXML
	Button submitButton = new Button();
	@FXML
	ToggleButton matrixToggle1 = new ToggleButton(); 
	@FXML
	ToggleButton matrixToggle2 = new ToggleButton();
	@FXML
	ToggleButton matrixToggle3 = new ToggleButton(); 
	@FXML
	ToggleButton matrixToggle4 = new ToggleButton();
	@FXML 
	private ListView<Task> TaskList;
	
	private ObservableList<Task> taskObservableList; 
	
	public void initialize() {
		//ToggleGroup for exclusive selection of priority 
		matrixToggle1.setToggleGroup(MatrixButtons); 
		matrixToggle2.setToggleGroup(MatrixButtons);
		matrixToggle3.setToggleGroup(MatrixButtons);
		matrixToggle4.setToggleGroup(MatrixButtons);
		
		 MatrixButtons.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
	            if (newToggle == matrixToggle1) {
	            	matrixToggle1.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
	            	matrixToggle2.setStyle(null);
	            	matrixToggle3.setStyle(null);
	            	matrixToggle4.setStyle(null);
	            } else if (newToggle == matrixToggle2) {
	            	matrixToggle1.setStyle(null);
	            	matrixToggle2.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
	            	matrixToggle3.setStyle(null);
	            	matrixToggle4.setStyle(null);
	            } else if (newToggle == matrixToggle3) {
	            	matrixToggle1.setStyle(null);
	            	matrixToggle2.setStyle(null);
	            	matrixToggle3.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
	            	matrixToggle4.setStyle(null);
	            } else if (newToggle == matrixToggle4) {
	            	matrixToggle1.setStyle(null);
	            	matrixToggle2.setStyle(null);
	            	matrixToggle3.setStyle(null);
	            	matrixToggle4.setStyle("-fx-background-color: red; -fx-text-fill: black;");
	            } else {
	                // Clear all styles if no toggle is selected
	            	matrixToggle1.setStyle(null);
	            	matrixToggle2.setStyle(null);
	            	matrixToggle3.setStyle(null);
	            	matrixToggle4.setStyle(null);
	            }
	        });
		
		//Create the sorted list object
		taskObservableList = FXCollections.observableArrayList(); 
		SortedList<Task> sortedList = new SortedList<>(taskObservableList);
		
		//Sort tasks in order of priority and then by time required 
		sortedList.setComparator((task1, task2) -> {
			int priorityComparison = task1.getTaskPriority().compareTo(task2.getTaskPriority());
			if(priorityComparison != 0) {
				return priorityComparison;
			}
			
			return task1.getMinutesRequired().compareTo(task2.getMinutesRequired()); 
		});
		
		TaskList.setItems(sortedList);
		//this is to allow for custom coloring of tasks displayed in Listview
		TaskList.setCellFactory(new TaskCellFactory());
	}
	
	@FXML
    public void setDoTaskID() {
        taskID = 1; // Task ID for "Do"
        
        System.out.println("TaskID set to: " + taskID);
    }

    @FXML
    public void setScheduleTaskID() {
        taskID = 2; // Task ID for "Delegate"
        System.out.println("TaskID set to: " + taskID);
    }

    @FXML
    public void setDelegateTaskID() {
        taskID = 3; // Task ID for "Defer"
        System.out.println("TaskID set to: " + taskID);
    }

    @FXML
    public void setDeleteTaskID() {
        taskID = 4; // Task ID for "Delete"
        System.out.println("TaskID set to: " + taskID);
    }
	  
	@FXML
	public void submitTask() {
		this.inputName = TaskName.getText(); 
		this.inputDescript = TaskDescription.getText();
		
	    try {
	        this.minutes = Integer.parseInt(MinsReq.getText());
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Invalid input for minutes. Please enter a valid number.");
	        return;
	    } 
		this.newTask = TaskFactory.createTask(inputName, inputDescript, minutes, taskID);
   
		taskObservableList.add(newTask);
		
	}
	
	public void deleteTask() {
		//Task that needs to be deleted
		Task selectedTask = TaskList.getSelectionModel().getSelectedItem();
		
		//If task selected, delete and show alert, if no task selected alert warning
		if(selectedTask != null) {
			taskObservableList.remove(selectedTask);
			TaskList.getSelectionModel().clearSelection();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("GREAT WORK"); 
			alert.setHeaderText(null);
			alert.setContentText("Great Job! You're getting things done!");
			alert.show();
			System.out.println("Deleted task " + selectedTask.getName());
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Selection"); 
			alert.setHeaderText(null);
			alert.setContentText("Please select a task to delete.");
			alert.showAndWait();
		}
	}
	
}

	