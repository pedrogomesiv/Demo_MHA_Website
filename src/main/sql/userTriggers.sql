drop trigger if exists storeUserInserts;
drop trigger if exists storeUserDeletes;
drop trigger if exists storeUserUpdates;


DELIMITER //
create trigger storeUserInserts
  after INSERT
  on user
  for each row
BEGIN
		INSERT into record_changes (date, table_changed, change_type, description) values (current_timestamp(), "user", "insert", CONCAT("User with email: ", NEW.username, " created"));
	END//


create trigger storeUserDeletes
  after DELETE
  on user
  for each row
  BEGIN
  INSERT into record_changes(date, table_changed, change_type, description) values (CURRENT_TIMESTAMP(), "user", "delete", CONCAT("User deleted for user: ", OLD.username));
  END//


create trigger storeUserUpdates
  before UPDATE
  on user
  for each row
  BEGIN
  INSERT into record_changes(date, table_changed, change_type, description) values (CURRENT_TIMESTAMP(), "user", "update", CONCAT("User updated for user: ", NEW.username));
  END//

DELIMITER ;