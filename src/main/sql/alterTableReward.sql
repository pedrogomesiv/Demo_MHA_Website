##Changes Proposed by Abbey Ross 06.12.2018##

##Adding column to reward table to keep a track of the 
##number of times each reward has been redeemed, ever.
ALTER TABLE reward 
ADD num_times_redeemed int;

DROP PROCEDURE IF EXISTS increaseRewardsNumTimesRedeemed;

DELIMITER $$

CREATE PROCEDURE increaseRewardsNumTimesRedeemed(rewards_id int)
BEGIN
	DECLARE AddOne int;
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK;
			SELECT "Transaction cancelled because an SQL execption occured." as Message;
		END;

	START TRANSACTION;
		
	IF (SELECT id from reward WHERE id = rewards_id) IS NOT NULL THEN
		SET @AddOne = 1;
        
        IF ((SELECT num_times_redeemed from reward WHERE id = rewards_id) IS NOT NULL) = TRUE THEN
			UPDATE reward
			SET num_times_redeemed = num_times_redeemed + @AddOne
			WHERE rewards_id = id;
		ELSE
			UPDATE reward
			SET num_times_redeemed = 1
			WHERE rewards_id = id;
		END IF;
	END IF;
    
    COMMIT;
    
    SELECT "Reward Redemptions for: id=", r.id, ", name=", r.reward_name, ", was incremented successfully." as Message;
    
END $$increaseRewardsNumTimesRedeemed

DELIMITER ;