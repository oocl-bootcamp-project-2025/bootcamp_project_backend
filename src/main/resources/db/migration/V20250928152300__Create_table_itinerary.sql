CREATE TABLE if not exists itinerary (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     title VARCHAR(255) NULL,
     description VARCHAR(255) NULL,
     phone_number VARCHAR(20) NULL,
     start_date VARCHAR(255) NULL,
     all_number INT NULL,
     itinerary_data_id BIGINT NULL
);