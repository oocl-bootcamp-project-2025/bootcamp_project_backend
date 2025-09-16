create table if not exists employee (
    id BIGINT auto_increment primary key,
    age int null,
    company_id BIGINT null,
    gender varchar(255) null,
    name varchar(255) null,
    salary double null,
    active_status bit(1) null,
    foreign key (company_id) references company (id)
);