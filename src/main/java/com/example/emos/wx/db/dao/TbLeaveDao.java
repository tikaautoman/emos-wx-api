package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbLeave;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbLeaveDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLeave record);

    int insertSelective(TbLeave record);

    TbLeave selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLeave record);

    int updateByPrimaryKey(TbLeave record);
}