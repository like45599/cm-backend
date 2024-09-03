package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classactivities.ClassActivitiesAddRequest;
import com.yupi.springbootinit.model.dto.classactivities.ClassActivitiesQueryRequest;
import com.yupi.springbootinit.model.dto.classactivities.ClassActivitiesUpdateRequest;
import com.yupi.springbootinit.model.entity.ClassActivities;
import com.yupi.springbootinit.service.ClassActivitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 班级活动管理接口
 */
@RestController
@RequestMapping("/classActivities")
@Slf4j
public class ClassActivitiesController {

    @Resource
    private ClassActivitiesService classActivitiesService;

    @GetMapping("/{id}")
    public BaseResponse<ClassActivities> getClassActivityById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassActivities classActivity = classActivitiesService.getById(id);
        if (classActivity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(classActivity);
    }

    @PostMapping("/create")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> createClassActivity(@RequestBody ClassActivitiesAddRequest classActivitiesAddRequest) {
        if (classActivitiesAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassActivities classActivity = new ClassActivities();
        BeanUtils.copyProperties(classActivitiesAddRequest, classActivity);
        boolean result = classActivitiesService.save(classActivity);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(classActivity.getId());
    }

    @PostMapping("/update/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateClassActivity(@PathVariable Long id, @RequestBody ClassActivitiesUpdateRequest classActivitiesUpdateRequest) {
        if (id <= 0 || classActivitiesUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassActivities existingClassActivity = classActivitiesService.getById(id);
        if (existingClassActivity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(classActivitiesUpdateRequest, existingClassActivity);
        existingClassActivity.setId(id);
        boolean result = classActivitiesService.updateById(existingClassActivity);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteClassActivity(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = classActivitiesService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ClassActivities>> listClassActivitiesByPage(@RequestBody ClassActivitiesQueryRequest classActivitiesQueryRequest,
                                                                         HttpServletRequest request) {
        long current = classActivitiesQueryRequest.getCurrent();
        long size = classActivitiesQueryRequest.getPageSize();
        Page<ClassActivities> classActivitiesPage = classActivitiesService.page(new Page<>(current, size),
                classActivitiesService.getQueryWrapper(classActivitiesQueryRequest));
        return ResultUtils.success(classActivitiesPage);
    }
}
