-- 创建用户表（user），存储用户核心信息，作为行程表关联主表
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户表主键ID（自增，唯一标识用户）',
                                      `phone` VARCHAR(20) NOT NULL COMMENT '用户手机号（用于登录/身份验证，非空且唯一）',
                                      PRIMARY KEY (`id`) COMMENT '用户表主键约束：确保id唯一且非空',
                                      UNIQUE KEY `uk_user_phone` (`phone`) COMMENT '手机号唯一约束：防止重复注册同一手机号'
) COMMENT = '用户表：存储用户基础信息，与行程表（trip）为一对多关系';