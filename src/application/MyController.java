package application;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class MyController {
	private final String saveLocation = System.getenv("C:\todosave");
	private File save;
	private String inputName;
	private String inputDescript;
	private Integer minutes;
	private Integer taskID; 
	private Task newTask;
	private Byte alertState;
	private DoubleProperty progressCompleted = new SimpleDoubleProperty(0);
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
	Button saveButton = new Button();
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
	
	private ObservableList<Task> taskObservableList; 
	private ObservableList<Task> completedObservableList;
	
	
	@FXML
	public void initialize() {
		this.alertState = 0;
		if (save == null) {
	        save = new File("tasks.txt"); 
	    }
		//ToggleGroup for exclusive selection of priority 
		matrixToggle1.setToggleGroup(MatrixButtons); 
		matrixToggle2.setToggleGroup(MatrixButtons);
		matrixToggle3.setToggleGroup(MatrixButtons);
		matrixToggle4.setToggleGroup(MatrixButtons);
		
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
		taskObservableList = FXCollections.observableArrayList(); 
		SortedList<Task> sortedList = new SortedList<>(taskObservableList);
		
		completedObservableList = FXCollections.observableArrayList();
		
		//Sort tasks in order of priority and then by time required 
		sortedList.setComparator((task1, task2) -> {
			int priorityComparison = task1.getTaskPriority().compareTo(task2.getTaskPriority());
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
		this.inputName = TaskName.getText();  //Name of task from user input text field
		this.inputDescript = TaskDescription.getText(); //Description of task from user input text field
		
		//Minutes required for task with exception catch
	    try {
	        this.minutes = Integer.parseInt(MinsReq.getText());
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Invalid input for minutes. Please enter a valid number.");
	        return;
	    } 
		
	    //creation of submitted task
	    this.newTask = TaskFactory.createTask(inputName, inputDescript, minutes, taskID);
	    
	    //task submitted to ListView for sorting and dispay
		taskObservableList.add(newTask);
		
		//reset toggles after submit
		matrixToggle1.setStyle(null);
    	matrixToggle2.setStyle(null);
    	matrixToggle3.setStyle(null);
    	matrixToggle4.setStyle(null);
    	MatrixButtons.selectToggle(null);
		
	}

	public void deleteTask() {
		//Task that needs to be deleted - selected from ListView
		Task selectedTask = TaskList.getSelectionModel().getSelectedItem();
		
		//If task selected, delete and show alert, if no task selected alert warning
		if(selectedTask != null) {
			completedObservableList.add(selectedTask);
			taskObservableList.remove(selectedTask);
			TaskList.getSelectionModel().clearSelection();
			sendAlert(alertState = 1); //update alertState and pass to function to send Alert corresponding with alert needed
			System.out.println("Deleted task " + selectedTask.getName());
			updateProgress(progressCompleted);
		} else {
			sendAlert(alertState = 2);
			
		}
	}
	
	@FXML
	public void editTask() {
		//Task that needs to be edited - selected from ListView
		Task selectedTask = TaskList.getSelectionModel().getSelectedItem();
		
		//If task selected, remove from listview, show alert notification, place name and description back in textfield and area. if no task selected alert warning
		if(selectedTask != null) {
			taskObservableList.remove(selectedTask);
			TaskName.setText(selectedTask.getName());
			TaskDescription.setText(selectedTask.getDescription());
			MinsReq.setText(null);
			TaskList.getSelectionModel().clearSelection();
			sendAlert(alertState = 3);
		} else {
			sendAlert(alertState = 2); //update alertState and pass to function to send Alert corresponding with alert needed
		}
	}

	
	public void updateProgress(DoubleProperty progressCompleted) {
		//update percentage of progress bar when tasks are completed
		progressCompleted.set(progressCompleted.get() + 0.05);
		System.out.println(progressCompleted);
		
	}
	
	public void sendAlert(Byte alertState) {
		Alert alert;
		switch(alertState) {
			case 1:
				alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("GREAT WORK"); 
				alert.setHeaderText(null);
				alert.setContentText("Great Job! You're getting things done!");
				alert.show();
				break;
			case 2:
				alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("No Selection"); 
				alert.setHeaderText(null);
				alert.setContentText("Please select a task to delete.");
				alert.showAndWait();
				break;
			case 3:
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Edit selection");
				alert.setHeaderText(null);
				alert.setContentText("Edit by inputting new values and click submit");
				alert.show();
				break;
		}
	}

	public void save_Load() {
        try {
            if (save == null) {
                throw new IllegalStateException("File reference is null.");
            }
            if (!save.exists()) {
                save.createNewFile(); // Create the file if it doesn't exist
            }

            // Convert Task objects to Strings
            List<String> taskStrings = TaskList.getItems()
                                               .stream()
                                               .map(Task::toString)
                                               .toList();

            // Write data to the file
            Files.write(save.toPath(), taskStrings);

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText("Failed to save tasks.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

	    if (save.exists()) {
	        try {
	            // Read data from the file
	            List<String> linesLoadedFromFile = Files.readAllLines(save.toPath());
	            for (String line : linesLoadedFromFile) {
	                Task task = parseTask(line); // Convert String back to Task
	                TaskList.getItems().add(task);
	            }
	        } catch (IOException ex) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Load Error");
	            alert.setHeaderText("Failed to load tasks.");
	            alert.setContentText(ex.getMessage());
	            alert.showAndWait();
	        }
	    }
	}

	// Helper method to parse a String into a Task object
	private Task parseTask(String line) {
	    // Example parsing logic
	    String[] parts = line.split(", ");
	    String name = parts[0].split("=")[1];
	    String description = parts[1].split("=")[1];
	    int minutesRequired = Integer.parseInt(parts[2].split("=")[1]);
	    int taskPriority = Integer.parseInt(parts[3].split("=")[1].replace("]", ""));
	    
	    switch (taskPriority) {
	        case 1:
	            return new DoTaskItem(name, description, minutesRequired);
	        case 2:
	            return new ScheduleTaskItem(name, description, minutesRequired);
	        case 3:
	            return new DelegateTaskItem(name, description, minutesRequired);
	        case 4:
	            return new DeleteTaskItem(name, description, minutesRequired);
	        default:
	            throw new IllegalArgumentException("Invalid taskPriority: " + taskPriority);
	    }
	}
}
	

	