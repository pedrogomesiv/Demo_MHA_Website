##Changes Proposed by Abbey Ross 09.12.2018##
USE CM6211_1819_TEAM_9;

##Changes Proposed by Abbey Ross 11.12.2018##
ALTER TABLE reward
ADD active varchar(10);


DROP procedure IF EXISTS deleteReward;

DELIMITER $$

CREATE PROCEDURE deleteReward (IN rid int)
BEGIN

	UPDATE reward
    SET active = "no"
    WHERE id = rid;
    
    IF (SELECT active FROM reward WHERE id = rid) = "no" THEN
		SET rid = 0;
	ELSE
		SET rid = 1;
	END IF;

END $$

DELIMITER ;