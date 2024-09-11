package com.yupi.springbootinit.model.dto.courseinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 添加课程信息请求
 */
@Data
public class CourseInfoAddRequest implements Serializable {
    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     *
     */
    private String classRoom;

    private static final long serialVersionUID = 1L;
}