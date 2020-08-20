##Changes Proposed by Abbey Ross 03.12.2018##
CREATE TABLE reward_changes (
	id int(11) NOT NULL AUTO_INCREMENT,
    date Datetime NOT NULL,
    table_changed varchar(45) NOT NULL,
    r_description varchar(500),
    r_name varchar(500),
    PRIMARY KEY (id)
);

##Trigger 1##
DELIMITER $$
DROP TRIGGER IF EXISTS CM6211_1819_TEAM_9.storeRewardChanges$$
USE `CM6211_1819_TEAM_9`$$
CREATE TRIGGER `CM6211_1819_TEAM_9`.`storeRewardChanges` 
AFTER INSERT ON `reward` FOR EACH ROW
BEGIN

	INSERT INTO reward_changes (date, table_changed, r_description, r_name) VALUES (CURRENT_TIMESTAMP(), "reward", CONCAT("NEW reward with desc = ", NEW.reward_description), CONCAT("New reward with name = ", NEW.reward_name));

END$$
DELIMITER ;