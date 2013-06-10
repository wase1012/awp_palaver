ALTER TABLE `palaver`.`regel` 
	CHANGE COLUMN `zeile` `zeile` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `spalte` `spalte` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `regeltyp` `regeltyp` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `operator` `operator` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `kriterien` `kriterien` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `fehlermeldung` `fehlermeldung` VARCHAR(500) NULL DEFAULT NULL;