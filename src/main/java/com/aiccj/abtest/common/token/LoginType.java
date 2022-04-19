package com.aiccj.abtest.common.token;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public enum LoginType {

    /**
     * 单点登录
     */
    SINGLE,
    /**
     * 多点登录
     */
    MULTIPLE,
    ;

    public boolean is(String loginType) {
        return SINGLE.name().equals(loginType);
    }
}
