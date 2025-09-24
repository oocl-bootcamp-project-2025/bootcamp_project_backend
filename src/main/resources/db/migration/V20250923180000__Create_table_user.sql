CREATE TABLE "user" (  -- Use double quotes for H2
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        phone VARCHAR(20) NOT NULL,
                        PRIMARY KEY (id),
                        UNIQUE (phone)
);