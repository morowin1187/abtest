package com.aiccj.abtest.mapper;

import com.aiccj.abtest.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Mapper
public interface UserMapper {

	User loadByEmail(@Param("usr") User user);

    User loadById(@Param("id")Long id);

    long findUserCount();

    List<User> findUserList(int pageIndex, int limit);

    int insert(User user);

    int update(User userReq);
}


