DROP schema if exists mha;
CREATE schema mha;
USE mha;
DROP table if exists `user_role`;
DROP table if exists `user_volunteer`;
DROP table if exists `achievement`;
DROP table if exists `address`;
DROP table if exists `allocation`;

DROP table if exists `business_changes`;
DROP table if exists `record_changes`;
DROP table if exists `redemption`;
DROP table if exists `reward`;
DROP table if exists `reward_changes`;
DROP table if exists `volunteer_achievement`;
DROP table if exists `volunteer_application`;
DROP table if exists `business`;
DROP table if exists `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;

CREATE TABLE `user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_ibfk_1` (`userid`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

CREATE TABLE `user_volunteer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `surname` varchar(30) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `emergency_number` varchar(15) NOT NULL,
  `mha_tennant` int(11) NOT NULL,
  `previous_volunteer` int(11) NOT NULL,
  `volunteer_experience` text DEFAULT NULL,
  `conditions` int(11) NOT NULL,
  `condition_details` text DEFAULT NULL,
  `medication` int(11) NOT NULL,
  `medication_details` text DEFAULT NULL,
  `allergies` int(11) NOT NULL,
  `allergy_details` text DEFAULT NULL,
  `current_balance` int(11) DEFAULT 0,
  `total_amount` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_volunteer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;

CREATE TABLE `achievement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(455) DEFAULT NULL,
  `credits` int(11) NOT NULL,
  `file_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `address` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(255) NOT NULL,
  `address_line_2` varchar(255) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

CREATE TABLE `allocation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_allocated` datetime NOT NULL,
  `credits_allocated` int(11) NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `allocation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

CREATE TABLE `business` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `business_name` varchar(25) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TABLE `business_changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `table_changed` varchar(255) DEFAULT NULL,
  `change_type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

CREATE TABLE `record_changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `table_changed` varchar(45) DEFAULT NULL,
  `change_type` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1;

CREATE TABLE `reward` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reward_name` varchar(50) NOT NULL,
  `reward_description` text NOT NULL,
  `cost` int(11) NOT NULL,
  `business_id` int(10) unsigned NOT NULL,
  `num_times_redeemed` int(11) DEFAULT NULL,
  `creator` varchar(50) DEFAULT NULL,
  `active` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `business_id` (`business_id`),
  CONSTRAINT `reward_ibfk_1` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

CREATE TABLE `redemption` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `reward_id` int(10) unsigned NOT NULL,
  `redemption_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `reward_id` (`reward_id`),
  CONSTRAINT `redemption_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `redemption_ibfk_2` FOREIGN KEY (`reward_id`) REFERENCES `reward` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

CREATE TABLE `reward_changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `table_changed` varchar(45) NOT NULL,
  `r_description` varchar(500) DEFAULT NULL,
  `r_name` varchar(500) DEFAULT NULL,
  `changer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

CREATE TABLE `volunteer_achievement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `achievement_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `achievement_id` (`achievement_id`),
  CONSTRAINT `volunteer_achievement_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `volunteer_achievement_ibfk_2` FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE `volunteer_application` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `username` varchar(50) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `emergency_number` varchar(15) NOT NULL,
  `address_line_1` varchar(255) NOT NULL,
  `address_line_2` varchar(255) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `mha_tennant` int(11) NOT NULL,
  `previous_volunteer` int(11) NOT NULL,
  `volunteer_experience` text DEFAULT NULL,
  `conditions` int(11) NOT NULL,
  `condition_details` text DEFAULT NULL,
  `medication` int(11) NOT NULL,
  `medication_details` text DEFAULT NULL,
  `allergies` text NOT NULL,
  `allergy_details` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

