package com.aiccj.abtest.mapper;

import com.aiccj.abtest.common.PageableEntity;
import com.aiccj.abtest.model.TestGroup;
import com.aiccj.abtest.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author morowin
 * @Date 2022/4/19 21:36
 */
@Mapper
public interface TestGroupMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TestGroup record);

    int insertSelective(TestGroup record);

    TestGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestGroup record);

    int updateByPrimaryKey(TestGroup record);

    long findCount();

    List<TestGroup> finds(Integer pageIndex, Integer limit);
}