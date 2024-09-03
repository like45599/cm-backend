package com.yupi.springbootinit.controller;


import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.mapper.CourseScheduleMapper;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleAddRequest;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleDTO;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleQueryRequest;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleUpdateRequest;
import com.yupi.springbootinit.model.entity.CourseSchedule;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.CourseScheduleService;
import com.yupi.springbootinit.service.StudentInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author GaoLike
 * @date 2024/09/02 20:45
 **/

@RestController
@RequestMapping("/course-schedule")
@Slf4j
public class CourseScheduleController {

    @Resource
    private CourseScheduleService courseScheduleService;

    @Resource
    private UserService userService;

    @Resource
    private StudentInfoService studentInfoService;
    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    /**
     * 创建课程表
     *
     * @param courseScheduleAddRequest
     * @return
     */
    @PostMapping("/create")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> createCourseSchedule(@RequestBody CourseScheduleAddRequest courseScheduleAddRequest) {
        if (courseScheduleAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseSchedule courseSchedule = new CourseSchedule();
        BeanUtils.copyProperties(courseScheduleAddRequest, courseSchedule);
        boolean result = courseScheduleService.save(courseSchedule);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(courseSchedule.getId());
    }

    /**
     * 更新课程表
     *
     * @param id
     * @param courseScheduleUpdateRequest
     * @return
     */
    @PostMapping("/update/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateCourseSchedule(@PathVariable Long id, @RequestBody CourseScheduleUpdateRequest courseScheduleUpdateRequest) {
        if (id <= 0 || courseScheduleUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseSchedule existingCourseSchedule = courseScheduleService.getById(id);
        if (existingCourseSchedule == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(courseScheduleUpdateRequest, existingCourseSchedule);
        existingCourseSchedule.setId(id);
        boolean result = courseScheduleService.updateById(existingCourseSchedule);
        return ResultUtils.success(result);
    }

    /**
     * 删除课程表
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteCourseSchedule(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = courseScheduleService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 分页获取课程表列表
     *
     * @param courseScheduleQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<CourseSchedule>> listCourseSchedulesByPage(@RequestBody CourseScheduleQueryRequest courseScheduleQueryRequest,
                                                                        HttpServletRequest request) {
        long current = courseScheduleQueryRequest.getCurrent();
        long size = courseScheduleQueryRequest.getPageSize();
        Page<CourseSchedule> courseSchedulePage = courseScheduleService.page(new Page<>(current, size),
                courseScheduleService.getQueryWrapper(courseScheduleQueryRequest));
        return ResultUtils.success(courseSchedulePage);
    }


    /**
     * 获取指定日期的课程表信息
     *
     * @param date
     * @param request
     * @return
     */
    @GetMapping("/date/{date}")
    public BaseResponse<List<CourseScheduleDTO>> getCoursesByDate(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            HttpServletRequest request) {

        User currentUser = userService.getLoginUser(request);
        String studentNum = userService.getStudentNumByUserId(currentUser.getId());
        Long classId = studentInfoService.getClassIdByStudentNum(studentNum);

        if (classId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Class ID is missing");
        }

        List<CourseScheduleDTO> courses = courseScheduleService.getCoursesByDateAndClassId(date, classId);

        return ResultUtils.success(courses);
    }


}