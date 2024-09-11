package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.courseinfo.CourseInfoQueryRequest;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleQueryRequest;
import com.yupi.springbootinit.model.entity.CourseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.CourseSchedule;

/**
* @author Administrator
* @description 针对表【course_info(课程信息表)】的数据库操作Service
* @createDate 2024-09-03 00:56:33
*/
public interface CourseInfoService extends IService<CourseInfo> {
    /**
     * 获取查询条件
     * @param courseInfoQueryRequest
     * @return
     */
    QueryWrapper<CourseInfo> getQueryWrapper(CourseInfoQueryRequest courseInfoQueryRequest);
}
