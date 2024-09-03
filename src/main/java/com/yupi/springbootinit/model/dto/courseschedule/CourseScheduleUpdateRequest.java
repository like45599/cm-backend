package com.yupi.springbootinit.model.dto.courseschedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新课程安排请求
 */
@Data
public class CourseScheduleUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long courseId;

    private Long classId;

    private Integer dayOfWeek;

    private String timeSlot;

    private String weeks;

    private Date startDate;

    private Date endDate;
}