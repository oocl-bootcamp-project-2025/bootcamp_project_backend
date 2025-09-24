-- 修改trip表中多个行程字段为允许为空（原配置部分字段为NOT NULL，现统一适配非必填场景）
ALTER TABLE `trip`
    -- 1. 行程名称：允许为空，默认值NULL
    MODIFY COLUMN `name` VARCHAR(100) DEFAULT NULL ,-- COMMENT '行程名称（如“北京5日游”，允许为空）' AFTER `user_id`,
    -- 2. 行程开始信息：允许为空（已单独修改过，此处可保留或重新声明，确保一致性）
    MODIFY COLUMN `start` VARCHAR(100) DEFAULT NULL ,-- COMMENT '行程开始信息（如“2024-10-01 08:00”，允许为空）' AFTER `name`,
    -- 3. 行程描述：保持允许为空（原配置已为DEFAULT NULL，此处可省略，若需统一格式可保留）
    MODIFY COLUMN `description` TEXT DEFAULT NULL ,-- COMMENT '行程详细描述（允许为空）' AFTER `start`,
    -- 4. 行程时长：允许为空，默认值NULL
    MODIFY COLUMN `duration` VARCHAR(50) DEFAULT NULL ,-- COMMENT '行程时长（如“3天2晚”，允许为空）' AFTER `description`,
    -- 5. 行程时间：允许为空，默认值NULL
    MODIFY COLUMN `time` VARCHAR(50) DEFAULT NULL ,-- COMMENT '行程具体时间（如“2024-10-01至2024-10-03”，允许为空）' AFTER `duration`,
    -- 6. 行程地点：允许为空，默认值NULL
    MODIFY COLUMN `location` VARCHAR(100) DEFAULT NULL ,-- COMMENT '行程目的地地点（如“北京”，允许为空）' AFTER `time`,
    -- 7. 行程图片：保持允许为空（原配置已为DEFAULT NULL，此处可省略，若需统一格式可保留）
    MODIFY COLUMN `images` TEXT DEFAULT NULL ,-- COMMENT '行程图片URL（多个URL用英文逗号分隔，允许为空）' AFTER `location`,
    -- 8. 随行达人：保持允许为空（原配置已为DEFAULT NULL，此处可省略，若需统一格式可保留）
    MODIFY COLUMN `experts` VARCHAR(200) DEFAULT NULL; -- COMMENT '随行达人（多个达人ID/名称用英文逗号分隔，允许为空）' AFTER `images`;