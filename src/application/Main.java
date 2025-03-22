package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = FXMLLoader.load(getClass().getResource("ToDo.fxml"));
		
			
			MyController controller = new MyController(); 
			loader.setController(controller);
			
			VBox root = loader.load();
			
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
