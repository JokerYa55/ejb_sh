/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.ejb_sh;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import rtk.eip_sheduler.DAO.AppPropertiesDAO;
import rtk.eip_sheduler.DAO.UserEntityDAO;
import rtk.eip_sheduler.DAO.UsersLogDAO;
import rtk.eip_sheduler.XMLUtil.utlXML;
import static rtk.eip_sheduler.XMLUtil.utlXML.stringToXml;
import rtk.eip_sheduler.beans.AppProperties;
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
    private final long i = 0;
    //private final String propFileName = "app.properties";

    @PersistenceContext(unitName = "elk_sh_jpa")
    protected EntityManager em;

    @PostConstruct
    public void postConstruct() {
        log.debug("postConstruct");
    }

    @PreDestroy
    private void preDestriy() {
        log.debug("preDestriy");
    }

    @Schedule(minute = "*/1", hour = "*")
    //@Lock(LockType.READ)
    public void runSh(Timer time) {
        try {

            log.info("\n\n********************************* " + new Date() + " ******************************************************");
            log.info("\tSTART \t\t\t=> " + (new Date()).toString());
            log.info("\tNEXT START \t\t=> " + time.getNextTimeout());

            String url = getAppParams("url", "null");
            log.info("URL = " + url);
            String sendCount = getAppParams("max_send_count", "10");
            log.info("send_count = " + sendCount);
            String maxRecUserLog = getAppParams("max_rec_user_log", "30");
            log.info("max_rec_user_log = " + maxRecUserLog);

            utlEip Eip = new utlEip(new URL(url));
            log.info("\tStart => " + (new Date()).toString());

            Map<String, Object> param = new HashMap<>();
            param.put("flag", false);
            param.put("send_count", new Integer(sendCount));
            param.put("limit", new Integer(maxRecUserLog));
            List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class, param);
            //List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class);

            log.info("\tcount => " + logList.size());

            for (UsersLog item : logList) {
                try {
                    log.info("\t\t************************** LOG RECORD BEGIN *****************************");
                    log.log(Logger.Level.INFO, "item => " + item);
                    UserEntity user = (new UserEntityDAO(em)).getItem(item.getUserId(), "userEntity.findById", UserEntity.class);

                    log.debug("\t\t\tuser => " + user.toString() + " userID => " + item.getUserId().toString());

                    if (user != null) {
                        log.info("\t\t\tuser => " + user);

                        String res = null;
                        Document resXml = null;
                        Element root;
                        String resultCode;
                        String lastCommand;
                        String resultComment;

                        switch (item.getOperType().toUpperCase()) {
                            case "I":
                                res = Eip.addUser(user);
                                if (res != null) {
                                    item.setLast_res(res);
                                    resXml = stringToXml(res);
                                    log.info(resXml);
                                    root = resXml.getDocumentElement();
                                    log.info("resXml = " + utlXML.xmlToString(resXml));
                                    resultCode = root.getAttribute("resultCode");
                                    lastCommand = root.getAttribute("lastCommand");
                                    item.setLast_command(lastCommand);
                                    resultComment = root.getAttribute("resultComment");
                                    log.info("resultCode = " + resultCode);
                                    switch (resultCode) {
                                        case "0":
                                            item.setFlag(true);
                                            item.setSend_count(item.getSend_count() + 1);
                                            break;
                                        case "-1":
                                            log.log(Logger.Level.WARN, resultComment);
                                            break;
                                        default:
                                            item.setFlag(false);
                                            item.setSend_count(item.getSend_count() + 1);
                                            break;
                                    }
                                } else {
                                    log.error("ADD_USER RES => NULL");
                                    item.setFlag(true);
                                    item.setLast_res("ADD_USER RES => NULL");
                                }

                                break;
                            case "U":
                                // Если поменялся пароль
//                            Pattern p = Pattern.compile("^(<\\w+>)*$");
//                            Matcher m = p.matcher(item.getInfo());

                                res = Eip.updateUser(user);
                                if (res != null) {
                                    item.setLast_res(res);
                                    resXml = stringToXml(res);
                                    log.info(resXml);
                                    root = resXml.getDocumentElement();
                                    log.info("resXml = " + utlXML.xmlToString(resXml));
                                    resultCode = root.getAttribute("resultCode");
                                    lastCommand = root.getAttribute("lastCommand");
                                    resultComment = root.getAttribute("resultComment");
                                    item.setLast_command(lastCommand);
                                    log.info("resultCode = " + resultCode);
                                    switch (resultCode) {
                                        case "0":
                                            item.setFlag(true);
                                            break;
                                        case "-1":
                                            log.log(Logger.Level.WARN, resultComment);
                                            break;
                                        default:
                                            item.setFlag(false);
                                            break;
                                    }
                                    item.setSend_count(item.getSend_count() + 1);
                                    // Обновляем если пароль
                                    if (item.getInfo().contains("<password>")) {
                                        res = Eip.changePassword(user);
                                        item.setLast_res(res);
                                        resXml = stringToXml(res);
                                        log.info(resXml);
                                        root = resXml.getDocumentElement();
                                        log.info("resXml = " + utlXML.xmlToString(resXml));
                                        resultCode = root.getAttribute("resultCode");
                                        lastCommand = root.getAttribute("lastCommand");
                                        item.setLast_command(lastCommand);
                                        log.info("resultCode = " + resultCode);
                                        if (resultCode.equals("0")) {
                                            item.setFlag(true);
                                        } else {
                                            item.setFlag(false);
                                        }
                                        item.setSend_count(item.getSend_count() + 1);
                                    }
                                } else {
                                    log.error("UPD_USER RES => NULL");
                                    item.setFlag(true);
                                    item.setLast_res("ADD_USER RES => NULL");
                                }
                                break;
                            case "D":
                                break;
                            default: ;
                        }
                    } else {
                        item.setFlag(true);
                        item.setInfo("<NO User id = " + item.getUserId() + " name = " + item.getUsername() + ">");
                    }
                    log.info("\t\t************************** LOG RECORD END *****************************");
                } catch (Exception ex1) {
                    log.error(ex1.getMessage());
                }

                log.info("item => " + item);
                //em.merge(item);
                (new UsersLogDAO(em)).updateItem(item);
            }
        } catch (Exception e) {
            log.info("e = " + e.getMessage());
        }
    }

    /**
     *
     * @param pName
     * @param pDefVal
     * @return
     */
    private String getAppParams(String pName, String pDefVal) {
        String res = null;
        try {
            AppPropertiesDAO appDAO = new AppPropertiesDAO(em);
            AppProperties prop = null;
            try {
                prop = appDAO.getItem(pName);
                res = prop.getParam_value();
            } catch (NoResultException e) {
                prop = new AppProperties();
                prop.setParam_name(pName);
                prop.setParam_value(pDefVal);
                appDAO.addItem(prop);
                res = prop.getParam_value();
            }
        } catch (Exception e1) {
            log.log(Logger.Level.ERROR, e1);
        }
        return res;
    }

//    public String getPropValues() throws IOException {
//        InputStream inputStream = null;
//        String result = null;
//        try {
//            Properties prop = new Properties();
//            String propFileName = "app.properties";
//
//            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//
//            if (inputStream != null) {
//                prop.load(inputStream);
//            } else {
//                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//            }
//
//            Date time = new Date(System.currentTimeMillis());
//
//            // get the property value and print it out
//            String user = prop.getProperty("user");
//            String company1 = prop.getProperty("company1");
//            String company2 = prop.getProperty("company2");
//            String company3 = prop.getProperty("company3");
//
//            result = "Company List = " + company1 + ", " + company2 + ", " + company3;
//            System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
//        } catch (Exception e) {
//            System.out.println("Exception: " + e);
//        } finally {
//            inputStream.close();
//        }
//        return result;
//    }
}
