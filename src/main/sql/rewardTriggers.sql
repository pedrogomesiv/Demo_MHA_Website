CREATE TABLE reward_changes (redeemReward)
	id int(11) NOT NULL AUTO_INCREMENT,
    date Datetime NOT NULL,
    table_changed varchar(45) NOT NULL,
    r_description varchar(500),
    r_name varchar(500),
    PRIMARY KEY (id)
);

ALTER TABLE reward ADD creator varchar(50);

ALTER TABLE reward_changes ADD changer varchar(50);

##Trigger 1##
DELIMITER $$
DROP TRIGGER IF EXISTS CM6211_1819_TEAM_9.storeRewardChanges$$
USE `CM6211_1819_TEAM_9`$$
CREATE TRIGGER `CM6211_1819_TEAM_9`.`storeRewardChanges` 
AFTER INSERT ON `reward` FOR EACH ROW
BEGIN

	INSERT INTO reward_changes (date, table_changed, r_description, r_name, changer) VALUES (CURRENT_TIMESTAMP(), 
    "reward", CONCAT("NEW reward with desc = ", NEW.reward_description), 
    CONCAT("New reward with name = ", NEW.reward_name), NEW.creator);

END$$
DELIMITER ;

##Changes Proposed by Abbey Ross 11.12.2018##
##Trigger 2##
DELIMITER $$
DROP TRIGGER IF EXISTS CM6211_1819_TEAM_9.storeRewardDeactivations$$
USE `CM6211_1819_TEAM_9`$$
CREATE TRIGGER `CM6211_1819_TEAM_9`.`storeRewardDeactivations` 
AFTER UPDATE ON `reward` FOR EACH ROW
BEGIN

	INSERT INTO reward_changes (date, table_changed, r_description, r_name, changer) VALUES (CURRENT_TIMESTAMP(), 
	"reward", CONCAT("Reward deactivated with desc = ", NEW.reward_description), 
	CONCAT("Reward deactivated with name = ", NEW.reward_name), NEW.creator);

END$$
DELIMITER ;