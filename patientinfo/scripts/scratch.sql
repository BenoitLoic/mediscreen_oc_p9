CREATE DATABASE `MediscreenSql` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

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
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `MediscreenSql`.`patient` (`family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('famillyNameTest3', 'givenNameTest3', '1949-01-01', 'F', '949 mysql address test', '222-303-4444');


SELECT `patient`.`id`,
       `patient`.`family_name`,
       `patient`.`given_name`,
       `patient`.`date_of_birth`,
       `patient`.`sex`,
       `patient`.`address`,
       `patient`.`phone`
FROM `MediscreenSql`.`patient`;

SELECT *
FROM `MediscreenSql`.`patient`
where patient.family_name = 'familyNameX' and patient.given_name='givenNameX';


