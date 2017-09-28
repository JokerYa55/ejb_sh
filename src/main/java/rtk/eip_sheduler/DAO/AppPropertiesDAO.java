/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import rtk.eip_sheduler.beans.AppProperties;
import rtk.eip_sheduler.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class AppPropertiesDAO implements daoInterface<AppProperties, Long> {

    private final EntityManager em;

    public AppPropertiesDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }

    public AppProperties getItem(String id) {
        TypedQuery<AppProperties> query = em.createNamedQuery("findByProperties", AppProperties.class);
        query.setParameter("pname", id);
        return query.getSingleResult();
    }

}
