CREATE TABLE IF NOT EXISTS `testGM`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(512) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `salt` VARCHAR(32) NOT NULL,
  `fname` VARCHAR(128) NULL,
  `lname` VARCHAR(128) NULL,
  `create_date` DATETIME NOT NULL,
  `is_admin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `testGM`.`pending_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(512) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `salt` VARCHAR(32) NOT NULL,
  `fname` VARCHAR(128) NULL,
  `lname` VARCHAR(128) NULL,
  `create_date` DATETIME NOT NULL,
  `position` TEXT NOT NULL,
  `reason` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;