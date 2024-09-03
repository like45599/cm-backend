package com.yupi.springbootinit.model.dto.courseinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新课程信息请求
 */
@Data
public class CourseInfoUpdateRequest implements Serializable {
    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 课程展示颜色
     */
    private String color;

    private static final long serialVersionUID = 1L;
}