# insert statements 
Insert into achievement values ( 1 , 'Started Volunteering', 'You have earned your first volunteering credit, a few more hours of work and you could earn yourself a wonderful meal at Bean & Bread!', 1, 'achievement1.png');
Insert into achievement values ( 2 , 'Experience Volunteer', 'You\'ve already earned 10 credits for doing great work in the community! Perhaps it\'s time to reward yourself with a day out to Raglan Castle on us, get your ticket on the rewards page.', 10, 'achievement2.png');
Insert into achievement values (3, 'Veteran Volunteer', '50 credits earned in total, wow! Now would be a great time for a walk with Strolls \'n\' Stories, breathe in the fresh air and admire the beautiful country views as you think about how much great work you\'ve done.', 50, 'achievement3.png');
Insert into achievement values (4, 'Master Volunteer', 'We can\'t congratulate you enough for earning 100 credits, every credit earned was you making a difference. We hope you have enjoyed your time spent volunteering so far and made the most of those credits by spending them on treats for yourself and others. Keep up the great work!',100, 'achievement4.png');
insert into user values ('2', 'thetest@email.com', '$2a$10$f7XUPOWAfkFyiucChl2KnOqyLxL1g3KGij6EukR0t4FoUDvwz3ZSG'),
('56', 'Rossa9@cardiff.ac.uk', 'password'),
('86', 'cooper.henry30@googlemail.com', 'password2');
insert into user_role values ('1', '2', 'ROLE_ADMIN'),
('2', '2', 'ROLE_USER'),
('67', '86', 'ROLE_USER');


insert into business values  ('1', 'vue', 'bacon@vue.com');
insert into business values('2', 'kfc', 'kfc@test.com');
insert into business values('10', 'Henry\'s Business', 'test@test.com');
insert into address values ('1', '46 Coop Coop Road', 'Cathays', 'Cardiff', 'CF245WH', '2');
insert into address values('57', '8 Stratford Rd', '', 'Salisbury', 'SP1 3JH', '86');

insert into reward values ('1', 'Free Cinema Tickets', 'Get 2 free cinema tickets for only 5 time credits', '5', '1', '5', NULL, 'yes'),
('2', '2 Piece Meal', 'Get a free 2 piece meal!', '3', '2', NULL, NULL, 'yes'),
('3', 'Test', 'a test', '5', '2', NULL, NULL, 'yes'),
('4', 'Test 2', 'fweelacs', '3', '2', NULL, NULL, 'yes'),
('5', 'adfg', 'ffff', '10', '2', NULL, NULL, 'yes'),
('9', 'Cinema Offer', 'You can use credits to lower the price of a 2D superscreen viewing.', '3', '1', NULL, NULL, 'yes'),
('10', 'Mini Donut Free', 'Mini donut free with every child meal purchased.', '1', '2', '2', NULL, 'yes'),
('23', '3D Glasses', 'Free for your next 3D screening.', '1', '1', '1', 'CooperHJ@Cardiff.ac.uk', 'no'),
('26', 'Milkshake', 'Small free strawberry milkshake.', '1', '1', '1', 'CooperHJ@Cardiff.ac.uk', 'no'),
('27', 'Milkshake', 'Small free strawberry milkshake.', '2', '1', '1', 'CooperHJ@Cardiff.ac.uk', 'yes'),
('28', 'Superscreen', 'Free child superscreen ticket age 12, with an adult purchase.', '3', '1', '1', 'CooperHJ@Cardiff.ac.uk', 'yes');


