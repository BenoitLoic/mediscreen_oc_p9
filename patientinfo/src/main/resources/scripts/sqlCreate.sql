DROP DATABASE MediscreenSql;
CREATE DATABASE `MediscreenSql` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `MediscreenSql`.`patient`
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


INSERT INTO `MediscreenSql`.`patient` (`id`, `family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('1', 'familyNameTest1', 'givenNameTest1', '1987-03-23', 'm', '44 rue', '0123456');
INSERT INTO `MediscreenSql`.`patient` (`id`, `family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('2', 'familyNameTest2', 'givenNameTest2', '1990-03-23', 'F', '44 rue', '0123456');
INSERT INTO `MediscreenSql`.`patient` (`id`, `family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('3', 'familyNameTest3', 'givenNameTest3', '2000-03-23', 'm', '44 rue', '0123456');
INSERT INTO `MediscreenSql`.`patient` (`id`, `family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('4', 'familyNameTest4', 'givenNameTest4', '2010-03-23', 'F', '44 rue', '0123456');
