/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author esperanza
 */

class MenuButtonHandler implements EventHandler<ActionEvent> {
    Bank bank;
    User user;
    
    MenuButtonHandler(Bank bk) {
        bank = bk;
    }

    MenuButtonHandler(Bank aThis, User usr) {
       bank = aThis;
       user = usr;
    }

    @Override public void handle(ActionEvent e) {
        Button btn = (Button) e.getSource();
        int id = Integer.parseInt(btn.getId());
        if(Bank.procedure == 0) {
            bank.bankSystem.menuForBank(id);
        } else {
            bank.bankSystem.menuForClient(id, user);
        }
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
    int mode;
    ToggleGroup toggleGroup;

    MyButtonHandler(TextField field, Stage stg, Bank bk, ToggleGroup selected){
        name = field;
        root = stg;
        bank = bk;
        mode = Bank.procedure;
        toggleGroup = selected;
    }
     @Override
    public void handle(ActionEvent event) {
        ToggleButton selectedToggleButton = (ToggleButton) toggleGroup.getSelectedToggle();
        if(selectedToggleButton.getUserData().equals("Bank")) {
            Bank.procedure = 0;
        } else {
            Bank.procedure = 1;
        }

        Button btn = (Button) event.getSource();
        String value = btn.getText();
        String text = name.getText();
        if(value.equals("Create")) {
            bank.bankSystem = new BankSystem(text, 0, mode);
        } else {
            bank.bankSystem = new BankSystem(text, 1, mode);
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
        boolean flag = false;
        
        enterUserNumberButton(TextField text, Stage stag) {
            tf = text;
            stg = stag;
        }
        @Override
        public void handle(ActionEvent e) {
            flag = true;
            number = Integer.parseInt(tf.getText());
            stg.hide();
        }
    }

class enterUserLongButton implements EventHandler<ActionEvent> {
        TextField tf;
        long number;
        Stage stg;
        boolean flag = false;

        enterUserLongButton(TextField text, Stage stag) {
            tf = text;
            stg = stag;
        }
        @Override
        public void handle(ActionEvent e) {
            flag = true;
            number = Long.parseLong(tf.getText());
            stg.hide();
        }
}


class enterUserTextButton implements EventHandler<ActionEvent> {
        TextField tf;
        String text;
        Stage stg;
        boolean flag = false;
        
        enterUserTextButton(TextField text, Stage stag) {
            tf = text;
            stg = stag;
        }
        @Override
        public void handle(ActionEvent e) {
            flag = true;
            text = tf.getText();
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
   static displayUtil display = new displayUtil();
   
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
                    display.setText("There is no resources to do this!", lb2);
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

class closeWindowButton implements EventHandler<WindowEvent> {
    Stage stage;
    boolean saveFlag;
    BankSystem bankSystem;
    
    closeWindowButton(Stage stg, BankSystem bank, boolean flag) {
        stage = stg;
        bankSystem = bank;
        saveFlag = flag;
    }
    @Override
    public void handle(WindowEvent event) {
        event.consume();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Confirmation");
        alert.setHeaderText("Do you really want to close this window?");
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(saveFlag) {
              bankSystem.saveState();//////////////////////// saaaaaaaaaaave ????
            }
            stage.close();
        }
     }
}

class exitButton implements EventHandler<WindowEvent> {
    Stage stage;
    boolean saveFlag;
    BankSystem bankSystem;
    
    exitButton(Stage stg, BankSystem bank, boolean flag) {
        stage = stg;
        bankSystem = bank;
        saveFlag = flag;
    }
        @Override
        public void handle(WindowEvent event) {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Do you really want to quit?");
            alert.initOwner(stage);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(saveFlag) {
                  bankSystem.saveState();//////////////////////// saaaaaaaaaaave ????
                }
                Platform.exit();
            }
     }
}
        

