/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javafx.stage.Stage;
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
public class enterUtilTest {
    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    public enterUtilTest() {
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
    public void testEnterUserNumber() {
        System.out.println("enterUserNumber");
        String text = "test int";
        Stage stg = new Stage();
        enterUtil instance = new enterUtil();
        int expResult = 2;
        int result = instance.enterUserNumber(text, stg);
        assertEquals(expResult, result);
    }


    @Test
    public void testEnterUserLong() {
        System.out.println("enterUserLong");
        String text = "test long";
        Stage stg = new Stage();
        enterUtil instance = new enterUtil();
        long expResult = 11111111111L;
        long result = instance.enterUserLong(text, stg);
        assertEquals(expResult, result);
    }


    @Test
    public void testEnterUserText() {
        System.out.println("enterUserText");
        String text = "text";
        String text2 = "test Text";
        Stage stg = new Stage();
        enterUtil instance = new enterUtil();
        String expResult = "test";
        String result = instance.enterUserText(text, text2, stg);
        assertEquals(expResult, result);
    }
    
}
