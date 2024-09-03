package com.yupi.springbootinit.service.impl;

import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.classofficers.ClassOfficersQueryRequest;
import com.yupi.springbootinit.model.dto.stu.StuQueryRequest;
import com.yupi.springbootinit.model.entity.ClassOfficers;
import com.yupi.springbootinit.model.entity.StudentInfo;
import com.yupi.springbootinit.service.ClassOfficersService;
import com.yupi.springbootinit.mapper.ClassOfficersMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【class_officers(班级干部表)】的数据库操作Service实现
* @createDate 2024-09-02 02:44:28
*/
@Service
public class ClassOfficersServiceImpl extends ServiceImpl<ClassOfficersMapper, ClassOfficers>
    implements ClassOfficersService{
    @Override
    public QueryWrapper<ClassOfficers> getQueryWrapper(ClassOfficersQueryRequest classOfficersQueryRequest) {
        if (classOfficersQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = classOfficersQueryRequest.getId();
        Long classId = classOfficersQueryRequest.getClassId();
        Long userId = classOfficersQueryRequest.getUserId();
        String position = classOfficersQueryRequest.getPosition();
        String sortField = classOfficersQueryRequest.getSortField();
        String sortOrder = classOfficersQueryRequest.getSortOrder();

        // 构建查询条件
        QueryWrapper<ClassOfficers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(classId != null, "class_id", classId);
        queryWrapper.eq(userId != null, "user_id", userId);
        queryWrapper.eq(StringUtils.isNotBlank(position), "position", position);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        }

        return queryWrapper;
    }

}




