-- 为行程表新增is_done字段（标识行程是否完成），放在day字段之后
ALTER TABLE `trip`
    ADD COLUMN `is_done` TINYINT(1) DEFAULT 0 COMMENT '行程是否完成（0=未完成，1=已完成）' AFTER `day`;