package com.zhgtrade.dao;

import com.zhgtrade.model.PrivatechatMsg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author xxp
 * @version 2017- 12- 04 9:54
 * @description
 * @copyright www.zhgtrade.com
 */
@Repository
@Transactional
public class PrivatechatMsgDao {
    private static final Logger log = LoggerFactory
            .getLogger(PrivatechatMsgDao.class);
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    public void save(PrivatechatMsg transientInstance) {
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PrivatechatMsg persistentInstance) {
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void update(PrivatechatMsg persistentInstance) {
        try {
            getSession().update("PrivatechatMsg",persistentInstance);
            log.debug("update successful");
        } catch (RuntimeException re) {
            log.error("update failed", re);
            throw re;
        }
    }

    public PrivatechatMsg findById(int id) {
        return getSession().get(PrivatechatMsg.class, id);
    }

    public List<PrivatechatMsg> findByProperty(String propertyName, Object value) {
        log.debug("finding OtcFentrust instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from PrivatechatMsg as model where model."
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
            return ((BigInteger)list.get(0)).intValue();
        } catch (RuntimeException re) {
            log.error("find OtcFentrustlog by filter name failed", re);
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
            log.error("find OtcFentrustlog by filter name failed", re);
            throw re;
        }
    }

    public void deleteOldChat(int time){
        String sql=" delete from privatechat_msg where datediff(curdate(), create_time)>= "+time;
        Query query=getSession().createSQLQuery(sql);
        query.executeUpdate();
    }
}
