package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classofficers.ClassOfficersAddRequest;
import com.yupi.springbootinit.model.dto.classofficers.ClassOfficersQueryRequest;
import com.yupi.springbootinit.model.dto.classofficers.ClassOfficersUpdateRequest;
import com.yupi.springbootinit.model.entity.ClassOfficers;

import com.yupi.springbootinit.service.ClassOfficersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 班级干部管理接口
 */
@RestController
@RequestMapping("/classOfficer")
@Slf4j
public class ClassOfficerController {

    @Resource
    private ClassOfficersService classOfficerService;

    /**
     * 根据 ID 获取班级干部信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse<ClassOfficers> getClassOfficerById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassOfficers classOfficer = classOfficerService.getById(id);
        if (classOfficer == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(classOfficer);
    }

    /**
     * 创建新的班级干部信息
     *
     * @param classOfficerAddRequest
     * @return
     */
    @PostMapping("/create")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> createClassOfficer(@RequestBody ClassOfficersAddRequest classOfficerAddRequest) {
        if (classOfficerAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassOfficers classOfficer = new ClassOfficers();
        BeanUtils.copyProperties(classOfficerAddRequest, classOfficer);
        boolean result = classOfficerService.save(classOfficer);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(classOfficer.getId());
    }

    /**
     * 更新班级干部信息
     *
     * @param id
     * @param classOfficerUpdateRequest
     * @return
     */
    @PostMapping("/update/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateClassOfficer(@PathVariable Long id, @RequestBody ClassOfficersUpdateRequest classOfficerUpdateRequest) {
        if (id <= 0 || classOfficerUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassOfficers existingClassOfficer = classOfficerService.getById(id);
        if (existingClassOfficer == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(classOfficerUpdateRequest, existingClassOfficer);
        existingClassOfficer.setId(id);
        boolean result = classOfficerService.updateById(existingClassOfficer);
        return ResultUtils.success(result);
    }

    /**
     * 删除班级干部信息
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteClassOfficer(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = classOfficerService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 分页获取班级干部列表（仅管理员）
     *
     * @param classOfficerQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ClassOfficers>> listClassOfficerByPage(@RequestBody ClassOfficersQueryRequest classOfficerQueryRequest,
                                                                   HttpServletRequest request) {
        long current = classOfficerQueryRequest.getCurrent();
        long size = classOfficerQueryRequest.getPageSize();
        Page<ClassOfficers> classOfficerPage = classOfficerService.page(new Page<>(current, size),
                classOfficerService.getQueryWrapper(classOfficerQueryRequest));
        return ResultUtils.success(classOfficerPage);
    }
}
