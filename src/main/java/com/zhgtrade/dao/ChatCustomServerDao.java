package com.zhgtrade.dao;

import com.zhgtrade.model.ChatCustomServer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.hibernate.criterion.Example.create;

/**
 * @author xxp
 * @version 2017- 12- 04 9:54
 * @description
 * @copyright www.zhgtrade.com
 */
@Repository
@Transactional
public class ChatCustomServerDao {
    private static final Logger log = LoggerFactory
            .getLogger(ChatCustomServerDao.class);

    @Resource
    protected SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    public void save(ChatCustomServer instance) {
        getSession().save(instance);
    }

    public void delete(ChatCustomServer persistentInstance) {
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void update(ChatCustomServer instance) {

        sessionFactory.getCurrentSession().update(instance);
    }

    public void attachDirty(ChatCustomServer instance) {
        log.debug("attaching dirty Fuser instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public ChatCustomServer findById(int id) {
        log.debug("getting Privatechat instance with id: " + id);
        try {
            ChatCustomServer instance = getSession().get(ChatCustomServer.class,id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            System.out.println("挂单ID：" + id);
            throw re;
        }
    }

    public List<ChatCustomServer> findByProperty(String propertyName, Object value) {
        log.debug("finding Privatechat instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from ChatCustomServer as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }


    public ChatCustomServer getChatCustomServerByParam(ChatCustomServer instance){
        try {
            List<ChatCustomServer> results = (List<ChatCustomServer>) getSession()
                    .createCriteria("com.zhgtrade.model.ChatCustomServer").add(create(instance))
                    .list();
            if(CollectionUtils.isEmpty(results)){
                return null;
            }
            return results.get(0);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List<Map> findByParam(String filter) {
        try {
            Query queryObject = getSession().createSQLQuery(filter);
            return queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        } catch (RuntimeException re) {
            throw re;
        }
    }

}
