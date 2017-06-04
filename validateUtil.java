/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;
import javafx.scene.control.TextField;

/**
 *
 * @author esperanza
 */
public class validateUtil {
     private static displayUtil display = new displayUtil();
    
    boolean checkUser(ArrayList<TextField> textFields) {
        boolean flag = true;
        flag = checkSystemNumber(textFields.get(0));
        flag = checkName(textFields.get(1));
        flag = checkName(textFields.get(2));
        flag = checkPesel(textFields.get(3));
        
        return flag;
    }
    
    boolean checkAddress(TextField tf) {
        String text = tf.getText();
        int i = 0; 
        boolean flag = false;
        
        for(char c : text.toCharArray()) {
            if(Character.isLetter(c)) {
                i++;
                Character.toLowerCase(c);
            } else if (Character.isSpaceChar(c)) {
                flag = true;
            } else if (Character.isDigit(c)) {
                if(!flag || i == 0) {
                    tf.clear();
                    display.alert("Enter correct address!");
                    return false;
                }
            }
        }
        
        Character.toUpperCase(text.charAt(0));
        tf.setText(text);
        return true;
    }
    
    boolean checkName(TextField tf) {
        String text = tf.getText();
        for(char c : text.toCharArray()) {
            if(Character.isDigit(c)) {
                tf.clear();
                display.alert("Name cannot contains digits");
                return false;
            }
            Character.toLowerCase(c);
        }
        
        Character.toUpperCase(text.charAt(0));
        System.out.println(text);
        tf.setText(text);
        return true;
    }
    
    boolean checkSystemNumber(TextField tf) {
        String text = tf.getText();
        try {
            int number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            tf.clear();
            display.alert("Wrong format of input to System Number field!");
            return false;
        }
        return true;
    }
    
    boolean checkPesel(TextField tf) {
        String text = tf.getText();
        long number;
        try {
            number = Long.parseLong(text);
        } catch (NumberFormatException e) {
            tf.clear();
            display.alert("Wrong format of input to PESEL field!");
            return false;
        }
        
        if((int)(Math.log10(number)+1) != 11) {
            tf.clear();
            display.alert("PESEL should have 11 digits");
            return false;
        }
        
        return true;
    }
}
