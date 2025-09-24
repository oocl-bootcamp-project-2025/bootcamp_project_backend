-- 为用户表新增密码字段，用于存储用户登录密码
ALTER TABLE account
    ADD COLUMN password VARCHAR(100) NOT NULL;