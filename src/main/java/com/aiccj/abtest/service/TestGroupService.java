package com.aiccj.abtest.service;

import com.aiccj.abtest.common.PageableEntity;
import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.aiccj.abtest.common.token.TokenManager;
import com.aiccj.abtest.mapper.TestGroupMapper;
import com.aiccj.abtest.mapper.UserMapper;
import com.aiccj.abtest.model.TestGroup;
import com.aiccj.abtest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Slf4j
@Service
public class TestGroupService {


    @Autowired
    private TestGroupMapper testGroupMapper;


    public PageableEntity<TestGroup> list(Integer pageIndex, Integer limit) {

        long count = testGroupMapper.findCount();
        if (count<=0) {
            return PageableEntity.pageBuilder(0l, new ArrayList<>());
        }
        List<TestGroup> finds = testGroupMapper.finds(pageIndex, limit);

        return PageableEntity.pageBuilder(count, finds);
    }

    @Transactional
    public TestGroup create(TestGroup testGroup, String curUserName) {
        testGroup.createCheck();
        testGroup.createForUser(curUserName);
        testGroupMapper.insert(testGroup);
        return testGroup;
    }

    @Transactional
    public TestGroup edit(TestGroup testGroup, String name) {
        testGroup.editCheck();
        testGroup.editForUser(name);
        int i = testGroupMapper.updateByPrimaryKey(testGroup);
        if (i<1) {
            throw new BusinessException(BusinessMsg.MCLC0005);
        }
        return testGroup;
    }
}
