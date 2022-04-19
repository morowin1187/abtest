package com.aiccj.abtest.model.dto;

import com.aiccj.abtest.common.GlobalPageBaseReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Data
public class UserListReq extends GlobalPageBaseReq {

    private static final long serialVersionUID = 2794650051669414986L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;

    private String userNo;

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }
}
