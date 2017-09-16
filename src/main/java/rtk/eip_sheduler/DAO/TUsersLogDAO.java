/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import rtk.eip_sheduler.beans.TUsersLog;
import rtk.eip_sheduler.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class TUsersLogDAO implements daoInterface<TUsersLog, Long> {

    private EntityManager em;
    private Logger log = Logger.getLogger(getClass().getName());

    public TUsersLogDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }

    @Override
    public TUsersLog getItem(Long id) {
        TUsersLog res = null;
        try {
             TypedQuery<TUsersLog> namedQuery = em.createNamedQuery("TUsersLog.findById", TUsersLog.class);
             namedQuery.setParameter("id", id);
             res = namedQuery.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public List<TUsersLog> getList() {
        log.debug("getList");
        List<TUsersLog> res = null;
        try {
             TypedQuery<TUsersLog> namedQuery = em.createNamedQuery("TUsersLog.findAll", TUsersLog.class);
             namedQuery.setParameter("send_count", 10);
             res = namedQuery.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public List<TUsersLog> getList(Long startIdx, Long stopIdx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
