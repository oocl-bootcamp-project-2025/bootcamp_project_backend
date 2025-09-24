create table attraction (
    id BIGINT auto_increment primary key,
    area nvarchar(255) null,
    name nvarchar(255) null,
    description nvarchar(255) null,
    location nvarchar(255) null,
    longitude double null,
    latitude double null,
    images varchar(255) null,
    duration int null
);