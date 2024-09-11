package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleDTO;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleQueryRequest;
import com.yupi.springbootinit.model.entity.CourseSchedule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
* @author Administrator
* @description 针对表【course_schedule(课程安排表)】的数据库操作Service
* @createDate 2024-09-03 00:52:21
*/
public interface CourseScheduleService extends IService<CourseSchedule> {
    /**
     * 获取查询条件
     * @param courseScheduleQueryRequest
     * @return
     */
    QueryWrapper<CourseSchedule> getQueryWrapper(CourseScheduleQueryRequest courseScheduleQueryRequest);

    /**
     * 根据日期和班级id获取课程
     * @param date
     * @param classId
     * @return
     */
    List<CourseScheduleDTO> getCoursesByDateAndClassId(LocalDate date, Long classId);
}
