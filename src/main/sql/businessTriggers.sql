drop trigger if exists storeBusinessInserts;
drop trigger if exists storeBusinessDeletes;
drop trigger if exists storeBusinessUpdates;


DELIMITER //
create trigger storeBusinessInserts
  after INSERT
  on business
  for each row
BEGIN
		INSERT into business_changes (date, table_changed, change_type, description) values (current_timestamp(), "business", "insert", CONCAT("New business added :", NEW.business_name));
	END//


create trigger storeBusinessDeletes
  after DELETE
  on business
  for each row
  BEGIN
  INSERT into business_changes (date, table_changed, change_type, description) values (CURRENT_TIMESTAMP(), "user", "delete", CONCAT("Business deleted", OLD.business_name));
  END//


create trigger storeBusinessUpdates
  before UPDATE
  on business
  for each row
  BEGIN
  INSERT into business_changes(date, table_changed, change_type, description) values (CURRENT_TIMESTAMP(), "user", "update", CONCAT("businsess updated:",OLD.business_name,"/", OLD.email_address," to: ", NEW.business_name, NEW.email_address));
  END//

DELIMITER ;