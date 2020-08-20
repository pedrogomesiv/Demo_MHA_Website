DROP procedure if exists giveAchievement;

DELIMITER //

CREATE procedure giveAchievement(uid INTEGER, aid INTEGER)
BEGIN
	INSERT into volunteer_achievement (user_id, achievement_id, date) values (uid, aid, CURRENT_TIMESTAMP());
END //

DELIMITER ;