package com.aiccj.abtest.common;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public abstract class GlobalPageBaseReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer page = 1;

	private Integer limit = 10;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public Integer getPageIndex() {
		return (page - 1) * limit;
	}
}
