/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
/**
 *
 * @author esperanza
 */
public class Bank extends Application {
    BankSystem bankSystem;
    displayUtil display = new displayUtil();
    static int procedure;
    loginUtil loginutil = new loginUtil(bankSystem);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new exitButton(primaryStage, bankSystem, false));
        initUI(primaryStage);
    }
    
    
    
    private void initUI(Stage stage){

        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(15, 10, 0, 5));
       
        final ToggleGroup group = new ToggleGroup();
        ToggleButton tb1 = new ToggleButton("Bank");
        tb1.setToggleGroup(group);
        tb1.setUserData("Bank");
        tb1.setSelected(true);
        ToggleButton tb2 = new ToggleButton("User");
        tb2.setUserData("User");
        tb2.setToggleGroup(group);
        tb2.setTranslateX(50);
        
        
        Button btn1 = new Button("Load");
        Button btn2 = new Button("Create");
        
        tb2.setOnAction(new toggleMyButton(btn2, false));
        tb1.setOnAction(new toggleMyButton(btn2, true));
        
        Label lbl1 = new Label("Name of database");
        TextField field1 = new TextField();
        
        MyButtonHandler mbh = new MyButtonHandler(field1, stage, this, group);
        
        lbl1.setMnemonicParsing(true);
        lbl1.setMnemonicParsing(true);
        btn2.setOnAction(mbh);
        btn1.setOnAction(mbh);
        btn1.setMinWidth(100);
        btn2.setMinWidth(100);
        
        
        root.add(tb1, 1, 0);
        root.add(tb2, 1, 0);
        root.add(lbl1, 1, 1);
        root.add(field1, 2, 1);
        root.add(btn1, 2, 4);
        root.add(btn2, 1, 4);
        
        GridPane.setHalignment(lbl1, HPos.RIGHT);
        
        Scene scene = new Scene(root);
        stage.setTitle("Database");
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    
    
    void menu (Stage root) {
        root.hide();
        root.setOnCloseRequest(new exitButton(root, bankSystem, true));
        loginutil.menu(procedure , root, this);
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
