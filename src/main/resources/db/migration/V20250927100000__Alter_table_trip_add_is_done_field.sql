-- 为行程表新增is_done字段（标识行程是否完成），放在day字段之后
ALTER TABLE trip
    ADD COLUMN is_done BOOLEAN DEFAULT FALSE;