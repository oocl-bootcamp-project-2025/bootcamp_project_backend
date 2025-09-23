-- V20250926100000__Alter_table_trip_add_day_field.sql
ALTER TABLE `trip`
    ADD COLUMN `day` VARCHAR(50) DEFAULT NULL COMMENT '行程天数信息（如"day1"、"day2"）' AFTER `experts`;