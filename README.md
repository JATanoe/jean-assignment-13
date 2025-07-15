# User Bank Account Management System

A Spring Boot application for managing users and their bank accounts. This application allows for the creation, retrieval, updating, and deletion of users, as well as the management of their associated bank accounts.

## Features

- User management (CRUD operations)
  - Create new users with username, password, and personal information
  - View all users or a specific user
  - Update user information
  - Delete users
- Address management
  - Each user has an associated address
  - Address information includes address lines, city, region, country, and zip code
- Account management
  - Users can have multiple bank accounts
  - New users automatically get checking and savings accounts
  - Create additional accounts
  - Update account names
- Transaction support
  - Accounts can have multiple transactions
  - Transactions include amount, date, and type

## Technologies Used

- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications
- **Spring Data JPA**: Simplifies data access using the Java Persistence API
- **Hibernate**: Object-relational mapping tool for database interaction
- **MySQL**: Relational database for data storage
- **Thymeleaf**: Server-side Java template engine for web applications
- **HTML/CSS**: Frontend presentation

## Prerequisites

- Java 17 or higher
- Maven
- MySQL Server

## Setup Instructions

1. Clone the repository
2. Configure the database connection in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hibernate_example
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Create a MySQL database named `hibernate_example`
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`
6. Access the application at: `http://localhost:8080/users`

## API Endpoints

### User Management
- `GET /register` - Display user registration form
- `POST /register` - Create a new user
- `GET /users` - List all users
- `GET /users/{userId}` - View a specific user
- `POST /users/{userId}` - Update a user
- `POST /users/{userId}/delete` - Delete a user

### Account Management
- `POST /users/{userId}/accounts` - Create a new account for a user
- `GET /users/{userId}/accounts/{accountId}` - View a specific account
- `POST /users/{userId}/accounts/{accountId}` - Update an account

## Data Model

### User
- userId (Long): Primary key
- username (String): User's login name
- password (String): User's password
- name (String): User's full name
- createdDate (LocalDate): Date when the user was created
- accounts (List<Account>): User's bank accounts
- address (Address): User's address

### Account
- accountId (Long): Primary key
- accountName (String): Name of the account
- transactions (List<Transaction>): Account's transactions
- users (List<User>): Users associated with this account

### Address
- userId (Long): Primary key (same as User's ID)
- addressLine1 (String): First line of address
- addressLine2 (String): Second line of address
- city (String): City
- region (String): State/Province/Region
- country (String): Country
- zipCode (String): Postal/Zip code

### Transaction
- transactionId (Long): Primary key
- transactionDate (LocalDateTime): Date and time of the transaction
- amount (Double): Transaction amount
- type (String): Transaction type (e.g., 'D' for debit, 'C' for credit)
- account (Account): Associated account

## License

This project is licensed under the MIT License - see the LICENSE file for details.