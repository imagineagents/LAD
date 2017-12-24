package statemachine.states;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import filemanagement.core.FileConstants;
import filemanagement.fileretrieval.RetrievedFile;
import gui.core.GUI;
import gui.core.SceneContainerStage;
import gui.utilities.GUIText;
import javafx.concurrent.Task;
import statemachine.core.StateMachine;
import statemachine.utils.StateName;

public class RetrievingFileState extends State {
	StateMachine stateMachine;
	SceneContainerStage sceneContainerStage;
	GUI gui;
	
	public RetrievingFileState(StateMachine stateMachine, SceneContainerStage sceneContainerStage, GUI gui) {
		this.stateMachine = stateMachine;
		this.sceneContainerStage = sceneContainerStage;
		this.gui = gui;
	}

	@Override
	public void execute(StateName param) {
		sceneContainerStage.changeScene(gui.getFileRetrievalScene());
		sceneContainerStage.setTitle(GUIText.FILE_RETRIEVAL);
		
		switch (param) {
			case RETRIEVING_FILE:
				retrieveFileAndOpen();
				break;
			default:
				break;
		}
	}
	
	private void retrieveFileAndOpen() {
		RetrievedFile retrievedFile = new RetrievedFile();
		
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				retrievedFile.setFile(retrieveFile());
				return null ;
			}
		};
		task.setOnSucceeded(e -> {
			try {
				openFile(retrievedFile);
				rateFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				}
		});
		new Thread(task).start();
	}
	
	private File retrieveFile() {
		//String remoteFileLocation = gui.getDashBoardScene().getListView().getSelectionModel().getSelectedItem().getFileLocation();
		
		File retrievedFile = null;
		/*try {
			//TODO get local storage directory from config file.
			retrievedFile = FileRetriever.downloadFile(remoteFileLocation, getLocalFileStorageDirectoryPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		return retrievedFile;
	}
	
	private void openFile(RetrievedFile retrievedFile) throws IOException {
		if (!Desktop.isDesktopSupported()) {
			System.err.println(FileConstants.DESKTOP_NOT_SUPPORTED);
			return;
		}
		
		Desktop desktop = Desktop.getDesktop();
		if (retrievedFile.getFile().exists()) {
			desktop.open(retrievedFile.getFile());
		}
	}
	
	private void rateFile() {
		stateMachine.setCurrentState(StateName.RATING.toString());
		stateMachine.execute(StateName.INIT);
	}
	
	/*private String getLocalFileStorageDirectoryPath() throws IOException {
		FileReader configFile = new FileReader(FileConstants.CONFIG_FILE_NAME);
		
		Properties props = new Properties();
		props.load(configFile);
		
		String localFilesDirectory = props.getProperty(FileConstants.DIRECTORY_KEY);
		configFile.close();
		
		return localFilesDirectory;
	}*/
}