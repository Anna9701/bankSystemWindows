/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;


public class AccountTest {
    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    public AccountTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}


    @Test
    public void testGetResources() {
        System.out.println("getResources");
        double expResult = 500;
        Account instance = new Account(expResult);
        double result = instance.getResources();
        assertEquals(expResult, result, 0.5);
    }

    @Test
    public void testPayment() {
        System.out.println("payment");
        double money = 300;
        Account instance = new Account(0);
        instance.payment(money);
        double result = instance.getResources();
        assertEquals(money, result, 0.5);
    }


    @Test
    public void testPayout() throws Exception {
        System.out.println("payout");
        double money = 500;
        Account instance = new Account(money);
        instance.payout(money);
        double result = instance.getResources();
        assertEquals(0, result, 0.5);
    }
    
}
