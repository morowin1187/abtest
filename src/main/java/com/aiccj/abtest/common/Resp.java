package com.aiccj.abtest.common;

import com.aiccj.abtest.common.exception.BusinessEnumIfc;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resp<T> implements Serializable {

	private static final long serialVersionUID = 4765535308338670054L;

	private String code;

	private String msg;

	private String msgCn;

	private T data;

	public final static <T> Resp<T> createSuccessResp(T data) {
		Resp<T> resp = new Resp<>();
		resp.setCode(BusinessMsg.SUCCESS.getBusinessRespCode());
		resp.setMsg(BusinessMsg.SUCCESS.getRespMsg());
		resp.setMsgCn(BusinessMsg.SUCCESS.getRespMsgCn());
		resp.setData(data);
		return resp;
	}

	public final static <T> Resp<T> createSuccessResp() {
		return createSuccessResp(null);
	}

	public final static <T> Resp<T> createFailResp(BusinessEnumIfc businessEnumIfc) {
		Resp<T> resp = new Resp<>();
		resp.setCode(businessEnumIfc.getBusinessRespCode());
		resp.setMsg(businessEnumIfc.getRespMsg());
		resp.setMsgCn(businessEnumIfc.getRespMsgCn());
		return resp;
	}

	public final static <T> Resp<T> createFailResp() {
		Resp<T> resp = new Resp<>();
		resp.setCode(BusinessMsg.SYS_ERROR.getBusinessRespCode());
		resp.setMsg(BusinessMsg.SYS_ERROR.getRespMsg());
		resp.setMsgCn(BusinessMsg.SYS_ERROR.getRespMsgCn());
		return resp;
	}
}
