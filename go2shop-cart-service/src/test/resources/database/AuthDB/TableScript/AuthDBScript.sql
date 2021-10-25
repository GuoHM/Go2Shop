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
DROP TABLE IF EXISTS `authdb`.`tb_authority` ;

CREATE TABLE IF NOT EXISTS `authdb`.`tb_authority` (
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
DROP TABLE IF EXISTS `authdb`.`tb_security_user` ;

CREATE TABLE IF NOT EXISTS `authdb`.`tb_security_user` (
  `ID` BIGINT NOT NULL  AUTO_INCREMENT,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(100) NOT NULL,
  `USER_ID` BIGINT NOT NULL,
  `ENABLED` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `authdb`.`user_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authdb`.`tb_user_authority` ;

CREATE TABLE IF NOT EXISTS `authdb`.`tb_user_authority` (
  `ID` BIGINT NOT NULL  AUTO_INCREMENT,
  `USER_ID` BIGINT NOT NULL,
  `AUTHORITY_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `authdb`.`tb_user_authority` 
ADD INDEX `user_authority_to_authority_idx` (`AUTHORITY_ID` ASC) VISIBLE,
ADD INDEX `user_authority_to_security_user_idx` (`USER_ID` ASC) VISIBLE;
;
ALTER TABLE `authdb`.`tb_user_authority` 
ADD CONSTRAINT `user_authority_to_authority`
  FOREIGN KEY (`AUTHORITY_ID`)
  REFERENCES `authdb`.`tb_authority` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `user_authority_to_security_user`
  FOREIGN KEY (`USER_ID`)
  REFERENCES `authdb`.`tb_security_user` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `authdb`.`tb_security_user` 
ADD INDEX `username` (`USERNAME` ASC) VISIBLE;
;
ALTER TABLE `authdb`.`tb_security_user` 
ADD UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC) VISIBLE;
;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