insert into redemption values
('4', '2', '2', '2018-11-29 00:00:00'),
('5', '2', '2', '2018-11-29 00:00:00'),
('6', '2', '2', '2018-11-29 00:00:00'),
('7', '2', '1', '2018-12-05 13:10:36'),
('8', '2', '1', '2018-12-05 13:46:34'),
('9', '2', '1', '2018-12-05 13:49:13'),
('10', '2', '1', '2018-12-05 13:59:23'),
('11', '2', '1', '2018-12-05 14:01:09'),
('12', '2', '1', '2018-12-05 14:09:01'),
('13', '2', '1', '2018-12-05 14:18:53'),
('14', '2', '1', '2018-12-05 14:20:02'),
('15', '2', '1', '2018-12-05 14:28:06'),
('16', '2', '1', '2018-12-06 10:28:55'),
('17', '2', '2', '2018-12-06 10:30:45'),
('18', '2', '1', '2018-12-06 10:48:53'),
('19', '2', '1', '2018-12-06 10:59:48'),
('22', '2', '1', '2018-12-09 14:17:23'),
('23', '2', '1', '2018-12-09 22:24:58'),
('24', '2', '10', '2018-12-09 22:32:15'),
('25', '2', '10', '2018-12-09 22:36:32'),
('26', '2', '9', '2018-12-09 22:41:32'),
('27', '2', '10', '2018-12-09 22:49:33'),
('28', '2', '10', '2018-12-09 22:52:23'),
('29', '2', '23', '2018-12-10 16:13:57'),
('33', '2', '26', '2018-12-11 19:56:45'),
('34', '2', '27', '2018-12-11 20:36:00'),
('35', '2', '28', '2018-12-11 21:01:22'),
('36', '2', '1', '2018-12-12 18:54:50'),
('38', '2', '1', '2018-12-12 19:09:37'),
('39', '2', '1', '2018-12-13 10:11:47'),
('40', '2', '1', '2018-12-13 10:12:41');
insert into user_volunteer values ('1', '2', 'Benry', 'Cooper', '07935964692', '07935964692', '0', '1', 'Ate Chicken for a day', '0', '', '0', '', '0', '', '66', '110'),
('64', '86', 'Henry', 'Cooper', '7935964692', '', '0', '0', '', '0', '', '0', '', '0', '', '5', '5');
insert into allocation values('8', '2018-12-08 15:48:17', '10', 'Mowing the lawn', '2');
insert into allocation values('9', '2018-12-08 16:27:47', '1', 'Mowing the lawn', '2');
insert into allocation values('10', '2018-12-08 16:29:04', '1', 'Mowing the lawn', '2');
insert into allocation values('11', '2018-12-08 16:33:49', '1', 'Mowing the lawn', '2');
insert into allocation values('12', '2018-12-08 16:34:24', '1', 'Mowing the lawn', '2');
insert into allocation values('13', '2018-12-08 16:48:53', '1', 'Mowing the lawn', '2');
insert into allocation values('14', '2018-12-08 23:01:24', '1', 'Mowing the lawn', '2');
insert into allocation values('15', '2018-12-09 01:51:59', '1', 'Testing', '2');
insert into allocation values('16', '2018-12-09 01:52:40', '61', 'Mowing the lawn', '2');
insert into allocation values('19', '2018-12-13 10:08:50', '5', 'Mowing the lawn', '86');
insert into volunteer_application values ('35', 'Henry', 'Cooper', 'cooper.henry30@googlemail.com', '7935964692', '', '8 Stratford Rd', '', 'Salisbury', 'SP1 3JH', '0', '0', '', '0', '', '0', '', '0', '');
insert into volunteer_achievement values ('5', '2', '1', '2018-12-08 23:01:25');

#end of inset 
#############

# Select statements 
SELECT * FROM MHA.volunteer_application;
SELECT * FROM MHA.volunteer_achievement;
SELECT * FROM MHA.user_volunteer;
SELECT * FROM MHA.user_role;
SELECT * FROM MHA.user;
SELECT * FROM MHA.reward;
SELECT * FROM MHA.redemption;
SELECT * FROM MHA.record_changes;
SELECT * FROM MHA.business_changes;
SELECT * FROM MHA.business;
SELECT * FROM MHA.allocation;
SELECT * FROM MHA.address;
SELECT * FROM MHA.achievement;
#end ot select statemtens



DROP PROCEDURE IF EXISTS `createSystemAdministrator`;

DELIMITER $$
CREATE PROCEDURE `createSystemAdministrator`(email varchar(255), encpassword varchar(255), firstName varchar(255), surName varchar(255), pNumber varchar(15), eNumber varchar(15), mhaTenant int, previousVolunteer int, volunteerExperience varchar(255), conditions int, conditionDetails varchar(255), medication int, medicationDetails varchar(255), allerigies int, allergyDetails varchar(255), addressLine1 varchar(255), addressLine2 varchar(255), City varchar(255), postCode varchar(255))
BEGIN
	DECLARE new_id INT;
    
	INSERT into user(username, password) values(email, encpassword);
    
	SET new_id = (SELECT id FROM user WHERE username = email);
    
	INSERT into user_role(userid, role) values (new_id, "ROLE_ADMIN");
	INSERT into user_volunteer (user_id, first_name, surname, phone_number, emergency_number, mha_tennant, previous_volunteer, volunteer_experience, conditions, condition_details, medication, medication_details, allergies, allergy_details, current_balance, total_amount) values (new_id, firstName, surName, pNumber, eNumber, mhaTenant, previousVolunteer, volunteerExperience, conditions, conditionDetails, medication, medicationDetails, allerigies, allergyDetails, 0, 0);
	INSERT into address (address_line_1, address_line_2, city, postcode, user_id) values (addressLine1, addressLine2, City, postCode, new_id);

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `deleteBusiness`;

