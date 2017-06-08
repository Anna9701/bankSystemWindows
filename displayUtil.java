/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;
import java.util.Formatter;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        Button btn8 = new Button("History of transaction");
        
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
    
    void setText(String txt, Text lb2) {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        lb2.setEffect(ds);
        lb2.setCache(true);
        lb2.setFill(Color.RED);
        lb2.setFont(Font.font(null, FontWeight.BOLD, 12));
        lb2.setText(txt);
    }
    
    ArrayList<Button> createMenuButtonsForClient() {
        ArrayList<Button> buttons = new ArrayList<>();
        

        Button btn1 = new Button("Transfer money");
        Button btn2 = new Button("Display account");
        Button btn3 = new Button("Delete Account");
        Button btn4 = new Button("Change Password"); 
        Button btn5 = new Button("History of transaction");
        
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        
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
    
    ArrayList<Label> createAddLabels() {
        Label label1 = new Label("System Number:");
        Label label2 = new Label("First name:");
        Label label3 = new Label("LastName:");
        Label label4 = new Label("PESEL:");
        Label label5 = new Label("Adress:");
        Label label6 = new Label("Amount of money:");
        
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        
        return labels;
    }
    
    ArrayList<TextField> createAddTextFields() {
        int amount = 6;
        ArrayList<TextField> fields = new ArrayList<>();
        
        for(int i = 0; i < amount; ++i) {
            TextField textField = new TextField ();
            fields.add(textField);
        }
        
        return fields;
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
        GridPane window = createGridPane();

        Text txt = new Text();
        Formatter format1 = new Formatter();
        format1.format("%-15s%-20s%-20s%-15s%-20s%-20s", "SystemNo", "Firstname", "Lastname", "PESEL", "Address", "Resources");
        String text = format1.toString();
        txt.setText(text);
          
        final ObservableList data = FXCollections.observableArrayList();
        data.addAll(users);
        final ListView listView = new ListView(data);
        listView.setPrefSize(500, 250);
        listView.setEditable(false);
        listView.autosize();
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(data));   
        
        Button btn = new Button ("Close");
        btn.setOnAction(new cancelButton(stg));
        btn.setTranslateX(150);
        btn.setMinWidth(400);
        
        StackPane root = new StackPane();
        root.getChildren().add(listView);

        window.add(txt, 0, 1);
        window.add(root, 0, 2);
        window.add(btn, 0, 3);

        stg.setScene(new Scene(window, 700, 300));
        stg.show();
    }
    
    void displayHistory(ArrayList<String> history) {
        Stage stg = createStage("History");
        GridPane window = createGridPane();
        window.setMinSize(900, 900);
        final ObservableList data = FXCollections.observableArrayList();
        data.addAll(history);
        final ListView listView = new ListView(data);
        listView.setMinSize(800, 400);
        listView.setEditable(false);
        listView.autosize();
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(data));   

        Button btn = new Button ("Close");
        btn.setOnAction(new cancelButton(stg));
        btn.setTranslateX(150);
        btn.setMinWidth(500);

        StackPane root = new StackPane();
        root.getChildren().add(listView);
       

    //    window.add(txt, 0, 1);
        window.add(root, 0, 2);
        window.add(btn, 0, 3);

        stg.setScene(new Scene(window, 900, 500));
        stg.show();
    }
    
    void displayUser(User user) {
        Stage stg = createStage("Bank Account");
        
        GridPane window = createGridPane();
        
        Text txt = new Text();
        Formatter format1 = new Formatter();
        format1.format("%-12s%-15s%-15s%-15s%-15s%-15s", "SystemNo", "Firstname", "Lastname", "PESEL", "Address", "Resources");
        String text = format1.toString();
        txt.setText(text);
        txt.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
 
        Text tmp = new Text();
        tmp.setText(user.toString());
        
        Button btn = new Button ("Close");
        btn.setOnAction(new cancelButton(stg));
        btn.setTranslateX(150);
        btn.setMinWidth(400);
        
        window.add(txt, 1, 0);
        window.add(tmp, 1, 2);
        window.add(btn, 1, 3);

        Scene scene = new Scene(window);
        stg.setScene(scene);
        stg.show();
        
    }
}
