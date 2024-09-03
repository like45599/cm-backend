package com.yupi.springbootinit.model.dto.stu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StuQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 学号
     */
    private String studentNum;

    /**
     * 所属班级
     */
    private Long classId;

    /**
     * 性别
     */
    private String gender;

    /**
     * 姓名
     */
    private String name;

    private static final long serialVersionUID = 1L;
}