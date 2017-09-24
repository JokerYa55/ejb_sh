/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.ejb_sh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import rtk.eip_sheduler.DAO.UserEntityDAO;
import rtk.eip_sheduler.DAO.UsersLogDAO;
import rtk.eip_sheduler.beans.UserEntity;
import rtk.eip_sheduler.beans.UsersLog;

/**
 *
 * @author Vasiliy.Andricov
 */
@Singleton
@Local(shadulerExec.class)
public class shadulerExec {

    private final Logger log = Logger.getLogger(getClass().getName());
    private long i = 0;
    private final String propFileName = "app.properties";

    @PersistenceContext(unitName = "elk_sh_jpa")
    protected EntityManager em;

    @Schedule(minute = "*/1", hour = "*")
    public void runSh() {
        try {
            //i++;
            
            utlEip Eip = new utlEip(new URL(args[0]));

            log.debug("***************************************************************************************");
            log.debug("\tStart => " + (new Date()).toString());

            //log.info("i = " + i);
            //log.info("em = " + em);
            List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class);

            log.debug("\tcount => " + logList.size());
            logList.forEach((t) -> {
                log.debug("\t\tlog record => " + t);
                UserEntity user = (new UserEntityDAO(em)).getItem(t.getUserId(), "UserEntity.findById", UserEntity.class);
                log.debug("\t\t\tuser => " + user);
            });
        } catch (Exception e) {
            log.info("e = " + e.getMessage());
        }
    }

    public String getPropValues() throws IOException {
        InputStream inputStream = null;
        String result = null;
        try {
            Properties prop = new Properties();
            String propFileName = "app.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            String user = prop.getProperty("user");
            String company1 = prop.getProperty("company1");
            String company2 = prop.getProperty("company2");
            String company3 = prop.getProperty("company3");

            result = "Company List = " + company1 + ", " + company2 + ", " + company3;
            System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
