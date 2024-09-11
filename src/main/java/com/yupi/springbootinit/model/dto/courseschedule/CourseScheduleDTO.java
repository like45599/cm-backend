package com.yupi.springbootinit.model.dto.courseschedule;

import lombok.Data;

import java.util.Date;

/**
 * @author GaoLike
 * @date 2024/09/02 21:14
 **/
@Data
public class CourseScheduleDTO {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long courseId;

    private Long classId;

    private Integer dayOfWeek;

    private String timeSlot;

    private String weeks;

    private Date startDate;

    private Date endDate;

    private String courseName;

    private String teacherName;

    private String classRoom;
}