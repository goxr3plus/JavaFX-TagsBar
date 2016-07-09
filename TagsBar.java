package tags;

import java.util.SortedSet;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

/**
 * @author GOXR3PLUS
 *
 */
public class TagsBar extends HBox {

	private HBox hBox = new HBox();
	private ScrollPane scrollPane = new ScrollPane(hBox);
	private AutoCompleteTextField field = new AutoCompleteTextField();

	// Constructor
	public TagsBar() {

		getStyleClass().setAll("tags-bar");

		// hBox
		hBox.setStyle(" -fx-spacing:3px;");

		// scrollPane
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		// field
		field.setPromptText("tag...");
		field.setMinSize(120, 30);
		field.setBackground(null);
		field.setOnAction(evt -> {
			String text = field.getText();
			// No Duplicates allowed
			if (!text.isEmpty() && getEntries().contains(text) && !hBox.getChildren().stream()
					.anyMatch(s -> ((Tag) s).getTag().toLowerCase().equals(text.toLowerCase())))
				hBox.getChildren().add(new Tag(text));
			field.clear();
		});

		getChildren().addAll(scrollPane, field);
	}

	/**
	 * Returns all the tags of TagsBar
	 * 
	 * @return
	 */
	public ObservableList<Node> getTags() {
		return hBox.getChildren();
	}

	/**
	 * Get the existing set of auto complete entries.
	 * 
	 * @return The existing auto complete entries.
	 */
	public SortedSet<String> getEntries() {
		return field.getEntries();
	}

	/**
	 * Clears all the tags
	 * 
	 */
	public void clearAllTags() {
		hBox.getChildren().clear();
	}

	/**
	 * Add this tag if it doesn't exist
	 * 
	 * @param tag
	 */
	public void addTag(String tag) {
		if (!hBox.getChildren().stream().anyMatch(s -> ((Tag) s).getTag().toLowerCase().equals(tag.toLowerCase())))
			hBox.getChildren().add(new Tag(tag));
	}

	/**
	 * @author SuperGoliath TagClass
	 */
	public class Tag extends HBox {

		private Label textLabel = new Label();
		private Label iconLabel = new Label(null, new ImageView(new Image(getClass().getResourceAsStream("x.png"))));

		// Constructor
		public Tag(String tag) {
			getStyleClass().add("tag");

			// drag detected
			setOnDragDetected(event -> {

				/* allow copy transfer mode */
				Dragboard db = startDragAndDrop(TransferMode.MOVE);

				/* put a string on dragboard */
				ClipboardContent content = new ClipboardContent();
				content.putString("#c" + getTag());

				db.setDragView(snapshot(null, new WritableImage((int) getWidth(), (int) getHeight())), getWidth() / 2,
						0);

				db.setContent(content);

				event.consume();
			});

			// drag over
			setOnDragOver((event) -> {
				/*
				 * data is dragged over the target accept it only if it is not
				 * dragged from the same imageView and if it has a string data
				 */
				if (event.getGestureSource() != this && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.MOVE);

				event.consume();
			});

			// drag dropped
			setOnDragDropped(event -> {

				boolean sucess = false;
				if (event.getDragboard().hasString() && event.getDragboard().getString().startsWith("#c")) {
					String currentTag = getTag();
					setTag(event.getDragboard().getString().replace("#c", ""));
					((Tag) event.getGestureSource()).setTag(currentTag);
					sucess = true;
				}

				event.setDropCompleted(sucess);
				event.consume();
			});

			// drag done
			setOnDragDone(event -> {
				if (event.getTransferMode() == TransferMode.MOVE) {
					// System.out.println("Source"+event.getGestureSource() + "
					// /Target:" + event.getGestureTarget());
				}

				event.consume();
			});

			// textLabel
			textLabel.getStyleClass().add("label");
			textLabel.setText(tag);
			// textLabel.setMinWidth(getTag().length() * 6);

			// iconLabel
			iconLabel.setOnMouseReleased(r -> {
				hBox.getChildren().remove(this);
			});

			getChildren().addAll(textLabel, iconLabel);
		}

		public String getTag() {
			return textLabel.getText();
		}

		public void setTag(String text) {
			textLabel.setText(text);
		}

	}

}