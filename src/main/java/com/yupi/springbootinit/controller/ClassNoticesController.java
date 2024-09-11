package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticeDTO;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticesAddRequest;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticesQueryRequest;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticesUpdateRequest;
import com.yupi.springbootinit.model.entity.ClassNotices;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.ClassNoticesService;
import com.yupi.springbootinit.service.StudentInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/classNotices")
@Slf4j
public class ClassNoticesController {

    @Resource
    private ClassNoticesService classNoticesService;

    @Resource
    private UserService userService;

    @Resource
    private StudentInfoService studentInfoService;

    @GetMapping("/{id}")
    public BaseResponse<ClassNotices> getClassNoticeById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassNotices classNotice = classNoticesService.getById(id);
        if (classNotice == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(classNotice);
    }

    @PostMapping("/create")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> createClassNotice(@RequestBody ClassNoticesAddRequest classNoticesAddRequest) {
        if (classNoticesAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassNotices classNotice = new ClassNotices();
        BeanUtils.copyProperties(classNoticesAddRequest, classNotice);

        if (classNotice.getNoticeDate() == null) {
            classNotice.setNoticeDate(new Date());
        }

        boolean result = classNoticesService.save(classNotice);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(classNotice.getId());
    }



    @PostMapping("/update/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateClassNotice(@PathVariable Long id, @RequestBody ClassNoticesUpdateRequest classNoticesUpdateRequest) {
        if (id <= 0 || classNoticesUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ClassNotices existingClassNotice = classNoticesService.getById(id);
        if (existingClassNotice == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(classNoticesUpdateRequest, existingClassNotice);
        existingClassNotice.setId(id);
        boolean result = classNoticesService.updateById(existingClassNotice);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteClassNotice(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = classNoticesService.removeById(id);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/list/page")
    //@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ClassNotices>> listClassNoticesByPage(@RequestBody ClassNoticesQueryRequest classNoticesQueryRequest,
                                                                   HttpServletRequest request) {
        long current = classNoticesQueryRequest.getCurrent();
        long size = classNoticesQueryRequest.getPageSize();
        Page<ClassNotices> classNoticesPage = classNoticesService.page(new Page<>(current, size),
                classNoticesService.getQueryWrapper(classNoticesQueryRequest));
        return ResultUtils.success(classNoticesPage);
    }

    /**
     * 获取当前用户班级的所有通知
     *
     * @param request
     * @return
     */
    @GetMapping("/list-by-class")
    public BaseResponse<List<ClassNoticeDTO>> getNoticesByClassId(HttpServletRequest request) {
        User currentUser = userService.getLoginUser(request);
        String studentNum = userService.getStudentNumByUserId(currentUser.getId());
        Long classId = studentInfoService.getClassIdByStudentNum(studentNum);

        if (classId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Class ID is missing");
        }

        List<ClassNoticeDTO> notices = classNoticesService.getNoticesByClassId(classId);
        return ResultUtils.success(notices);
    }
}
