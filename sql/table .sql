-- 创建库
create database if not exists cm_db;

-- 切换库
use cm_db;

# 用户表
create table if not exists user
(
    id           bigint auto_increment primary key,
    userAccount  varchar(256) not null unique comment '账号',
    userPassword varchar(512) not null comment '密码',
    userName     varchar(100) null comment '用户昵称',
    userRole     varchar(256) default 'user' not null comment '用户角色：user/admin',
    studentNum   varchar(50) unique comment '学号', -- 改为引用学号
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint default 0 not null comment '是否删除',
    foreign key (studentNum) references student_info(studentNum) -- 设置外键
) comment '用户表' collate = utf8mb4_unicode_ci;


# 学生信息表
create table if not exists student_info
(
    id          bigint auto_increment primary key,
    studentNum  varchar(50) not null unique comment '学号',
    classId     bigint not null comment '所属班级',
    name VARCHAR(256) NOT NULL COMMENT '姓名',
    gender      varchar(256) null comment '性别: male / female',
    birthDate   datetime null comment '出生日期',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint default 0 not null comment '是否删除',
    foreign key (classId) references classes(id) -- 设置外键
) comment '学生信息表' collate = utf8mb4_unicode_ci;

# 班级表
create table if not exists classes
(
    id          bigint auto_increment primary key,
    className   varchar(256) not null comment '班级名称',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint default 0 not null comment '是否删除'
) comment '班级表' collate = utf8mb4_unicode_ci;

# 班级活动表
create table if not exists class_activities
(
    id           bigint auto_increment primary key,
    activityName varchar(256) not null comment '活动名称',
    activityDate datetime not null comment '活动日期',
    description  text null comment '活动描述',
    classId      bigint not null comment '所属班级',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint default 0 not null comment '是否删除',
    foreign key (classId) references classes(id) -- 设置外键
) comment '班级活动表' collate = utf8mb4_unicode_ci;

# 班级通知表
create table if not exists class_notices
(
    id          bigint auto_increment primary key,
    title       varchar(256) not null comment '通知标题',
    content     text not null comment '通知内容',
    noticeDate  datetime not null comment '通知日期',
    classId     bigint not null comment '所属班级',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint default 0 not null comment '是否删除',
    foreign key (classId) references classes(id) -- 设置外键
) comment '班级通知表' collate = utf8mb4_unicode_ci;

# 班级干部表
create table if not exists class_officers
(
    id          bigint auto_increment primary key,
    userId      bigint not null comment '用户ID',
    classId     bigint not null comment '班级ID',
    position    varchar(100) not null comment '职务',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint default 0 not null comment '是否删除',
    foreign key (userId) references user(id), -- 设置外键
    foreign key (classId) references classes(id) -- 设置外键
) comment '班级干部表' collate = utf8mb4_unicode_ci;

-- 插入班级干部数据

INSERT INTO class_officers (userId, classId, position, createTime, updateTime, isDelete) VALUES
                                                                                             (2, 2, '班长', NOW(), NOW(), 0),  -- 吴耀祖担任班长
                                                                                             (3, 2, '团支书', NOW(), NOW(), 0),  -- 王琦担任团支书
                                                                                             (4, 2, '学习委员', NOW(), NOW(), 0),  -- 苗凯泉担任学习委员
                                                                                             (5, 2, '生活委员', NOW(), NOW(), 0),  -- 李亦然担任生活委员
                                                                                             (6, 2, '班主任', NOW(), NOW(), 0),  -- 高力柯担任班主任
                                                                                             (7, 1, '班长', NOW(), NOW(), 0);   -- 张三担任班长
# 课程信息表
CREATE TABLE IF NOT EXISTS course_info (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     courseName VARCHAR(256) NOT NULL COMMENT '课程名称',
     teacherName VARCHAR(256) NOT NULL COMMENT '教师名称',
     color VARCHAR(7) NOT NULL COMMENT '课程展示颜色',
     createTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
     updateTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT '课程信息表' COLLATE = utf8mb4_unicode_ci;

INSERT INTO course_info (courseName, teacherName, color, createTime, updateTime, isDelete)
VALUES
    ('Mathematics', 'Professor Smith', '#FF5733', NOW(), NOW(), 0),
    ('Physics', 'Professor Johnson', '#33FF57', NOW(), NOW(), 0),
    ('Chemistry', 'Professor Brown', '#3357FF', NOW(), NOW(), 0),
    ('Biology', 'Professor Davis', '#FF33A1', NOW(), NOW(), 0),
    ('History', 'Professor Miller', '#FFC300', NOW(), NOW(), 0)
;


# 课程管理表
CREATE TABLE IF NOT EXISTS course_schedule (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       courseId BIGINT NOT NULL COMMENT '课程ID',
       classId BIGINT NOT NULL COMMENT '班级ID',
       dayOfWeek TINYINT NOT NULL COMMENT '星期几，例如: 1表示周一, 7表示周日',
       timeSlot VARCHAR(50) NOT NULL COMMENT '上课时间段，例如: 第1-2节',
       weeks VARCHAR(50) NOT NULL COMMENT '上课的周次，例如: 3-5周',
       startDate DATE NOT NULL COMMENT '课程开始日期',
       endDate DATE NOT NULL COMMENT '课程结束日期',
       createTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
       updateTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
       FOREIGN KEY (courseId) REFERENCES course_info(id) ON DELETE CASCADE, -- 外键，引用课程信息表
       FOREIGN KEY (classId) REFERENCES classes(id) ON DELETE CASCADE -- 外键，引用班级表
) COMMENT '课程安排表' COLLATE = utf8mb4_unicode_ci;


INSERT INTO course_schedule (courseId, classId, dayOfWeek, timeSlot, weeks, startDate, endDate, createTime, updateTime, isDelete) VALUES
           (1, 1, 1, '第1-2节', '3-5', '2024-09-01', '2024-12-31', NOW(), NOW(), 0),
           (2, 1, 2, '第3-4节', '1-10', '2024-09-01', '2024-12-31', NOW(), NOW(), 0),
           (3, 1, 3, '第5-6节', '6-10', '2024-09-01', '2024-12-31', NOW(), NOW(), 0),
           (1, 2, 4, '第1-2节', '2,4,6', '2024-09-01', '2024-12-31', NOW(), NOW(), 0),
           (2, 2, 5, '第3-4节', '4,7-8', '2024-09-01', '2024-12-31', NOW(), NOW(), 0),
           (3, 2, 6, '第5-6节', '7,9-12', '2024-09-01', '2024-12-31', NOW(), NOW(), 0)
;
