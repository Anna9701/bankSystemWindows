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
    
    String enterPassword() {
        Stage stg = display.createStage("Enter password");
        GridPane window = display.createGridPane();
        Label lb = new Label("Enter password: ");
        TextField tf = new TextField();
        Label lb2 = new Label("Confirm password: ");
        TextField tf2 = new TextField();
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
        btn2.setOnAction(new cancelButton(stg));
        
        passwordButton pB = new passwordButton(tf, tf2, stg, error);
        btn1.setOnAction(pB);
        
        Scene scene = new Scene(window);
        stg.setScene(scene);
        stg.showAndWait();
        
        return pB.tekst;
    }
    
   
    
    class passwordButton implements EventHandler<ActionEvent> {
        String tekst;
        TextField tf;
        TextField tf2;
        Stage stage;
        Text error;
        
        passwordButton(TextField text, TextField text2, Stage stg, Text err) {
            tf = text;
            tf2 = text2;
            stage = stg;
            error = err;
        }

        @Override
        public void handle(ActionEvent event) {
            if(tf.getText().equals(tf2.getText())) {
                tekst = tf.getText();
                stage.hide();
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
     
    void changePassword() {
        String inputText = enterPassword();
  
        usr.setPassword(inputText);
    }

}
