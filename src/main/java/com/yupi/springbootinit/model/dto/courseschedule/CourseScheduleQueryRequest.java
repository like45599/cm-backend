package com.yupi.springbootinit.model.dto.courseschedule;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.io.Serializable;

/**
 * 查询课程安排请求
 */
@Data
public class CourseScheduleQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long courseId;

    private Long classId;

    private Integer dayOfWeek;

    private String timeSlot;

    private String weeks;

    private Date startDate;

    private Date endDate;

    private String sortField;

    private String sortOrder;
}