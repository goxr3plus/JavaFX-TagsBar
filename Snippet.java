package tags;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author GOXR3PLUS
 *
 */
public class Snippet extends Application {

	@Override
	public void start(Stage primaryStage) {

		// All the musicGenres
		List<String> genres = Arrays.asList("50s", "60s", "70s", "80s", "90s", "Adult Contemporary", "African",
				"Alternative", "Ambient", "Americana", "Baladas", "Bass", "Big Band", "Big Beat", "Bluegrass", "Blues ",
				"Bollywood", "Breakbeat", "Breakcore", "Breaks", "Calypso", "Caribbean", "Celtic", "Chill", "Zouk");

		TagsBar tagBar = new TagsBar();
		tagBar.getEntries().addAll(genres);

		// Root
		VBox root = new VBox();
		root.getChildren().addAll(tagBar);
		root.setMinSize(300, 400);

		// Scene
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		// PrimaryStage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
