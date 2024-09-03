package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.stu.StuQueryRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.StudentInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.StudentInfoService;
import com.yupi.springbootinit.mapper.StudentInfoMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author Administrator
* @description 针对表【student_info(学生信息表)】的数据库操作Service实现
* @createDate 2024-08-30 15:37:30
*/
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo>
    implements StudentInfoService{
    @Override
    public QueryWrapper<StudentInfo> getQueryWrapper(StuQueryRequest stuQueryRequest) {
        if (stuQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = stuQueryRequest.getId();
        String stuNum = stuQueryRequest.getStudentNum();
        String stuName = stuQueryRequest.getName();
        String stuGender = stuQueryRequest.getGender();
        Long classId = stuQueryRequest.getClassId();
        String sortField = stuQueryRequest.getSortField();
        String sortOrder = stuQueryRequest.getSortOrder();

        // 构建查询条件
        QueryWrapper<StudentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(stuNum), "student_num", stuNum);
        queryWrapper.eq(classId != null, "classId", classId); // 处理 classId
        queryWrapper.eq(StringUtils.isNotBlank(stuGender), "gender", stuGender);
        queryWrapper.like(StringUtils.isNotBlank(stuName), "name", stuName);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        }

        return queryWrapper;
    }
    @Override
    public Long getClassIdByStudentNum(String studentNum) {
        StudentInfo studentInfo = this.lambdaQuery().eq(StudentInfo::getStudentNum, studentNum).one();
        if (studentInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "学生信息不存在");
        }
        return studentInfo.getClassId();
    }

}




