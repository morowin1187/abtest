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
public class TestGroupListReq extends GlobalPageBaseReq {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;

}
