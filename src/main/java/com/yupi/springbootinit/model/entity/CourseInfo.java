package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 课程信息表
 * @TableName course_info
 */
@TableName(value ="course_info")
@Data
public class CourseInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 课程展示颜色
     */
    private String classRoom;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        com.yupi.springbootinit.model.entity.CourseInfo other = (com.yupi.springbootinit.model.entity.CourseInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
                && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
                && (this.getClassRoom() == null ? other.getClassRoom() == null : this.getClassRoom().equals(other.getClassRoom()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getClassRoom() == null) ? 0 : getClassRoom().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", coursename=").append(courseName);
        sb.append(", teachername=").append(teacherName);
        sb.append(", classroom=").append(classRoom);
        sb.append(", createtime=").append(createTime);
        sb.append(", updatetime=").append(updateTime);
        sb.append(", isdelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}