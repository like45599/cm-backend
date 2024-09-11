package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.stu.StuQueryRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.User;

/**
* @author Administrator
* @description 针对表【student_info(学生信息表)】的数据库操作Service
* @createDate 2024-08-30 15:37:30
*/
public interface StudentInfoService extends IService<StudentInfo> {

    /**
     * 获取查询条件
     *
     * @param stuQueryRequest
     * @return
     */
    QueryWrapper<StudentInfo> getQueryWrapper(StuQueryRequest stuQueryRequest);

    /**
     * 根据学号获取班级id
     * @param studentNum
     * @return
     */
    Long getClassIdByStudentNum(String studentNum);
}
