package application;

public class TaskManager implements TaskSelectionListener {

    @Override
    public void onTaskSelected(int taskId) {
        System.out.println("Task with ID " + taskId + " was selected.");
        // Implement additional task handling logic here
    }
}


