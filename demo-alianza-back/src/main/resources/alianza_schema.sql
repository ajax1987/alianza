create database demo_alianza;

use demo_alianza;


CREATE TABLE IF NOT EXISTS `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `shared_Key` varchar(100) NOT NULL,
  `bussines_id` varchar(100) NOT NULL,
  `mobile_number` int(20) NOT NULL,
  `email` varchar(200) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
   PRIMARY KEY (`person_id`),
);
