package com.yupi.springbootinit.model.dto.classnotices;

/**
 * @author GaoLike
 * @date 2024/09/04 03:27
 **/

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 班级通知数据传输对象（DTO）
 */
@Data
public class ClassNoticeDTO implements Serializable {

    /**
     * 通知 ID
     */
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知日期
     */
    private Date noticeDate;

    /**
     * 所属班级 ID
     */
    private Long classId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}