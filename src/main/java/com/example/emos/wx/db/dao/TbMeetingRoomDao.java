package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbMeetingRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbMeetingRoomDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbMeetingRoom record);

    int insertSelective(TbMeetingRoom record);

    TbMeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbMeetingRoom record);

    int updateByPrimaryKey(TbMeetingRoom record);
}