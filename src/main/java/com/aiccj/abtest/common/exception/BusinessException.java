package com.aiccj.abtest.common.exception;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public class BusinessException extends RuntimeException {


    /**
     * 业务异常详细信息
     */
    private BusinessEnumIfc businessEnumIfc;

    /**
     * 部分code需要携带错误参数
     */
    private Object data;

    public BusinessException(BusinessEnumIfc aBusinessEnumIfc) {
        super(String.format("业务异常！code: %s, msg: %s", aBusinessEnumIfc.getBusinessRespCode(),
            aBusinessEnumIfc.getRespMsg()));
        businessEnumIfc = aBusinessEnumIfc;
    }

    public BusinessException(BusinessEnumIfc aBusinessEnumIfc, Object aData) {
        super(String.format("业务异常！code: %s, msg: %s, data: %s",
            aBusinessEnumIfc.getBusinessRespCode(), aBusinessEnumIfc.getRespMsg(), aData));
        this.businessEnumIfc = aBusinessEnumIfc;
        this.data = aData;
    }

    /**
     * @return
     */
    public BusinessEnumIfc getBusinessEnumIfc() {
        return businessEnumIfc;
    }

    public Object getData() {
        return data;
    }
}
