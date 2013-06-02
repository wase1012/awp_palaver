SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `palaver` ;
CREATE SCHEMA IF NOT EXISTS `palaver` DEFAULT CHARACTER SET 'UTF8' COLLATE utf8_bin ;
USE `palaver` ;


-- -----------------------------------------------------
-- Table `palaver`.`regel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`regel` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`regel` (
  id INT NOT NULL AUTO_INCREMENT,
  `zeile` VARCHAR(50) ,
  `spalte` VARCHAR(50)  ,
  `regeltyp` VARCHAR(45) ,
  `operator` VARCHAR(45) ,
  `kriterien` VARCHAR(100) ,
  `fehlermeldung` VARCHAR(100) ,
  `aktiv` BOOLEAN ,
  PRIMARY KEY (id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`mitarbeiter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`mitarbeiter` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`mitarbeiter` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `vorname` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NULL ,
  `passwort` VARCHAR(60) NULL ,
  `eintrittsdatum` VARCHAR(45) NULL ,
  `austrittsdatum` VARCHAR(45) NULL ,
  `benutzername` VARCHAR(30) NOT NULL ,
  PRIMARY KEY (`id`) , 
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`schichtplan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`schichtplan` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`schichtplan` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `mitarbeiter` INT NOT NULL ,
  `datum` DATE NOT NULL ,
  `arbeitsbeginn` TIME NOT NULL ,
  `erbeitsende` TIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_schichtplan_mitarbeiter1_idx` (`mitarbeiter` ASC) ,
  CONSTRAINT `fk_schichtplan_mitarbeiter1`
    FOREIGN KEY (`mitarbeiter` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`rollen`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rollen` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rollen` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,  
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`mitarbeiter_has_rollen`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`mitarbeiter_has_rollen` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`mitarbeiter_has_rollen` (
  `mitarbeiter_fk` INT NOT NULL ,
  `rollen_fk` INT NOT NULL ,
  PRIMARY KEY (`mitarbeiter_fk`, `rollen_fk`) ,
  INDEX `fk_mitarbeiter_has_rollen_rollen1_idx` (`rollen_fk` ASC) ,
  INDEX `fk_mitarbeiter_has_rollen_mitarbeiter_idx` (`mitarbeiter_fk` ASC) ,
  CONSTRAINT `fk_mitarbeiter_has_rollen_mitarbeiter`
    FOREIGN KEY (`mitarbeiter_fk` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mitarbeiter_has_rollen_rollen1`
    FOREIGN KEY (`rollen_fk` )
    REFERENCES `palaver`.`rollen` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`fussnote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`fussnote` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`fussnote` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `abkuerzung` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `palaver`.`geschmack`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`geschmack` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`geschmack` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `inaktiv` BOOLEAN ,
  PRIMARY KEY (`id`) )
 -- UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `palaver`.`zubereitung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`zubereitung` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`zubereitung` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
-- ,
 -- UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `palaver`.`menueart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menueart` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`menueart` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE_menueart` (`name` ASC) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `palaver`.`menue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menue` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`menue` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(200) NOT NULL ,
  `koch` INT NOT NULL ,
  `geschmack_fk` INT NULL ,
  `menueart_fk` INT NOT NULL ,
`aufwand` BOOLEAN NULL ,
 `favorit` BOOLEAN  NULL ,
  PRIMARY KEY (`id`) ,
--  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `fk_menue_mitarbeiter1_idx` (`koch` ASC) ,
   INDEX `fk_geschmack1_idx` (`geschmack_fk` ASC) ,
   CONSTRAINT `fk_geschmack1`
    FOREIGN KEY (`geschmack_fk` )
    REFERENCES `palaver`.`geschmack` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
     CONSTRAINT `fk_menue_menueart12`
    FOREIGN KEY (`menueart_fk` )
    REFERENCES `palaver`.`menueart` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menue_mitarbeiter1`
    FOREIGN KEY (`koch` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`mengeneinheit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`mengeneinheit` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`mengeneinheit` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `kurz` VARCHAR(5) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  UNIQUE INDEX `kurz_UNIQUE` (`kurz` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`zutatenkategorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`zutatenkategorie` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`zutatenkategorie` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`rezeptart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rezeptart` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rezeptart` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;





-- -----------------------------------------------------
-- Table `palaver`.`rezept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rezept` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rezept` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(200) NOT NULL ,
  `rezeptart_fk` INT NOT NULL ,
  `kommentar` VARCHAR(1000) NULL ,
  `portion` INT NOT NULL ,
  `mitarbeiter_fk` INT NULL ,
   `erstellt` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
 -- UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `fk_rezept_rezeptart1_idx` (`rezeptart_fk` ASC) ,
  INDEX `fk_mitarbeiter_idx` (`mitarbeiter_fk` ASC) ,
  CONSTRAINT `fk_rezept_rezeptart1`
    FOREIGN KEY (`rezeptart_fk` )
    REFERENCES `palaver`.`rezeptart` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
   CONSTRAINT `fk_mitarbeiter`
    FOREIGN KEY (`mitarbeiter_fk` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `palaver`.`lieferant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`lieferant` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`lieferant` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `kundennummer` VARCHAR(45) NULL ,
  `bezeichnung` VARCHAR(45) NULL ,
  `strasse` VARCHAR(45) NULL ,
  `plz` VARCHAR(45) NULL ,
  `ort` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  `telefon` VARCHAR(45) NULL ,
  `fax` VARCHAR(45) NULL ,
  `notiz` VARCHAR(300) NULL ,
  `mehrereliefertermine` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`kategorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`kategorie` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`kategorie` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`artikel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`artikel` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`artikel` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `artikelnr` VARCHAR(45) NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `bestellgroesse` DOUBLE NULL ,
  `mengeneinheit_fk` INT NOT NULL ,
  `preis` FLOAT NULL ,
  `lieferant_fk` INT NOT NULL ,
  `bio` TINYINT(1) NOT NULL ,
  `kategorie_fk` INT NOT NULL ,
  `standard` TINYINT(1) NOT NULL ,
  `grundbedarf` TINYINT(1) NOT NULL ,
  `durchschnitt` INT NULL ,
  `lebensmittel` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `LIEFER_FK_idx` (`lieferant_fk` ASC) ,
  INDEX `KATEGOIRE_FK_idx` (`kategorie_fk` ASC) ,
  INDEX `fk_ARTIKEL_mengeneinheit1_idx` (`mengeneinheit_fk` ASC) ,
  CONSTRAINT `LIEFER_FK`
    FOREIGN KEY (`lieferant_fk` )
    REFERENCES `palaver`.`lieferant` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `KATEGOIRE_FK`
    FOREIGN KEY (`kategorie_fk` )
    REFERENCES `palaver`.`kategorie` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ARTIKEL_mengeneinheit1`
    FOREIGN KEY (`mengeneinheit_fk` )
    REFERENCES `palaver`.`mengeneinheit` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`rezept_has_artikel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rezept_has_artikel` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rezept_has_artikel` (
  `rezept_fk` INT NOT NULL ,
  `artikel_fk` INT NOT NULL ,
  `menge` DECIMAL(10,4) NOT NULL ,
  `einheit` INT NOT NULL ,
  `zutatenkategorie` INT NOT NULL ,
  INDEX `fk_rezept_has_artikel_einheit1_idx` (`einheit` ASC) ,
  INDEX `fk_rezept_has_artikel_zutatenkategorie1_idx` (`zutatenkategorie` ASC) ,
  INDEX `fk_rezept_has_artikel_rezept1_idx` (`rezept_fk` ASC) ,
  INDEX `fk_rezept_has_artikel_ARTIKEL11_idx` (`artikel_fk` ASC) ,
  PRIMARY KEY (`rezept_fk`, `artikel_fk`) ,
  CONSTRAINT `fk_rezept_has_artikel_rezept1`
    FOREIGN KEY (`rezept_fk` )
    REFERENCES `palaver`.`rezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rezept_has_artikel_einheit1`
    FOREIGN KEY (`einheit` )
    REFERENCES `palaver`.`mengeneinheit` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rezept_has_artikel_zutatenkategorie1`
    FOREIGN KEY (`zutatenkategorie` )
    REFERENCES `palaver`.`zutatenkategorie` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rezept_has_artikel_ARTIKEL11`
    FOREIGN KEY (`artikel_fk` )
    REFERENCES `palaver`.`artikel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`menue_has_rezept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menue_has_rezept` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`menue_has_rezept` (
  `menue_id` INT NOT NULL ,
  `rezept_id` INT NOT NULL ,
  `hauptgericht` boolean,
  PRIMARY KEY (`menue_id`, `rezept_id`) ,
  INDEX `fk_menue_has_rezept_rezept1_idx` (`rezept_id` ASC) ,
  INDEX `fk_menue_has_rezept_menue1_idx` (`menue_id` ASC) ,
  CONSTRAINT `fk_menue_has_rezept_menue1`
    FOREIGN KEY (`menue_id` )
    REFERENCES `palaver`.`menue` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menue_has_rezept_rezept1`
    FOREIGN KEY (`rezept_id` )
    REFERENCES `palaver`.`rezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`menueplan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menueplan` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`menueplan` (
  id INT NOT NULL AUTO_INCREMENT,
  `week` INT NOT NULL ,
  `year` INT NOT NULL ,
  PRIMARY KEY (id))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `palaver`.`rezept_has_fussnote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rezept_has_fussnote` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rezept_has_fussnote` (
  `rezept_id` INT NOT NULL ,
  `fussnote_fk` INT NOT NULL ,
  PRIMARY KEY (`rezept_id`, `fussnote_fk`) ,
  INDEX `fk_rezept_has_rezepteigenschaften_rezepteigenschaften1_idx` (`fussnote_fk` ASC) ,
  INDEX `fk_rezept_has_rezepteigenschaften_rezept1_idx` (`rezept_id` ASC) ,
  CONSTRAINT `fk_rezept_has_rezepteigenschaften_rezept1`
    FOREIGN KEY (`rezept_id` )
    REFERENCES `palaver`.`rezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rezept_has_rezepteigenschaften_rezepteigenschaften1`
    FOREIGN KEY (`fussnote_fk` )
    REFERENCES `palaver`.`fussnote` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`bestellung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`bestellung` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`bestellung` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `lieferant_fk` INT NOT NULL ,
  `datum` DATE NOT NULL ,
  `lieferdatum` DATE NULL ,
  `lieferdatum2` DATE NULL,
  `bestellt` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`) ,
  INDEX `LIEFERANT_FK_idx` (`lieferant_fk` ASC) ,
  CONSTRAINT `LIEFERANT_FK`
    FOREIGN KEY (`lieferant_fk` )
    REFERENCES `palaver`.`lieferant` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`bestellposition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`bestellposition` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`bestellposition` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `artikel_fk` INT NOT NULL ,
  `bestellung_fk` INT NOT NULL ,
  `durchschnitt` INT NULL ,
  `kantine` INT NULL ,
  `gesamt` INT NULL ,
  `freitag` INT NULL ,
  `montag` INT NULL ,
  `geliefert` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`) ,
  INDEX `ARTIKEL_FK_idx` (`artikel_fk` ASC) ,
  INDEX `BESTELL_FK_idx` (`bestellung_fk` ASC) ,
  CONSTRAINT `ARTIKEL_FK`
    FOREIGN KEY (`artikel_fk` )
    REFERENCES `palaver`.`artikel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BESTELL_FK`
    FOREIGN KEY (`bestellung_fk` )
    REFERENCES `palaver`.`bestellung` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`ansprechpartner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`ansprechpartner` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`ansprechpartner` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `telefon` VARCHAR(45) NULL ,
  `handy` VARCHAR(45) NULL ,
  `fax` VARCHAR(45) NULL ,
  `lieferant_fk` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_ansprechpartner_lieferant1_idx` (`lieferant_fk` ASC) ,
  CONSTRAINT `fk_ansprechpartner_lieferant1`
    FOREIGN KEY (`lieferant_fk` )
    REFERENCES `palaver`.`lieferant` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`nachrichten`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`nachrichten` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`nachrichten` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nachricht` VARCHAR(300) NULL ,
  `sender_fk` INT NOT NULL ,
  `empf_rolle_fk` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `sender_fk_idx` (`sender_fk` ASC) ,
  INDEX `empfaenger_fk_idx` (`empf_rolle_fk` ASC) ,
  CONSTRAINT `sender_fk`
    FOREIGN KEY (`sender_fk` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `empf_rolle_fk`
    FOREIGN KEY (`empf_rolle_fk` )
    REFERENCES `palaver`.`rollen` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`rezept_has_zubereitung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`rezept_has_zubereitung` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`rezept_has_zubereitung` (
  `rezept_fk` INT NOT NULL ,
  `zubereitung_fk` INT NOT NULL ,
  PRIMARY KEY (`rezept_fk`, `zubereitung_fk` ) ,
  INDEX `fk_zubereitung_idx` (`zubereitung_fk` ASC) ,
  CONSTRAINT `fk_rezept`
    FOREIGN KEY (`rezept_fk` )
    REFERENCES `palaver`.`rezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zubereitung`
    FOREIGN KEY (`zubereitung_fk` )
    REFERENCES `palaver`.`zubereitung` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------

-- Table `palaver`.`rezept_has_fussnote`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `palaver`.`menue_has_fussnote` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`menue_has_fussnote` (
  `menue_fk` INT NOT NULL,
  `fussnote_fk` INT NOT NULL ,
  INDEX `fk_menue_has_fussnote_menue1_idx` (`menue_fk` ASC) ,
  INDEX `fk_menue_has_fussnote_fussnote1_idx` (`fussnote_fk` ASC) ,
  CONSTRAINT `fk_menue_has_fussnote_menue1`
    FOREIGN KEY (`menue_fk` )
    REFERENCES `palaver`.`menue` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menue_has_fussnote_fussnote1`
    FOREIGN KEY (`fussnote_fk` )
    REFERENCES `palaver`.`fussnote` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------

-- Table `palaver`.`menueplan_has_koeche`

-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menueplan_has_koeche` ;

CREATE TABLE IF NOT EXISTS `palaver`.`menueplan_has_koeche` (
	id INT NOT NULL AUTO_INCREMENT,
	menueplan INT NOT NULL,
	spalte INT NOT NULL,
	koch1 INT,
	koch2 INT,
	PRIMARY KEY (id),
	CONSTRAINT `fk_menueplan_has_koch_menue`
    FOREIGN KEY (`menueplan` )
    REFERENCES `palaver`.`menueplan` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menueplan_has_koch_koch1`
    FOREIGN KEY (`koch1` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
	ENGINE = InnoDB;


-- -----------------------------------------------------

-- Table `palaver`.`menueplan_has_menues`

-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`menueplan_has_menues` ;

CREATE TABLE IF NOT EXISTS `palaver`.`menueplan_has_menues` (
	id INT NOT NULL AUTO_INCREMENT,
	menueplan INT NOT NULL,
	menue INT NOT NULL,
	spalte INT NOT NULL,
	zeile INT NOT NULL,
	PRIMARY KEY (id),
  CONSTRAINT `fk_menueplan_has_menue_menueplan`
    FOREIGN KEY (`menueplan` )
    REFERENCES `palaver`.`menueplan` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION  ,
  CONSTRAINT `fk_menueplan_has_menue_menue`
	FOREIGN KEY (`menue` )
    REFERENCES `palaver`.`menue` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    )
	ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `palaver`.`kuchenrezept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`kuchenrezept` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`kuchenrezept` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(200) NOT NULL ,
  `kommentar` VARCHAR(1000) NULL ,
  `mitarbeiter_fk` INT NULL ,
  `erstellt` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
 -- UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `fk_mitarbeiter_kuchen_idx` (`mitarbeiter_fk` ASC) ,
  CONSTRAINT `fk_mitarbeiter_kuchen`
    FOREIGN KEY (`mitarbeiter_fk` )
    REFERENCES `palaver`.`mitarbeiter` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`kuchenrezept_has_artikel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`kuchenrezept_has_artikel` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`kuchenrezept_has_artikel` (
  `kuchenrezept_fk` INT NOT NULL AUTO_INCREMENT ,
  `artikel_fk` INT NOT NULL ,
  `menge` DECIMAL(10,4) NOT NULL ,
  `einheit` INT NOT NULL ,
  INDEX `fk_kuchenrezept_has_artikel_einheit_idx` (`einheit` ASC) ,
  INDEX `fk_kuchenrezept_has_artikel_rezept_idx` (`kuchenrezept_fk` ASC) ,
  INDEX `fk_kuchenrezept_has_artikel_ARTIKEL_idx` (`artikel_fk` ASC) ,
  PRIMARY KEY (`kuchenrezept_fk`, `artikel_fk`) ,
  CONSTRAINT `fk_kuchenrezept_has_artikel_rezept`
    FOREIGN KEY (`kuchenrezept_fk` )
    REFERENCES `palaver`.`kuchenrezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenrezept_has_artikel_einheit`
    FOREIGN KEY (`einheit` )
    REFERENCES `palaver`.`mengeneinheit` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenrezept_has_artikel_ARTIKEL`
    FOREIGN KEY (`artikel_fk` )
    REFERENCES `palaver`.`artikel` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`kuchenplan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`kuchenplan` ;

CREATE  TABLE IF NOT EXISTS `palaver`.`kuchenplan` (
  id INT NOT NULL AUTO_INCREMENT,
  `week` INT NOT NULL ,
  `year` INT NOT NULL ,
  PRIMARY KEY (id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `palaver`.`kuchenplan_has_kuchenrezepte`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `palaver`.`kuchenplan_has_kuchenrezepte` ;

CREATE TABLE IF NOT EXISTS `palaver`.`kuchenplan_has_kuchenrezepte` (
	id INT NOT NULL AUTO_INCREMENT,
	kuchenplan_fk INT NOT NULL,
	kuchenrezept_fk INT NOT NULL,
	spalte INT NOT NULL,
	PRIMARY KEY (id),
  CONSTRAINT `fk_kuchenplan_has_kuchenrezepte_kuchenplan`
    FOREIGN KEY (`kuchenplan_fk` )
    REFERENCES `palaver`.`kuchenplan` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION  ,
  CONSTRAINT `fk_kuchenplan_has_kuchenrezepte_kuchenrezept`
	FOREIGN KEY (`kuchenrezept_fk` )
    REFERENCES `palaver`.`kuchenrezept` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    )
	ENGINE = InnoDB;


USE `palaver` ;


USE `palaver` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
