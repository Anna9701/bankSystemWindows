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



public class UserTest {
    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    User test; 
    
    public UserTest() {
        test = new User(1, "Jan", "Kowalski", 87654587654L, "Miejska 2", 2872.2, "test");
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
        String txt = "History Test";
        User instance = test;
        instance.addToHistory(txt);
    }


    @Test
    public void testAddText() {
        System.out.println("addText");
        String text = "Test Add Text";
        User instance = test;
        String expResult = "Test add text";
        String result = instance.addText(text);
        assertEquals(expResult, result);
    }


    @Test
    public void testCheckPassword() {
        System.out.println("checkPassword");
        String p = "test";
        User instance = test;
        boolean expResult = true;
        boolean result = instance.checkPassword(p);
        assertEquals(expResult, result);
    }

    @Test
    public void testDisplay() {
        System.out.println("display");
        User instance = test;
        instance.display();
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        User instance = test;
        String expResult = instance.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        User instance = test;
        int expResult = 1;
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = test;
        String expResult = "Jan";
        String result = instance.getName();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        User instance = test;
        String expResult = "Kowalski";
        String result = instance.getLastName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPesel() {
        System.out.println("getPesel");
        User instance = test;
        long expResult = 87654587654L;
        long result = instance.getPesel();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetAdress() {
        System.out.println("getAdress");
        User instance = test;
        String expResult = "Miejska 2";
        String result = instance.getAdress();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = test;
        String expResult = "test";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String s = "test2";
        User instance = test;
        instance.setPassword(s);
        String result = instance.getPassword();
        assertEquals(s, result);
    }

    @Test
    public void testGetHistory() {
        System.out.println("getHistory");
        User instance = test;
        LinkedList<String> expResult = new LinkedList<>();
        LinkedList<String> result = instance.getHistory();
        assertEquals(expResult, result);
    }
    
}
