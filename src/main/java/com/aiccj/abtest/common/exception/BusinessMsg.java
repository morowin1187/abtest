package com.aiccj.abtest.common.exception;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public enum BusinessMsg implements BusinessEnumIfc {


    // ----------- 全局状态码-begin -----------

    SUCCESS("MSCS0000", "Request succeeded", "成功"),
    SYS_ERROR("MSCE9999", "Request failed", "系统忙"),
    PARAM_LOSS("MSAE0001", "Missing parameters", "参数缺失"),
    PARAM_UN_SUPPORT("MSAE0002", "Illegal parameters", "参数不合法"),
    // 未获取到锁PARAM_UN_SUPPORT
    URI_ERROR("MSCE9998", "The interface does not exist", "接口不存在"),
    LOGIN_NOT_EMPTY("MSCE9996", "Username and password cannot be empty", "用户名密码不能为空"),
    LOGIN_NOT_EXIST("MSCE9995", "Username does not exist", "用户名不存在"),
    TOKEN_EXPIRED("MSCE9994", "Login overtime", "登录超时"),
    TOKEN_OFFLINE("MSCE9993", "The account has been registered", "账号被注册"),
    NOT_LOGIN("MSCE9992", "Unregistered", "未登录"),

    // ----------- 全局状态码-end -----------
    MCLC0001("MCLC0001", "The account does not exit", "账号不存在"),
    MCLC0002("MCLC0002", "Incorrect password", "密码错误"),
    MCLC0003("MCLC0003", "The email is not Blank", "邮箱不能为空"),
    MCLC0004("MCLC0004", "The password is not Blank", "密码不能为空"),

    ;

    private String errorCode;

    private String errorMsg;

    private String errorMsgCn;

    BusinessMsg(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    BusinessMsg(String errorCode, String errorMsg, String errorMsgCn) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorMsgCn = errorMsgCn;
    }


    @Override
    public String getBusinessRespCode() {
        return errorCode;
    }

    @Override
    public String getRespMsg() {
        return errorMsg;
    }

    @Override
    public String getRespMsgCn() {
        return errorMsgCn;
    }

}
