package com.aiccj.abtest.service;

import com.aiccj.abtest.common.PageableEntity;
import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.aiccj.abtest.common.token.TokenManager;
import com.aiccj.abtest.mapper.UserMapper;
import com.aiccj.abtest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Slf4j
@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    @Resource
    private LoginTokenDataService loginTokenDataService;

    public String login(User user) {
        user.checkLoginParam();
        User dbResp = userMapper.loadByEmail(user);
        user.validatePassword(dbResp);
        return loginTokenDataService.generateLoginToken(dbResp.getId(), dbResp.getName(), dbResp.getRole(), dbResp.getLoginType());
    }

    public TokenManager.TokenDetail validateToken(String token) {
        TokenManager.ValidateResult validateResult = loginTokenDataService.verifyUserToken(token);
        validateResult.getCheckTokenResult().validateResult();
        return validateResult.getTokenDetail();
    }


    public void logout(String token) {
        loginTokenDataService.logout(token);
    }

    public User loadById(Long id) {
        User user = userMapper.loadById(id);
        return user;
    }

    public PageableEntity<User> list(int pageIndex, int limit) {
        long total = userMapper.findUserCount();
        if (total <= 0) {
            return PageableEntity.pageBuilder(0L, new ArrayList<>());
        }
        List<User> userList = userMapper.findUserList(pageIndex, limit);
        return PageableEntity.pageBuilder(total, userList);
    }

    @Transactional(rollbackFor = Exception.class)
    public User createUser(User userReq) {
        userReq.checkCreate();
        //可以根据role选择create方法
        userReq.createForAdmin();
        int row = userMapper.insert(userReq);
        if (row <= 0)
            throw new BusinessException(BusinessMsg.SYS_ERROR);
        return userReq;
    }

    public User editUser(User userReq) {
        userReq.checkCreate();
        //可以根据role选择edit方法
        userReq.editForAdmin();
        int row = userMapper.update(userReq);
        if (row <= 0)
            throw new BusinessException(BusinessMsg.SYS_ERROR);
        return userReq;
    }
}
