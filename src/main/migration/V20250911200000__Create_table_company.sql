CREATE TABLE company
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id            BIGINT AUTO_INCREMENT NOT NULL COMMENT 'employee id',
    name          VARCHAR(255) NULL COMMENT 'employee name',
    age           INT NULL COMMENT 'employee age',
    gender        VARCHAR(255) NULL COMMENT 'employee gender',
    salary        FLOAT NULL COMMENT 'employee salary',
    company_id    BIGINT NULL COMMENT 'company id',
    active_status BIT(1) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
) COMMENT ='employee table';

ALTER TABLE employee
    ADD CONSTRAINT FK5v50ed2bjh60n1gc7ifuxmgf4 FOREIGN KEY (company_id) REFERENCES company (id) ON DELETE NO ACTION;

CREATE INDEX FK5v50ed2bjh60n1gc7ifuxmgf4 ON employee (company_id);
ALTER TABLE employee
    MODIFY age INT NOT NULL;

ALTER TABLE employee
    DROP COLUMN salary;

ALTER TABLE employee
    ADD salary DOUBLE NOT NULL;

ALTER TABLE employee
    MODIFY salary DOUBLE NOT NULL;