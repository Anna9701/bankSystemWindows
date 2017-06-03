package bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class BankSystem implements Serializable {
    private ArrayList<User> users;
    private String filename;
    private transient Scanner in = new Scanner (System.in);
    static findUtil find;
    private static displayUtil display = new displayUtil();
    private static paymentUtil payments;
    private static int procedure;
    
    BankSystem(String file, int mode, int md) {
        if(mode == 1) {
            try{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                BankSystem tmp1 = (BankSystem) ois.readObject();
                ois.close();
                this.users = tmp1.users;
                this.filename = tmp1.filename;
            } catch (Exception e) {
                display.alert("We can't load this bank database! Sorry!");
                Platform.exit();
            }
        } else {
            filename = file;
            users = new ArrayList<User> ();
        }
        find = new findUtil(in, users);
        payments = new paymentUtil(this);
        BankSystem.procedure = md;
    }

    void saveState() {
        try{
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.flush();
                oos.close();
        } catch (IOException e) {
                display.alert("Exception while save bank state");
        }

    }

     void deleteUser() {
        Stage stg = display.createStage("Delete User");
        stg.setOnCloseRequest(new closeWindowButton(stg, this, true));
        String text2 = "delete";
        int todelete = display.enterUserNumber(text2, stg);
        if (todelete == -1) {
            return;
        }
        try {
            User tmp = find.findByNumber(todelete);
            if(confirm("delete", tmp)) {
                deleteUser(tmp);
            }
        } catch (NoUserFindException er) {
            System.out.println("tu jestem");
            display.alert("No such user found");
        }  

    }
    
     
    void menuForBank(int choise) {
        switch(choise) {
        case 1:
                addUser();
                break;
        case 2:
                deleteUser();
                break;
        case 3:
                payments.toPay();
                break;
        case 4:
                payments.toTake();
                break;
        case 5:
                payments.transferMoney();
                break;
        case 6:
                display.displayTable(users);
                //display.displayUsers(users);
                break;
        case 7:
                displaySpecific();
                break;
        case 8:
                saveState();
                break;
        }
    }
    
  
    void transferForClient(User user) {
        Stage stg = display.createStage("Transfer");
        stg.setOnCloseRequest(new closeWindowButton(stg, this, true));
        int number = display.enterUserNumber("transfer money", stg);
        if(number == -1) {
            return;
        }
        User target;
        try {
            target = find.findByNumber(number);
        } catch (NoUserFindException ex) {
            display.alert("No such user found!");
            return;
        }    
        
        payments.transferForClient(user, target, stg);
          
    }
    
    void menuForClient(int choise, User user) {
        switch(choise) {
        case 1:
                transferForClient(user);
                break;
        case 2:
                display.displayUser(user);
                break;
        case 3:
                deleteUserForClient(user);
                break;
        case 4:
                saveState();
                break;
        }
    }
    
    void deleteUserForClient(User user) {
        if(confirm("delete", user)) {
            users.remove(user);
            saveState();
            Platform.exit();
        }
    }
    
    boolean confirm (String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to " + text + " ?");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } 
        return false;
    }


    boolean confirm (String text, User u) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Do you really want to " + text + " this user?\n\n" + u.toString());
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(600);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } 
        return false;
    }

    void addUser() {
        Stage stg = display.createStage("Add user");
        stg.setOnCloseRequest(new closeWindowButton(stg, this, true));
        GridPane mainWindow = display.createGridPane();

        Label label1 = new Label("System Number:");
        final TextField textField1 = new TextField ();
        Label label2 = new Label("First name:");
        TextField textField2 = new TextField ();
        Label label3 = new Label("LastName:");
        TextField textField3 = new TextField ();
        Label label4 = new Label("PESEL:");
        TextField textField4 = new TextField ();
        Label label5 = new Label("Adress:");
        TextField textField5 = new TextField ();
        Label label6 = new Label("Amount of money:");
        TextField textField6 = new TextField ();

        Label labels [] = {label1, label2, label3, label4, label5, label6};
        TextField textFields [] = {textField1, textField2, textField3, textField4, textField5, textField6};

        int start = 1;
        for (Label label : labels) {
            mainWindow.add(label, 1, start++);
        }
        start = 1;
        for (TextField textField : textFields) {
            mainWindow.add(textField, 2, start++);
        }

        Button btn1 = new Button("Accept");
        Button btn2 = new Button("Cancel");
        btn2.setOnAction(new cancelButton(stg));
        mainWindow.add(btn2, 1, start);
        mainWindow.add(btn1, 2, start);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int number = Integer.parseInt(textField1.getText());
                String fname = textField2.getText();
                String lname = textField3.getText();
                long pesel = Long.parseLong(textField4.getText());
                String adress = textField5.getText();
                Double money = Double.parseDouble(textField6.getText());
                addUser(number, fname, lname, pesel, adress, money);
                stg.close();
            }
        });

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.show();
    }

    private void deleteUser(User todelete) {
       users.remove(todelete);
    }

    private void addUser (int sNo, String fname, String lname, long p, String adr, double money){
        User add;
        try {
            add = new User(sNo, fname, lname, p, adr, money);
        } catch (noPasswordException e) {
            return;
        }
        if(confirm("add", add)) {
            users.add(add);
        }
    }

    
    
    void displaySpecific() {
        try {
            ArrayList<User> tmp = find.find();
            if (!tmp.isEmpty()) {
                display.displayTable(tmp);
            }
        } catch (NoUserFindException e) {
            display.alert("No such user!");
            return;
        }
    }
}

