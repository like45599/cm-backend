package com.yupi.springbootinit.model.dto.courseinfo;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询课程信息请求
 */
@Data
public class CourseInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师名称
     */
    private String teacherName;

    private static final long serialVersionUID = 1L;
}