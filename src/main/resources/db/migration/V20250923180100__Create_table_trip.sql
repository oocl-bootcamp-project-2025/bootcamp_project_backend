-- 创建行程表（trip），关联用户表，存储用户旅行行程信息
CREATE TABLE IF NOT EXISTS `trip` (
                                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '行程表主键ID（自增，唯一标识行程）',
                                      `user_id` BIGINT NOT NULL COMMENT '关联用户表（user）的主键id，标识行程所属用户',
                                      PRIMARY KEY (`id`) COMMENT '行程表主键约束：确保id唯一且非空',
                                      KEY `idx_trip_user_id` (`user_id`) COMMENT '用户ID索引：优化根据user_id查询行程的效率'
) COMMENT = '行程表：存储用户旅行行程信息，与用户表（user）为多对一关系';