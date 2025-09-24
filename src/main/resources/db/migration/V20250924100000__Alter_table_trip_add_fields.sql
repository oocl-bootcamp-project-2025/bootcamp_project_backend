-- 为trip表新增行程相关字段，适配实体类扩展属性
ALTER TABLE "trip"
    ADD COLUMN "name" VARCHAR(100) NOT NULL, -- 行程名称（如“北京5日游”）
    ADD COLUMN "start" VARCHAR(100) NOT NULL, -- 行程开始信息（如“2024-10-01 08:00”）
    ADD COLUMN "description" TEXT DEFAULT NULL, -- 行程详细描述
    ADD COLUMN "duration" VARCHAR(50) NOT NULL, -- 行程时长（如“3天2晚”）
    ADD COLUMN "time" VARCHAR(50) NOT NULL, -- 行程具体时间（如“2024-10-01至2024-10-03”）
    ADD COLUMN "location" VARCHAR(100) NOT NULL, -- 行程目的地地点（如“北京”）
    ADD COLUMN "images" TEXT DEFAULT NULL, -- 行程图片URL（多个URL用英文逗号分隔）
    ADD COLUMN "experts" VARCHAR(200) DEFAULT NULL; -- 随行达人
