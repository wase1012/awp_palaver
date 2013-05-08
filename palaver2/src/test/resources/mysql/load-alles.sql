LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/lieferant.csv'
INTO TABLE lieferant
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/ansprechpartner.csv'
INTO TABLE ansprechpartner
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/kategorie.csv'
INTO TABLE kategorie
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/mengeneinheit.csv'
INTO TABLE mengeneinheit
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/artikel.csv'
INTO TABLE artikel
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

-- Gruppe1

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/fussnote.csv'
INTO TABLE fussnote
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/geschmack.csv'
INTO TABLE geschmack
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/rezeptart.csv'
INTO TABLE rezeptart
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/zubereitung.csv'
INTO TABLE zubereitung
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/mitarbeiter.csv'
INTO TABLE mitarbeiter
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/rollen.csv'
INTO TABLE rollen
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/rezept.csv'
INTO TABLE rezept
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/menue_has_fussnote.csv'
INTO TABLE menue_has_fussnote
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/rezept_has_zubereitung.csv'
INTO TABLE rezept_has_zubereitung
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/mitarbeiter_has_rollen.csv'
INTO TABLE mitarbeiter_has_rollen
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/rezept_has_artikel.csv'
INTO TABLE rezept_has_artikel
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'C:/Users/Android/git/awp_palaver/palaver2/src/test/resources/mysql/menue.csv'
INTO TABLE menue
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES;

-- vergesst nicht den pfad anzupassen