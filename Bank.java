/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
/**
 *
 * @author esperanza
 */
public class Bank extends Application {
    BankSystem bankSystem;
    displayUtil display = new displayUtil();
    static int procedure;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Close Confirmation");
                alert.setHeaderText("Do you really want to quit?");
                alert.initOwner(primaryStage);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                  //  bankSystem.saveState();//////////////////////// saaaaaaaaaaave ????
                    Platform.exit();
                }
            }
        });
        chooseUser();
        initUI(primaryStage);
    }
    
    private void chooseUser() {
        Stage stage = display.createStage("Choose User");
        GridPane root = display.createGridPane();
        stage.setTitle("Choose mode");
        Button btn1 = new Button("Client");
        btn1.setId("1");
        chooseUserButton handler = new chooseUserButton(stage);
        btn1.setOnAction(handler);
        Button btn2 = new Button("Bank");
        btn2.setId("0");
        btn2.setOnAction(handler);
        Label lbl1 = new Label("Choose who you are");
        root.add(lbl1, 0, 0);
        root.add(btn1, 2, 2);
        root.add(btn2, 1, 2);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void initUI(Stage stage){

        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(15, 10, 0, 5));
       
        
        
        Button btn1 = new Button("Load");
        Button btn2 = new Button("Create");
        Label lbl1 = new Label("Name of database");
        TextField field1 = new TextField();
        
        MyButtonHandler mbh = new MyButtonHandler(field1, stage, this);
        
        lbl1.setMnemonicParsing(true);
        lbl1.setMnemonicParsing(true);
        btn2.setOnAction(mbh);
        btn1.setOnAction(mbh);
        btn1.setMinWidth(100);
        btn2.setMinWidth(100);
        
        root.add(lbl1, 0, 0);
        root.add(field1, 2, 0);
        root.add(btn1, 2, 3);
        root.add(btn2, 1, 3);
        
        GridPane.setHalignment(lbl1, HPos.RIGHT);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Database");
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    
    
    void menu (Stage root) {
        root.hide();
        ArrayList<Button> buttons = new ArrayList<>();
        GridPane mainWindow = new GridPane();
        mainWindow.setVgap(10);
        mainWindow.setHgap(10);
        mainWindow.setPadding(new Insets(15, 15, 15, 15));
        User usr = null;
        MenuButtonHandler mbh = new MenuButtonHandler(this, usr);
     

        
        Label lb = new Label("Welcome in our Bank System!");
        lb.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        lb.setPadding(new Insets(15, 15, 15, 15));
        mainWindow.add(lb, 1, 1);
        
        int start = 3;
        int id = 1;
        
        if(procedure == 0) {
            buttons = display.createMenuButtonsForBank();
        } else {
            try {
                Stage stg = display.createStage("Client");
                int number = display.enterUserNumber("start", stg);
                usr = BankSystem.find.findByNumber(number);
            } catch (NoUserFindException ex) {
                new displayUtil().alert("No such user found!");
                Platform.exit();
            }
            buttons = display.createMenuButtonsForClient();
        }
        for (Button button : buttons) {
            mainWindow.add(button, 1, start++);
            button.setId(Integer.toString(id++));
            button.setMinWidth(400);
            button.setOnAction(mbh);
        }
        
        Scene scene = new Scene(mainWindow);
        root.setTitle("Bank System");
        root.setScene(scene);
        root.show();
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
