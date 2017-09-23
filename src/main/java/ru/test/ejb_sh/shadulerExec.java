/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.ejb_sh;

import javax.ejb.Local;
import org.jboss.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author Vasiliy.Andricov
 */
@Singleton
@Local(shadulerExec.class)
public class shadulerExec {

    private final Logger log = Logger.getLogger(getClass().getName());
    private long i = 0;

    @Schedule(minute = "*/1", hour = "*")
    public void runSh() {
        try {
            i++;
            log.info("i = " + i);           
        } catch (Exception e) {
            log.info("e = " + e.getMessage());
        }
    }
}
