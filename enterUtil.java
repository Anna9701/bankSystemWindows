/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class enterUtil {
    private static final displayUtil display = new displayUtil();
    
    int enterUserNumber (String text, Stage stg) {
        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter system number of user to " + text);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserNumberButton handler = new enterUserNumberButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();

       if(handler.flag == true) {
        return handler.number;
       } else {
           return -1;
       }
    }
    
        long enterUserLong (String text, Stage stg) {

        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter number of user to " + text);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserLongButton handler = new enterUserLongButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();

        if(handler.flag) {
            return handler.number;
        } else {
            return -1;
        }
    }
         String enterUserText (String text, String text2, Stage stg) {

        GridPane mainWindow = display.createGridPane();

        Label lb = new Label("Enter " + text + " of user to " + text2);
        TextField tf = new TextField();
        mainWindow.add(lb, 1, 1);
        mainWindow.add(tf, 2, 1);
        Button btn1 = new Button("Enter");
        Button btn2 = new Button("Cancel");
        mainWindow.add(btn1, 1, 2);
        mainWindow.add(btn2, 2, 2);
        btn2.setOnAction(new cancelButton(stg));

        enterUserTextButton handler = new enterUserTextButton(tf, stg);
        btn1.setOnAction(handler);

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();

       if(handler.flag) {
        return handler.text;
       } else {
           return "-1";
       }
    }
}
