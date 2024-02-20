package Phase3;

import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MemorySet extends Application {
	private ArrayList<Martyr> martyrs = new ArrayList<>();
	private TextField first, second;
	private Button submit, clear;
	private Label response;
	private Label[] nameLabels;
	
	FlowPane fp = new FlowPane();                 // pane of martyrs interface
	@Override
	public void start(Stage primaryStage) {
		
		// Start scene ------------------------------------------------------------------------------------------
		StackPane pane = new StackPane();                             // the main pane of the first window
		// creating buttons
		Button create = new Button("Create Martyr List Window");
		Button memory = new Button("Memory Test Window");
		VBox vb = new VBox(20);
		vb.getChildren().addAll(create, memory);
		pane.getChildren().add(vb);
		vb.setAlignment(Pos.CENTER);

		Scene primaryScene = new Scene(pane);                         // the primary scene has the first window
		primaryStage.setTitle("The war on Gaza");
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		//-----------------------------------------------------------------------------------------------------------

		// Add martyr Scene------------------------------------------------------------------------------------------
		readFile();                                   // reading data from file and putting them in the martyrs ArrayList
		nameLabels = new Label[martyrs.size()];
		for(int i=0; i<martyrs.size(); i++) {                    // to add the martyrs names on the label
			nameLabels[i] = new Label(martyrs.get(i).getName());
			nameLabels[i].setFont(new Font(16));
			fp.getChildren().add(nameLabels[i]); 
		}
		StackPane p = new StackPane();                                 // the main pane of the second window (adding a martyr)
		Label lbl = new Label("Add Martyr:(Name date of martyrdom)");
		lbl.setFont(new Font(16));
		TextField txt = new TextField();
		Button bt = new Button("Add to file");
		Button back = new Button("Back");
		HBox hb = new HBox(10);
		hb.getChildren().addAll(lbl, txt, bt);
		Label wrong = new Label();                                     // the label of the message when trying to add a person
		wrong.setFont(new Font(16));
		VBox vb2 = new VBox(20);
		hb.setAlignment(Pos.CENTER);
		vb2.getChildren().addAll(hb, wrong, back);
		p.getChildren().add(vb2);
		vb2.setAlignment(Pos.CENTER);

		back.setOnAction(e -> {                                  // button to go back to the previous window
			primaryStage.setTitle("The war on Gaza");
			primaryStage.setScene(primaryScene);
		});

		GregorianCalendar current = new GregorianCalendar();              // calendar with the current date
		int[] days = { 31, 29, 31, 30, 31, 30, 31, 30, 31, 30, 31, 31 };
		bt.setOnAction(e -> {                                              // add-to-file button
			boolean b = false;
			wrong.setTextFill(Color.RED);
			String[] info = txt.getText().split(" ");
			String[] date = {};
			Martyr person = new Martyr();
			if (info.length == 2) {                                    // check if the user entered two inputs (name & date)
				person = new Martyr(info[0], info[1]);
				date = info[1].split("/");
				if (date.length == 3) {                                // check if user entered 3 parts of the date (day/month/year)
					try {
						int day = Integer.parseInt(date[0]), month = Integer.parseInt(date[1]),
								year = Integer.parseInt(date[2]);
						Calendar calendar = new GregorianCalendar(year, month, day);              // calendar with the user inputs date
						b = true;
						if (month > 12 || day > days[month - 1] || (month==2 && day > 28 && year%4!=0) || year < 1 || month < 1 || day < 1
								|| calendar.compareTo(current) == 1) {                                   // compare the entered date with the current one
							wrong.setText("The added date is not available");
							b = false;
						}
					} catch (NumberFormatException ex) {                     // if the added date isn't numbers
						b = false;
						wrong.setText("The added date is not available");
					}
				}
			}
			if (info.length != 2 || date.length != 3)                       // if the inputs isn't enough
				wrong.setText("The format is (Name date/month/year)");
			if (b) {
				if (inList(person.getName())) wrong.setText("The person is added before");
				else {
					martyrs.add(person);
					person.writeToFile();
					wrong.setText("Added successfully");
					wrong.setTextFill(Color.GREEN);
				}
			}
			nameLabels = new Label[martyrs.size()];                           // adding the person to the test-memory interface
			fp.getChildren().clear();
			for(int i=0; i<martyrs.size(); i++) {
				nameLabels[i] = new Label(martyrs.get(i).getName());
				nameLabels[i].setFont(new Font(16));
				fp.getChildren().add(nameLabels[i]);
			}
		}); 

		Scene createScene = new Scene(p);                                   // the scene of adding a martyr
		create.setOnAction(e -> {                                           // button to go to this scene
			primaryStage.setScene(createScene);
			primaryStage.setTitle("Add a martyr to the files");
		});
		//--------------------------------------------------------------------------------------------------------

		
		StackPane p1 = new StackPane();                                    // the main scene of the third window (test memory)
		Label test = new Label("Test your memory");
		test.setFont(new Font(20));
		test.setAlignment(Pos.CENTER);
		Label hi = new Label("Hey my friend! Test your memory to see if you remember who martyred before");
		hi.setAlignment(Pos.CENTER);
		hi.setFont(new Font(16));
		Label des = new Label("Pick two Martyr names from the following list, enter them in the boxes in the correct order(date of death), and then press the Submit "
				+ "button.");
		des.setFont(new Font(16));
		des.setAlignment(Pos.CENTER);
		
		fp.setVgap(10);                                              // properties of the pane of the martyrs names
		fp.setHgap(10);
		fp.setPadding(new Insets(0, 30, 0, 30));
		
		HBox inputs = new HBox(20);                                  // pane for the text fields  
		first = new TextField();
		second = new TextField();
		Label before = new Label("Martyred before");
		before.setFont(new Font(16));
		inputs.getChildren().addAll(first, before, second);
		inputs.setAlignment(Pos.CENTER);
		
		response = new Label();
		response.setFont(new Font(16));
		
		HBox buttons = new HBox(10);                                  // pane for the buttons submit and clear and the combo box of colors         
		buttons.setAlignment(Pos.CENTER);
		submit = new Button("Submit");
		submit.setOnAction(e->{                                      // when submit pressed, check the different cases
			if(first.getText().equals("") || second.getText().equals("")) {               // both fields are empty
				response.setText("Enter names in both boxes. Then press Submit.");
				response.setTextFill(Color.RED);
			}
			else if(!inList(first.getText()) && !inList(second.getText())) {        //both persons aren't in the list
				response.setText(" Neither entry is in the name list.");
				response.setTextFill(Color.RED);
			}
			else if(!inList(first.getText())) {                                    // only first person isn't in the list
				response.setText("First entry not in name list – check spelling.");
				response.setTextFill(Color.RED);
			}
			else if(!inList(second.getText())) {                                    // only first person isn't in the list
				response.setText("Second entry not in name list – check spelling.");
				response.setTextFill(Color.RED);
			}
			else if(first.getText().toLowerCase().equals(second.getText().toLowerCase())) {     // the same name entered
				response.setText("You Entered the same name, try again");
				response.setTextFill(Color.RED);
			}
			else {
				GregorianCalendar m1 = new GregorianCalendar(), m2 =  new GregorianCalendar();    
				String[] s= {};
				for(int i=0; i<martyrs.size(); i++) {
					if(martyrs.get(i).getName().toLowerCase().equals(first.getText().toLowerCase())) {           // searching about the first person
						s = martyrs.get(i).getDateOfMartyrdom().split("/");
						m1 = new GregorianCalendar(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
					}
					if(martyrs.get(i).getName().toLowerCase().equals(second.getText().toLowerCase())) {               // searching about the last person
						s = martyrs.get(i).getDateOfMartyrdom().split("/");
						m2 = new GregorianCalendar(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
					}
				}
				if(m1.compareTo(m2) == -1) {                               // comparing the calendar dates of the both martyrs
					response.setText("You are correct");
					response.setTextFill(Color.GREEN);
				}
				else {
					response.setText("Wrong. Try again.");
					response.setTextFill(Color.RED);
				}
			}
		});
		
		clear = new Button("Clear");                                     // to clear the text fields
		clear.setOnAction(e->{
			first.clear();
			second.clear();
		});
		
		Button back1 = new Button("Back");                        // to go back to the previous window
		back1.setOnAction(e->{
			primaryStage.setTitle("The war on Gaza");
			primaryStage.setScene(primaryScene);
		});
		
		ComboBox<String> cmp = new ComboBox<>();                            // combo box for different colors of themes
		cmp.getItems().addAll("Light Grey", "Light Green", "Light Blue", "Light Yellow");
		buttons.getChildren().addAll(submit, clear, cmp);
		fp.setAlignment(Pos.CENTER);
		
		VBox testMemory = new VBox(20);
		testMemory.getChildren().addAll(test, hi, des, fp, inputs, buttons, response, back);
		testMemory.setAlignment(Pos.CENTER);
		
		p1.getChildren().add(testMemory);
		Scene memoryScene = new Scene(p1);
		cmp.setOnAction(e->{                                                  // changing the screen color passed on the selected value in the combo box
			if(cmp.getValue().equals("Light Grey")) p1.setStyle("-fx-background-color: lightgrey;");
			else if(cmp.getValue().equals("Light Blue")) p1.setStyle("-fx-background-color: lightblue;");
			else if(cmp.getValue().equals("Light Green")) p1.setStyle("-fx-background-color: lightgreen;");
			else if(cmp.getValue().equals("Light Yellow")) p1.setStyle("-fx-background-color: lightyellow;");

		});
		memory.setOnAction(e -> primaryStage.setScene(memoryScene));

	}

	public static void main(String[] args) {

		launch(args);
	}

	private void readFile() {                                            // method to read martyrs from the file
		try (FileInputStream input = new FileInputStream("MartyrsListtt.dat");
				DataInputStream data = new DataInputStream(input)) {
			while (input.available() > 0) { 
				String[] info = data.readUTF().split(" ");
				String name = info[0];
				String date = info[1];
				data.readChar();
				Martyr person = new Martyr(name, date);
				martyrs.add(person);
			}

		} catch (IOException e) {
			System.out.println("Sorry, something went wrong");
		}
	}

	private boolean inList(String word) {                              // method to check if the martyr is added before
		for (int i = 0; i < martyrs.size(); i++) {
			if (martyrs.get(i).getName().toLowerCase().equals(word.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}


