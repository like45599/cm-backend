package com.yupi.springbootinit.model.dto.classactivities;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassActivitiesQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private String activityName;

    private Long classId;

    private static final long serialVersionUID = 1L;
}