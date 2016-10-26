package ui;

import java.awt.Dialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	
	//current note
	String currentNote = "";
	Stage stage;
	
	String title;  //shown in the folders bar
	

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		this.stage = stage;
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);
		buttonLoad.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contain a NoteBook Object!");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized ObjectFile (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				//Stage s = new Stage();
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null){
					loadNoteBook(file);
				}
				currentFolder = null;
				updateListView();
				
			}
			
		});
		
		
		//Save Button
		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		
		
		buttonSave.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contain a NoteBook Object!");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized ObjectFile (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				//Stage s = new Stage();
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null){
					noteBook.save(file.getAbsolutePath());
				}
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Successfully saved");
				alert.setContentText("Your file has been saved to file " + file.getName());
				alert.showAndWait().ifPresent( rs -> {
					if(rs == ButtonType.OK){
						System.out.println("Pressed OK.");
					}
				});
			}
		});
		
		
		Label label = new Label("Label:");
		TextField textSearch = new TextField();
		
		//hbox.setSpacing(300);
		Button buttonSearch = new Button("Search");
		buttonSearch.setPrefSize(100, 20);
		
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			public void handle(ActionEvent event) {
			//Labeled textSearch;
			currentSearch = textSearch.getText();
			textAreaNote.setText("");
			
			updateListView();
			}
		});
		
		
		Button buttonRemove = new Button("Clear Search");
		buttonRemove.setOnAction(new EventHandler<ActionEvent>() {
		 @Override
		public void handle(ActionEvent event) {
		 currentSearch = "";
		 textSearch.setText("");
		textAreaNote.setText("");
		updateListView();
		 }
		 });
		
		hbox.getChildren().addAll(buttonLoad, buttonSave,label, textSearch, buttonSearch, buttonRemove);
		
		return hbox;
	}

	
	
	
	
	
	
	
	
	
	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		for(Folder i : noteBook.getFolders()){
			foldersComboBox.getItems().add(i.getName());
		}

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (ov == null || t == null || t1 == null) return;
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null){
					currentNote = null;
					return;
				}
				title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				for(Folder i: noteBook.getFolders()){
					for(Note j: i.getNotes()){
						if(title == j.getTitle()){
							currentNote = j.getTitle();
							content = j.getContent();
						}
					}
				}
				textAreaNote.setText(content);

			}
		});
		
		
		Button AddFolder = new Button("Add a Folder");
		AddFolder.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			public void handle(ActionEvent event) {
				 TextInputDialog dialog = new TextInputDialog("Add a Folder");
				 dialog.setTitle("Input");
				 dialog.setHeaderText("Add a new folder for your notbook");
				 dialog.setContentText("Please enter the name you want to creat:");
				 //Traditional way to get the response value.
				 Optional<String> result = dialog.showAndWait();
				 if(result.isPresent()){
					 Alert alert = new Alert(AlertType.INFORMATION);
					 alert.setTitle("Warning");
					 alert.setHeaderText("Warining");
					 
					 for(Folder f : noteBook.getFolders() ){
						 if(f.getName().equals(result.get())){
							 alert.setContentText("You already have a folder named with " + result.get());
							 alert.showAndWait().ifPresent(rs ->{
								 if(rs == ButtonType.OK){
									 System.out.println("Pressed OK");
									 return;
								 }
							 });
							 return;
						 }
					 }
					 
					 
					 if(result.get().length() == 0){
						 alert.setContentText("Please input an valid folder name");
						 alert.showAndWait().ifPresent(rs ->{
							 if(rs == ButtonType.OK){
								 System.out.println("Pressed OK");
								 return;
							 }
						 });
						 return;
					 }
					 
					 
					 noteBook.addFolder(result.get());
					foldersComboBox.getItems().add(noteBook.getFolders().get(noteBook.getFolders().size()-1).getName());
				 }
			 }
		});
		
		
		Button AddNote = new Button("Add a Note");
		AddNote.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			public void handle(ActionEvent event) {
				 Alert alert = new Alert(AlertType.INFORMATION);
				 //System.out.println(currentFolder);
				 if(currentFolder == null){
					 
					 alert.setTitle("Warning");
					 alert.setHeaderText("Warining");
					 alert.setContentText("Please choose a folder first");
					 alert.showAndWait().ifPresent(rs ->{
						 if(rs == ButtonType.OK){
							 System.out.println("Pressed OK");
						 }
					 });
					 return;
				 }
				 TextInputDialog dialog = new TextInputDialog("Add a Note");
				 dialog.setTitle("Input");
				 dialog.setHeaderText("Add a new folder for your notbook");
				 dialog.setContentText("Please enter the name of your note");
				 //Traditional way to get the response value.				
				 Optional<String> result = dialog.showAndWait();
				 if(result.isPresent()){
					 	if(noteBook.createTextNote(currentFolder, result.get())){
					 		alert.setTitle("Successful!");
					 		alert.setContentText("Insert note" + result.get() + " to folder" + currentFolder + " successfully!");alert.showAndWait().ifPresent(rs ->{
								 if(rs == ButtonType.OK){
									 System.out.println("Pressed OK");
								 }
							 });
					 		updateListView();
					 	}
						 
				 }
				 

			 }
		});
		
		
		
		
		
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10)); // Set all sides to 10
		hbox.setSpacing(8); // Gap between nodes

		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(AddFolder);
		
		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(AddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		//f(currentSearch == ""){
		List<Note> notes = noteBook.searchNotes(currentSearch);
		System.out.println(notes);
			for(Folder i: noteBook.getFolders()){
				if(i.getName() == currentFolder){
					for(Note j: i.getNotes()){
						for(Note k:notes){
							if(k.equals(j)) list.add(j.getTitle());
						}
						
					}
				}
			}
		//}
		
		
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		
		
		
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(20);
		saveView.setFitWidth(20);
		saveView.setPreserveRatio(true);
		
		ImageView deleteView = new ImageView(new Image(new File("delete.png").toURI().toString()));
		deleteView.setFitHeight(22);
		deleteView.setFitWidth(22);
		deleteView.setPreserveRatio(true);
		
		Button saveNote = new Button("Save Note");
		saveNote.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			public void handle(ActionEvent event) {
				 if(currentFolder == null || currentNote == null){
					 
					 Alert alert = new Alert(AlertType.INFORMATION);
					 alert.setTitle("Warning");
					 alert.setHeaderText("Warining");
					 alert.setContentText("Please select a folder and a note");
					 alert.showAndWait().ifPresent(rs ->{
						 if(rs == ButtonType.OK){
							 System.out.println("Pressed OK");
						 }
					 });
					 return;
				 }
				 
				 
				 
				 else{
					 for(Folder i : noteBook.getFolders()){
						 for(Note j : i.getNotes()){
							 if(currentNote.equals(j.getTitle())){
								 ((TextNote)j).setContent(textAreaNote.getText());
							 }
						 }
					 }
				 }
			 }
		});
		
		
		
		
		
		Button deleteNote = new Button("Delete Note");
		deleteNote.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			public void handle(ActionEvent event) {
				 if(currentFolder == null || currentNote == null){
					 
					 Alert alert = new Alert(AlertType.INFORMATION);
					 alert.setTitle("Warning");
					 alert.setHeaderText("Warining");
					 alert.setContentText("Please select a folder and a note");
					 alert.showAndWait().ifPresent(rs ->{
						 if(rs == ButtonType.OK){
							 System.out.println("Pressed OK");
						 }
					 });
					 return;
				 }
				 
				 
				 
				 
				 else{
					 
					 Alert alert = new Alert(AlertType.CONFIRMATION);
					 alert.setTitle("Succeed");
					 alert.setHeaderText("Confirmation");
					 alert.setContentText("Your note has been successfully removed!");
					 alert.showAndWait().ifPresent(rs ->{
						 if(rs == ButtonType.OK){
							 System.out.println("Note deleted");
							 
							 for(Folder i : noteBook.getFolders()){
								 if(currentFolder.equals(i.getName())){
									 if(i.removeNotes(currentNote)) {System.out.println("Note deleted");}
								 }
							 }
						 }
					 });
					 updateListView();
					 return;
				 }
			 }
		});
		
		HBox hb = new HBox();
		hb.setPadding(new Insets(10)); // Set all sides to 10
		hb.setSpacing(8); // Gap between nodes
		
		hb.getChildren().addAll(saveView, saveNote, deleteView, deleteNote);
		grid.add(hb, 0, 0);
		//grid.add(saveNote, 1, 0);
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 1);
		

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;
		currentFolder = null;

	}
	
	
	private void loadNoteBook(File f){
		
		NoteBook nb = new NoteBook(f.getAbsolutePath());
		noteBook = nb;
		foldersComboBox.getItems().clear();
		for(Folder i : noteBook.getFolders()){
			foldersComboBox.getItems().add(i.getName());
		}
		currentFolder = null;

	}

}
