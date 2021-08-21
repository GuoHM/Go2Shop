-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema authdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `authdb` ;

-- -----------------------------------------------------
-- Schema authdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `authdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `authdb` ;

-- -----------------------------------------------------
-- Table `authdb`.`authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authdb`.`authority` ;

CREATE TABLE IF NOT EXISTS `authdb`.`authority` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `AUTHORITY_NAME` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `authdb`.`security_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authdb`.`security_user` ;

CREATE TABLE IF NOT EXISTS `authdb`.`security_user` (
  `ID` BIGINT NOT NULL,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(100) NOT NULL,
  `ENABLED` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `authdb`.`user_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authdb`.`user_authority` ;

CREATE TABLE IF NOT EXISTS `authdb`.`user_authority` (
  `ID` BIGINT NOT NULL,
  `USER_ID` BIGINT NOT NULL,
  `AUTHORITY_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
