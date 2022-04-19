package com.aiccj.abtest.common;

import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Getter
public class PageableEntity<T> implements Serializable {


    private Long total;

    private List<T> data;


    private PageableEntity(Long total, List<T> data) {
        this.setTotal(total);
        this.setData(data);
    }

    public static <T> PageableEntity<T> pageBuilder(Long total, List<T> data){
        return new PageableEntity<T>(total, data);
    }

    public void setTotal(Long total) {
        if (total < 0)
            total = 0L;
        this.total = total;
    }

    public void setData(List<T> data) {
        if (data == null || data.size() <= 0)
            data = new LinkedList<>();
        this.data = data;
    }
}
