package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.classactivities.ClassActivitiesQueryRequest;
import com.yupi.springbootinit.model.entity.ClassActivities;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【class_activities(班级活动表)】的数据库操作Service
* @createDate 2024-09-02 12:11:24
*/
public interface ClassActivitiesService extends IService<ClassActivities> {
    /**
     *  封装查询条件
     * @param classActivitiesQueryRequest
     * @return
     */
    QueryWrapper<ClassActivities> getQueryWrapper(ClassActivitiesQueryRequest classActivitiesQueryRequest);

}
