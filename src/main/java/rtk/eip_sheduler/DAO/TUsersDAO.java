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
import rtk.eip_sheduler.beans.TUsers;
import rtk.eip_sheduler.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class TUsersDAO implements daoInterface<TUsers, Long> {

    private EntityManager em;
    private Logger log = Logger.getLogger(getClass().getName());

    public TUsersDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }

    @Override
    public TUsers getItem(Long id) {
        TUsers res = null;
        try {
            TypedQuery<TUsers> namedQuery = em.createNamedQuery("TUsers.findById", TUsers.class);
            namedQuery.setParameter("id", id);
            res = namedQuery.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public List<TUsers> getList() {
        List<TUsers> res = null;
        try {
            TypedQuery<TUsers> namedQuery = em.createNamedQuery("TUsers.findAll", TUsers.class);
            res = namedQuery.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @Override
    public List<TUsers> getList(Long startIdx, Long stopIdx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
