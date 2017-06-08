/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import javafx.scene.text.Text;

/**
 *
 * @author esperanza
 */
class Transaction {
    User from;
    User to;
    double amount;
    String result = new String ();
    String result2 = new String ();
    
    Transaction (User a, User b, double money) {
        from = a;
        to = b;
        amount = money;
    }
    
    Transaction (User a, double money) {
        from = a;
        amount = money;
    }
    
    void transactionIn() {
        LocalDateTime localdatetime = LocalDateTime.now();
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15.2f%-20s%-20s\n", from.getNumber(), from.getName(), from.getLastName(), amount, "pay out", localdatetime.toString());
        result = format1.toString();
    }
    
    void transactionOut() {
        LocalDateTime localdatetime = LocalDateTime.now();
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15.2f%-20s%-20s\n", from.getNumber(), from.getName(), from.getLastName(), amount, "pay out", localdatetime.toString());
        result = format1.toString();
    }
    
    void transactionBeetwen() {
        LocalDateTime localdatetime = LocalDateTime.now();
        Formatter format1 = new Formatter();
        format1.format("%-15d%-20s%-20s%-15.2f%-20s%-20s\n", from.getNumber(), from.getName(), from.getLastName(), amount, "pay out", localdatetime.toString());
        result = format1.toString();
        Formatter format2 = new Formatter();
        format2.format("%-15d%-20s%-20s%-15.2f%-20s%-20s\n", to.getNumber(), to.getName(), to.getLastName(), amount, "pay in", localdatetime.toString());
        result2 = format2.toString();
    }
    
    String getResult1 () {
        return result;
    }
    
    String getResult2 () {
        return result2;
    }
    
    void addToUserIn (User usr) {
        transactionIn();
        usr.history.add(getResult1());
    }
    
    void addToUserOut (User usr) {
        transactionOut();
        usr.history.add(getResult1());
    }
    
    void addToUsers () {
        transactionBeetwen();
        from.history.add(getResult1());
        to.history.add(getResult2());
    }
    
    void addToBankPayIn (BankSystem bs) {
        transactionIn();
        bs.addToHistory(getResult1());
    }
    
    void addToBankPayOut (BankSystem bs) {
        transactionOut();
        bs.addToHistory(getResult1());
    }
    
    void addToBankTransfer (BankSystem bs) {
        transactionBeetwen();
        bs.addToHistory(getResult1());
        bs.addToHistory(getResult2());
    }
}