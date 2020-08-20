DROP procedure IF EXISTS redeemReward;

DELIMITER //

CREATE PROCEDURE redeemReward (uid Integer, rid Integer)
BEGIN
	UPDATE user_volunteer SET current_balance = current_balance - (SELECT cost from reward where id = rid) where user_id = uid;
	INSERT into redemption(user_id, reward_id, redemption_date) values(uid, rid, CURRENT_TIMESTAMP());
END //

DELIMITER ;