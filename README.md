Title: Employee Registration & Authentication System (Spring Boot + React + PostgreSQL)

Description

This project is a full-stack web application that provides secure user registration, login, and account management features.
It uses Spring Boot for the backend, React for the frontend, and PostgreSQL as the database.

Features:-
User Registration: Create new accounts with username, password, and role.
Secure Login: Passwords are encrypted using BCrypt.
Account Locking: After 3 failed login attempts, the account is automatically locked.
Audit Tracking: Stores last login and logout timestamps.
Role-Based Access: Supports roles like USER and ADMIN.
CORS Configured: Allows secure communication between React and Spring Boot

Tech Stack:-
Backend: Spring Boot, Spring Security, JPA/Hibernate
Frontend: React.js
Database: PostgreSQL
Security: BCrypt password hashing, account lock mechanism

How It Works:-
Register: User submits details → stored in PostgreSQL.
Login: Validates credentials → resets failed attempts on success.
Failed Attempts: After 3 wrong attempts → account locked → returns "User is blocked. Max attempts reached.".
Frontend Alerts: React shows alert and message when blocked.



Explanation about each attributes:-
**role** – tells what type of user it is (like “admin” or “user”).  
**username** – the name that the user uses to log in.  
**version** – shows the version of the user record or data (often for tracking changes).  
**account_non_locked** – shows if the account is locked or not (t for true, meaning not locked).  
**failed_attempts** – counts how many times the user entered the wrong password.  
**last_login_at** – the date and time when the user last logged in.  
**last_logout_at** – the date and time when the user last logged out.  
**password** – stores the user’s password in encrypted (hashed) form, not plain text.  


<img width="1920" height="1080" alt="Screenshot (3)" src="https://github.com/user-attachments/assets/0de49517-8782-4941-a6b7-4635722c1a6a" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/086421bc-f789-4da5-835f-3df26c916880" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/daf9a77a-0e39-400d-bc3e-43cdd816ba90" />
<img width="354" height="290" alt="{B35E9610-F3D2-466E-AC41-C445C65C4477}" src="https://github.com/user-attachments/assets/4a9db18f-97f4-4ed1-9dba-dfbf8ec3c051" />
