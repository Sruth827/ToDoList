package application;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TaskCellFactory implements Callback<ListView<Task>, ListCell<Task>> {
    @Override
    public ListCell<Task> call(ListView<Task> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    // Set task details
                    setText(task.getTaskType() + ": " + task.getName() + " / " + task.getMinutesRequired() + " / " + task.getDescription());
                    // Apply styles based on task priority
                    switch (task.getTaskType()) {
                        case DO:
                            setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold" );
                            break;
                        case SCHEDULE:
                            setStyle("-fx-background-color: yellow;  -fx-font-weight: bold");
                            break;
                        case DELEGATE:
                            setStyle("-fx-background-color: orange;  -fx-font-weight: bold");
                            break;
                        case DELETE:
                            setStyle("-fx-background-color: red;  -fx-font-weight: bold");
                            break;
                    }
                    
                    if(this.isSelected()) {
                    	setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-style: italic");
                    } 
                  
                }
            }
        };
    }
}