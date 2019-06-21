DROP DATABASE IF EXISTS `profilr`;

CREATE DATABASE `profilr`;

USE `profilr`;

CREATE TABLE `Users` (
	`user_id` varchar(30) not null,
	`email_address` varchar(30) not null,
	`given_name` varchar(30) not null,
	`family_name` varchar(30) not null,
	PRIMARY KEY (`user_id`),
	UNIQUE KEY `Email_UNQ` (`email_address`)
);

CREATE TABLE `Courses` (
	`course_id` int(10) not null auto_increment,
	`name` varchar(45) not null,
	PRIMARY KEY (`course_id`)
);

CREATE TABLE `Sections` (
	`section_id` int(10) not null auto_increment,
	`name` varchar(45) not null,
	`course_id` int(10) not null,
	PRIMARY KEY (`section_id`),
	CONSTRAINT `Course_ID_FK` FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Enrollments` (
	`user_id` varchar(30) not null,
	`section_id` int(10) not null,
	PRIMARY KEY (`user_id`, `section_id`),
	CONSTRAINT `User_ID_FK` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `Section_ID_FK` FOREIGN KEY (`section_id`) REFERENCES `Sections` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE
);
