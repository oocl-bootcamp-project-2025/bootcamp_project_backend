-- 修改trip表的start字段，允许为空（原配置为NOT NULL，现调整为DEFAULT NULL）
ALTER TABLE "trip"
    ALTER COLUMN start VARCHAR(100) DEFAULT NULL;