/*
 * Erstellt von Eike Becher am 10.06.2013
 * In DB ausgeführt: 
 */
ALTER TABLE `palaver`.`regel` 
	CHANGE COLUMN `zeile` `zeile` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `spalte` `spalte` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `regeltyp` `regeltyp` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `operator` `operator` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `kriterien` `kriterien` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `fehlermeldung` `fehlermeldung` VARCHAR(500) NULL DEFAULT NULL;
	
/*
 * Erstellt von Eike Becher am 10.06.2013
 * In DB ausgeführt: 
 */
ALTER TABLE `palaver`.`menueplan_has_menues` 
	ADD COLUMN `portion` INT(11) NOT NULL  AFTER `zeile` ;
	
ALTER TABLE `palaver`.`menueplan` 
	ADD COLUMN `freigegeben` TINYINT(1) NULL  AFTER `year` ;