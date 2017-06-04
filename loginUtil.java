/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import static bank.Bank.procedure;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class loginUtil implements java.io.Serializable {
    displayUtil display = new displayUtil();
  
    
    User logInClient() {
        User usr = null;
        
        Stage stg = display.createStage("Client");
        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter system number of user to log in");
        Label lb2 = new Label("Enter password of user to log in");
        Label lb3 = new Label("Confirm password of user to log in");
        TextField tf = new TextField();
        PasswordField tf2 = new PasswordField();
        PasswordField tf3 = new PasswordField();
        Text text = new Text();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        mainWindow.add(lb2, 1, 2);
        mainWindow.add(tf2, 2, 2);
        mainWindow.add(lb3, 1, 3);
        mainWindow.add(tf3, 2, 3);
        mainWindow.add(text, 1, 5);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 4);
        mainWindow.add(btn2, 2, 4);
        btn2.setOnAction(new cancelLogInButton(stg));

        clientLogInButton handler = new clientLogInButton(stg, tf, tf2, tf3, text);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();
        
        return handler.getUser();
    }
    
     class cancelLogInButton implements EventHandler<ActionEvent> {
        Stage stg;
        
        cancelLogInButton(Stage s) {
            stg = s;
        }
        @Override
        public void handle(ActionEvent event) {
           stg.close();
           Platform.exit();
        }
    }
    
    class clientLogInButton implements EventHandler<ActionEvent> {
        TextField tf; 
        TextField tf2; 
        TextField tf3;
        Stage stg;
        Text txt;
        displayUtil display;
        User usr;
        
        clientLogInButton(Stage s, TextField t1, TextField t2, TextField t3, Text t) {
            stg = s;
            tf = t1;
            tf2 = t2;
            tf3 = t3;
            txt = t;
            display = new displayUtil();
        }
        
        @Override
        public void handle(ActionEvent event) {
            checkNumber();
            boolean state = false;
            if(tf2.getText().equals(tf3.getText())) {
               state = usr.checkPassword(tf2.getText());
               if(!state) {
                display.alert("Wrong password!");
                tf2.clear();
                tf3.clear();
                txt.setText("");
                } else {
                    stg.hide();
                }
            } else {
                display.setText("Passwords aren't the same", txt);
                tf2.clear();
                tf3.clear();
            }
            
        }
        
        void checkNumber() {
            String text = tf.getText();
            try {
              int number = Integer.parseInt(text);
              usr = BankSystem.find.findByNumber(number);
            } catch (NoUserFindException ex) {
                new displayUtil().alert("No such user found!");
                Platform.exit();
            }
        }
        
        User getUser() {
            return usr;
        }
                
    }
    
    void menu (int procedure, Stage root, Bank bank) {
        ArrayList<Button> buttons = new ArrayList<>();
        GridPane mainWindow = new GridPane();
        mainWindow.setVgap(10);
        mainWindow.setHgap(10);
        mainWindow.setPadding(new Insets(15, 15, 15, 15));
        User usr = null;
 
        Label lb = new Label("Welcome in our Bank System!");
        lb.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        lb.setPadding(new Insets(15, 15, 15, 15));
        mainWindow.add(lb, 1, 1);
        
        int start = 3;
        int id = 1;
        
        if(procedure == 0) {
            buttons = display.createMenuButtonsForBank();
        } else {
            usr = logInClient();
            buttons = display.createMenuButtonsForClient();
        }
        MenuButtonHandler mbh = new MenuButtonHandler(bank, usr);
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
    
}
