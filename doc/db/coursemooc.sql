# 数据库建立时设定字符集 utf8mb4 建表时再次无需指定

USE coursemooc;

# 测试表
CREATE TABLE `test`(
    `id`   varchar(255) NOT NULL,
    `name` varchar(255) NULL,
    PRIMARY KEY (`id`)
) CHARACTER SET = utf8mb4;

# 课程章节表
DROP table if exists `chapter`;
CREATE TABLE `chapter`(
    `id`        char(8) not null comment '大章ID',
    `course_id` char(8) comment '课程ID',
    `name`      varchar(50) comment '名称',
    primary key (`id`)
) engine = innodb comment = '大章';

# 大章节表测试数据
insert into `chapter` (id, course_id, name) values ('00000000', '00000000', '测试大章一');
insert into `chapter` (id, course_id, name) values ('00000001', '00000000', '测试大章二');
select id, course_id, name from chapter;