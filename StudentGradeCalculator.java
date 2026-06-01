import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ================================================
 * CODSOFT Internship – Task 2 : STUDENT GRADE CALCULATOR
 * ================================================
 * Features:
 *  - Accept any number of subjects
 *  - Input marks (0–100) per subject with validation
 *  - Calculate total marks and average percentage
 *  - Assign grade on standard 10-band scale
 *  - Display formatted report card
 *  - Multiple students in one session
 * ================================================
 */
public class StudentGradeCalculator {

    static final Scanner scanner = new Scanner(System.in);

    // ── Entry Point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        printBanner();

        boolean continueSession = true;
        while (continueSession) {
            processStudent();
            System.out.print("\n  🔄  Calculate grades for another student? (yes / no): ");
            String ans = scanner.next().trim().toLowerCase();
            continueSession = ans.equals("yes") || ans.equals("y");
        }

        System.out.println("\n  👋  Thank you for using the Grade Calculator. Goodbye!");
        scanner.close();
    }

    // ── Process One Student ──────────────────────────────────────────────────
    static void processStudent() {
        scanner.nextLine(); // consume trailing newline

        // Student name
        System.out.print("\n  👤  Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Student";

        // Number of subjects
        int numSubjects = readPositiveInt("  📚  Enter number of subjects: ");
        scanner.nextLine();

        List<String> subjects = new ArrayList<>();
        List<Integer> marks   = new ArrayList<>();

        System.out.println("\n  📝  Enter subject names and marks (0 – 100):\n");
        for (int i = 1; i <= numSubjects; i++) {
            System.out.printf("     Subject %d name : ", i);
            String subj = scanner.nextLine().trim();
            if (subj.isEmpty()) subj = "Subject " + i;
            subjects.add(subj);

            int mark = readMarkInRange("     Marks obtained : ");
            marks.add(mark);
            scanner.nextLine();
            System.out.println();
        }

        // ── Calculations ────────────────────────────────────────────────────
        int    total   = marks.stream().mapToInt(Integer::intValue).sum();
        double average = (double) total / numSubjects;
        String grade   = assignGrade(average);
        String remark  = getRemark(average);

        printReportCard(name, subjects, marks, numSubjects, total, average, grade, remark);
    }

    // ── Grade Logic ──────────────────────────────────────────────────────────
    static String assignGrade(double avg) {
        if      (avg >= 90) return "O  (Outstanding)";
        else if (avg >= 80) return "A+ (Excellent)";
        else if (avg >= 70) return "A  (Very Good)";
        else if (avg >= 60) return "B+ (Good)";
        else if (avg >= 50) return "B  (Above Average)";
        else if (avg >= 45) return "C  (Average)";
        else if (avg >= 40) return "D  (Pass)";
        else                return "F  (Fail)";
    }

    static String getGradeLetter(double avg) {
        if      (avg >= 90) return "O";
        else if (avg >= 80) return "A+";
        else if (avg >= 70) return "A";
        else if (avg >= 60) return "B+";
        else if (avg >= 50) return "B";
        else if (avg >= 45) return "C";
        else if (avg >= 40) return "D";
        else                return "F";
    }

    static String getRemark(double avg) {
        if      (avg >= 90) return "Outstanding  🌟";
        else if (avg >= 80) return "Excellent    🎉";
        else if (avg >= 70) return "Very Good    👍";
        else if (avg >= 60) return "Good         😊";
        else if (avg >= 50) return "Above Avg    📚";
        else if (avg >= 45) return "Average      📖";
        else if (avg >= 40) return "Pass         ✔️";
        else                return "Fail – needs improvement ❌";
    }

    // ── Report Card Display ──────────────────────────────────────────────────
    static void printReportCard(String name, List<String> subjects, List<Integer> marks,
                                 int n, int total, double avg, String grade, String remark) {

        int colW = Math.max(subjects.stream().mapToInt(String::length).max().orElse(10), 14);
        String divider = "═".repeat(colW + 34);

        System.out.println("\n╔" + divider + "╗");
        System.out.printf ("║%s📋  GRADE REPORT CARD%s║%n",
                " ".repeat((colW + 34 - 22) / 2), " ".repeat((colW + 34 - 22 + 1) / 2));
        System.out.println("╠" + divider + "╣");
        System.out.printf ("║  Student  : %-" + (colW + 30) + "s║%n", name);
        System.out.println("╠" + divider + "╣");
        System.out.printf ("║  %-" + colW + "s  │  Marks  │  Max  │  Grade  │  Status   ║%n", "Subject");
        System.out.println("╠" + "═".repeat(colW + 2) + "══╪═════════╪═══════╪═════════╪═══════════╣");

        for (int i = 0; i < n; i++) {
            int    m      = marks.get(i);
            String subG   = getGradeLetter(m);
            String status = m >= 40 ? "PASS ✅" : "FAIL ❌";
            System.out.printf("║  %-" + colW + "s  │   %3d   │  100  │   %-5s │  %-9s║%n",
                    subjects.get(i), m, subG, status);
        }

        System.out.println("╠" + divider + "╣");
        System.out.printf ("║  Total Marks       : %d / %d%-" +
                        (colW + 10) + "s║%n", total, n * 100, "");
        System.out.printf ("║  Average %%         : %-" + (colW + 20) + ".2f║%n", avg);
        System.out.printf ("║  Overall Grade     : %-" + (colW + 20) + "s║%n", grade);
        System.out.printf ("║  Remark            : %-" + (colW + 20) + "s║%n", remark);
        System.out.println("╠" + divider + "╣");
        System.out.printf ("║  %-" + (colW + 32) + "s║%n",
                "Grade Scale: O≥90 | A+≥80 | A≥70 | B+≥60 | B≥50 | C≥45 | D≥40 | F<40");
        System.out.println("╚" + divider + "╝");
    }

    // ── Input Helpers ────────────────────────────────────────────────────────
    static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.next().trim());
                if (v > 0) return v;
                System.out.println("  ⚠️   Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️   Invalid input – enter a whole number.");
            }
        }
    }

    static int readMarkInRange(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.next().trim());
                if (v >= 0 && v <= 100) return v;
                System.out.println("  ⚠️   Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️   Invalid input – enter a number (0–100).");
            }
        }
    }

    // ── Banner ───────────────────────────────────────────────────────────────
    static void printBanner() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  🎓  STUDENT GRADE CALCULATOR  🎓           ║");
        System.out.println("║      CODSOFT Internship – Task 2            ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("  Enter marks per subject to generate a report card.");
    }
}
