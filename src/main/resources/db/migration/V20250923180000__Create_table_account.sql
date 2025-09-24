-- 创建用户表（user），存储用户核心信息，作为行程表关联主表
CREATE TABLE IF NOT EXISTS account (
    id BIGINT NOT NULL AUTO_INCREMENT,
    phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (phone)
);