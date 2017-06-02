/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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
        Cipher cipher = null;
        String password = new String();
        try {
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, usr.getKey());
            byte[] decrypted = cipher.doFinal(usr.getPassword().getBytes());
            password = new String(decrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            Logger.getLogger(passwordsUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        if(password.equals(p)) {
            return true;
        } else {
            return false;
        }
    }
     
    void changePassword() {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("Blowfish");
            SecretKey secretk = keygenerator.generateKey();
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretk);
            String inputText = enterPassword();
            byte[] encrypted = cipher.doFinal(inputText.getBytes());
            usr.setKey(secretk);
            usr.setPassword(encrypted.toString());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(passwordsUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
