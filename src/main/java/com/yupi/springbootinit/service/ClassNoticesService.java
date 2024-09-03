package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticeDTO;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticesQueryRequest;
import com.yupi.springbootinit.model.entity.ClassNotices;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【class_notices(班级通知表)】的数据库操作Service
* @createDate 2024-09-02 12:25:32
*/
public interface ClassNoticesService extends IService<ClassNotices> {
    /**
     * 获取查询条件
     *
     * @param classofficersQueryRequest
     * @return
     */
    QueryWrapper<ClassNotices> getQueryWrapper(ClassNoticesQueryRequest classNoticesQueryRequest);

    List<ClassNoticeDTO> getNoticesByClassId(Long classId);
}
