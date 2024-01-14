package com.zhaobo.multidstx.mapper.db1;

import com.zhaobo.multidstx.model.Message;

/**
* @author gxzq
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-08-28 11:17:11
* @Entity com.zhaobo.multidstx.model.Message
*/
public interface MessageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

}
