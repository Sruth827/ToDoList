package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.*;

public class EisenhowerMatrix extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("ToDo.fxml"));
			
			Parent root = loader.load();
			
			MyController controller = new MyController(); 
			
			loader.setController(controller);
			
			Scene scene = new Scene(root,900,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Eisenhower Matrix");
			primaryStage.setScene(scene);
			primaryStage.show();  
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
