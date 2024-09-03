package com.yupi.springbootinit.model.dto.classofficers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户更新请求
 */
@Data
public class ClassOfficersUpdateRequest implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 职务
     */
    private String position;

    private static final long serialVersionUID = 1L;
}