# Profilr

Profilr is a new way to learn from your mistakes. Profile your tests to see what type of questions you miss the most, and track your performance over time. Profilr is perfect for teachers who want their students to get better at the subject they teach.

## Getting started

Visit [profilr.org](https://profilr.org) and create an account.

Sign up using your Google account. You Àcan then create and join courses. Currently, course creation is limited to people whitelisted by the admins in order to avoid abuse.

## Technical details

Profilr is developed in Java using the Jersey 2.27 (JAX-RS) framework. Views are written as Freemarker templates, and populated with domain models from the database. The database layer uses Hibernate as the ORM framework, using JPA annotations on the domain objects. Dependency Injection is leveraged in order to maintain the cleanliness of the codebase, as well as to abstract away JPA-level transaction management from resource classes.

Profilr is hosted using the Tomcat 9 servlet container on AWS EC2. Its database is a MySQL 5.7 database hosted via AWS RDS.

*If none of that made sense to you, just ignore it :slightly_smiling_face:*
