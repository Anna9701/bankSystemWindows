/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author esperanza
 */
public class historyUtilBank {
    ArrayList<Transaction> history = new ArrayList<> ();
}

class historyUtilClient {
    private ArrayList<Transaction> history = new ArrayList<> ();
    
    void addTransaction (Transaction t) {
        history.add(t);
    }
    
    void readTransactions () {
        history.forEach((t) -> {
            System.out.println(t);
        });
    }
}

class Transaction {
    User from;
    User to;
    Double amount;
    boolean both;
    String result = new String ();

    Transaction (User a, User b, double money) {
        from = a;
        to = b;
        amount = money;
        both = true;
    }
    
    Transaction (User a, double money) {
        from = a;
        amount = money;
        both = false;
    }
    
    void transactionIn() {
        result += from.getNumber() + from.getName() + from.getLastName();
        LocalDateTime localdatetime = LocalDateTime.now();
        result += amount + "Pay in" + localdatetime.toString();
    }
    
    void transactionOut() {
        result += from.getNumber() + from.getName() + from.getLastName();
        LocalDateTime localdatetime = LocalDateTime.now();
        result += amount + "Pay out" + localdatetime.toString();
    }
    
    void transactionBeetwen() {
        result += from.getNumber() + from.getName() + from.getLastName();
        LocalDateTime localdatetime = LocalDateTime.now();
        result += amount + "pay out" + localdatetime.toString() + '\n';
        result += to.getNumber() + to.getName() + to.getLastName();
        result += amount + "pay in" + localdatetime.toString();
    }
    
    @Override
    public String toString() {
        return result;
    }
}