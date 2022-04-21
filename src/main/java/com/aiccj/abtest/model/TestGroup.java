package com.aiccj.abtest.model;

import com.aiccj.abtest.common.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author morowin
 * @Date 2022/4/19 21:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class TestGroup {

    private Integer id;

    private String name;

    private Integer layer;

    private String status;

    private Integer splitFlowNum;

    private Byte directional;

    private String inLogic;

    private String inParams;

    private Date startDate;

    private Date endDate;

    private Date planEndDate;

    private Long moduleId;

    private String description;

    private String shuntIdType;

    private Byte type;

    private String source;

    private String creator;

    private String editor;

    private Date createdAt;

    private Date updatedAt;

    public void createCheck() {


    }

    public void createForUser(String curUserName) {
        status = "";
        createdAt = DateUtil.getCurTime();
        updatedAt = DateUtil.getCurTime();
        creator = curUserName;
    }

    public void editCheck() {

    }

    public void editForUser(String curUserName) {
        updatedAt = DateUtil.getCurTime();
        editor = curUserName;
    }
}