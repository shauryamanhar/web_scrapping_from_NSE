drop table details;
drop table stocks;



CREATE TABLE IF NOT EXISTS `stock`.`stocks` (
  `id` int not null AUTO_INCREMENT UNIQUE,
  `uid` VARCHAR(20) NOT NULL,
  `symbol` VARCHAR(100) NOT NULL ,
  `company_name` VARCHAR(200) NOT NULL,
  `country` VARCHAR(100) NULL DEFAULT "US",
  PRIMARY KEY (`uid`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `stock`.`details` (
  `uid` VARCHAR(20) NOT NULL,
  `high` VARCHAR(45) NOT NULL,
  `low` VARCHAR(45) NOT NULL,
  `open` VARCHAR(45) NOT NULL,
  `close` VARCHAR(45) NOT NULL,
  `time` VARCHAR(45) NOT NULL,
  `volume` VARCHAR(45) NOT NULL,
  INDEX `id_idx` (`uid` ASC),
  CONSTRAINT `uid`
    FOREIGN KEY (`uid`)
    REFERENCES `stock`.`stocks` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

drop table details;
drop table stocks;
