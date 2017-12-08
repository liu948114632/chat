package com.zhgtrade.dao;

import com.zhgtrade.model.Fuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/12/8
 */
@Repository
public class FuserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Fuser findById(int id){
        Fuser fuser = new Fuser();
        Map<String,Object> map = jdbcTemplate.queryForMap("SELECT rank,fNickName,fid,fEmail,fTelephone FROM fuser where fid = "+id);
        fuser.setRank((Boolean) map.get("rank"));
        fuser.setFnickName((String) map.get("fNickName"));
        fuser.setFemail((String) map.get("fEmail"));
        fuser.setFtelephone((String) map.get("fTelephone"));
        fuser.setFid((int)map.get("fid"));
        return fuser;
    }
}
