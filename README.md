# SELLOTE_FBTS - Fishing Boat Trip System

Layered Java console app (Maven + SQLite) for PF201: OOP - IT2C.

## Structure

```
src/main/java
├── Main.java                  app entry point
├── dao/
│   ├── DatabaseConnection.java   SQLite connection + users table creation
│   └── UserDao.java              SQL queries (register, login)
├── model/
│   └── User.java                 Java record (id, username, password, roles, status)
└── ui/
    ├── MainMenu.java             Register / Login / Exit
    └── DashboardMenu.java        post-login screen with Logout
```

## Features

- **Register Account**: role selection (Boat Owner / Administrator, Boat Captain, Port Clerk), default status `PENDING`.
  The very first account is always the Boat Owner / Administrator (status `ACTIVE`).
- **Login**: username and password checked against the database; `PENDING` accounts are blocked.
- **Logout / Exit**: logout returns to the main menu; Exit closes the app.

## Database

`users` table: `id`, `username`, `password` (SHA-256 hash), `roles`, `status`.
The SQLite file `fbts.db` is created automatically in the project folder on first run.

## Run

Requires JDK 17+ and Maven.

```
mvn compile exec:java
```
