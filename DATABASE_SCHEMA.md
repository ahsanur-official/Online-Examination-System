# Database Schema Requirements

## Tables that should exist in `online_exam` database

### 1. student table
```sql
CREATE TABLE student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. admin table
```sql
CREATE TABLE admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. question table
```sql
CREATE TABLE question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT NOT NULL,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    answer VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4. result table
```sql
CREATE TABLE result (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    score INT,
    total_questions INT,
    exam_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id)
);
```

## Sample Insert Statements for Testing

### Sample Students
```sql
INSERT INTO student (name, username, password, email) 
VALUES 
('John Doe', 'student1', 'pass1', 'john@example.com'),
('Jane Smith', 'student2', 'pass2', 'jane@example.com'),
('Mike Johnson', 'student3', 'pass3', 'mike@example.com');
```

### Sample Admin
```sql
INSERT INTO admin (name, username, password) 
VALUES ('Admin User', 'admin', 'admin');
```

### Sample Questions
```sql
INSERT INTO question (question, option1, option2, option3, option4, answer) 
VALUES 
('What is 2 + 2?', '3', '4', '5', '6', '4'),
('Capital of France?', 'London', 'Paris', 'Berlin', 'Rome', 'Paris'),
('What is the largest planet?', 'Mars', 'Venus', 'Jupiter', 'Saturn', 'Jupiter'),
('Who wrote Romeo and Juliet?', 'Dante', 'Shakespeare', 'Cervantes', 'Molière', 'Shakespeare'),
('What is H2O?', 'Hydrogen', 'Water', 'Salt', 'Oxygen', 'Water');
```

## Configuration Update

Update `src/main/resources/config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/online_exam?useSSL=false&serverTimezone=UTC
db.user=root
db.password=YOUR_PASSWORD
```

## Key Features Using Database

1. **Student Registration** - Stores in `student` table with unique username
2. **Login Authentication** - Queries `student` or `admin` table
3. **Profile Updates** - Updates `student` table with new info
4. **Exam Results** - Stores in `result` table with score and date
5. **Analytics** - Queries and aggregates `result` table data
6. **Questions** - Loads from `question` table with shuffle feature
7. **Admin Dashboard** - Queries all tables for statistics

## Important Notes

1. Ensure MySQL is running on localhost:3306
2. Database name must be `online_exam`
3. All foreign key constraints should be enabled
4. Collation should be utf8mb4_unicode_ci for better character support
5. InnoDB engine is recommended for better transaction support

## Backup Recommendation

Before running new code, backup your database:
```bash
mysqldump -u root -p online_exam > online_exam_backup.sql
```

## Verification Query

Check table structure:
```sql
DESCRIBE student;
DESCRIBE question;
DESCRIBE result;
DESCRIBE admin;
```
