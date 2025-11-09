# Student Management Database System

A Java-based student management system that interfaces with PostgreSQL to perform CRUD (Create, Read, Update, Delete) operations on student records.

## Features

- View all students in the database
- Add new student records
- Update student email addresses
- Delete student records
- Interactive command-line interface

## Prerequisites

- Java JDK 8 or higher
- PostgreSQL 9.6 or higher
- PostgreSQL JDBC Driver
- Access to a PostgreSQL database named `studentsdb`

## Database Setup

1. Create a PostgreSQL database named `studentsdb`
2. Create the students table using the following SQL:

```sql
-- Create students table
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    enrollment_date DATE
);

-- Insert initial data
INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
```

## Configuration

Database connection parameters are defined in the `StudentManagementdb.java` file:
- Database URL: `jdbc:postgresql://localhost:5432/studentsdb`
- Username: `postgres`
- Password: `postgres`

Modify these values in the source code if your PostgreSQL configuration is different.

## Usage

The application provides a menu-driven interface with the following options:
1. View all students
2. Add new student
3. Update student email
4. Delete student
5. Exit

Follow the on-screen prompts to perform various operations.

## Contributing

Feel free to fork this project and submit pull requests for any improvements!

## Demonstration Video

The video below is a link to a demonstration video of how this interface works:
https://youtu.be/l9WlfgKMVwU