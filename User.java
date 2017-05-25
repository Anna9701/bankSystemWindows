package bank;

import java.util.Formatter;

class NoUserFindException extends Exception {}

class User implements java.io.Serializable {
    private int systemNumber;
    private String firstname;
    private String lastname;
    private long pesel;
    private String adress;
    Account account;


    User(int sNo, String fname, String lname, long p, String adr, double money){
        systemNumber = sNo;
        firstname = fname;
        lastname = lname;
        pesel = p;
        adress = adr;
        account = new Account (money);
    }

    void display () {
        System.out.println(systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources());
    }

    @Override
    public String toString() {
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15d%-20s%-20.2f", systemNumber, firstname, lastname, pesel, adress, account.getResources());
        String text = format1.toString();
        System.out.println(text);
        
        return text;
    }
    
    String toText(int systemNumber, String fname) {
        String text = String.valueOf(systemNumber);
        int size = 20 - text.length();
        for(int i = 0; i < size ; i++) {
            text += " ";
        }
        size = 20 - fname.length();
        text += fname;
        for(int i = 0; i < size ; i++) {
            text += " ";
        }
        
        return text;
    }
    
    String toText(long number) {
        String text = String.valueOf(number);
        int size = 20 - text.length();
        for(int i = 0; i < size ; i++) {
            text += ' ';
        }
 
        return text;
    }
    
    String toText(String tmp) {
        int size = 20 - tmp.length();
        for(int i = 0; i < size ; i++) {
            tmp += ' ';
        }
        
        return tmp;
    }
    
    /*@Override
    public String toString() {
        return systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources();
    }*/

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
