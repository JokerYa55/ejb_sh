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
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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

    private String flag;
    private String dateFlag;
    private boolean runFlag = false;
    private String masterID = UUID.randomUUID().toString();
    private String tempMasterID;

    @Schedule(minute = "*/1", hour = "*")
    //@Lock(LockType.READ)
    public void runSh(Timer time) {
        try {

            log.info("\n\n********************************* " + new Date() + " ******************************************************");
            log.info("START \t\t\t=> " + (new Date()).toString());
            log.info("NEXT START \t\t=> " + time.getNextTimeout());
            log.info("runFlag => " + runFlag);

            // Инициализация параметров синхронизации в кластере
            getAppParams("master", "false");
            getAppParams("master_id", "0");
            getAppParams("master_set_time", "0");

            isMaster();

            if (runFlag) {
                String url = getAppParams("url", "null");
                log.info("URL = " + url);
                String sendCount = getAppParams("max_send_count", "10");
                log.info("send_count = " + sendCount);
                String maxRecUserLog = getAppParams("max_rec_user_log", "30");
                log.info("max_rec_user_log = " + maxRecUserLog);

                utlEip Eip = new utlEip(new URL(url));
                log.info("Start => " + (new Date()).toString());

                Map<String, Object> param = new HashMap<>();
                param.put("flag", false);
                param.put("send_count", new Integer(sendCount));
                param.put("limit", new Integer(maxRecUserLog));
                List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findByFlag", UsersLog.class, param);

                log.info("\tlog record count => " + logList.size());
                for (UsersLog item : logList) {
                    try {
                        log.info("************************** LOG RECORD BEGIN *****************************");
                        log.log(Logger.Level.INFO, "log item => " + item);
                        UserEntity user = null;
                        try {
                            log.info("\tGET USER REC => ");
                            user = (new UserEntityDAO(em)).getItem(item.getUserId(), "userEntity.findById", UserEntity.class);
                        } catch (Exception e11) {
                            log.log(Logger.Level.ERROR, "getuser error => ");
                            log.log(Logger.Level.ERROR, e11);
                        }

                        log.debug("\tuser => " + user.toString());

                        if (user != null) {
                            //log.info("tuser => " + user);

                            String res = null;
                            Document resXml = null;
                            Element root;
                            String resultCode = null;
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
                                            case "36":
                                                item.setFlag(true);
                                                item.setSend_count(1000);
                                                break;
                                            default:
                                                item.setFlag(false);
                                                item.setSend_count(item.getSend_count() + 1);
                                                break;
                                        }
                                    } else {
                                        log.error("add_user() res => NULL");
                                        item.setFlag(true);
                                        item.setSend_count(item.getSend_count() + 1);
                                        item.setLast_res("add_user() res => NULL");
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
                                        log.info(masterID + "\tresXml => " + resXml);
                                        root = resXml.getDocumentElement();
                                        log.info(masterID + "\tresXml = " + utlXML.xmlToString(resXml));
                                        resultCode = root.getAttribute("resultCode");
                                        lastCommand = root.getAttribute("lastCommand");
                                        resultComment = root.getAttribute("resultComment");
                                        item.setLast_command(lastCommand);
                                        log.info(masterID + "\tresultCode = " + resultCode);
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
                                            log.info(masterID + "\t" + resXml);
                                            root = resXml.getDocumentElement();
                                            log.info(masterID + "\tresXml = " + utlXML.xmlToString(resXml));
                                            resultCode = root.getAttribute("resultCode");
                                            lastCommand = root.getAttribute("lastCommand");
                                            item.setLast_command(lastCommand);
                                            log.info(masterID + "\tresultCode = " + resultCode);
                                            if (resultCode.equals("0")) {
                                                item.setFlag(true);
                                            } else {
                                                item.setFlag(false);
                                            }
                                            item.setSend_count(item.getSend_count() + 1);
                                        }
                                    } else {
                                        log.error("upd_user() res => NULL");
                                        item.setFlag(false);
                                        item.setSend_count(item.getSend_count() + 1);
                                        item.setLast_res("upd_user() res => NULL");
                                    }
                                    break;
                                case "D":
                                    break;
                                default: ;
                            }
                        } else {
                            item.setFlag(true);
                            item.setInfo("<NO User id = " + item.getUserId() + " name = " + item.getUsername() + ">");
                            item.setLast_res("USER => NULL");
                        }
                        log.info("************************** LOG RECORD END *****************************");
                    } catch (NullPointerException ex1) {
                        log.log(Logger.Level.ERROR, ex1);
                        log.error("USER => NULL");
                        item.setFlag(true);
                        //item.setSend_count(item.getSend_count() + 1);
                        item.setInfo("<NO User id = " + item.getUserId() + " name = " + item.getUsername() + " in table>");
                        item.setLast_res("USER => NULL");
                    } catch (Exception ex23) {
                        log.log(Logger.Level.ERROR, ex23);
                    }

                    log.info("\n\n");
                    log.info("item => " + item);
                    //em.merge(item);
                    (new UsersLogDAO(em)).updateItem(item);
                }
            } else {
                log.info(masterID + "\tTimer run other server.");
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

    @Transactional
    private boolean isMaster() {
        log.info("isMaster => " + masterID);
        boolean res = false;
        try {
            // Блокируем параметры 
            TypedQuery<AppProperties> q = em.createQuery("SELECT t FROM AppProperties t\n"
                    + " WHERE t.param_name LIKE 'master%'", AppProperties.class);
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            List<AppProperties> pList = q.getResultList();
            pList.forEach(new Consumer<AppProperties>() {
                @Override
                public void accept(AppProperties t) {
                    switch (t.getParam_name()) {
                        case "master":
                            flag = t.getParam_value();
                            break;
                        case "master_set_time":
                            dateFlag = t.getParam_value();
                            break;
                        case "master_id":
                            tempMasterID = t.getParam_value();
                            break;
                    }
                }
            });

            //log.info("flag => " + this.flag);
            //log.info("dateFlag => " + dateFlag);
            log.info("masterID => " + masterID);
            log.info(masterID + "\ttempMasterID => " + tempMasterID);
            long tempDate = Long.parseLong(dateFlag);
            long currentDate = new Date().getTime();
            log.info(masterID + "\ttempDate => " + tempDate);
            log.info(masterID + "\tcurrentDate => " + currentDate);

            // Проверяем является ли данный сервер активным
            if (tempMasterID.equalsIgnoreCase(masterID)) {
                log.info(masterID + "\tCURRENT TIMER");
                pList.forEach(new Consumer<AppProperties>() {
                    @Override
                    public void accept(AppProperties t) {
                        switch (t.getParam_name()) {
                            case "master_id":
                                t.setParam_value(masterID);
                                break;
                            case "master_set_time":
                                t.setParam_value(String.valueOf(currentDate));
                                break;
                        }
                    }
                });
            } else if (flag.equalsIgnoreCase("true")) {
                // Если сервер не активный проверяем время последнего обновления
                if ((currentDate - tempDate) > 120000) {
                    // Если время последнего обновления больше 2 минут 
                    // Делаем таймер мастером
                    log.info(masterID + "\tsetMaster => TRUE");
                    pList.forEach(new Consumer<AppProperties>() {
                        @Override
                        public void accept(AppProperties t) {
                            switch (t.getParam_name()) {
                                case "master":
                                    t.setParam_value("TRUE");
                                    break;
                                case "master_set_time":
                                    t.setParam_value(String.valueOf(currentDate));
                                    break;
                                case "master_id":
                                    t.setParam_value(masterID);
                                    break;
                            }
                        }
                    });
                    runFlag = true;
                    res = true;
                } else {
                    runFlag = false;
                    res = false;
                }

            } else {
                // Тимер не запущен не на одном сервере
                // Устанавливаем master = TRUE
                // master_set_time = текущая дата
                // Делаем таймер мастером
                log.info(masterID + "\tsetMaster => TRUE");
                pList.forEach(new Consumer<AppProperties>() {
                    @Override
                    public void accept(AppProperties t) {
                        switch (t.getParam_name()) {
                            case "master":
                                t.setParam_value("TRUE");
                                break;
                            case "master_set_time":
                                t.setParam_value(String.valueOf(currentDate));
                                break;
                            case "master_id":
                                t.setParam_value(masterID);
                                break;
                        }
                    }
                });
                runFlag = true;
                res = true;
            }
        } catch (Exception e) {
            log.log(Logger.Level.ERROR, e);
        }
        return res;
    }
}
