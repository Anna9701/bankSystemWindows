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
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class displayUtil {
    
    ArrayList<Button> createMenuButtons() {
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
        Label lb1 = new Label("Number");
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

    
}
