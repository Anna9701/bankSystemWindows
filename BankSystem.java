package bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.application.Platform;
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
    static findUtil find;
    private static displayUtil display = new displayUtil();
    private static paymentUtil payments;
    private static final enterUtil enter = new enterUtil();
    private LinkedList<String> history = null;
    
    BankSystem(String file, int mode) {
        if(mode == 1) {
            try{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                BankSystem tmp1 = (BankSystem) ois.readObject();
                ois.close();
                this.users = tmp1.users;
                this.filename = tmp1.filename;
                this.history = tmp1.history;    
            } catch (Exception e) {
                display.alert("We can't load this bank database! Sorry!");
                Platform.exit();
            }
        } else {
            filename = file;
            users = new ArrayList<> ();
            history = new LinkedList<> ();
        }
        find = new findUtil(users);
        payments = new paymentUtil(this);
    }
    
    void addToHistory (String s) {
        history.addFirst(s);
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
        int todelete = enter.enterUserNumber(text2, stg);
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
                break;
        case 7:
                displaySpecific();
                break;
        case 8: 
                display.displayHistory(history);
                break;
        }
    }
    
  
    void transferForClient(User user) {
        Stage stg = display.createStage("Transfer");
        stg.setOnCloseRequest(new closeWindowButton(stg, this, true));
        int number = enter.enterUserNumber("transfer money", stg);
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
    
    LinkedList<String> getHistory () {
        return history;
    }
    
    void menuForClient(int choise, User user) throws noPasswordException {
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
                new passwordsUtil(user).changePassword();
                break;
        case 5:
                display.displayHistory(user.getHistory());
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
        alert.getDialogPane().setPrefWidth(800);
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

        ArrayList <Label> labels = display.createAddLabels();
        ArrayList <TextField> textFields = display.createAddTextFields();

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

        btn1.setOnAction(new addUserButton(textFields, stg, this)); 

        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.show();
    }

    private void deleteUser(User todelete) {
       users.remove(todelete);
    }

    void addUser (int sNo, String fname, String lname, long p, String adr, double money){
        User add;
        try {
            add = new User(sNo, fname, lname, p, adr, money);
        } catch (noPasswordException e) {
            return;
        }
        if(confirm("add", add)) {
            addUser(add);
        }
    }
    
    void addUser (User tmp) {
        users.add(tmp);
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

