DROP procedure IF EXISTS deleteBusiness;

DELIMITER //

CREATE PROCEDURE deleteBusiness (businessid INTEGER)
BEGIN
	declare bid int;
    
    set bid = (select id from business where id = businessid);
	
	DELETE from business where id = bid;

END //

DELIMITER ;
