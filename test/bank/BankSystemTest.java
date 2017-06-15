/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author esperanza
 */
public class BankSystemTest {
    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    BankSystem bs = new BankSystem("sample", 2);
    User test = new User(1, "Jan", "Kowalski", 87654587654L, "Miejska 2", 2872.2, "test");
    
    public BankSystemTest() {
        bs.addUser(test);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testAddToHistory() {
        System.out.println("addToHistory");
        String s = "Add to History Bank";
        BankSystem instance = bs;
        instance.addToHistory(s);
    }
    
    @Test
    public void testSaveState() {
        System.out.println("saveState");
        BankSystem instance = bs;
        instance.saveState();
    }

    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        BankSystem instance = bs;
        instance.deleteUser();
    }


    @Test
    public void testMenuForBank() {
        System.out.println("menuForBank");
        int choise = 6;
        BankSystem instance = bs;
        instance.menuForBank(choise);
    }

    @Test
    public void testTransferForClient() {
        System.out.println("transferForClient");
        User user = test;
        BankSystem instance = bs;
        instance.transferForClient(user);
    }

    @Test
    public void testGetHistory() {
        System.out.println("getHistory");
        BankSystem instance = bs;
        LinkedList<String> expResult = new LinkedList<> ();
        LinkedList<String> result = instance.getHistory();
        assertEquals(expResult, result);
    }

    @Test
    public void testMenuForClient() throws Exception {
        System.out.println("menuForClient");
        int choise = 2;
        User user = test;
        BankSystem instance = bs;
        instance.menuForClient(choise, user);
    }


    @Test
    public void testConfirm_String() {
        System.out.println("confirm");
        String text = "test";
        BankSystem instance = bs;
        boolean expResult = true;
        boolean result = instance.confirm(text);
        assertEquals(expResult, result);
    }

    @Test
    public void testConfirm_String_User() {
        System.out.println("confirm");
        String text = "test";
        User u = test;
        BankSystem instance = bs;
        boolean expResult = true;
        boolean result = instance.confirm(text, u);
        assertEquals(expResult, result);
    }


    @Test
    public void testAddUser_0args() {
        System.out.println("addUser");
        BankSystem instance = bs;
        instance.addUser();
    }


    @Test
    public void testAddUser_6args() {
        System.out.println("addUser");
        int sNo = 2;
        String fname = "Maciej";
        String lname = "Kot";
        long p = 89876545676L;
        String adr = "Moniuszki 11";
        double money = 2122.0;
        BankSystem instance = bs;
        instance.addUser(sNo, fname, lname, p, adr, money);
    }

    @Test
    public void testDisplaySpecific() {
        System.out.println("displaySpecific");
        BankSystem instance = bs;
        instance.displaySpecific();
    }
    
}
