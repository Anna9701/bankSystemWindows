package bank;

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
    private SecretKey secretkey;
    private String pass;
    private final passwordsUtil passwordsutil;

    User(int sNo, String fname, String lname, long p, String adr, double money){
        passwordsutil = new passwordsUtil(this);
        systemNumber = sNo;
        firstname = fname;
        lastname = lname;
        pesel = p;
        adress = adr;
        account = new Account (money);
        passwordsutil.changePassword();
    }

    void display () {
        System.out.println(systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources());
    }
    
    boolean checkPassword(String p) {
        return passwordsutil.checkPassword(p);
    }
    
    @Override
    public String toString() {
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15d%-20s%-20.2f", systemNumber, firstname, lastname, pesel, adress, account.getResources());
        String text = format1.toString();
        System.out.println(text);
        
        return text;
    }
    
    SecretKey getKey() {
        return secretkey;
    }
    
    String getPassword() {
        return pass;
    }
    
    void setKey(SecretKey key) {
        secretkey = key;
    }
    
    void setPassword(String password) {
        pass = password;
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

}
