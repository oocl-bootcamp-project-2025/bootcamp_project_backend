-- 创建行程表（trip），关联用户表，存储用户旅行行程信息
CREATE TABLE IF NOT EXISTS "trip" (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);