DROP procedure IF EXISTS deleteUser;

DELIMITER //

CREATE PROCEDURE deleteUser (uid Integer)
BEGIN
	DELETE from address where user_id = uid;
	DELETE from user_volunteer where user_id = uid;
    DELETE from user_role where userid = uid;
    DELETE from allocation where user_id = uid;
    DELETE from redemption where user_id = uid;
    DELETE from user_volunteer where user_id = uid;
    DELETE from user where id = uid;

END //

DELIMITER ;