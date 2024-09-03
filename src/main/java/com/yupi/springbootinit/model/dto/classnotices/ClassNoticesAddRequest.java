package com.yupi.springbootinit.model.dto.classnotices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 */
@Data
public class ClassNoticesAddRequest implements Serializable {

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date noticeDate;

    private Long classId;

    private static final long serialVersionUID = 1L;
}