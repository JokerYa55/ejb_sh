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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import rtk.eip_sheduler.DAO.UserEntityDAO;
import rtk.eip_sheduler.DAO.UsersLogDAO;
import rtk.eip_sheduler.XMLUtil.utlXML;
import static rtk.eip_sheduler.XMLUtil.utlXML.stringToXml;
import rtk.eip_sheduler.beans.UserEntity;
import rtk.eip_sheduler.beans.UsersLog;
import rtk.eip_sheduler.eipUtil.utlEip;

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

    @Schedule(minute = "*/2", hour = "*")
    public void runSh() {
        try {
            //i++;

            utlEip Eip = new utlEip(new URL("http://192.168.1.150:8080/elkAdminRest/elkadm/addUser1"));

            log.debug("***************************************************************************************");
            log.debug("\tStart => " + (new Date()).toString());

            Map<String, Object> param = new HashMap<>();
            param.put("flag", false);
            param.put("send_count", 10);
            List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class, param);
            //List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class);

            log.debug("\tcount => " + logList.size());

            for (UsersLog item : logList) {
                try {
                    log.debug("\t\tlog record => " + item);
                    UserEntity user = (new UserEntityDAO(em)).getItem(item.getUserId(), "userEntity.findById", UserEntity.class);
                    log.debug("\t\t\tuser => " + user);

                    String res = null;
                    Document resXml = null;
                    Element root;
                    String resultCode;

                    switch (item.getOperType().toUpperCase()) {
                        case "I":
                            res = Eip.addUser(user);
                            item.setLast_command(res);
                            resXml = stringToXml(res);
                            log.debug(resXml);
                            root = resXml.getDocumentElement();
                            log.debug("resXml = " + utlXML.xmlToString(resXml));
                            resultCode = root.getAttribute("resultCode");
                            log.debug("resultCode = " + resultCode);
                            if (resultCode.equals("0")) {
                                item.setFlag(true);
                            } else {
                                item.setFlag(false);
                                item.setSend_count(item.getSend_count() + 1);
                            }
                            break;
                        case "U":
                            // Если поменялся пароль
//                            Pattern p = Pattern.compile("^(<\\w+>)*$");
//                            Matcher m = p.matcher(item.getInfo());

                            res = Eip.updateUser(user);
                            item.setLast_res(res);
                            resXml = stringToXml(res);
                            log.debug(resXml);
                            root = resXml.getDocumentElement();
                            log.debug("resXml = " + utlXML.xmlToString(resXml));
                            resultCode = root.getAttribute("resultCode");
                            String lastCommand = root.getAttribute("lastCommand");                            
                            item.setLast_command(res);
                            log.debug("resultCode = " + resultCode);
                            if (resultCode.equals("0")) {
                                item.setFlag(true);
                            } else {
                                item.setFlag(false);
                                item.setSend_count(item.getSend_count() + 1);
                            }

                            // Обновляем если пароль
                            if (item.getInfo().contains("<password>")) {
                                res = Eip.changePassword(user);
                                item.setLast_command(res);
                                resXml = stringToXml(res);
                                log.debug(resXml);
                                root = resXml.getDocumentElement();
                                log.debug("resXml = " + utlXML.xmlToString(resXml));
                                resultCode = root.getAttribute("resultCode");
                                log.debug("resultCode = " + resultCode);
                                if (resultCode.equals("0")) {
                                    item.setFlag(true);
                                } else {
                                    item.setFlag(false);
                                    item.setSend_count(item.getSend_count() + 1);
                                }
                            }
                            break;
                        case "D":
                            break;
                        default: ;
                    }
                } catch (Exception ex1) {
                    log.error(ex1.getMessage());
                }
                
                log.debug("item => " + item);                
                //em.merge(item);
                (new UsersLogDAO(em)).updateItem(item);
            }
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
