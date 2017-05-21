package bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

class BankSystem implements Serializable {
	private Vector<User> users;
        private String filename;
	private transient Scanner in = new Scanner (System.in);

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
                            alert("We can't load this bank database! Sorry!");
                            Platform.exit();
                    }
                } else {
                    filename = file;
                    users = new Vector<User> ();
                }
	}

	void saveState() {
		try{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			alert("Exception while save bank state");
		}
	}

	void deleteUser() {
            Stage stg = createStage("Delete User");
            try {
                int todelete = enterUserNumber("delete", stg);
                User tmp = findByNumber(todelete);
                if(confirm("delete", tmp)) {
                        deleteUser(tmp);
                }
            } catch (NoUserFindException er) {
                alert("No such user found");
            }  
	}

        
        void alert(String text) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(text);
            alert.showAndWait();
        }
        
	void toPay() {
            Stage stg = new Stage();	
            User topay;
		try {   
                        int number = enterUserNumber("payment", stg);
			topay = findByNumber(number);
			if(confirm("payment", topay)) {
				payIn(topay);
			}
		} catch (NoUserFindException e1) {
			System.out.println("No such user find!");
		}
	}

	void toTake() {
		User totake;
                Stage stg = new Stage();
		try {
                        int number = enterUserNumber("pay out", stg);
			totake = findByNumber(number);
			if(confirm("payout", totake)) {
				try {
					payOut(totake);
				} catch (NoResourcesException e) {
					System.out.println("There is no resources to do this!");
				}
			}
		} catch (NoUserFindException e1) {
			System.out.println("No such user find!");
		}
	}

	void menu(int choise) {
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
			users.addElement(add);
		}
	}

	private int enterUserNumber (String text, Stage stg) throws NoUserFindException {
     
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
        
	void payIn(User topay) {
		System.out.println("Enter amount of money to pay in: ");
		double moneytopay = in.nextDouble();
		if(moneytopay <= 0) {
			System.out.println("You cannot pay in less than 0!");
			return;
		}
		if(confirm("amount " + Double.toString(moneytopay))) {
			topay.account.payment(moneytopay);
		}
	}

	private void payIn(User topay, double money) {
		topay.account.payment(money);
	}

	void payOut(User topayout) throws NoResourcesException {
		System.out.println("Enter amount of money: ");
		double moneytopayout = in.nextDouble();
		if(moneytopayout <= 0) {
			System.out.println("You cannot take less than 0!");
			return;
		}
		if(confirm("amount " + Double.toString(moneytopayout))) {
			try {
				topayout.account.payout(moneytopayout);
			} catch (NoResourcesException e) {
				throw e;
			}
		}
	}

	private double payOutTransfer(User topayout) throws NoResourcesException {
		final int smtwentwrong = -1;
		System.out.println("Enter amount of money: ");
		double moneytopayout = in.nextDouble();
		if(moneytopayout <= 0) {
			System.out.println("You cannot take less than 0!");
			return smtwentwrong;
		}
		if(confirm("amount " + Double.toString(moneytopayout))) {
			try {
				topayout.account.payout(moneytopayout);
			} catch (NoResourcesException e) {
				throw e;
			}
		} else {
			moneytopayout = smtwentwrong;
		}

		return moneytopayout;
	}

	void transferMoney() {
		User user1, user2;
		String txt1 = "pay in", txt2 = "take from";
                Stage stg = new Stage();
		try {
			int number1 = enterUserNumber (txt1, stg);
			int number2 = enterUserNumber (txt2, stg);
                        user1 = findByNumber(number1);
                        user2 = findByNumber(number2);
		} catch (NoUserFindException e) {
			System.out.println("No such user find.");
			return;
		}
		try {
			if(confirm(txt2, user2) && confirm(txt1, user1)) {
				double money = payOutTransfer(user2);
				if(money <= 0) {
					return;
				}
				payIn(user1, money);
			} else {
				return;
			}
		} catch (NoResourcesException e) {
			System.out.println("No resources to do this!");
		}
	}

	void displayAll () {
		System.out.println();
		//System.out.println("No.\t FName\t\t LName\t\t PESEL\t Adress\t\t Money");
	    Iterator<User> it = users.iterator();
		while(it.hasNext()) {
				it.next().display();
		}
		System.out.println();
	}

	void displaySpecific() {
		try {
			Vector<User> tmp = find();
			Iterator<User> it = tmp.iterator();
			while(it.hasNext()) {
				it.next().display();
			}
		} catch (NoUserFindException e) {
			System.out.println("No such user!");
			return;
		}
	}


	private void findDisplay () {
		System.out.println("Do you want search by: ");
		System.out.println("1. Users number in system");
		System.out.println("2. Name");
		System.out.println("3. Last name");
		System.out.println("4. PESEL");
		System.out.println("5. Adress");
		System.out.println("0. Exit");
	}

	private int chooseFind () {
		int choise;
		do {
			findDisplay();
			choise = in.nextInt();
		} while (choise < 0 || choise > 5);

		return choise;

	}

	private Vector<User> find () throws NoUserFindException {
		int choise = chooseFind();
		Vector<User> usersfinded = new Vector<User> ();
		User user = null;
		in.nextLine();

		try {
			switch (choise) {
			case 0:
				break;
			case 1:
				user = findByNumber();
				usersfinded.add(user);
				break;
			case 2:
				usersfinded = findByName();
				break;
			case 3:
				usersfinded = findByLastName();
				break;
			case 4:
				user = findByPesel();
				usersfinded.add(user);
				break;
			case 5:
				usersfinded = findByAdress();
				break;
			default:
				System.out.println("Something went wrong!");
				break;
			}
		} catch (NoUserFindException e){
			throw e;
		}

		return usersfinded;
	}

	private User findByNumber() throws NoUserFindException {
		User user;


		System.out.println("Enter system number of user: ");
		int numbertofind = in.nextInt();

		try {
			user = findByNumber(numbertofind);
		} catch (NoUserFindException e) {
			throw e;
		}

		return user;
	}

	private User findByNumber(int number) throws NoUserFindException {

		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User searched = it.next();
			if (searched.getNumber() == number) {
				return searched;
			}
		}
		throw new NoUserFindException();
	}

	private Vector<User> findByName () throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();


		System.out.println("Enter name of user: ");
		String name = new String(in.nextLine());

		try {
			usersfinded = findByName(name);
		} catch (NoUserFindException e) {
			throw e;
		}

		return usersfinded;
	}

	private Vector<User> findByName (String name) throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();

		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User searched = it.next();
			if (searched.getName().compareTo(name) == 0) {
				usersfinded.add(searched);
			}
		}

		if(usersfinded.isEmpty()) {
			throw new NoUserFindException();
		} else {
			return usersfinded;
		}
	}

	private Vector<User> findByLastName () throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();

		String lastname;

		System.out.println("Enter lastname of user: ");
		lastname = in.nextLine();

		try {
			usersfinded = findByLastName(lastname);
		} catch (NoUserFindException e) {
			throw e;
		}

		return usersfinded;
	}

	private Vector<User> findByLastName (String lastname) throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();


		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User searched = it.next();
			if (searched.getLastName().compareTo(lastname) == 0) {
				usersfinded.add(searched);
			}
		}

		if(usersfinded.isEmpty()) {
			throw new NoUserFindException();
		} else {
			return usersfinded;
		}
	}

	private Vector <User> findByAdress () throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();
		String adress;

		System.out.println("Enter adress of user: ");
		adress = in.nextLine();

		try {
			usersfinded = findByAdress(adress);
		} catch (NoUserFindException e) {
			throw e;
		}

		return usersfinded;
	}

	private Vector <User> findByAdress (String adress) throws NoUserFindException {
		Vector<User> usersfinded = new Vector<User> ();

		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User searched = it.next();
			if (searched.getAdress().compareTo(adress) == 0) {
				usersfinded.add(searched);
			}
		}
		if (usersfinded.isEmpty()) {
			throw new NoUserFindException();
		} else {
			return usersfinded;
		}
	}

	private User findByPesel() throws NoUserFindException {
		User user;

		System.out.println("Enter PESEL number: ");
		long numbertofind = in.nextLong();

		try {
			user = findByPesel(numbertofind);
		} catch (NoUserFindException e) {
			throw e;
		}

		return user;
	}

	private User findByPesel(long number) throws NoUserFindException {
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User searched = it.next();
			if (searched.getPesel() == number) {
				return searched;
			}
		}
		throw new NoUserFindException();
	}

}
