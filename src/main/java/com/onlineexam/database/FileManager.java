package com.onlineexam.database;

import com.onlineexam.model.Admin;
import com.onlineexam.model.Question;
import com.onlineexam.model.Result;
import com.onlineexam.model.Student;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * FileManager - CSV-based file storage system Handles all file I/O operations
 * for students, admins, questions, and results
 */
public class FileManager {

    private static final String DATA_FOLDER = "data";
    private static final String STUDENTS_FILE = "data/students.csv";
    private static final String ADMINS_FILE = "data/admins.csv";
    private static final String QUESTIONS_FILE = "data/questions.csv";
    private static final String RESULTS_FILE = "data/results.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            // Create data folder if it doesn't exist
            Files.createDirectories(Paths.get(DATA_FOLDER));

            // Initialize CSV files if they don't exist
            initializeFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize CSV files with headers if they don't exist
     */
    private static void initializeFiles() throws IOException {
        if (!Files.exists(Paths.get(STUDENTS_FILE))) {
            String header = "id,name,username,password,email,created_at\n";
            Files.write(Paths.get(STUDENTS_FILE), header.getBytes());
        }

        if (!Files.exists(Paths.get(ADMINS_FILE))) {
            String header = "id,name,username,password\n";
            Files.write(Paths.get(ADMINS_FILE), header.getBytes());
            // Add default admin
            addAdmin(new Admin(1, "Administrator", "admin", "admin"));
        }

        if (!Files.exists(Paths.get(QUESTIONS_FILE))) {
            String header = "id,question,option1,option2,option3,option4,answer,exam_id\n";
            Files.write(Paths.get(QUESTIONS_FILE), header.getBytes());
        }

        if (!Files.exists(Paths.get(RESULTS_FILE))) {
            String header = "id,student_id,score,total_questions,exam_date\n";
            Files.write(Paths.get(RESULTS_FILE), header.getBytes());
        }
    }

