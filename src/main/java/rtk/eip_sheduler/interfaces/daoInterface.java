/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.interfaces;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author vasil
 */
public interface daoInterface<T, V> {

    /**
     *
     * @return
     */
    public EntityManager getEM();

    /**
     *
     * @param id
     * @return
     */
    public T getItem(V id);

    /**
     *
     * @return
     */
    public List<T> getList();

    /**
     *
     * @param startIdx
     * @param stopIdx
     * @return
     */
    public List<T> getList(V startIdx, V stopIdx);

    /**
     *
     * @param Item
     * @return
     */
    default public T addItem(T Item) {
        T res = null;
        try {
            EntityManager em = getEM();
            em.getTransaction().begin();
            em.merge(Item);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param Item
     * @return
     */
    default public boolean deleteItem(T Item) {
        boolean res = true;
        try {
            EntityManager em = getEM();
            em.getTransaction().begin();
            em.detach(Item);
            em.getTransaction().commit();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param Item
     * @return
     */
    default public boolean updateItem(T Item) {
        boolean res = true;
        try {
            EntityManager em = getEM();
            em.getTransaction().begin();
            em.merge(Item);
            em.getTransaction().commit();
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    default public List<T> test(long id, Class<T> cl)
    {
        T res = null;
        try {
            EntityManager em = getEM();
            TypedQuery<T> namedQuery = em.createNamedQuery("TUsers.findById", cl);
            namedQuery.setParameter("id", id);
            res = namedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
