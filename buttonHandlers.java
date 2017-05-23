/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author esperanza
 */

class MenuButtonHandler implements EventHandler<ActionEvent> {
    Bank bank;

    MenuButtonHandler(Bank bk) {
        bank = bk;
    }

    @Override public void handle(ActionEvent e) {
        Button btn = (Button) e.getSource();
        int id = Integer.parseInt(btn.getId());
        bank.bankSystem.menu(id);
    }
}

class FindButtonHandler implements EventHandler<ActionEvent> {
    int choise;
    Stage stg;
    FindButtonHandler(Stage stage) {
        stg = stage;
    }
    
    @Override public void handle(ActionEvent e) {
        Button btn = (Button) e.getSource();
        int id = Integer.parseInt(btn.getId());
        choise = id;
        stg.hide();
    }
}
    
class MyButtonHandler implements EventHandler<ActionEvent> {
    TextField name;
    Stage root;
    Bank bank;


    MyButtonHandler(TextField field, Stage stg, Bank bk){
        name = field;
        root = stg;
        bank = bk;
    }
     @Override
    public void handle(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String value = btn.getText();
        String text = name.toString();
        if(value.equals("Create")) {
            bank.bankSystem = new BankSystem(text, 0);
        } else {
            bank.bankSystem = new BankSystem(text, 1);
        }
        bank.menu(root);
    }
}

class cancelButton implements EventHandler<ActionEvent> {
        Stage window;

        cancelButton(Stage stg) {
            window = stg;
        }
        @Override 
        public void handle(ActionEvent e) {
            window.close();
        }
}

class enterUserNumberButton implements EventHandler<ActionEvent> {
        TextField tf;
        int number;
        Stage stg;

        enterUserNumberButton(TextField text, Stage stag) {
            tf = text;
            stg = stag;
        }
        @Override
        public void handle(ActionEvent e) {
            number = Integer.parseInt(tf.getText());
            stg.hide();
        }
    }

class applyPaymentButton implements EventHandler<ActionEvent> {
   TextField textField1;
   Text lb2;
   int mode;
   BankSystem bank;
   Stage stg;
   User toPay;
   
   applyPaymentButton(TextField tf, Text lb, int m, BankSystem bk, Stage stage, User tP) {
       textField1 = tf;
       lb2 = lb;
       mode = m;
       bank = bk;
       stg = stage;
       toPay = tP;
   }
   
    @Override
 
    public void handle(ActionEvent e) {
        double moneytopay = Double.parseDouble(textField1.getText());
        if(moneytopay <= 0 ){
            if(mode == 1) {
                lb2.setText("You cannot pay in less than 0!");
            } else {
                lb2.setText("You cannot pay out less than 0!");
            }
            textField1.clear();
        } else {
            if(mode == 1) {
                if(bank.confirm("amount " + Double.toString(moneytopay))) {
                    toPay.account.payment(moneytopay);
                    stg.hide();
                }
            } else {
                try {
                    toPay.account.payout(moneytopay);
                    stg.hide();
                } catch (NoResourcesException ee) {
                    lb2.setText("There is no resources to do this!");
                    textField1.clear();
                }
            }
        }
    }
}

class applyTransferButton implements EventHandler<ActionEvent> {
    TextField textField1;
    Text lb2;
    BankSystem bank;
    User toPayOut;
    Stage stg;
    
    applyTransferButton(TextField tf, Text tx, BankSystem bk, User usr, Stage stage) {
        textField1 = tf;
        lb2 = tx;
        bank = bk;
        toPayOut = usr;
        stg = stage;
    }
    
    @Override
    public void handle(ActionEvent e) {
        double amount = Double.parseDouble(textField1.getText());
        if(amount <= 0) {
            lb2.setText("You cannot take less than 0!");
            textField1.clear();
        } else {
            if(bank.confirm("amount " + Double.toString(amount))) {
                try {
                    toPayOut.account.payout(amount);
                    stg.hide();
                } catch (NoResourcesException ee) {
                    lb2.setText("There is no resources!");
                    textField1.clear();
                }
            } 
        }

    } 
}
