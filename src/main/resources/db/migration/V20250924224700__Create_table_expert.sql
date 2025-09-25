create table if not exists expert (
    id BIGINT auto_increment primary key,
    name nvarchar(255) null,
    avatar varchar(255) null,
    location nvarchar(255) null,
    speciality nvarchar(255) null,
    xiaohongshu_url varchar(255) null,
    service_name nvarchar(255) null,
    price double null,
    description nvarchar(255) null
);