package bank;

import java.security.Key;
import java.util.Formatter;
import javax.crypto.SecretKey;

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
    

    User(int sNo, String fname, String lname, long p, String adr, double money){
        
        systemNumber = sNo;
        firstname = fname;
        lastname = lname;
        pesel = p;
        adress = adr;
        account = new Account (money);
        passwordsutil = new passwordsUtil(this);
        passwordsutil.changePassword();
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
}


