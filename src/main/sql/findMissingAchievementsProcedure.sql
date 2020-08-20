drop procedure if exists getMissingAchievements;

DELIMITER //

create procedure getMissingAchievements(uid INTEGER)
BEGIN
select * from achievement where credits <= (select total_amount from user_volunteer where user_id = uid) and id not in (select achievement_id from volunteer_achievement where user_id = uid);
END //

DELIMITER ;