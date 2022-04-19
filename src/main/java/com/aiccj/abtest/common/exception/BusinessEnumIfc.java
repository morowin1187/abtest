package com.aiccj.abtest.common.exception;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public interface BusinessEnumIfc {


    /**
     * 获取返回体 业务-code
     *
     * @return
     */
    String getBusinessRespCode();


    /**
     * 获取返回体 msg信息
     *
     * @return
     */
    String getRespMsg();


    String getRespMsgCn();


}
