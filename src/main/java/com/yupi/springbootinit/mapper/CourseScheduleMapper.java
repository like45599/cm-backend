package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleDTO;
import com.yupi.springbootinit.model.entity.CourseSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDate;
import java.util.List;

/**
* @author Administrator
* @description 针对表【course_schedule(课程安排表)】的数据库操作Mapper
* @createDate 2024-09-03 00:52:21
* @Entity com.yupi.springbootinit.model.entity.CourseSchedule
*/
public interface CourseScheduleMapper extends BaseMapper<CourseSchedule> {

    List<CourseScheduleDTO> getCoursesForPeriod(LocalDate date, int dayOfWeek, Long classId);
}




