package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classactivities.ClassActivitiesQueryRequest;
import com.yupi.springbootinit.model.entity.ClassActivities;
import com.yupi.springbootinit.service.ClassActivitiesService;
import com.yupi.springbootinit.mapper.ClassActivitiesMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

/**
* @author Administrator
* @description 针对表【class_activities(班级活动表)】的数据库操作Service实现
* @createDate 2024-09-02 12:11:24
*/
@Service
public class ClassActivitiesServiceImpl extends ServiceImpl<ClassActivitiesMapper, ClassActivities>
    implements ClassActivitiesService{
    @Override
    public QueryWrapper<ClassActivities> getQueryWrapper(ClassActivitiesQueryRequest classActivitiesQueryRequest) {
        if (classActivitiesQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = classActivitiesQueryRequest.getId();
        Long classId = classActivitiesQueryRequest.getClassId();
        String activityName = classActivitiesQueryRequest.getActivityName();
        String sortField = classActivitiesQueryRequest.getSortField();
        String sortOrder = classActivitiesQueryRequest.getSortOrder();

        QueryWrapper<ClassActivities> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(classId != null, "classId", classId);
        queryWrapper.like(StringUtils.isNotBlank(activityName), "activityName", activityName);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals("ASC"), sortField);
        }

        return queryWrapper;
    }
}




