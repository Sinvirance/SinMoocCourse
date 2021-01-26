# 数据库建立时设定字符集 utf8mb4 建表时再次无需指定

USE coursemooc;

# 测试表
CREATE TABLE `test`(
    `id`   varchar(255) NOT NULL,
    `name` varchar(255) NULL,
    PRIMARY KEY (`id`)
) CHARACTER SET = utf8mb4;

# 大章表
DROP table if exists `chapter`;
CREATE TABLE `chapter`(
    `id`        char(8) not null comment '大章ID',
    `course_id` char(8) comment '课程ID',
    `name`      varchar(50) comment '名称',
    primary key (`id`)
) engine = innodb comment = '大章';

# 大章表测试数据
insert into `chapter` (id, course_id, name) values ('00000001', '00000000', '测试大章01');
insert into `chapter` (id, course_id, name) values ('00000002', '00000000', '测试大章02');
insert into `chapter` (id, course_id, name) values ('00000003', '00000000', '测试大章03');
insert into `chapter` (id, course_id, name) values ('00000004', '00000000', '测试大章04');
insert into `chapter` (id, course_id, name) values ('00000005', '00000000', '测试大章05');
insert into `chapter` (id, course_id, name) values ('00000006', '00000000', '测试大章06');
insert into `chapter` (id, course_id, name) values ('00000007', '00000000', '测试大章07');
insert into `chapter` (id, course_id, name) values ('00000008', '00000000', '测试大章08');
insert into `chapter` (id, course_id, name) values ('00000009', '00000000', '测试大章09');
insert into `chapter` (id, course_id, name) values ('00000010', '00000000', '测试大章10');
insert into `chapter` (id, course_id, name) values ('00000011', '00000000', '测试大章11');
insert into `chapter` (id, course_id, name) values ('00000012', '00000000', '测试大章12');
insert into `chapter` (id, course_id, name) values ('00000013', '00000000', '测试大章13');
insert into `chapter` (id, course_id, name) values ('00000014', '00000000', '测试大章14');
insert into `chapter` (id, course_id, name) values ('00000015', '00000000', '测试大章15');
insert into `chapter` (id, course_id, name) values ('00000016', '00000000', '测试大章16');
insert into `chapter` (id, course_id, name) values ('00000017', '00000000', '测试大章17');
insert into `chapter` (id, course_id, name) values ('00000018', '00000000', '测试大章18');
insert into `chapter` (id, course_id, name) values ('00000019', '00000000', '测试大章19');
insert into `chapter` (id, course_id, name) values ('00000020', '00000000', '测试大章20');
select id, course_id, name from chapter;

-- 小节表
DROP table if exists `section`;
CREATE TABLE `section` (
    `id`         char(8)     not null default '' comment '小节Id',
    `title`      varchar(50) not null comment '标题',
    `course_id`  char(8) comment '课程|course.id',
    `chapter_id` char(8) comment '大章|chapter.id',
    `video`      varchar(200) comment '视频地址',
    `time`       int comment '时长|单位秒(s)',
    `charge`     char(1) comment '收费|C:收费;F:免费',
    `sort`       int comment '顺序',
    `created_at` datetime(3) comment '创建时间',
    `updated_at` datetime(3) comment '修改时间',
    primary key (`id`)
) engine = innodb comment = '小节';
-- 小节表测试数据
insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
VALUES ('00000001','测试小节01', '00000001','00000000','', 500, 'F', 1, now(), now());
select id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at from section;
