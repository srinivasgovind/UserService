# UserService
APIs Implementation for User Service

## Steps to Install this Project Locally

1. Clone the project to your local machine.
2. Set up your PostgreSQL and replace the values in `application.properties`.
3. Run `mvn clean install`.
4. You should now be able to run this project successfully.

## What is this Project?

1. This project is one of the microservices for an e-commerce web application. It plays a key role in handling new user sign-up, login, validation, logout, role management, session management, and more.
2. Implemented OAuth2 for secure authentication.
3. Passwords are encoded in the database before saving using `BcryptPasswordEncoder`.
4. PostgreSQL tables are created using Flyway scripts.
5. The Product Service and all other services will communicate with the User Service for all user-related information and validations.