DELIMITER $$
CREATE PROCEDURE `deleteBusiness`(businessid INTEGER)
BEGIN
	declare bid int;
    
    set bid = (select id from business where id = businessid);
	
	DELETE from business where id = bid;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `deleteReward`;

DELIMITER $$
CREATE PROCEDURE `deleteReward`(IN rid int)
BEGIN

	UPDATE reward
    SET active = "no"
    WHERE id = rid;
    
    IF (SELECT active FROM reward WHERE id = rid) = "no" THEN
		SET rid = 0;
	ELSE
		SET rid = 1;
	END IF;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `deleteUser`;

DELIMITER $$
CREATE PROCEDURE `deleteUser`(uid Integer)
BEGIN
	DELETE from address where user_id = uid;
	DELETE from user_volunteer where user_id = uid;
    DELETE from user_role where userid = uid;
    DELETE from allocation where user_id = uid;
    DELETE from redemption where user_id = uid;
    DELETE from user_volunteer where user_id = uid;
    DELETE from user where id = uid;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `getMissingAchievements`;

DELIMITER $$
CREATE PROCEDURE `getMissingAchievements`(uid INTEGER)
BEGIN
select * from achievement where credits <= (select total_amount from user_volunteer where user_id = uid) and id not in (select achievement_id from volunteer_achievement where user_id = uid);
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `giveAchievement`;

DELIMITER $$
CREATE PROCEDURE `giveAchievement`(uid INTEGER, aid INTEGER)
BEGIN
	INSERT into volunteer_achievement (user_id, achievement_id, date) values (uid, aid, CURRENT_TIMESTAMP());
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `increaseRewardsNumTimesRedeemed`;

DELIMITER $$
CREATE PROCEDURE `increaseRewardsNumTimesRedeemed`(rewards_id int)
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
    
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `redeemReward`;

DELIMITER $$
CREATE PROCEDURE `redeemReward`(uid Integer, rid Integer)
BEGIN
	UPDATE user_volunteer SET current_balance = current_balance - (SELECT cost from reward where id = rid) where user_id = uid;
	INSERT into redemption(user_id, reward_id, redemption_date) values(uid, rid, CURRENT_TIMESTAMP());
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `registerUser`;

DELIMITER $$
CREATE PROCEDURE `registerUser`(email varchar(255), encpassword varchar(255), firstName varchar(255), surName varchar(255), pNumber varchar(15), eNumber varchar(15), mhaTenant int, previousVolunteer int, volunteerExperience varchar(255), conditions int, conditionDetails varchar(255), medication int, medicationDetails varchar(255), allerigies int, allergyDetails varchar(255), addressLine1 varchar(255), addressLine2 varchar(255), City varchar(255), postCode varchar(255))
BEGIN
	DECLARE new_id INT;
    
	INSERT into user(username, password) values(email, encpassword);
	SET new_id = (SELECT id FROM user WHERE username = email);
	INSERT into user_role(userid, role) values (new_id, "ROLE_USER");
	INSERT into user_volunteer (user_id, first_name, surname, phone_number, emergency_number, mha_tennant, previous_volunteer, volunteer_experience, conditions, condition_details, medication, medication_details, allergies, allergy_details, current_balance, total_amount) values (new_id, firstName, surName, pNumber, eNumber, mhaTenant, previousVolunteer, volunteerExperience, conditions, conditionDetails, medication, medicationDetails, allerigies, allergyDetails, 0, 0);
	INSERT into address (address_line_1, address_line_2, city, postcode, user_id) values (addressLine1, addressLine2, City, postCode, new_id);

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `viewRedemptions`;

DELIMITER $$
CREATE PROCEDURE `viewRedemptions`()
BEGIN
SELECT redemption.id AS `Redemption Id`, user.id AS `User Id`, user_volunteer.first_name AS `First Name`, user_volunteer.surname, reward.reward_name, redemption.redemption_date FROM redemption
JOIN user on redemption.user_id = user.id
JOIN user_volunteer
JOIN reward ON redemption.reward_id=reward.id;
END$$
DELIMITER ;
