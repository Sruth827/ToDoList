package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;



public class MyController {
	public enum AlertState {CONFIRMATION, WARNING_SEL, WARNING_DEL, WARNING_NUM, INFORMATION} ;
	public enum TaskType {DO, SCHEDULE, DELEGATE, DELETE};
	private final Map<ToggleButton, TaskType> buttonPriorityMap = new HashMap<>();
	private List<Task> tasks;
	private ObservableList<Task> taskObservableList; 
	private ObservableList<Task> completedObservableList;
	private String inputName;
	private String inputDescript;
	private Integer minutes;
	private Task newTask;
	private DoubleProperty progressCompleted = new SimpleDoubleProperty(0);
	TaskFileHandler taskFileHandler = new TaskFileHandler("tasks.json");
	
	@FXML
	TextField TaskName = new TextField(); 
	@FXML 
	TextArea TaskDescription = new TextArea(); 
	@FXML 
	TextField MinsReq = new TextField(); 
	@FXML
	Button submitButton = new Button();
	
	@FXML
	final ToggleGroup MatrixButtons = new ToggleGroup(); 
	@FXML
	ToggleButton matrixToggle1 = new ToggleButton(); 
	@FXML
	ToggleButton matrixToggle2 = new ToggleButton();
	@FXML
	ToggleButton matrixToggle3 = new ToggleButton(); 
	@FXML
	ToggleButton matrixToggle4 = new ToggleButton();
	@FXML
	ProgressBar updateProgress = new ProgressBar(0); 
	@FXML 
	ListView<Task> TaskList;
	@FXML 
	ListView<Task> CompletedList;
	
	
	
	
	@FXML
	public void initialize() {
		
		
		//menu1.getItems().add(saveButton);
		
		//ToggleGroup for exclusive selection of priority 
		matrixToggle1.setToggleGroup(MatrixButtons); 
		matrixToggle2.setToggleGroup(MatrixButtons);
		matrixToggle3.setToggleGroup(MatrixButtons);
		matrixToggle4.setToggleGroup(MatrixButtons);
		
		buttonPriorityMap.put(matrixToggle1, TaskType.DO);
	    buttonPriorityMap.put(matrixToggle2, TaskType.SCHEDULE);
	    buttonPriorityMap.put(matrixToggle3, TaskType.DELEGATE);
	    buttonPriorityMap.put(matrixToggle4, TaskType.DELETE);
		
		
		updateProgress.progressProperty().bind(progressCompleted);
		updateProgress.setStyle("-fx-accent: blue");
		
		
		//Visual update of toggle buttons on eisenhower matrix for task priority selected
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
		tasks = new ArrayList<>();
		taskObservableList = FXCollections.observableArrayList(); 
		SortedList<Task> sortedList = new SortedList<>(taskObservableList);
		
		completedObservableList = FXCollections.observableArrayList();
		
		//Sort tasks in order of priority and then by time required 
		sortedList.setComparator((task1, task2) -> {
			int priorityComparison = task1.getTaskType().compareTo(task2.getTaskType());
			if(priorityComparison != 0) {
				return priorityComparison;
			}
			
			return task1.getMinutesRequired().compareTo(task2.getMinutesRequired()); 
		});
		
		TaskList.setItems(sortedList);
		CompletedList.setItems(completedObservableList);
		//this is to allow for custom coloring of tasks displayed in Listview
		TaskList.setCellFactory(new TaskCellFactory());
		CompletedList.setCellFactory(new TaskCellFactory());
		
	}
	
	private void resetToggles() {
	    matrixToggle1.setStyle(null);
	    matrixToggle2.setStyle(null);
	    matrixToggle3.setStyle(null);
	    matrixToggle4.setStyle(null);
	    MatrixButtons.selectToggle(null);
	}
	  
