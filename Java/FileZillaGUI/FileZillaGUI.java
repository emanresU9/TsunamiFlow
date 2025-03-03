package application;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FileZillaGUI extends Application
{
	ComboBox<String> optionsMenu;
	TextField tField;
	Button srcButton = null;
	Button destButton = null;
	File srcDir, destDir;
	TextField selectedSrcInfo = new TextField();
	TextField selectedDestInfo = new TextField();
	TextArea output = new TextArea();
	DirectoryChooser directoryChooser = null;
	
	public void start(Stage primaryStage) 
	{
		Label menuChoiceLabel = new Label("Command:");
		String[] programs = new String[] {"PushDirectoryChanges", "CopyDirectoryStructure"};
		optionsMenu = new ComboBox<>(FXCollections.observableArrayList(programs));
		Label programOptionsLabel = new Label("Options:");
		tField = new TextField();
		tField.setText("");
		tField.setPrefWidth(30);
		tField.setAlignment(Pos.CENTER);
		HBox program_menu = new HBox();
		program_menu.getChildren().addAll(menuChoiceLabel,optionsMenu,programOptionsLabel,tField);
		program_menu.setSpacing(15);
		
		srcButton = new Button("Source");
		destButton = new Button("Dest");
		srcButton.setOnAction(this::chooseFileMethod);
		destButton.setOnAction(this::chooseFileMethod);
		srcButton.setPrefWidth(100);
		destButton.setPrefWidth(100);
		selectedSrcInfo.setText("No Dir Chosen.");
		selectedDestInfo.setText("No Dir Chosen");
		selectedSrcInfo.setEditable(false);
		selectedDestInfo.setEditable(false);
		selectedSrcInfo.setPrefWidth(125);
		selectedDestInfo.setPrefWidth(125);
		
		GridPane src_and_dest_menu = new GridPane();
		src_and_dest_menu.setHgap(5);
		src_and_dest_menu.setVgap(10);
		src_and_dest_menu.add(srcButton , 0, 0);
		src_and_dest_menu.add(destButton, 0, 1);
		src_and_dest_menu.add(selectedSrcInfo, 1, 0);
		src_and_dest_menu.add(selectedDestInfo, 1, 1);
		
		Button runButton = new Button("RUN!");
		runButton.setOnAction((event) -> {
			String options = tField.getText();
			if (optionsMenu.getValue().equals("PushDirectoryChanges")) 
			{
				if (options.equals("-i"))
					FileZilla.pushChangesIgnoreDuplicates(srcDir, destDir);
				else if (options.equals("-f"))
					FileZilla.forceChanges(srcDir, destDir);
				else if (options.equals(""))
					FileZilla.pushChanges(srcDir, destDir);
			} 
			else if (optionsMenu.getValue().equals("CopyDirectoryStructure")) 
			{
				FileZilla.copyStructure(srcDir, destDir, false);
			}
		});
		
		VBox root = new VBox();
		root.setSpacing(20);
		root.setAlignment(Pos.TOP_LEFT);
		root.getChildren().addAll(program_menu,src_and_dest_menu,runButton);
		
		Scene scene = new Scene(root,500,200);
		primaryStage.setTitle("FileZilla3 GUI");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) 
	{
		launch(args);
	}
	public void chooseFileMethod(ActionEvent event) 
	{
		Stage stage = new Stage();
		directoryChooser = new DirectoryChooser();
		if (event.getSource() == srcButton) 
		{
			srcDir = directoryChooser.showDialog(stage);
			selectedSrcInfo.setText(srcDir.toString());
		} 
		else 
		{
			destDir = directoryChooser.showDialog(stage);
			selectedDestInfo.setText(destDir.toString());
		}
	}
	public void appendText(String str) 
	{
		output.appendText(str);
	}
}
