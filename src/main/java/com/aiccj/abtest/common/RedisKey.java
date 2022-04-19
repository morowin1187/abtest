package com.aiccj.abtest.common;

import com.aiccj.abtest.common.enums.BusinessType;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public class RedisKey {

    private static final String SYS_PREFIX = "ROXE_USER_CENTER:";

    private static final String LOGIN_FAIL_KEY = SYS_PREFIX + "LOGIN_FAIL:";


    public static String getLoginFailKey(Long userId) {
        return LOGIN_FAIL_KEY + userId;
    }

    public static String getPhoneCodeKey(BusinessType businessType, String phone) {
        return "SMS:" + businessType + ":" + phone;
    }

    public static String getPhoneCodeSendTimeoutRecordKey(BusinessType businessType, String phone) {
        return "SMS:SEND:TIMEOUT:" + businessType + ":" + phone;
    }

    public static String getEmailCodeSendTimeoutRecordKey(BusinessType businessType, String phone) {
        return "EMAIL:SEND:TIMEOUT:" + businessType + ":" + phone;
    }

    public static String getEmailCodeKey(BusinessType businessType, String email) {
        return "EMAIL:" + businessType + ":" + email;
    }

    public static String getRegisterKey(String phone) {
        return "TOKEN:" + BusinessType.REGISTER + ":" + phone;
    }

    public static String getEmailRegisterKey(String email) {
        return "TOKEN:" + BusinessType.REGISTER + ":" + email;
    }
    public static String getLoginKey(Long userId) {
        return "TOKEN:" + BusinessType.USER_LOGIN + ":" + userId;
    }

    public static String getResetPasswordKeyByUserId(Long userId) {
        return "TOKEN:" + BusinessType.RESET_PASSWORD + ":" + userId;
    }

    public static String getEmailActivateKey(BusinessType businessType, String email) {
        return "EMAIL:ACTIVATE:" + businessType + ":" + email;
    }
}
