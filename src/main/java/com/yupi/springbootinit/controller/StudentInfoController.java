package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.stu.StuAddRequest;
import com.yupi.springbootinit.model.dto.stu.StuQueryRequest;
import com.yupi.springbootinit.model.dto.stu.StuUpdateRequest;
import com.yupi.springbootinit.model.entity.StudentInfo;
import com.yupi.springbootinit.service.StudentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/studentInfo")
@Slf4j
public class StudentInfoController {

    @Resource
    private StudentInfoService studentInfoService;

    /**
     * 根据 ID 获取学生信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse<StudentInfo> getStudentById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StudentInfo studentInfo = studentInfoService.getById(id);
        if (studentInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(studentInfo);
    }

    /**
     * 创建新的学生信息
     *
     * @param stuAddRequest
     * @return
     */
    @PostMapping("/create")
    public BaseResponse<Long> createStudentInfo(@RequestBody StuAddRequest stuAddRequest) {
        if (stuAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(stuAddRequest, studentInfo);
        boolean result = studentInfoService.save(studentInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(studentInfo.getId());
    }

    /**
     * 更新学生信息
     *
     * @param id
     * @param stuUpdateRequest
     * @return
     */
    @PostMapping("/update/{id}")
    public BaseResponse<Boolean> updateStudentInfo(@PathVariable Long id, @RequestBody StuUpdateRequest stuUpdateRequest) {
        if (id <= 0 || stuUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StudentInfo existingStudentInfo = studentInfoService.getById(id);
        if (existingStudentInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(stuUpdateRequest, existingStudentInfo);
        existingStudentInfo.setId(id);
        boolean result = studentInfoService.updateById(existingStudentInfo);
        return ResultUtils.success(result);
    }

    /**
     * 删除学生信息
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteStudentInfo(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = studentInfoService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param stuQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<StudentInfo>> listUserByPage(@RequestBody StuQueryRequest stuQueryRequest,
                                                          HttpServletRequest request) {
        long current = stuQueryRequest.getCurrent();
        long size = stuQueryRequest.getPageSize();
        Page<StudentInfo> studentInfoPagePage = studentInfoService.page(new Page<>(current, size),
                studentInfoService.getQueryWrapper(stuQueryRequest));
        return ResultUtils.success(studentInfoPagePage);
    }
}

