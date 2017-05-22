package bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class BankSystem implements Serializable {
    private ArrayList<User> users;
    private String filename;
    private transient Scanner in = new Scanner (System.in);
    private static findUtil find;
    private static displayUtil display = new displayUtil();

    BankSystem(String file, int mode) {
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
        Stage stg = createStage("Delete User");
        try {
            int todelete = enterUserNumber("delete", stg);
            User tmp = find.findByNumber(todelete);
            if(confirm("delete", tmp)) {
                    deleteUser(tmp);
            }
        } catch (NoUserFindException er) {
            display.alert("No such user found");
        }  
    }


   

    void toPay() {
        Stage stg = createStage("Payment");	
        User topay;
            try {   
                int number = enterUserNumber("payment", stg);
                topay = find.findByNumber(number);
                if(confirm("payment", topay)) {
                    try {
                        payment(topay, stg, 1);
                    } catch (NoResourcesException er) {

                    }
                }
            } catch (NoUserFindException e1) {
                display.alert("No such user find!");
            }
    }

    void toTake() {
        User totake;
        Stage stg = createStage("Payment");
        try {
            int number = enterUserNumber("pay out", stg);
            totake = find.findByNumber(number);
            if(confirm("payout", totake)) {
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

    void menu(int choise){
        switch(choise) {
        case 1:
                addUser();
                break;
        case 2:
                deleteUser();
                break;
        case 3:
                toPay();
                break;
        case 4:
                toTake();
                break;
        case 5:
                transferMoney();
                break;
        case 6:
                displayAll();
                break;
        case 7:
                displaySpecific();
                break;
        case 8:
                saveState();
                break;
        }
    }

    private boolean confirm (String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to " + text + " ?");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } 
        return false;
    }


    private boolean confirm (String text, User u) {
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

    Stage createStage(String text) {
        Stage stg = new Stage();
        stg.setTitle(text);
        return stg;
    }
    
    GridPane createGridPane(){
        GridPane mainWindow = new GridPane();
        mainWindow.setVgap(10);
        mainWindow.setHgap(10);
        mainWindow.setPadding(new Insets(15, 15, 15, 15));

        return mainWindow;
    }

    void addUser() {
        Stage stg = createStage("Add user");
        GridPane mainWindow = createGridPane();

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

    private class cancelButton implements EventHandler<ActionEvent> {
        Stage window;

        cancelButton(Stage stg) {
            window = stg;
        }
        @Override public void handle(ActionEvent e) {
            window.close();
        }
    }

    private void addUser (int sNo, String fname, String lname, long p, String adr, double money){
        User add = new User(sNo, fname, lname, p, adr, money);
        if(confirm("add", add)) {
            users.add(add);
        }
    }

    private int enterUserNumber (String text, Stage stg) {

        GridPane mainWindow = createGridPane();

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


       return handler.number;
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

    private void payIn(User topay, double money) {
        topay.account.payment(money);
    }

    void payment(User toPay, Stage stg, int mode) throws NoResourcesException {
        GridPane mainWindow = createGridPane();
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
        b2.setOnAction(new EventHandler<ActionEvent>() {
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
                        if(confirm("amount " + Double.toString(moneytopay))) {
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
        });

        mainWindow.add(lb, 1, 1);
        mainWindow.add(textField1, 2, 1);
        mainWindow.add(lb2, 1, 3);
        mainWindow.add(b1, 2, 2);
        mainWindow.add(b2, 1, 2);
        Scene scene = new Scene(mainWindow);
        stg.setScene(scene);
        stg.show();
    }



    private double payOutTransfer(User topayout, Stage stg) throws NoResourcesException {
        GridPane mainWindow = createGridPane();
        Label lb = new Label("Amount of money: ");
        TextField textField1 = new TextField();
        final int smtwentwrong = -1;
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
        b2.setOnAction(new EventHandler<ActionEvent>() {	
            @Override
            public void handle(ActionEvent e) {
                double amount = Double.parseDouble(textField1.getText());
                if(amount <= 0) {
                    lb2.setText("You cannot take less than 0!");
                    textField1.clear();
                } else {
                    if(confirm("amount " + Double.toString(amount))) {
                        try {
                            topayout.account.payout(amount);
                            stg.hide();
                        } catch (NoResourcesException ee) {
                            lb2.setText("There is no resources!");
                            textField1.clear();
                        }
                    } 
                }

            }   
        });

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
        Stage stg = createStage("transform");

        try {
            int number1 = enterUserNumber (txt1, stg);
            user1 = find.findByNumber(number1);         
        } catch (NoUserFindException e) {
            display.alert("No such user find.");
            stg.close();
            return;
        }

        try {
            int number2 = enterUserNumber (txt2, stg);
            user2 = find.findByNumber(number2);
        } catch (NoUserFindException e) {
            display.alert("No such user find.");
            stg.close();
            return;
        }

        try {
            if(confirm(txt2, user2) && confirm(txt1, user1)) {
                double money = payOutTransfer(user2, stg);
                if(money > 0) {
                    payIn(user1, money);
                }
            } 
        } catch (NoResourcesException e) {
            display.alert("No resources to do this!");
        }
    }

    void displayTable() {
        Stage stg = createStage("Bank Accounts");
        Label lb1 = new Label("Number");
        GridPane window = createGridPane();

        final ObservableList data = FXCollections.observableArrayList();
        data.addAll(users);
        final ListView listView = new ListView(data);
        listView.setPrefSize(500, 250);
        listView.setEditable(false);
        listView.autosize();
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(data));   
        StackPane root = new StackPane();
        root.getChildren().add(lb1);
        root.getChildren().add(listView);
        window.add(lb1, 1, 1);
        stg.setScene(new Scene(root, 500, 250));
        stg.show();
    }

    void displayAll () {

        displayTable();
            /*System.out.println();
            //System.out.println("No.\t FName\t\t LName\t\t PESEL\t Adress\t\t Money");
        Iterator<User> it = users.iterator();
            while(it.hasNext()) {
                            it.next().display();
            }
            System.out.println();*/
    }

    void displaySpecific() {
        try {
            ArrayList<User> tmp = find.find();
            Iterator<User> it = tmp.iterator();
            while(it.hasNext()) {
                it.next().display();
            }
        } catch (NoUserFindException e) {
            display.alert("No such user!");
            return;
        }
    }
}