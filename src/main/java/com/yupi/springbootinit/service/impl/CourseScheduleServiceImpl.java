package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.mapper.CourseInfoMapper;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleDTO;
import com.yupi.springbootinit.model.dto.courseschedule.CourseScheduleQueryRequest;
import com.yupi.springbootinit.model.entity.CourseInfo;
import com.yupi.springbootinit.model.entity.CourseSchedule;
import com.yupi.springbootinit.service.CourseInfoService;
import com.yupi.springbootinit.service.CourseScheduleService;
import com.yupi.springbootinit.mapper.CourseScheduleMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【course_schedule(课程安排表)】的数据库操作Service实现
* @createDate 2024-09-03 00:52:21
*/
@Service
public class CourseScheduleServiceImpl extends ServiceImpl<CourseScheduleMapper, CourseSchedule>
    implements CourseScheduleService{

    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private CourseScheduleMapper courseScheduleMapper;


    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Override
    public QueryWrapper<CourseSchedule> getQueryWrapper(CourseScheduleQueryRequest courseScheduleQueryRequest) {
        if (courseScheduleQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long courseId = courseScheduleQueryRequest.getCourseId();
        Long classId = courseScheduleQueryRequest.getClassId();
        Integer dayOfWeek = courseScheduleQueryRequest.getDayOfWeek();
        String timeSlot = courseScheduleQueryRequest.getTimeSlot();
        String weeks = courseScheduleQueryRequest.getWeeks();
        String sortField = courseScheduleQueryRequest.getSortField();
        String sortOrder = courseScheduleQueryRequest.getSortOrder();

        QueryWrapper<CourseSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(courseId != null, "courseId", courseId);
        queryWrapper.eq(classId != null, "classId", classId);
        queryWrapper.eq(dayOfWeek != null, "dayOfWeek", dayOfWeek);
        queryWrapper.like(StringUtils.isNotBlank(timeSlot), "timeSlot", timeSlot);
        queryWrapper.like(StringUtils.isNotBlank(weeks), "weeks", weeks);

        // 排序
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(true, sortOrder.equals("ASC"), sortField);
        }

        return queryWrapper;
    }

    @Override
    public List<CourseScheduleDTO> getCoursesByDateAndDayOfWeek(LocalDate date, int dayOfWeek) {
        QueryWrapper<CourseSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete", 0);
        queryWrapper.le("startDate", date);
        queryWrapper.ge("endDate", date);
        queryWrapper.eq("dayOfWeek", dayOfWeek);

        List<CourseSchedule> courseSchedules = this.list(queryWrapper);
        return courseSchedules.stream().map(course -> {
            CourseScheduleDTO dto = new CourseScheduleDTO();
            // 复制属性到DTO对象
            BeanUtils.copyProperties(course, dto);
            // 可以通过Mapper查询相关课程名称、教师名称等
            CourseInfo courseInfo = courseInfoService.getById(course.getCourseId());
            dto.setCourseName(courseInfo.getCourseName());
            dto.setTeacherName(courseInfo.getTeacherName());
            dto.setColor(courseInfo.getColor());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CourseScheduleDTO> getCoursesByDateAndDayOfWeekAndClassId(LocalDate date, int dayOfWeek, Long classId) {
        QueryWrapper<CourseSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete", 0);
        queryWrapper.le("startDate", date);
        queryWrapper.ge("endDate", date);
        queryWrapper.eq("dayOfWeek", dayOfWeek);
        queryWrapper.eq("classId", classId);  // 过滤指定 classId

        List<CourseSchedule> courseSchedules = this.list(queryWrapper);
        return courseSchedules.stream().map(course -> {
            CourseScheduleDTO dto = new CourseScheduleDTO();
            // 复制属性到DTO对象
            BeanUtils.copyProperties(course, dto);
            // 通过CourseInfoService查询课程详细信息
            CourseInfo courseInfo = courseInfoService.getById(course.getCourseId());
            dto.setCourseName(courseInfo.getCourseName());
            dto.setTeacherName(courseInfo.getTeacherName());
            dto.setColor(courseInfo.getColor());
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<CourseScheduleDTO> getCoursesByDateAndClassId(LocalDate date, Long classId) {
        // 获取当前日期是学期的第几周
        LocalDate semesterStartDate = LocalDate.of(2024, 9, 1); // 假设学期从9月1日开始
        long weekNumber = (date.toEpochDay() - semesterStartDate.toEpochDay()) / 7 + 1;

        QueryWrapper<CourseSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classId", classId)
                .eq("isDelete", 0)
                .le("startDate", date)
                .ge("endDate", date);

        // 执行初步查询
        List<CourseSchedule> courseSchedules = courseScheduleMapper.selectList(queryWrapper);

        // 过滤符合条件的课程表
        List<CourseScheduleDTO> filteredSchedules = courseSchedules.stream()
                .filter(schedule -> {
                    Set<Integer> weekSet = parseWeeks(schedule.getWeeks());
                    return weekSet.contains((int) weekNumber);
                })
                .map(schedule -> {
                    CourseScheduleDTO dto = new CourseScheduleDTO();
                    BeanUtils.copyProperties(schedule, dto);
                    CourseInfo courseInfo = courseInfoMapper.selectById(schedule.getCourseId());
                    if (courseInfo != null) {
                        dto.setCourseName(courseInfo.getCourseName());
                        dto.setTeacherName(courseInfo.getTeacherName());
                        dto.setColor(courseInfo.getColor());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return filteredSchedules;
    }

    public Set<Integer> parseWeeks(String weeks) {
        Set<Integer> weekSet = new HashSet<>();
        String[] weekRanges = weeks.split(",");
        for (String range : weekRanges) {
            if (range.contains("-")) {
                String[] bounds = range.split("-");
                int start = Integer.parseInt(bounds[0]);
                int end = Integer.parseInt(bounds[1]);
                for (int i = start; i <= end; i++) {
                    weekSet.add(i);
                }
            } else {
                weekSet.add(Integer.parseInt(range));
            }
        }
        return weekSet;
    }

    /**
     * 根据日期和班级ID获取课程表信息的逻辑   <防丢失>
     * SELECT
     *     cs.id AS id,
     *     cs.courseId AS courseId,
     *     cs.classId AS classId,
     *     cs.dayOfWeek AS dayOfWeek,
     *     cs.timeSlot AS timeSlot,
     *     cs.weeks AS weeks,
     *     cs.startDate AS startDate,
     *     cs.endDate AS endDate,
     *     ci.courseName AS courseName,
     *     ci.teacherName AS teacherName,
     *     ci.color AS color
     * FROM
     *     course_schedule cs
     *         JOIN course_info ci ON cs.courseId = ci.id
     * WHERE
     *     cs.classId = 2  -- 替换为实际的 classId
     *   AND cs.isDelete = 0
     *   AND cs.startDate <= '2024-10-22'  -- 替换为实际的查询日期
     *   AND cs.endDate >= '2024-10-22'  -- 替换为实际的查询日期
     *   AND cs.dayOfWeek = 1  -- 确保仅匹配指定的 dayOfWeek
     *   AND FIND_IN_SET(
     *               FLOOR(DATEDIFF('2024-10-15', cs.startDate) / 7) + 1,
     *               REPLACE(REPLACE(cs.weeks, '-', ','), ',', ',')
     *       ) > 0
     * ORDER BY
     *     cs.dayOfWeek ASC,
     *     cs.timeSlot ASC;
     */
}




