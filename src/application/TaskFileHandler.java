package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
 
public class TaskFileHandler{
	private final File file;
	ObjectMapper mapper = new ObjectMapper(); 
	
	public TaskFileHandler(String filePath) {
		this.file = new File(filePath);
		
	}
	
	// Method to save tasks to JSON
    public void saveTasks(List<Task> tasks) throws IOException {
        mapper.writeValue(file, tasks);
        System.out.println("Tasks successfully saved to " + file.getPath());
    }

    // Method to load tasks from JSON
    public List<Task> loadTasks() throws IOException {
         return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Task.class));
    }
	
    
		
	
	
}
