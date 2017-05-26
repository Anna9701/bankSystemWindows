/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class displayUtil {
    private static final displayUtil display = new displayUtil();
    
    ArrayList<Button> createMenuButtonsForBank() {
        Button btn1 = new Button("Add User");
        Button btn2 = new Button("Delete User");
        Button btn3 = new Button("Pay in account");
        Button btn4 = new Button("Pay out from account");
        Button btn5 = new Button("Transofrm money between accounts");
        Button btn6 = new Button("Display information about all accounts");
        Button btn7 = new Button("Display information about specific accounts");
        Button btn8 = new Button("Save State"); 
        
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        
        return buttons;
    }
    
    ArrayList<Button> createMenuButtonsForClient() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        Button btn1 = new Button("Pay in account");
        Button btn2 = new Button("Pay out from account");
        Button btn3 = new Button("Transofrm money someone");
        Button btn4 = new Button("Display account");
        Button btn5 = new Button("Delete Account");
        Button btn6 = new Button("Save State"); 
        
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        
        return buttons;
    }
    ArrayList<Button> createFindButtons() {
        Button btn1 = new Button("Users number in system");
        Button btn2 = new Button("First Name");
        Button btn3 = new Button("Last name");
        Button btn4 = new Button("PESEL");
        Button btn5 = new Button("Adress");
        Button btn6 = new Button("Back");
        
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        
        return buttons;
    }
    
    void alert(String text) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Error");
       alert.setHeaderText(text);
       alert.showAndWait();
    }
   
    Stage createStage(String text) {
        Stage stg = new Stage();
        stg.setTitle(text);
        return stg;
    }
    
    GridPane createGridPane(){
        GridPane mainWindow = new GridPane();
        mainWindow.setVgap(10);
        mainWindow.setHgap(10);
        mainWindow.setPadding(new Insets(15, 15, 15, 15));

        return mainWindow;
    }
    
    void displayTable(ArrayList<User> users) {
        Stage stg = createStage("Bank Accounts");
        Label lb1 = new Label("Bank Accounts");
        GridPane window = createGridPane();

        final ObservableList data = FXCollections.observableArrayList();
        data.addAll(users);
        final ListView listView = new ListView(data);
        listView.setPrefSize(500, 250);
        listView.setEditable(false);
        listView.autosize();
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(data));   
        StackPane root = new StackPane();
        root.getChildren().add(lb1);
        root.getChildren().add(listView);
        window.add(lb1, 1, 1);
        stg.setScene(new Scene(root, 500, 250));
        stg.show();
    }
    
    
    void displayUsers(ArrayList<User> users) {
        Stage stg = createStage("Bank Accounts");
        
        GridPane window = createGridPane();
        
        ArrayList<Text> text = new ArrayList<>();
        int start = 2;
        for(User user : users) {
            Text tmp = new Text();
            tmp.setText(user.toString());
            text.add(tmp);
            window.add(tmp, 1, start++);
        }
        
        Scene scene = new Scene(window);
        stg.setScene(scene);
        stg.show();
        
    }
    
    int enterUserNumber (String text, Stage stg) {

        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter system number of user to " + text);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserNumberButton handler = new enterUserNumberButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();


       return handler.number;
    }
    
     long enterUserLong (String text, Stage stg) {

        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter number of user to " + text);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserLongButton handler = new enterUserLongButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();


       return handler.number;
    }
     
    String enterUserText (String text, String text2, Stage stg) {

        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter " + text + " of user to " + text2);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserTextButton handler = new enterUserTextButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();


       return handler.text;
    }
    
}
