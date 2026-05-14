# CSV-Based Online Exam System - Quick Start Guide

## ✅ System Status
- **Storage**: File-based CSV system (no database needed)
- **Data Location**: `data/` folder with 4 CSV files
- **Application**: Fully functional GUI with LoginForm and RegistrationForm

## 🔐 Test Credentials

### Student Accounts
| Username | Password | Name |
|----------|----------|------|
| student1 | pass1 | Main Student |
| student2 | pass2 | Test User |
| demo | demo123 | Demo Student |

### Admin Accounts
| Username | Password | Name |
|----------|----------|------|
| admin | admin | Administrator |
| admin2 | admin123 | Test Admin |

## 📁 CSV File Structure

### students.csv
```
id,name,username,password,email,created_at
1,Main Student,student1,pass1,student1@exam.com,2026-05-14 10:00:00
```

### admins.csv
```
id,name,username,password
1,Administrator,admin,admin
```

### questions.csv
```
id,question,option1,option2,option3,option4,answer,exam_id
1,What is the capital of France?,Paris,Berlin,Madrid,Rome,1,1
```

### results.csv
```
id,student_id,score,total_questions,exam_date
1,1,8,10,2026-05-10 14:30:00
```

## 🚀 Running the Application

```bash
cd "c:\Users\msi\OneDrive\Desktop\Nowshin new\OnlineExamSystem"
java -cp target/classes com.onlineexam.main.Main
```

## 📋 Features Currently Working
✅ Login (Student/Admin)
✅ Registration
✅ File-based data storage
✅ Default admin account auto-creation
✅ Modern UI with colors and styling

## 🔄 Features Requiring UI Updates
- Dashboard
- Exam Window
- Results Display
- Analytics
- Profile Management
- Admin Panel

## 📝 How FileManager Works
- Automatically creates `data/` folder on startup
- Creates CSV files with headers if they don't exist
- Adds default admin account if admins.csv is empty
- Handles CSV parsing with proper quote escaping
- Provides methods for:
  - Student authentication: `FileManager.getStudent(username, password)`
  - Admin authentication: `FileManager.getAdmin(username, password)`
  - Student registration: `FileManager.addStudent(student)`
  - Question queries: `FileManager.getAllQuestions()`
  - Result tracking: `FileManager.addResult(result)`
  - Statistics: `FileManager.getStudentAverageScore(studentId)`

## 🛠 Adding New Features
Example: Update Dashboard to use FileManager

```java
// OLD (Database)
Connection con = DBConnection.getConnection();

// NEW (FileManager)
List<Student> students = FileManager.getAllStudents();
int totalExams = FileManager.getTotalExamsTaken();
double avgScore = FileManager.getAverageScore();
```

## 💾 Data Persistence
All data is automatically saved to CSV files in the `data/` folder. No additional setup needed!
