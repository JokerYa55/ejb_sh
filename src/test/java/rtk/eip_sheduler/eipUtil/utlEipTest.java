/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.eipUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rtk.eip_sheduler.beans.UserEntity;

/**
 *
 * @author vasiliy.andricov
 */
public class utlEipTest {
    
    public utlEipTest() {
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

    /**
     * Test of addUser method, of class utlEip.
     */
    @Test
    public void testAddUser() {
        System.out.println("TEST addUser");
        UserEntity user = new UserEntity();
        String username = "SSO_user_0" + Math.random()*1000 ;
        user.setUsername(username);
        utlEip instance = null;
        String expResult = "";
        String result = instance.addUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class utlEip.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        UserEntity user = null;
        utlEip instance = null;
        String expResult = "";
        String result = instance.updateUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePassword method, of class utlEip.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        UserEntity user = null;
        utlEip instance = null;
        String expResult = "";
        String result = instance.changePassword(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