	@FXML
	public void submitTask() {
		this.inputName = TaskName.getText();  //Name of task from user input text field
		this.inputDescript = TaskDescription.getText(); //Description of task from user input text field
		
		//Minutes required for task with exception catch
	    try {
	        this.minutes = Integer.parseInt(MinsReq.getText());
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Invalid input for minutes. Please enter a valid number.");
	        sendAlert(AlertState.WARNING_NUM);
	        return;
	    } 
		
	    //creation of submitted task
	    ToggleButton selectedButton = (ToggleButton) MatrixButtons.getSelectedToggle();
	    if (selectedButton == null) {
	        sendAlert(AlertState.WARNING_SEL); // Alert user to select a priority
	        return;
	    }
	    TaskType selectedTask = buttonPriorityMap.get(selectedButton);

	    
	    this.newTask = TaskFactory.createTask(inputName, inputDescript, minutes, selectedTask);
	    
	    //task submitted to ListView for sorting and dispay
		taskObservableList.add(newTask);
		System.out.println(newTask);
		tasks.add(newTask);
		//reset toggles after submit
		resetToggles();
		
		
	}

	public void deleteTask() {
		//Task that needs to be deleted - selected from ListView
		Task selectedTask = TaskList.getSelectionModel().getSelectedItem();
		
		//If task selected, delete and show alert, if no task selected alert warning
		if(selectedTask != null) {
			completedObservableList.add(selectedTask);
			taskObservableList.remove(selectedTask);
			tasks.remove(selectedTask);
			TaskList.getSelectionModel().clearSelection();
			sendAlert(AlertState.CONFIRMATION); //update alertState and pass to function to send Alert corresponding with alert needed
			System.out.println("Deleted task " + selectedTask.getName());
			updateProgress(progressCompleted);
		} else {
			sendAlert(AlertState.WARNING_DEL);
			
		}
	}
	
	@FXML
	public void editTask() {
		//Task that needs to be edited - selected from ListView
		Task selectedTask = TaskList.getSelectionModel().getSelectedItem();
		
		//If task selected, remove from listview, show alert notification, place name and description back in textfield and area. if no task selected alert warning
		if(selectedTask != null) {
			taskObservableList.remove(selectedTask);
			tasks.remove(selectedTask);
			TaskName.setText(selectedTask.getName());
			TaskDescription.setText(selectedTask.getDescription());
			MinsReq.setText(null);
			TaskList.getSelectionModel().clearSelection();
			sendAlert(AlertState.INFORMATION);
		} else {
			sendAlert(AlertState.WARNING_SEL); //update alertState and pass to function to send Alert corresponding with alert needed
		}
	}

	
	public void updateProgress(DoubleProperty progressCompleted) {
		//update percentage of progress bar when tasks are completed
		//progressCompleted.set(progressCompleted.get() + 0.05);
		int totalTasks = taskObservableList.size() + completedObservableList.size();
	    if (totalTasks > 0) {
	        progressCompleted.set((double) completedObservableList.size() / totalTasks);
	    }
		System.out.println(progressCompleted);
		
	}
	
	public void sendAlert(AlertState alertState) {
		Alert alert;
		switch(alertState) {
			case CONFIRMATION:
				alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("GREAT WORK"); 
				alert.setHeaderText(null);
				alert.setContentText("Great Job! You're getting things done!");
				alert.show();
				break;
			case WARNING_SEL:
				alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("No Selection"); 
				alert.setHeaderText(null);
				alert.setContentText("Please select a task to edit.");
				alert.showAndWait();
				break;
			case WARNING_DEL:
				alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("No Selection"); 
				alert.setHeaderText(null);
				alert.setContentText("Please select a task to delete.");
				alert.showAndWait();
				break;
			case WARNING_NUM:
				alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("No Selection"); 
				alert.setHeaderText(null);
				alert.setContentText("Error: Invalid input for minutes. Please enter a valid number.");
				alert.showAndWait();
				break;
			case INFORMATION:
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Edit selection");
				alert.setHeaderText(null);
				alert.setContentText("Edit by inputting new values and click submit");
				alert.show();
				break;
		}
	}

	public void handleSaves() {
		try {
			taskFileHandler.saveTasks(tasks);
			System.out.println("Save successful");
		} catch (IOException e) {
			System.err.println("Error saving" + e.getMessage());
		}
	}
	
	public void loadSaves() {
		try {
			tasks = taskFileHandler.loadTasks();
			System.out.println("Load successful");
			for(Task task : tasks) {
				System.out.println(task.getName() + " - " + task.getDescription() + " - " + task.getMinutesRequired() + " - " + task.getTaskType());
				taskObservableList.add(task);
			}
		}catch (IOException e) {
			System.err.println("Error loading" + e.getMessage());
		}
	}
}

	

	