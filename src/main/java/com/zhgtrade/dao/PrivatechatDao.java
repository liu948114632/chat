package com.zhgtrade.dao;

import com.zhgtrade.model.Privatechat;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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
public class PrivatechatDao  {
    private static final Logger log = LoggerFactory
            .getLogger(PrivatechatDao.class);
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    public void save(Privatechat instance) {
        log.debug("saving Privatechat instance");
        try {
            getSession().save(instance);
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Privatechat persistentInstance) {
        log.debug("deleting Privatechat instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void update(Privatechat instance) {
        sessionFactory.getCurrentSession().update(instance);
    }

    public Privatechat findById(int id) {
        log.debug("getting Privatechat instance with id: " + id);
        try {
            String hql = "from Privatechat f where f.id = ?";
            Privatechat instance = getSession().get(Privatechat.class,id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            System.out.println("挂单ID：" + id);
            throw re;
        }
    }

    public List<Privatechat> findByProperty(String propertyName, Object value) {
        log.debug("finding Privatechat instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Privatechat as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public int findByParamCount(String filter){
        try {
            Query queryObject = getSession().createSQLQuery(filter);
            List list = queryObject.list();
            Object object = list.get(0);
            if (object == null) return 0;
            return Integer.parseInt(object.toString());
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Privatechat getChatByParam(Privatechat instance){
        try {
            List<Privatechat> results = (List<Privatechat>) getSession()
                    .createCriteria("com.zhgtrade.model.Privatechat").add(create(instance))
                    .list();
            if(CollectionUtils.isEmpty(results)){
                return null;
            }
            return results.get(0);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List<Map> findByParam(int firstResult, int maxResults, String filter, boolean isFY) {
        try {
            Query queryObject = getSession().createSQLQuery(filter);
            if (isFY) {
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            return queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        } catch (RuntimeException re) {
            throw re;
        }
    }
}
