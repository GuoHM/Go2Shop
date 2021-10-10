-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema productdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema productdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `productdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `productdb` ;

-- -----------------------------------------------------
-- Table `productdb`.`tb_product_review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `productdb`.`tb_product_review` ;

CREATE TABLE IF NOT EXISTS `productdb`.`tb_product_review` (
  `ID` BIGINT NOT NULL,
  `Review` VARCHAR(255) NULL DEFAULT NULL,
  `Rating` INT NOT NULL,
  `TB_PRODUCT_ID` BIGINT NOT NULL,
  `TB_USER_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `TB_PRODUCT_ID`
    FOREIGN KEY (`TB_PRODUCT_ID`)
    REFERENCES `productdb`.`tb_product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `TB_PRODUCT_ID_idx` ON `productdb`.`tb_product_review` (`TB_PRODUCT_ID` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
