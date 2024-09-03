package com.yupi.springbootinit.model.dto.stu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 */
@Data
public class StuAddRequest implements Serializable {

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

    /**
     * 出生日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    private static final long serialVersionUID = 1L;
}