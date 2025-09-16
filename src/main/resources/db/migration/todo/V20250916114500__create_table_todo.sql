create table if not exists todo (
    id BIGINT auto_increment primary key,
    text varchar(255) null,
    done bit(1) null
);