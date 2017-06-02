/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();
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
        btn2.setOnAction(new cancelButton(stg));

        clientLogInButton handler = new clientLogInButton(stg, tf, tf2, tf3, text);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();
        
        return handler.getUser();
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
            } else {
                display.setText("Passwords aren't the same", txt);
            }
            if(!state) {
                display.alert("Wrong password!");
                tf2.clear();
                tf3.clear();
            } else {
                stg.hide();
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
}
