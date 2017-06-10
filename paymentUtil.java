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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */
public class paymentUtil {
    private final BankSystem bank;
    private static final displayUtil display = new displayUtil();
    private final enterUtil enter = new enterUtil();
    
    paymentUtil(BankSystem bk) {
        bank = bk;
    }
    
    void toPay() {
        Stage stg = display.createStage("Payment");
        stg.setOnCloseRequest(new closeWindowButton(stg, bank, true));
        User topay = null;
        String text2 = "payment";
            try {   
                int number = enter.enterUserNumber(text2, stg);
                if (number == -1) {
                    return;
                }
                topay = bank.find.findByNumber(number);
                if(bank.confirm("payment", topay)) {
                    try {
                        payment(topay, stg, 1);
                    } catch (NoResourcesException er) {}
                }
            } catch (NoUserFindException e1) {
                display.alert("No such user find!");
            }
            
           
    }
    
    void toTake() {
        User totake = null;
        double money = 0;
        Stage stg = display.createStage("Payment");
        stg.setOnCloseRequest(new closeWindowButton(stg, bank, true));
        String text2 = "pay out";
        try {
            int number = enter.enterUserNumber(text2, stg);
            if(number == -1) {
                return;
            }
            totake = bank.find.findByNumber(number);
            if(bank.confirm("payout", totake)) {
                try {
                    payment(totake, stg, 0);
                } catch (NoResourcesException e) {
                    display.alert("There is no resources to do this!");
                }
            }
        } catch (NoUserFindException e1) {
            display.alert("No such user find!");
        }
  
    }
    
    void payment(User toPay, Stage stg, int mode) throws NoResourcesException {
        GridPane mainWindow = display.createGridPane();
        Label lb = new Label("Amount of money: ");
        TextField textField1 = new TextField();

        Text lb2 = new Text();
        
        Button b1 = new Button("Cancel");
        Button b2 = new Button("Apply");
        b1.setOnAction(new cancelButton(stg));
        applyPaymentButton aPB = new applyPaymentButton (textField1, lb2, mode, bank, stg, toPay);
        b2.setOnAction(aPB);
         

        mainWindow.add(lb, 1, 1);
        mainWindow.add(textField1, 2, 1);
        mainWindow.add(lb2, 1, 3);
        mainWindow.add(b1, 2, 2);
        mainWindow.add(b2, 1, 2);
        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();
        
    }
    
    double payOutTransfer(User topayout, Stage stg) throws NoResourcesException {
        GridPane mainWindow = display.createGridPane();
        Label lb = new Label("Amount of money: ");
        TextField textField1 = new TextField();
        Text lb2 = new Text();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        lb2.setEffect(ds);
        lb2.setCache(true);
        lb2.setFill(Color.RED);
        lb2.setFont(Font.font(null, FontWeight.BOLD, 12));

        Button b1 = new Button("Cancel");
        Button b2 = new Button("Apply");
        b1.setOnAction(new cancelButton(stg));
        b2.setOnAction(new applyTransferButton(textField1, lb2, bank, topayout, stg));	
       

        mainWindow.add(lb, 1, 1);
        mainWindow.add(textField1, 2, 1);
        mainWindow.add(lb2, 1, 3);
        mainWindow.add(b1, 2, 2);
        mainWindow.add(b2, 1, 2);
        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.showAndWait();

        return Double.parseDouble(textField1.getText());
    }
    
    void transferMoney() {
        User user1, user2;
        String txt1 = "pay in", txt2 = "take from";
        Stage stg = display.createStage("Transfer");
        stg.setOnCloseRequest(new closeWindowButton(stg, bank, true));
        
        try {
            int number1 = enter.enterUserNumber (txt1, stg);
            if(number1 == -1) {
                return;
            }
            user1 = bank.find.findByNumber(number1);         
        } catch (NoUserFindException e) {
            display.alert("No such user find.");
            stg.close();
            return;
        }

        try {
            int number2 = enter.enterUserNumber (txt2, stg);
            if(number2 == -1) {
                return;
            }
            user2 = bank.find.findByNumber(number2);
        } catch (NoUserFindException e) {
            display.alert("No such user find.");
            stg.close();
            return;
        }
        double money = 0;
        try {
            if(bank.confirm(txt2, user2) && bank.confirm(txt1, user1)) {
                money = payOutTransfer(user2, stg);
                if(money > 0) {
                    payIn(user1, money);
                }
            } 
        } catch (NoResourcesException e) {
            display.alert("No resources to do this!");
        }
        
        Transaction t = new Transaction(user2, user1, money);
        t.addToUsers();
        t.addToBankTransfer(bank);
    }
    
    void transferForClient (User user, User target, Stage stg) { 
        String txt1 = "transfer money to";
        double money = 0;
        try {
            if(bank.confirm(txt1, target)) {
                money = payOutTransfer(user, stg);
                if(money > 0) {
                    payIn(target, money);
                }
            } 
        } catch (NoResourcesException e) {
            display.alert("No resources to do this!");
        }
        Transaction t = new Transaction(user, target, money);
        t.addToBankTransfer(bank);
        t.addToUsers();
    }
    
    private void payIn(User topay, double money) {
        topay.account.payment(money);
    }

}
