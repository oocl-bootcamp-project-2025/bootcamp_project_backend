create table if not exists attraction (
    id BIGINT auto_increment primary key,
    area nvarchar(255) null,
    name nvarchar(255) null,
    description nvarchar(255) null,
    location nvarchar(255) null,
    longitude double null,
    latitude double null,
    images JSON null,
    duration int null
);