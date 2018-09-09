-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forum` DEFAULT CHARACTER SET utf8 ;
USE `forum` ;

-- -----------------------------------------------------
-- Table `forum`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`section` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NULL DEFAULT NULL,
  `password` VARCHAR(60) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`topic` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `views` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `section_id` INT(11) NOT NULL,
  `creation_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NULL DEFAULT NULL,
  `closed` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_topics_users1_idx` (`user_id` ASC),
  INDEX `fk_topics_sections1_idx` (`section_id` ASC),
  CONSTRAINT `fk_topics_sections1`
  FOREIGN KEY (`section_id`)
  REFERENCES `forum`.`section` (`id`),
  CONSTRAINT `fk_topics_users1`
  FOREIGN KEY (`user_id`)
  REFERENCES `forum`.`user` (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` LONGTEXT NOT NULL,
  `user_id` INT(11) NOT NULL,
  `topic_id` INT(11) NOT NULL,
  `creation_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_posts_users_idx` (`user_id` ASC),
  INDEX `fk_posts_topics1_idx` (`topic_id` ASC),
  CONSTRAINT `fk_posts_topics1`
  FOREIGN KEY (`topic_id`)
  REFERENCES `forum`.`topic` (`id`),
  CONSTRAINT `fk_posts_users`
  FOREIGN KEY (`user_id`)
  REFERENCES `forum`.`user` (`id`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `description` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user_has_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user_has_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`role_id` ASC),
  INDEX `fk_users_has_roles_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_has_roles_roles1`
  FOREIGN KEY (`role_id`)
  REFERENCES `forum`.`role` (`id`),
  CONSTRAINT `fk_users_has_roles_users1`
  FOREIGN KEY (`user_id`)
  REFERENCES `forum`.`user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
