# Online Exam System - AI Agent Instructions

## Project Overview
A Java Swing desktop application providing an online examination platform. Students can log in, take exams, and view results. Administrators manage exam content and student data. The system uses MySQL for persistence and property files for configuration.

## Architecture

### Component Structure
```
com.onlineexam
├── main/        → Application entry point (Main.java, uses Swing event dispatch)
├── ui/          → GUI components (LoginForm, Dashboard, ExamWindow, ResultWindow)
├── model/       → Data models (Student, Question, Result)
├── database/    → Data access (DBConnection.java - singleton pattern)
└── admin/       → Administrative UI (AdminPanel.java)
```

### Key Design Patterns
- **Database Connection**: Static singleton pattern in `DBConnection.java`. Uses config.properties for database credentials
- **UI Framework**: Java Swing with manual layout management (BorderLayout, GridLayout)
- **Event Handling**: Action listeners for button clicks and user interactions
- **Threading**: SwingUtilities.invokeLater() for thread-safe GUI updates

## Build & Execution

### Database Setup
1. Create MySQL database: `online_exam`
2. Update [src/main/resources/config.properties](src/main/resources/config.properties):
   ```
   db.url=jdbc:mysql://localhost:3306/online_exam?useSSL=false&serverTimezone=UTC
   db.user=root
   db.password=<your_password>
   ```
3. Import SQL schema (check for schema files or create tables matching model classes)

### Running the Application
```bash
# Compile and run
javac -d target/classes src/main/java/com/onlineexam/**/*.java
java -cp target/classes:mysql-connector-java-x.x.x.jar com.onlineexam.main.Main
```

### Maven Commands (if pom.xml exists)
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.onlineexam.main.Main"
```

## Code Conventions

### Package Organization
- **ui/**: All GUI components; class names end with descriptor (LoginForm, ExamWindow)
- **model/**: POJOs with getters/setters following JavaBean pattern
- **database/**: Only DBConnection; all DB operations should be here or in a DAO layer
- **admin/**: Admin-specific UI and operations

### Naming Conventions
- UI components: `Descriptor + Frame/Window/Form` (e.g., `LoginForm`, `ExamWindow`)
- Models: Singular entity names (Student, Question, Result)
- Methods: Camel case, action-oriented names (startExam, viewResult)

### Common Patterns to Follow
- Initialize UI in `init()` method after constructor sets frame properties
- Use try-catch for database operations and log exceptions with `e.printStackTrace()`
- Access DB connection via `DBConnection.getConnection()`
- Update config.properties for environment-specific settings (never hardcode credentials)

## Key Files

| File | Purpose |
|------|---------|
| [src/main/java/com/onlineexam/main/Main.java](src/main/java/com/onlineexam/main/Main.java) | Application entry point; sets Look & Feel and launches LoginForm |
| [src/main/java/com/onlineexam/ui/LoginForm.java](src/main/java/com/onlineexam/ui/LoginForm.java) | Authentication UI; validates credentials against database |
| [src/main/java/com/onlineexam/ui/Dashboard.java](src/main/java/com/onlineexam/ui/Dashboard.java) | Main student interface after login |
| [src/main/java/com/onlineexam/ui/ExamWindow.java](src/main/java/com/onlineexam/ui/ExamWindow.java) | Exam-taking interface with questions |
| [src/main/java/com/onlineexam/database/DBConnection.java](src/main/java/com/onlineexam/database/DBConnection.java) | Singleton for database connections |
| [src/main/resources/config.properties](src/main/resources/config.properties) | Database and environment configuration |

## Common Development Tasks

### Adding a New UI Screen
1. Create class in `ui/` package extending `JFrame`
2. Set frame properties: title, size, location, close operation
3. Implement `init()` method for component layout
4. Add action listeners for user interactions
5. Use `DBConnection.getConnection()` for data operations

### Adding a New Model Class
1. Create class in `model/` package
2. Add private fields, no-arg constructor, full constructor
3. Implement getters/setters for all fields
4. Follow JavaBean convention for consistency

### Modifying Database Access
1. Get connection: `DBConnection.getConnection()`
2. Use PreparedStatement with parameterized queries (see LoginForm.java for pattern)
3. Handle ResultSet iteration for queries
4. Always close resources in try-with-resources or finally blocks

## Dependencies
- **Java**: Swing library (built-in)
- **Database**: MySQL Connector/J (mysql-connector-java)
- **Build Tool**: Maven (implied by target/ directory structure)

## Important Notes
- Database credentials are in `config.properties` — update for your environment before running
- The application uses system Look & Feel for native appearance
- All GUI initialization must happen on the Event Dispatch Thread
- Database connections should be managed carefully to avoid resource leaks
