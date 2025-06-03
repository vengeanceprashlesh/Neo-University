# University Campus Access Control System

## Overview
This project is a real-world access control system for a university campus, designed using core OOP principles, inheritance, exception handling, and a beautiful Swing-based GUI in Java.

## Features
- Abstract classes and interfaces for role-based access
- Exception handling for invalid login attempts
- Thread simulation for simultaneous user entries
- GUI-based login and dashboard using modern Swing components
- Use of collections (ArrayList, HashMap) for user data management
- Test cases for QA and reporting

## Roles
- **System Architect:** Designed the class hierarchy and OOP structure
- **Backend Developer:** Implemented exception handling and thread-based access simulation
- **Frontend Developer:** Designed and coded the Swing-based GUI
- **QA Tester & Reporting:** Handles test cases and reporting

## File Structure
```
model/        # User, Student, Staff, Admin, AccessRole
backend/      # AccessSimulator
exception/    # InvalidLoginException
frontend/     # LoginFrame, DashboardFrame
Main.java     # Entry point for GUI
TestCases.java# QA and thread simulation
```

## How to Run
1. **Compile all files:**
   ```
   javac model/*.java backend/*.java exception/*.java frontend/*.java Main.java TestCases.java
   ```
2. **Run the GUI application:**
   ```
   java Main
   ```
3. **Run the test cases:**
   ```
   java TestCases
   ```

## Demo Users
- Student: `student1` / `pass123`
- Staff:   `staff1` / `pass456`
- Admin:   `admin1` / `admin789`

Enjoy a modern, attractive, and robust access control system! 