package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.courseinfo.CourseInfoAddRequest;
import com.yupi.springbootinit.model.dto.courseinfo.CourseInfoQueryRequest;
import com.yupi.springbootinit.model.dto.courseinfo.CourseInfoUpdateRequest;
import com.yupi.springbootinit.model.entity.CourseInfo;
import com.yupi.springbootinit.service.CourseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author GaoLike
 * @date 2024/09/02 20:45
 **/

@RestController
@RequestMapping("/courseInfo")
@Slf4j
public class CourseInfoController {

    @Resource
    private CourseInfoService courseInfoService;

    @PostMapping("/create")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> createCourseInfo(@RequestBody CourseInfoAddRequest courseInfoAddRequest) {
        if (courseInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(courseInfoAddRequest, courseInfo);
        boolean result = courseInfoService.save(courseInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(courseInfo.getId());
    }

    @PostMapping("/update/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateCourseInfo(@PathVariable Long id, @RequestBody CourseInfoUpdateRequest courseInfoUpdateRequest) {
        if (id <= 0 || courseInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseInfo existingCourseInfo = courseInfoService.getById(id);
        if (existingCourseInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(courseInfoUpdateRequest, existingCourseInfo);
        existingCourseInfo.setId(id);
        boolean result = courseInfoService.updateById(existingCourseInfo);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteCourseInfo(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = courseInfoService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<CourseInfo>> listCourseInfosByPage(@RequestBody CourseInfoQueryRequest courseInfoQueryRequest,
                                                                HttpServletRequest request) {
        long current = courseInfoQueryRequest.getCurrent();
        long size = courseInfoQueryRequest.getPageSize();
        Page<CourseInfo> courseInfoPage = courseInfoService.page(new Page<>(current, size),
                courseInfoService.getQueryWrapper(courseInfoQueryRequest));
        return ResultUtils.success(courseInfoPage);
    }
}