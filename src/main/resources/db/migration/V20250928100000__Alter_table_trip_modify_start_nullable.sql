-- 修改trip表的start字段，允许为空（原配置为NOT NULL，现调整为DEFAULT NULL）
ALTER TABLE `trip`
    MODIFY COLUMN `start` VARCHAR(100) DEFAULT NULL COMMENT '行程开始信息（如“2024-10-01 08:00”，允许为空）' AFTER `name`;