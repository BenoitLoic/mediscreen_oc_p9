CREATE DATABASE `MediscreenSql` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

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
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

# Add Patient
INSERT INTO `MediscreenSql`.`patient` (`family_name`, `given_name`, `date_of_birth`, `sex`, `address`, `phone`)
VALUES ('famillyNameTest3', 'givenNameTest3', '1949-01-01', 'F', '949 mysql address test', '222-303-4444');

# Read patient with familyNameX and givenNameX
SELECT *
FROM `MediscreenSql`.`patient`
where patient.family_name = 'familyNameX' and patient.given_name='givenNameX';

# Update Script
UPDATE `MediscreenSql`.`patient`
SET
    `family_name` = '<{family_name: }>',
    `given_name` = '<{given_name: }>',
    `date_of_birth` = '<{date_of_birth: }>',
    `sex` = '<{sex: }>',
    `address` = '<{address: }>',
    `phone` = '<{phone: }>'
WHERE `id` = '<{expr}>';
