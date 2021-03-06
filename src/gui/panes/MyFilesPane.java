package gui.panes;

import content.frame.core.Content;
import gui.utilities.GUIText;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class MyFilesPane extends BorderPane {
	private Button backButton;
	private ListView<Content> filesListView;
	
	public MyFilesPane(ObservableList<Content> data) {
		VBox leftBar = configureLeftBar();
		this.setLeft(leftBar);
		
		ListView<Content> filesListView = configureListView(data);
		this.filesListView = filesListView;
		this.setCenter(filesListView);
	}
	
	private VBox configureLeftBar() {
		VBox content = new VBox();
	    content.setPadding(new Insets(10));
	    content.setSpacing(8);
	    
	    Button backButton = new Button(GUIText.BACK_BUTTON);
	    this.backButton = backButton;
	    
        content.getChildren().add(backButton);
	    
	    return content;
	}

	private ListView<Content> configureListView(ObservableList<Content> data) {
		final ListView<Content> listView = new ListView<Content>(data);
		
		listView.setCellFactory(new Callback<ListView<Content>, ListCell<Content>>() {
			
			@Override
			public ListCell<Content> call(ListView<Content> arg) {
				return new ListCell<Content>() {
					
					@Override
					protected void updateItem(Content item, boolean emptyCell) {
						super.updateItem(item, emptyCell);
						if (emptyCell || item == null) {
							setText(null);
                        		setGraphic(null);
                        	}
						if (item != null) {
							VBox vBox = new VBox(new Text(item.getFileName()), new Text(item.getFileFormat()));
							HBox hBox = new HBox(new Label("[Graphic]"), vBox);
							hBox.setSpacing(10);
							setGraphic(hBox);
						}
					}
				};
			}
		});
		
		return listView;
	}
	
	public Button getBackButton() {
		return backButton;
	}
	
	public ListView<Content> getListView() {
		return filesListView;
	}
}
