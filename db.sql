DROP DATABASE IF EXISTS `profilr`;

CREATE DATABASE `profilr`;

USE `profilr`;

CREATE TABLE `Users` (
	`user_id` varchar(30) not null,
	`email_address` varchar(30) not null,
	`given_name` varchar(30) not null,
	`family_name` varchar(30) not null,
	`can_create_course` boolean default false,
	PRIMARY KEY (`user_id`),
	UNIQUE KEY `Email_UNQ` (`email_address`)
);

CREATE TABLE `Courses` (
	`course_id` int(10) not null auto_increment,
	`name` varchar(50) not null,
	PRIMARY KEY (`course_id`)
);

CREATE TABLE `Sections` (
	`section_id` int(10) not null auto_increment,
	`name` varchar(50) not null,
	`course_id` int(10) not null,
	`join_code` varchar(10) not null,
	PRIMARY KEY (`section_id`),
	UNIQUE KEY `Join_code_UNQ` (`join_code`),
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `CourseAdministrators` (
	`user_id` varchar(30) not null,
	`course_id` int(10) not null,
	PRIMARY KEY (`user_id`, `course_id`),
	FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `SectionUsers` (
	`user_id` varchar(30) not null,
	`section_id` int(10) not null,
	PRIMARY KEY (`user_id`, `section_id`),
	FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
		ON DELETE CASCADE  ON UPDATE CASCADE,	
	FOREIGN KEY (`section_id`) REFERENCES `Sections` (`section_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Tests` (
	`test_id` int(10) not null auto_increment,
	`name` varchar(50) not null,
	`course_id` int(10) not null,
	`published` boolean default false,
	PRIMARY KEY (`test_id`),
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Topics` (
	`topic_id` int(10) not null auto_increment,
	`name` varchar(50) not null,
	`course_id` int(30) not null,
	PRIMARY KEY (`topic_id`),
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Questions` (
	`question_id` int(10) not null auto_increment,
	`test_id` int(10) not null,
	`topic_id` int(10) not null,
	`label` varchar(30) not null,
	`text` varchar(50) not null,
	`weight` int(10) not null,
	PRIMARY KEY (`question_id`),
	FOREIGN KEY (`test_id`) REFERENCES `Tests` (`test_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`topic_id`) REFERENCES `Topics` (`topic_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Reasons` (
	`reason_id` int(10) not null auto_increment,
	`text` varchar(50) not null,
	PRIMARY KEY (`reason_id`),
	UNIQUE KEY `reason_unq` (`text`)
);

CREATE TABLE `Answers` (
	`answer_id` int(10) not null auto_increment,
	`question_id` int(10) not null,
	`user_id` varchar(30) not null,
	`correct` int(16) not null,
	`reason_id` int(10), /*explicitly nullable*/
	`notes` varchar(500) not null,
	PRIMARY KEY (`answer_id`),
	FOREIGN KEY (`question_id`) REFERENCES `Questions` (`question_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`reason_id`) REFERENCES `Reasons` (`reason_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Responses` (
	`response_id` int(10) not null auto_increment,
	`user_id` varchar(30) not null,
	`test_id` int(10) not null,
	`text` varchar(500),
	`ts_created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`ts_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`response_id`),
	FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`test_id`) REFERENCES `Tests` (`test_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `Reasons` (`reason_id`, `text`) values 	(1, 'Arithmetic error'),
							(2, 'Significant digits or rounding'),
							(3, 'Silly mistake'),
							(4, 'Misunderstood question'),
							(5, 'Didn\'t understand topic'),
							(6, 'Didn\'t understand vocabulary'),
							(7, 'Forgot +C'),
							(8, 'Formatted answer incorrectly'),
							(9, 'Bubbling error'),
							(10, 'Calculator mistake'),
							(11, 'Other');
