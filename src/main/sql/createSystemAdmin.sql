##Changes Proposed by Abbey Ross 10.12.2018##

DROP PROCEDURE IF EXISTS createSystemAdministrator;

DELIMITER $$

CREATE PROCEDURE createSystemAdministrator(email varchar(255), encpassword varchar(255), firstName varchar(255), surName varchar(255), pNumber varchar(15), eNumber varchar(15), mhaTenant int, previousVolunteer int, volunteerExperience varchar(255), conditions int, conditionDetails varchar(255), medication int, medicationDetails varchar(255), allerigies int, allergyDetails varchar(255), addressLine1 varchar(255), addressLine2 varchar(255), City varchar(255), postCode varchar(255))
BEGIN
	DECLARE new_id INT;
    
	INSERT into user(username, password) values(email, encpassword);
    
	SET new_id = (SELECT id FROM user WHERE username = email);
    
	INSERT into user_role(userid, role) values (new_id, "ROLE_ADMIN");
	INSERT into user_volunteer (user_id, first_name, surname, phone_number, emergency_number, mha_tennant, previous_volunteer, volunteer_experience, conditions, condition_details, medication, medication_details, allergies, allergy_details, current_balance, total_amount) values (new_id, firstName, surName, pNumber, eNumber, mhaTenant, previousVolunteer, volunteerExperience, conditions, conditionDetails, medication, medicationDetails, allerigies, allergyDetails, 0, 0);
	INSERT into address (address_line_1, address_line_2, city, postcode, user_id) values (addressLine1, addressLine2, City, postCode, new_id);

END $$

DELIMITER ;
