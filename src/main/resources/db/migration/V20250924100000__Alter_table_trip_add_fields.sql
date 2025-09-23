-- 为trip表新增行程相关字段，适配实体类扩展属性（修复位置冲突和语法错误）
ALTER TABLE `trip`
    -- 1. 行程名称：紧跟user_id后
    ADD COLUMN `name` VARCHAR(100) NOT NULL COMMENT '行程名称（如“北京5日游”）' AFTER `user_id`,
    -- 2. 行程开始信息：紧跟name后（解决位置冲突）
    ADD COLUMN `start` VARCHAR(100) NOT NULL COMMENT '行程开始信息（如“2024-10-01 08:00”）' AFTER `name`,
    -- 3. 行程描述：紧跟start后（顺序逻辑通顺）
    ADD COLUMN `description` TEXT DEFAULT NULL COMMENT '行程详细描述' AFTER `start`,
    -- 4. 行程时长：紧跟description后
    ADD COLUMN `duration` VARCHAR(50) NOT NULL COMMENT '行程时长（如“3天2晚”）' AFTER `description`,
    -- 5. 行程时间：紧跟duration后
    ADD COLUMN `time` VARCHAR(50) NOT NULL COMMENT '行程具体时间（如“2024-10-01至2024-10-03”）' AFTER `duration`,
    -- 6. 行程地点：紧跟time后
    ADD COLUMN `location` VARCHAR(100) NOT NULL COMMENT '行程目的地地点（如“北京”）' AFTER `time`,
    -- 7. 行程图片：紧跟location后
    ADD COLUMN `images` TEXT DEFAULT NULL COMMENT '行程图片URL（多个URL用英文逗号分隔）' AFTER `location`,
    -- 8. 随行达人：紧跟images后（最后一个字段，末尾无逗号）
    ADD COLUMN `experts` VARCHAR(200) DEFAULT NULL COMMENT '随行达人（多个达人ID/名称用英文逗号分隔）' AFTER `images`;