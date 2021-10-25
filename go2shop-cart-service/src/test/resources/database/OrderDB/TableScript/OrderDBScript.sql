-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema orderdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `orderdb` ;

-- -----------------------------------------------------
-- Schema orderdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `orderdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `orderdb` ;

-- -----------------------------------------------------
-- Table `orderdb`.`tb_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderdb`.`tb_order` ;

CREATE TABLE IF NOT EXISTS `orderdb`.`tb_order` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `STATUS` VARCHAR(25) NOT NULL,
  `ORDER_DATE` DATETIME NOT NULL,
  `PAYMENT_TYPE` VARCHAR(45) NOT NULL,
  `BUYER_ID` BIGINT NOT NULL,
  `SELLER_ID` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 46
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `orderdb`.`tb_order_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderdb`.`tb_order_detail` ;

CREATE TABLE IF NOT EXISTS `orderdb`.`tb_order_detail` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `ORDER_DATE` DATETIME NOT NULL,
  `ORDER_RECEIVED` DATETIME NULL DEFAULT NULL,
  `PAYMENT_DATE` DATETIME NULL DEFAULT NULL,
  `PAYMENT` DECIMAL(6,0) NULL DEFAULT NULL,
  `PRODUCT_DEDUCT_QTY` INT NOT NULL,
  `TB_ORDER_ID` BIGINT NOT NULL,
  `TB_PRODUCT_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`, `TB_ORDER_ID`, `TB_PRODUCT_ID`),
  CONSTRAINT `fk_TB_ORDER_DETAIL_TB_ORDER1`
    FOREIGN KEY (`TB_ORDER_ID`)
    REFERENCES `orderdb`.`tb_order` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `fk_TB_ORDER_DETAIL_TB_ORDER1_idx` ON `orderdb`.`tb_order_detail` (`TB_ORDER_ID` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
