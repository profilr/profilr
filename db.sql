DROP DATABASE IF EXISTS `profilr`;

CREATE DATABASE `profilr`;

USE `profilr`;

CREATE TABLE `Users` (
	`user_id` varchar(30) not null,
	`email_address` varchar(30) not null,
	`given_name` varchar(30) not null,
	`family_name` varchar(30) not null,
	`course_admin_approved` boolean default false,
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
	`name` varchar(30) not null,
	`course_id` int(10) not null,
	PRIMARY KEY (`test_id`),
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Topics` (
	`topic_id` int(10) not null auto_increment,
	`name` varchar(30) not null,
	`course_id` int(30) not null,
	PRIMARY KEY (`topic_id`),
	FOREIGN KEY (`course_id`) REFERENCES `Courses` (`course_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `TestQuestions` (
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

CREATE TABLE `TestQuestionAnswers` (
	`answer_id` int(10) not null auto_increment,
	`question_id` int(10) not null,
	`user_id` varchar(30) not null,
	`correct` boolean not null,
	`reason` varchar(50) not null,
	`notes` varchar(500) not null,
	PRIMARY KEY (`answer_id`),
	FOREIGN KEY (`question_id`) REFERENCES `TestQuestions` (`question_id`)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
		ON DELETE CASCADE ON UPDATE CASCADE
);
