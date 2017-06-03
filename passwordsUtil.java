/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class passwordsUtil implements java.io.Serializable {
    private final User usr;
    private static displayUtil display;
    
    passwordsUtil(User u) {
        display = new displayUtil();
        usr = u;
    }
    
    String enterPassword() throws noPasswordException {
        Stage stg = display.createStage("Enter password");
        GridPane window = display.createGridPane();
        Label lb = new Label("Enter password: ");
        PasswordField tf = new PasswordField();
        
        Label lb2 = new Label("Confirm password: ");
        PasswordField tf2 = new PasswordField();
        window.add(lb, 1, 1);
        window.add(tf, 2, 1);
        window.add(lb2, 1, 2);
        window.add(tf2, 2, 2);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        window.add(btn1, 1, 3);
        window.add(btn2, 2, 3);
        Text error = new Text();
        window.add(error, 1, 4);
        
        
        cancelPasswordButton cPB = new cancelPasswordButton(stg);
        passwordButton pB = new passwordButton(tf, tf2, stg, error);
        btn1.setOnAction(pB);
        btn2.setOnAction(cPB);
        
        Scene scene = new Scene(window);
        stg.setScene(scene);
        stg.showAndWait();
        
        if(cPB.flag) {
            throw new noPasswordException();
        } else {
            return pB.tekst;
        }
    }
    
   class cancelPasswordButton implements EventHandler<ActionEvent> {
        Stage window;
        boolean flag = false;
        
        cancelPasswordButton(Stage stg) {
            window = stg;
        }
        @Override 
        public void handle(ActionEvent e) {
            flag = true;
            window.close();
        }
}
    
    class passwordButton implements EventHandler<ActionEvent> {
        String tekst;
        PasswordField tf;
        PasswordField tf2;
        Stage stage;
        Text error;
        
        passwordButton(PasswordField text, PasswordField text2, Stage stg, Text err) {
            tf = text;
            tf2 = text2;
            stage = stg;
            error = err;
        }

        @Override
        public void handle(ActionEvent event) {
            if(tf.getText().equals(tf2.getText()) && !tf.getText().isEmpty()) {
                tekst = tf.getText();
                stage.hide();
            } else if (tf.getText().isEmpty()) {
                display.setText("You have to enter password", error);
                tf.clear();
                tf2.clear();
            } else {
                display.setText("Password aren't the same!", error);
                tf.clear();
                tf2.clear();
            }
        }
    }
    
     boolean checkPassword(String p) {
      
        String password = usr.getPassword();
    
  
        if(password.equals(p)) {
            return true;
        } else {
            return false;
        }
    }
     
    void changePassword() throws noPasswordException {
        try {
            String inputText = enterPassword();
            usr.setPassword(inputText);
        } catch (noPasswordException e) {
            throw e;
        }
    }

}

class noPasswordException extends Exception {};
