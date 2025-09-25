CREATE TABLE if not exists itinerary (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(255) NULL,
   description VARCHAR(255) NULL,
   phoneNumber VARCHAR(20) NULL,
   startDate DATE NULL,
   allNumber INT NULL,
   itineraryDataId BIGINT NULL
);