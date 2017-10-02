/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.eipUtil;

import static java.lang.Math.random;
import java.net.MalformedURLException;
import java.net.URL;
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

    private String username;
    private utlEip instance = null;
    
    public utlEipTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MalformedURLException {
        Double user_num = random() * 1000;
        username = "sso_user_0" + user_num.intValue();
        instance = new utlEip(new URL("http://10.31.70.120/elkProxy"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class utlEip.
     *
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testAddUser() {
        System.out.println("TEST addUser");
        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setFirstName("Иван");
        user.setLastName("Иванов");
        user.setThirdName("Петрович");
        user.setEmail(username + "@mail.ru");
        user.setFirstName("Иван");
        user.setHash("9FA6E2D3CF6621B2384633042868D42C810B78FE");
        user.setHash_type("SHA1");
        user.setSalt("8356239303");
        user.setPhone("9274587879");
        user.setUser_region(23);

        

        String expResult = "resultCode=\"0\"";
        String result = instance.addUser(user);
        if (result.contains(expResult)) {
            result = expResult;
        }
        //System.out.println("result = " + result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class utlEip.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setFirstName("Иван_1");
        user.setLastName("Иванов_1");
        user.setThirdName("Петрович_1");
        user.setEmail(username + "@mail.ru");
        user.setFirstName("Иван");
        user.setHash("9FA6E2D3CF6621B2384633042868D42C810B78FE");
        user.setHash_type("SHA1");
        user.setSalt("8356239303");
        user.setPhone("9274587879");
        user.setUser_region(23);
        
        //utlEip instance = null;
        String expResult = "";
        String result = this.instance.updateUser(user);
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
