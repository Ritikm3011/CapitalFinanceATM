# CapitalFinanceATM
![GitHub Logo](https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png)


## Table of Contents

1. Introduction
2. Project Structure
3. Prerequisites
4. Setting Up the Project
5. Using the User Interface
6. Interacting with the Interface
7. Database Structure
8. Conclusion

## 1. Introduction

This document provides documentation for the ATM project developed using Java and MySQL. The project simulates an ATM system, allowing users to perform various banking operations such as deposits, withdrawals, transfers, and checking transaction history.

## 2. Project Structure

The project consists of the following components:

- `ConnectionProvider.java`: Establishes a connection to the MySQL database.
- `User.java`: Defines the `User` class with attributes and methods for user data.
- `Account.java`: Contains the main application logic and user interface.

## 3. Prerequisites

Before using the project, ensure you have the following prerequisites:

- NetBeans IDE installed.
- MySQL database server installed.
- A MySQL database named `atm` created with two tables: `user` and `transaction`.

## 4. Setting Up the Project

Follow these steps to set up the project:

1. Open NetBeans IDE.
2. Create a new Java project and name it appropriately.
3. Copy the provided Java classes (`ConnectionProvider.java`, `User.java`, `Account.java`) into your project's source folder.
4. Configure your MySQL database connection details in `ConnectionProvider.java` (URL, username, and password).
5. Ensure you have the MySQL JDBC driver added to your project's dependencies.

## 5. Using the User Interface

To use the ATM project, follow these steps:

1. Launch the project from Account.java class.
2. The application will start, and you will see the ATM login screen.
3. Enter your Card No. and 4 digit Pin for Login
4. After a successful Login user can see ATM interface.

## 6. Interacting with the Interface

The ATM interface allows users to perform the following actions:

- **Transaction History/Mini Statement**: View the transaction history for the logged-in user.
- **Withdraw**: Withdraw a specific amount from the account.
- **Deposit**: Deposit a specific amount into the account.
- **Transfer**: Transfer money to another account.

## 7. Database Structure

### `user` Table

- `id` (BIGINT, Primary Key, Auto-increment): Unique user ID.
- `name` (VARCHAR): User's name.
- `avl_bal` (INT): User's available balance.
- `address` (VARCHAR): User's address.
- `phone` (VARCHAR): User's phone number.
- `time` (VARCHAR): Card activation date and time.
- `password` (VARCHAR): User's password.

### `transaction` Table

- `t_id` (INT, Primary Key, Auto-increment): Unique transaction ID.
- `user_id` (BIGINT): ID of the user associated with the transaction.
- `amount` (INT): Transaction amount.
- `type` (VARCHAR): Transaction type (e.g., Deposit, Withdraw, Transfer).
- `note` (VARCHAR): Transaction note.
- `date` (VARCHAR): Transaction date and time.

## 8. Conclusion

This document provides an overview of the ATM project's structure, setup instructions, user interface usage, and database structure. 
