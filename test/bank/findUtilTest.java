/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;
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
public class findUtilTest {
    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    ArrayList<User> users = new ArrayList <> ();
    User test = new User(1, "Jan", "Kowalski", 87654587654L, "Miejska 2", 2872.2, "test");
    findUtil instance;
    
    public findUtilTest() {
       users.add(test); 
       instance = new findUtil(users);
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
    public void testFindDisplay() {
        System.out.println("findDisplay");
        int expResult = 1;
        int result = instance.findDisplay();
        assertEquals(expResult, result);
    }

    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        ArrayList<User> expResult = new ArrayList<> ();
        expResult.add(test);
        ArrayList<User> result = instance.find();
        assertEquals(expResult, result);
    }


    @Test
    public void testFindByNumber_0args() throws Exception {
        System.out.println("findByNumber");
        User expResult = test;
        User result = instance.findByNumber();
        assertEquals(expResult, result);
    }


    @Test
    public void testFindByNumber_int() throws Exception {
        System.out.println("findByNumber");
        int number = 1;
        User expResult = test;
        User result = instance.findByNumber(number);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByName_0args() throws Exception {
        System.out.println("findByName");
        ArrayList<User> expResult = new ArrayList<> ();
        ArrayList<User> result = instance.findByName();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByName_String() throws Exception {
        System.out.println("findByName");
        String name = "Jan";
        ArrayList<User> expResult = new ArrayList<> ();
        expResult.add(test);
        ArrayList<User> result = instance.findByName(name);
        assertEquals(expResult, result);
    }


    @Test
    public void testFindByLastName_0args() throws Exception {
        System.out.println("findByLastName");
        ArrayList<User> expResult = new ArrayList<> ();
        ArrayList<User> result = instance.findByLastName();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByLastName_String() throws Exception {
        System.out.println("findByLastName");
        String lastname = "Kowalski";
        ArrayList<User> expResult = new ArrayList<> ();
        expResult.add(test);
        ArrayList<User> result = instance.findByLastName(lastname);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByAdress_0args() throws Exception {
        System.out.println("findByAdress");
        ArrayList<User> expResult = new ArrayList<> ();
        ArrayList<User> result = instance.findByAdress();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByAdress_String() throws Exception {
        System.out.println("findByAdress");
        String adress = "Miejska 2";
        ArrayList<User> expResult = new ArrayList<> ();
        expResult.add(test);
        ArrayList<User> result = instance.findByAdress(adress);
        assertEquals(expResult, result);
    }


    @Test
    public void testFindByPesel_0args() throws Exception {
        System.out.println("findByPesel");
        User expResult = test;
        User result = instance.findByPesel();
        assertEquals(expResult, result);
    }


    @Test
    public void testFindByPesel_long() throws Exception {
        System.out.println("findByPesel");
        long number = test.getPesel();
        User expResult = test;
        User result = instance.findByPesel(number);
        assertEquals(expResult, result);
    }
    
}
