-- 为用户表新增密码字段，用于存储用户登录密码
ALTER TABLE "user"
    ADD COLUMN `password` VARCHAR(100) NOT NULL; -- COMMENT '用户登录密码（建议存储加密后的密码，如BCrypt加密结果）' AFTER `phone`;