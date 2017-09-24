/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.ejb_sh;

import java.util.List;
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

    @PersistenceContext(unitName = "elk_sh_jpa")
    protected EntityManager em;
    
    @Schedule(minute = "*/1", hour = "*")
    public void runSh() {
        try {
            i++;
            log.info("i = " + i); 
            log.info("em = " + em);
            List<UsersLog> logList = (new UsersLogDAO(em)).getList("UsersLog.findAll", UsersLog.class);
            
            log.debug("count => " + logList.size());
            
            logList.forEach((t) -> {
                log.debug("log record => " + t);
                UserEntity user = (new UserEntityDAO(em)).getItem(t.getUserId(), "UserEntity.findById", UserEntity.class);
                log.debug("user => " + user);
            });
        } catch (Exception e) {
            log.info("e = " + e.getMessage());
        }
    }
}
