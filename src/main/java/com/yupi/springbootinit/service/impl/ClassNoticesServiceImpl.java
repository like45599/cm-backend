package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticeDTO;
import com.yupi.springbootinit.model.dto.classnotices.ClassNoticesQueryRequest;
import com.yupi.springbootinit.model.entity.ClassNotices;
import com.yupi.springbootinit.service.ClassNoticesService;
import com.yupi.springbootinit.mapper.ClassNoticesMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【class_notices(班级通知表)】的数据库操作Service实现
* @createDate 2024-09-02 12:25:32
*/
@Service
public class ClassNoticesServiceImpl extends ServiceImpl<ClassNoticesMapper, ClassNotices>
    implements ClassNoticesService{
    @Override
    public QueryWrapper<ClassNotices> getQueryWrapper(ClassNoticesQueryRequest classNoticesQueryRequest) {
        if (classNoticesQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = classNoticesQueryRequest.getId();
        Long classId = classNoticesQueryRequest.getClassId();
        String title = classNoticesQueryRequest.getTitle();
        String sortField = classNoticesQueryRequest.getSortField();
        String sortOrder = classNoticesQueryRequest.getSortOrder();

        QueryWrapper<ClassNotices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(classId != null, "classId", classId);
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals("ASC"), sortField);
        }

        return queryWrapper;
    }

    @Override
    public List<ClassNoticeDTO> getNoticesByClassId(Long classId) {
        QueryWrapper<ClassNotices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classId", classId)
                .eq("isDelete", 0);

        List<ClassNotices> notices = this.list(queryWrapper);

        return notices.stream().map(notice -> {
            ClassNoticeDTO dto = new ClassNoticeDTO();
            BeanUtils.copyProperties(notice, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}




