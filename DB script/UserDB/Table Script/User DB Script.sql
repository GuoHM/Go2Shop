-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema userdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `userdb` ;
CREATE SCHEMA IF NOT EXISTS `userdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `userdb` ;

-- -----------------------------------------------------
-- Table `userdb`.`tb_user_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `userdb`.`tb_user_type` ;
CREATE TABLE IF NOT EXISTS `userdb`.`tb_user_type` (
  `ID` INT NOT NULL,
  `USER_TYPE` VARCHAR(25) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `userdb`.`tb_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `userdb`.`tb_user` ;
CREATE TABLE IF NOT EXISTS `userdb`.`tb_user` (
  `ID` INT NOT NULL,
  `CARD_NUMBER` VARCHAR(16) NOT NULL,
  `NAME` VARCHAR(50) NOT NULL,
  `EXPIRY` VARCHAR(5) NOT NULL,
  `ADDRESS` VARCHAR(100) NOT NULL,
  `CONTACT_DETAIL` VARCHAR(8) NOT NULL,
  `TB_USER_TYPE_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `TB_USER_TYPE_ID`),
  INDEX `fk_TB_USER_TB_USER_TYPE_idx` (`TB_USER_TYPE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_USER_TB_USER_TYPE`
    FOREIGN KEY (`TB_USER_TYPE_ID`)
    REFERENCES `userdb`.`tb_user_type` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
