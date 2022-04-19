package com.aiccj.abtest.service;


import com.aiccj.abtest.common.RedisKey;
import com.aiccj.abtest.common.token.LoginType;
import com.aiccj.abtest.common.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Service
@Slf4j
public class LoginTokenDataService {

    /**
     * 登录过期时间为 30 天
     */
    private static final int LOGIN_EXPIRE_MINUTES = 43200;

    @Resource
    private CacheService cacheService;


    public String generateLoginToken(Long userId, String name, String role, String loginTypeStr ) {

        LoginType loginType = LoginType.valueOf(loginTypeStr);
        String loginToken = TokenManager.createToken(userId,name, role, loginType);

        String loginRedisKey = RedisKey.getLoginKey(userId);
        try {
            if (LoginType.SINGLE == loginType) {
                cacheService.delete(loginRedisKey);
            }
//            cacheService.set(loginRedisKey, loginToken, LOGIN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            cacheService.set(loginToken, loginRedisKey, LOGIN_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginToken;
    }


    public TokenManager.ValidateResult verifyUserToken(String token) {

        TokenManager.TokenDetail tokenDetail = TokenManager.getTokenDetail(token);

        String loginKey = RedisKey.getLoginKey(tokenDetail.getId());

        String cacheToken = cacheService.get(token);


        if (cacheToken == null || cacheToken.trim().equals("")){
            return new TokenManager.ValidateResult(TokenManager.CheckTokenResult.EXPIRED, tokenDetail);
        }
        // 重新设置超时时间
        cacheService.set(token, loginKey, LOGIN_EXPIRE_MINUTES, TimeUnit.MINUTES);
        return new TokenManager.ValidateResult(TokenManager.CheckTokenResult.OK, tokenDetail);
    }

    public void logout(String token) {
        try {
            cacheService.delete(token);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
