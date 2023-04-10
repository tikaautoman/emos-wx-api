package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserDao {
    public boolean havaRootUser();

    public Integer searchIdByOpenId(String openId);
    int deleteByPrimaryKey(Integer id);

    int insert(HashMap param);

    public Set<String> searchUserPermissions(int userId);

    public TbUser searchById(int userId);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}