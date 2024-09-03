package com.yupi.springbootinit.service;

import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.classofficers.ClassOfficersQueryRequest;
import com.yupi.springbootinit.model.dto.stu.StuQueryRequest;
import com.yupi.springbootinit.model.entity.ClassOfficers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.StudentInfo;

/**
* @author Administrator
* @description 针对表【class_officers(班级干部表)】的数据库操作Service
* @createDate 2024-09-02 02:44:28
*/
public interface ClassOfficersService extends IService<ClassOfficers> {
    /**
     * 获取查询条件
     *
     * @param classofficersQueryRequest
     * @return
     */
    QueryWrapper<ClassOfficers> getQueryWrapper(ClassOfficersQueryRequest classofficersQueryRequest);
}
