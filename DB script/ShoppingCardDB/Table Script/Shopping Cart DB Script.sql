-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema shoppingcartdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `shoppingcartdb` ;
CREATE SCHEMA IF NOT EXISTS `shoppingcartdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `shoppingcartdb` ;

-- -----------------------------------------------------
-- Table `shoppingcartdb`.`tb_shopping_cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoppingcartdb`.`tb_shopping_cart` ;
CREATE TABLE IF NOT EXISTS `shoppingcartdb`.`tb_shopping_cart` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PRICE` DECIMAL(6,0) NULL DEFAULT NULL,
  `DISCOUNT` DECIMAL(3,0) NULL DEFAULT NULL,
  `TB_USER_ID` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `shoppingcartdb`.`tb_shopping_cart_has_tb_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoppingcartdb`.`tb_shopping_cart_has_tb_product` ;
CREATE TABLE IF NOT EXISTS `shoppingcartdb`.`tb_shopping_cart_has_tb_product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TB_SHOPPING_CART_ID` INT NOT NULL,
  `TB_PRODUCT_ID` INT NOT NULL,
  `Quantity` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `TB_SHOPPING_CART_ID_idx` (`TB_SHOPPING_CART_ID` ASC) VISIBLE,
  CONSTRAINT `TB_SHOPPING_CART_ID`
    FOREIGN KEY (`TB_SHOPPING_CART_ID`)
    REFERENCES `shoppingcartdb`.`tb_shopping_cart` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
