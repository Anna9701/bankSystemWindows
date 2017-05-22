/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.scene.control.Alert;

/**
 *
 * @author esperanza
 */
public class displayUtil {
    void alert(String text) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Error");
       alert.setHeaderText(text);
       alert.showAndWait();
   }
}
