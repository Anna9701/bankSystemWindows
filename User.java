package bank;

import java.util.Formatter;
import java.util.LinkedList;

class NoUserFindException extends Exception {}


class User implements java.io.Serializable {
    private final int systemNumber;
    private final String firstname;
    private final String lastname;
    private final long pesel;
    private final String adress;
    Account account;
    private String pass;
    private final passwordsUtil passwordsutil;
    LinkedList<String> history;

    User(int sNo, String fname, String lname, long p, String adr, double money) throws noPasswordException{
        systemNumber = sNo;
        firstname = addText(fname);
        lastname = addText(lname);
        pesel = p;
        adress = addText(adr);
        account = new Account (money);
        passwordsutil = new passwordsUtil(this);
        history = new LinkedList<> ();
        try {
            passwordsutil.changePassword();
        } catch (noPasswordException e) {
            throw e;
        }
    }
    
    User(int sNo, String fname, String lname, long p, String adr, double money, String pas) {
        systemNumber = sNo;
        firstname = addText(fname);
        lastname = addText(lname);
        pesel = p;
        adress = addText(adr);
        account = new Account (money);
        passwordsutil = new passwordsUtil(this);
        history = new LinkedList<> ();
        pass = pas;
    }
    
    void addToHistory (String txt) {
        history.addFirst(txt);
    }
    
    String addText(String text) {
        char [] tmp = text.toCharArray();
        for(int i=1; i < text.length(); i++) {
            if(Character.isLetter(tmp[i])) {
                tmp[i] = Character.toLowerCase(tmp[i]);
            }
        }
        tmp[0] = Character.toUpperCase(tmp[0]);
        
        return new String(tmp);
    }
    
    boolean checkPassword(String p) {
        return passwordsutil.checkPassword(p);
    }
    
    void display () {
        System.out.println(systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources());
    }
    
 

    @Override
    public String toString() {
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15d%-20s%-20.2f", systemNumber, firstname, lastname, pesel, adress, account.getResources());
        String text = format1.toString();
        
        return text;
    }

    int getNumber() {
        return systemNumber;
    }

    String getName () {
        return firstname;
    }

    String getLastName () {
        return lastname;
    }

    long getPesel () {
        return pesel;
    }

    String getAdress () {
        return adress;
    }

   
    
    String getPassword() {
        return pass;
    }
    
   
    
    void setPassword(String s) {
        pass = s;
    }
    
    LinkedList<String> getHistory () {
        return history;
    }
    
}


