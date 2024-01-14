package com.zhaobo.multidstx.mapper.db2;

import com.zhaobo.multidstx.model.User;

/**
* @author gxzq
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-09-14 13:43:51
* @Entity com.zhaobo.multidstx.model.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
