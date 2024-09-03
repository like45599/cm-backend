package com.yupi.springbootinit.model.dto.classactivities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 */
@Data
public class ClassActivitiesAddRequest implements Serializable {

    private String activityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityDate;

    private String description;

    private Long classId;

    private static final long serialVersionUID = 1L;
}