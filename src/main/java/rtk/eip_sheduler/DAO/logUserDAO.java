/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.DAO;

import javax.persistence.EntityManager;
import rtk.eip_sheduler.beans.TUsersLog;
import rtk.eip_sheduler.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class logUserDAO implements daoInterface<TUsersLog, Long> {

    private EntityManager em;

    public logUserDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }
    
    

}
