package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.courseinfo.CourseInfoQueryRequest;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleQueryRequest;
import com.yupi.springbootinit.model.entity.CourseInfo;
import com.yupi.springbootinit.model.entity.CourseSchedule;
import com.yupi.springbootinit.service.CourseInfoService;
import com.yupi.springbootinit.mapper.CourseInfoMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【course_info(课程信息表)】的数据库操作Service实现
* @createDate 2024-09-03 00:56:33
*/
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo>
    implements CourseInfoService{
    @Override
    public QueryWrapper<CourseInfo> getQueryWrapper(CourseInfoQueryRequest courseInfoQueryRequest) {
        if (courseInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String courseName = courseInfoQueryRequest.getCourseName();
        String teacherName = courseInfoQueryRequest.getTeacherName();

        String sortField = courseInfoQueryRequest.getSortField();
        String sortOrder = courseInfoQueryRequest.getSortOrder();

        QueryWrapper<CourseInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(courseName), "courseName", courseName);
        queryWrapper.eq(StringUtils.isNotBlank(teacherName), "teacherName", teacherName);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals("ASC"), sortField);
        }

        return queryWrapper;
    }
}




