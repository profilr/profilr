# Profilr

Profilr is a new way to learn from your mistakes. Profile your tests to see what type of questions you miss the most, and track your performance over time. Profilr is perfect for teachers who want their students to get better at the subject they teach.

## Getting started

~~Visit [profilr.org](https://profilr.org) and create an account~~

*Currently, as Profilr is still in development, you can only run it locally (profilr.org points to localhost). In order to do so, clone the repo (`git clone https://github.com/profilr/profilr`). Then edit `src/main/resources/hibernate.cfg.xml` to point to a valid database. Run `db.sql` to create the schema (it was tested on MySQL 5.7 but should work on most SQL compliant databases). Finally, build a war using Maven and deploy it to a servlet container, or alternatively launch the project in Eclipse and run it directly on a servlet container from there.*

Sign up using your Google account. You can then create and join courses. Unfortunately, course creation is limited to people whitelisted by the admins (directly in the SQL database, edit the column `course_admin_approved`) in order to avoid abuse.

## Technical details

Profilr is developed in Java using the Jersey 2.27 (JAX-RS) framework. Views are written as Freemarker templates, and populated with domain models from the database. The database layer uses Hibernate as the ORM framework, using JPA annotations on the domain objects. Dependency Injection is leveraged in order to maintain the cleanliness of the codebase, as well as to abstract away JPA-level transaction management from resource classes.

Profilr is hosted using the Tomcat 9 servlet container on AWS EC2. Its database is a MySQL 5.7 database hosted via AWS RDS.

*If none of that made sense to you, just ignore it :slightly_smiling_face:*

### SQL Tables/Entities

| Table                  | Use                                                          | Columns                                                      |
| ---------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `Users`                | Users represent people using the application, both students and teachers | `user_id`, `email_address`, `given_name`, `family_name`, `course_admin_approved` |
| `Courses`              | Courses are the overlying class taken by students. An example is "AP Biology." | `course_id`,  `name`                                         |
| `Sections`             | Sections are individual groups of students within courses. An example is "AP Bio 5th Period." Each section belongs to a course. | `section_id`,  `name`, `course_id`                           |
| `CourseAdministrators` | This table maps Courses and their administrators (Users) in a many-to-many relationship. | `user_id`,  `course_id`                                      |
| `SectionUsers`         | This table maps Sections and their students (Users) in a many-to-many relationship. | `user_id`,  `section_id`                                     |
| `Topics`               | Topics are types of questions asked in tests of a course. They usually represent some kind of standard, such as TEKS, Common Core, or an AP standard. An example is "Ecology." | `topic_id`, `name`, `course_id`                              |
| `Tests`                | Tests are actual examinations given by teachers to students, made up of questions. | `test_id`, `name`, `course_id`                               |
| `TestQuestions`        | Questions are individual questions appearing on tests. Each question is linked to exactly one topic. | `question_id`, `test_id`, `topic_id`, `label`, `text`, `weight` |
| `TestQuestionAnswers`  | Answers are responses to questions by students. Each unique question-student pair has its own answer. Answers don't represent student's actual responses but rather if they got the question correct and the reason they missed the question. Answers are the fundamental part of Profilr. | `answer_id`, `question_id`, `user_id`, `correct`, `reason`, `notes` |
