/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.DAO;

import javax.persistence.EntityManager;
import rtk.eip_sheduler.beans.UsersLog;
import rtk.eip_sheduler.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class UsersLogDAO implements daoInterface<UsersLog, Long> {

    private EntityManager em;

    public UsersLogDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }
    
    

}