    // ==================== STUDENT OPERATIONS ====================
    public static Student getStudent(String username, String password) {
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 4 && parts[2].equals(username) && parts[3].equals(password)) {
                    return new Student(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean studentExists(String username) {
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > 2 && parts[2].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addStudent(Student student) {
        try (FileWriter fw = new FileWriter(STUDENTS_FILE, true)) {
            int nextId = getNextStudentId();
            String line = String.format("%d,%s,%s,%s,%s,%s\n",
                    nextId, student.getName(), student.getUsername(),
                    student.getPassword(), student.getEmail(),
                    LocalDateTime.now().format(DATE_FORMAT));
            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(Student student) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(student.getId() + ",")) {
                    lines.add(String.format("%d,%s,%s,%s,%s,%s",
                            student.getId(), student.getName(), student.getUsername(),
                            student.getPassword(), student.getEmail(), LocalDateTime.now().format(DATE_FORMAT)));
                } else {
                    lines.add(line);
                }
            }
            Files.write(Paths.get(STUDENTS_FILE), String.join("\n", lines).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 5) {
                    students.add(new Student(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Student getStudentById(int studentId) {
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 5 && Integer.parseInt(parts[0]) == studentId) {
                    Student student = new Student(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]);
                    return student;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getNextStudentId() {
        int maxId = 0;
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > 0) {
                    maxId = Math.max(maxId, Integer.parseInt(parts[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    // ==================== ADMIN OPERATIONS ====================
    public static Admin getAdmin(String username, String password) {
        try (Scanner scanner = new Scanner(new File(ADMINS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3 && parts[2].equals(username) && parts[3].equals(password)) {
                    return new Admin(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addAdmin(Admin admin) {
        try (FileWriter fw = new FileWriter(ADMINS_FILE, true)) {
            String line = String.format("%d,%s,%s,%s\n",
                    admin.getId(), admin.getName(), admin.getUsername(), admin.getPassword());
            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(ADMINS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 4) {
                    admins.add(new Admin(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // ==================== QUESTION OPERATIONS ====================
    public static void addQuestion(Question question) {
        try (FileWriter fw = new FileWriter(QUESTIONS_FILE, true)) {
            int nextId = getNextQuestionId();
            String line = String.format("%d,%s,%s,%s,%s,%s,%s,1\n",
                    nextId, escapeCSV(question.getQuestion()),
                    escapeCSV(question.getOption1()), escapeCSV(question.getOption2()),
                    escapeCSV(question.getOption3()), escapeCSV(question.getOption4()),
                    question.getCorrectAnswer());
            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(QUESTIONS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = parseCSV(line);
                if (parts.length >= 8) {
                    questions.add(new Question(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5],
                            Integer.parseInt(parts[6])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static List<Question> getRandomQuestions(int count) {
        List<Question> questions = new ArrayList<>(getAllQuestions());
        Collections.shuffle(questions);
        if (count <= 0 || questions.isEmpty()) {
            return new ArrayList<>();
        }
        if (count >= questions.size()) {
            return questions;
        }
        return new ArrayList<>(questions.subList(0, count));
    }

    public static void deleteQuestion(int questionId) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(QUESTIONS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith(questionId + ",")) {
                    lines.add(line);
                }
            }
            Files.write(Paths.get(QUESTIONS_FILE), String.join("\n", lines).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNextQuestionId() {
        int maxId = 0;
        try (Scanner scanner = new Scanner(new File(QUESTIONS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > 0) {
                    maxId = Math.max(maxId, Integer.parseInt(parts[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    // ==================== RESULT OPERATIONS ====================
    public static void addResult(Result result) {
        try (FileWriter fw = new FileWriter(RESULTS_FILE, true)) {
            int nextId = getNextResultId();
            String line = String.format("%d,%d,%d,%d,%s\n",
                    nextId, result.getStudentId(), result.getScore(),
                    result.getTotalQuestions(), LocalDateTime.now().format(DATE_FORMAT));
            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Result> getStudentResults(int studentId) {
        List<Result> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESULTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 4 && Integer.parseInt(parts[1]) == studentId) {
                    results.add(new Result(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            parts[4]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Sort by date descending
        results.sort((r1, r2) -> r2.getExamDate().compareTo(r1.getExamDate()));
        return results;
    }

    public static List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(RESULTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 5) {
                    results.add(new Result(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            parts[4]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Sort by date descending
        results.sort((r1, r2) -> r2.getExamDate().compareTo(r1.getExamDate()));
        return results;
    }

    private static int getNextResultId() {
        int maxId = 0;
        try (Scanner scanner = new Scanner(new File(RESULTS_FILE))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > 0) {
                    maxId = Math.max(maxId, Integer.parseInt(parts[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    // ==================== STATISTICS ====================
    public static int getTotalStudents() {
        return getAllStudents().size();
    }

    public static int getTotalQuestions() {
        return getAllQuestions().size();
    }

    public static int getTotalExamsTaken() {
        return getAllResults().size();
    }

    public static double getAverageScore() {
        List<Result> results = getAllResults();
        if (results.isEmpty()) {
            return 0.0;
        }
        double total = results.stream().mapToDouble(Result::getScore).sum();
        return total / results.size();
    }

    public static int getStudentExamCount(int studentId) {
        return (int) getStudentResults(studentId).stream()
                .filter(r -> r.getStudentId() == studentId)
                .count();
    }

    public static double getStudentAverageScore(int studentId) {
        List<Result> results = getStudentResults(studentId);
        if (results.isEmpty()) {
            return 0.0;
        }
        double total = results.stream().mapToDouble(Result::getScore).sum();
        return total / results.size();
    }

    public static int getStudentHighestScore(int studentId) {
        List<Result> results = getStudentResults(studentId);
        if (results.isEmpty()) {
            return 0;
        }
        return results.stream().mapToInt(Result::getScore).max().orElse(0);
    }

    public static int getStudentLowestScore(int studentId) {
        List<Result> results = getStudentResults(studentId);
        if (results.isEmpty()) {
            return 0;
        }
        return results.stream().mapToInt(Result::getScore).min().orElse(0);
    }

    // ==================== UTILITY METHODS ====================
    /**
     * Escape CSV special characters
     */
    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Parse CSV line handling quoted values
     */
    private static String[] parseCSV(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        values.add(current.toString());
        return values.toArray(new String[0]);
    }
}
