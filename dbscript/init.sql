-- MySQL Workbench Forward Engineering
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


-- -----------------------------------------------------
-- Schema productdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `productdb` ;
CREATE SCHEMA IF NOT EXISTS `productdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `productdb` ;

-- -----------------------------------------------------
-- Table `productdb`.`tb_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `productdb`.`tb_product` ;
CREATE TABLE IF NOT EXISTS `productdb`.`tb_product` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Description` VARCHAR(255) NULL DEFAULT NULL,
  `Price` DECIMAL(6,0) NOT NULL,
  `Stock` INT NOT NULL,
  `TB_USER_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

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
-- Table `productdb`.`tb_product_images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `productdb`.`tb_product_images` ;

CREATE TABLE IF NOT EXISTS `productdb`.`tb_product_images` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `Product_ID` BIGINT NOT NULL,
  `Url` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `product_images_to_product`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `productdb`.`tb_product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `product_images_to_product_idx` ON `productdb`.`tb_product_images` (`Product_ID` ASC) VISIBLE;



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

-- MySQL Workbench Forward Engineering

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
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `PRICE` DECIMAL(6,0) NULL DEFAULT NULL,
  `DISCOUNT` DECIMAL(3,0) NULL DEFAULT NULL,
  `TB_USER_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `shoppingcartdb`.`tb_shopping_cart_has_tb_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoppingcartdb`.`tb_shopping_cart_has_tb_product` ;
CREATE TABLE IF NOT EXISTS `shoppingcartdb`.`tb_shopping_cart_has_tb_product` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `TB_SHOPPING_CART_ID` BIGINT NOT NULL,
  `TB_PRODUCT_ID` BIGINT NOT NULL,
  `Quantity` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `TB_SHOPPING_CART_ID_idx` (`TB_SHOPPING_CART_ID` ASC) VISIBLE,
  CONSTRAINT `TB_SHOPPING_CART_ID`
    FOREIGN KEY (`TB_SHOPPING_CART_ID`)
    REFERENCES `shoppingcartdb`.`tb_shopping_cart` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


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
-- Table `userdb`.`tb_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `userdb`.`tb_user` ;
CREATE TABLE IF NOT EXISTS `userdb`.`tb_user` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `CARD_NUMBER` VARCHAR(16) NOT NULL,
  `NAME` VARCHAR(50) NOT NULL,
  `EXPIRY` VARCHAR(5) NOT NULL,
  `ADDRESS` VARCHAR(100) NOT NULL,
  `CONTACT_DETAIL` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE authdb;

SET SQL_SAFE_UPDATES = 0;
DELETE FROM authdb.tb_user_authority;
DELETE FROM authdb.tb_authority;
DELETE FROM authdb.tb_security_user;

/*
-- Query: SELECT * FROM authdb.tb_authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:53
*/
INSERT INTO authdb.tb_authority (`ID`,`AUTHORITY_NAME`) VALUES (1,'BUYER');
INSERT INTO authdb.tb_authority (`ID`,`AUTHORITY_NAME`) VALUES (2,'SELLER');

/*
-- Query: SELECT * FROM authdb.tb_security_user
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.tb_security_user (`ID`,`USERNAME`,`PASSWORD`,`USER_ID`,`ENABLED`) VALUES (1,'haoming','$2a$12$65aXCVGoNocErfoa0oGbNuVT6eZIc5L2MANKb3Q2200lHrumUiBH.','1','A');
INSERT INTO authdb.tb_security_user (`ID`,`USERNAME`,`PASSWORD`,`USER_ID`,`ENABLED`) VALUES (2,'go2shop','$2a$12$wc1L3Qkbu7hIK39qQS0nFOzTMz3d3LBX2jVZOu3ciVJ06DUOyC0Xa','2','A');


/*
-- Query: SELECT * FROM authdb.tb_user_authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.tb_user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (1,1,1);
INSERT INTO authdb.tb_user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (2,2,2);

SET SQL_SAFE_UPDATES = 1;

USE productdb;

INSERT INTO `tb_product` VALUES (1,'Crystal','RESTOCKED! Assorted Crystal Tumbles Large Variety (ONE PIECE)',8,10,1),(2,'Samsung Galaxy S21','Samsung Galaxy S21 ǀ S21+ Plus 5G (2021) (Special price on violet )',1000,1,1);

INSERT INTO `tb_product_images` VALUES (1,1,'test_image.jpg'),(2,1,'test_image_2.jpg'),(3,2,'samsung_galaxy_s21.png');


INSERT INTO `tb_product_review` VALUES (1,'Seller respond very quickly. Product looks good',5,1,1),(2,'Prompt replies from seller but not many product varieties. Delivery is quite slow. Take about 7 days to deliver.',4,1,2);


USE shoppingcartdb;
INSERT INTO `tb_shopping_cart` VALUES (2,NULL,NULL,8),(4,NULL,NULL,10);

INSERT INTO `tb_shopping_cart_has_tb_product` VALUES (1,2,1,6),(2,2,2,1),(3,4,2,1),(4,4,1,1);


USE userdb;

INSERT INTO `userdb`.`tb_user` (`ID`, `CARD_NUMBER`, `NAME`, `EXPIRY`, `ADDRESS`, `CONTACT_DETAIL`) VALUES ('1', '1', 'haoming', '1', '1', '1');
INSERT INTO `userdb`.`tb_user` (`ID`, `CARD_NUMBER`, `NAME`, `EXPIRY`, `ADDRESS`, `CONTACT_DETAIL`) VALUES ('2', '2', 'go2shop', '2', '2', '2');

CREATE USER 'go2shop' IDENTIFIED WITH mysql_native_password BY 'go2shop';
GRANT ALL PRIVILEGES ON *.* TO 'go2shop';


