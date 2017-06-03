/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class findUtil {
    private transient Scanner in = new Scanner (System.in);
    private ArrayList<User> users;
    private displayUtil display = new displayUtil();
    private enterUtil enter = new enterUtil();
    
    findUtil(Scanner in, ArrayList<User> users) {
       this.in = in;
       this.users = users;
    }
    
    int findDisplay () {
        ArrayList<Button> buttons = display.createFindButtons();
        GridPane mainWindow = display.createGridPane();
        int id = 1, start = 3;
        Stage stage = display.createStage("Find");
        FindButtonHandler fbh = new FindButtonHandler(stage);
        
        for (Button button : buttons) {
            mainWindow.add(button, 1, start++);
            button.setId(Integer.toString(id++));
            button.setMinWidth(400);
            button.setOnAction(fbh);
        
        }
        Scene scene = new Scene(mainWindow);
        stage.setScene(scene);
        stage.showAndWait();

        return fbh.choise;
    }

   

    ArrayList<User> find () throws NoUserFindException {
        int choise = findDisplay();
        ArrayList<User> usersfinded = new ArrayList<User> ();
        User user = null;
  

        try {
            switch (choise) {
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
            case 6:
                break;
            default:
                display.alert("Something went wrong!");
                break;
            }
        } catch (NoUserFindException e){
            throw e;
        }

        return usersfinded;
    }
    
    
    
    User findByNumber() throws NoUserFindException {
        User user = null;
        Stage stg = display.createStage("Find by number");
        String text1 = "find";
       
        
       int numbertofind = enter.enterUserNumber(text1, stg);
       if(numbertofind == -1) {
           return user;
       }
       
       try {
            user = findByNumber(numbertofind);
        } catch (NoUserFindException e) {
            throw e;
        }

        return user;
    }

    User findByNumber(int number) throws NoUserFindException {

        Iterator<User> it = users.iterator();
        while(it.hasNext()) {
            User searched = it.next();
            if (searched.getNumber() == number) {
                return searched;
            }
        }
        throw new NoUserFindException();
    }

    ArrayList<User> findByName () throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();
        String text1 = "name", text2 = "to find";
        Stage stg = display.createStage("Find by name");
       
        String name = enter.enterUserText(text1, text2, stg);
        if(name.equals("-1")) {
            return usersfinded;
        }
        
        try {
            usersfinded = findByName(name);
        } catch (NoUserFindException e) {
            throw e;
        }

        return usersfinded;
    }

    ArrayList<User> findByName (String name) throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();

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

    ArrayList<User> findByLastName () throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();
        String text1 = "lastname", text2 = "to find";
        Stage stg = display.createStage("Find by lastname");
       
        String lastname = enter.enterUserText(text1, text2, stg);
        if(lastname.equals("-1")) {
            return usersfinded;
        }
        
        try {
            usersfinded = findByLastName(lastname);
        } catch (NoUserFindException e) {
            throw e;
        }

        return usersfinded;
    }

    ArrayList<User> findByLastName (String lastname) throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();


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

    ArrayList <User> findByAdress () throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();
        String text1 = "address", text2 = "to find";
        Stage stg = display.createStage("Find by adress");
       
        String adress = enter.enterUserText(text1, text2, stg);
        if(adress.equals("-1")) {
            return usersfinded;
        }
        
        try {
            usersfinded = findByAdress(adress);
        } catch (NoUserFindException e) {
            throw e;
        }

        return usersfinded;
    }

    ArrayList <User> findByAdress (String adress) throws NoUserFindException {
        ArrayList<User> usersfinded = new ArrayList<User> ();

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

    User findByPesel() throws NoUserFindException {
        User user = null;
        String text1 = "find";
        Stage stg = display.createStage("Find by PESEL");
       
        long numbertofind = enter.enterUserLong(text1, stg);
        if(numbertofind == -1) {
            return user;
        }
        
        try {
            user = findByPesel(numbertofind);
        } catch (NoUserFindException e) {
            throw e;
        }

        return user;
    }

    User findByPesel(long number) throws NoUserFindException {
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
