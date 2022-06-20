CREATE DATABASE IF NOT EXISTS `mediscreen_mysql_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

### Create Table patient
CREATE TABLE `patient`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `family_name`   varchar(45)  NOT NULL,
    `given_name`    varchar(45)  NOT NULL,
    `date_of_birth` date         NOT NULL,
    `sex`           char(1)      NOT NULL,
    `address`       varchar(100) NOT NULL,
    `phone`         varchar(45)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;