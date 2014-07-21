package fub.xml.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	private static Stage stage;
	
	public void start(Stage stage) throws Exception {
		Application.stage = stage;
		stage.setTitle("Bilder zuschneiden");
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fub/xml/tools/assets/main.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
	}
	
	public static Stage getStage() {
		if (stage == null) {
			throw new IllegalStateException("stage not available yet");
		}
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
