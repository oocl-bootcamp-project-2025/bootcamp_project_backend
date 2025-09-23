create table if not exists viewpoint (
    id BIGINT auto_increment primary key,
    name varchar(255) null,
    area varchar(255) null,
    coordinate varchar(255) null,
    preference varchar(255) null
);