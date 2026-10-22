package utils;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

public class ReportListener implements ISuiteListener {

    private static final Path RESULTS = Paths.get("target", "allure-results");
    private static final Path REPORT  = Paths.get("reports");

    @Override
    public void onStart(ISuite suite) {
        deleteFolder(RESULTS);
        deleteFolder(REPORT);
    }

    @Override
    public void onFinish(ISuite suite) {
        generateReport();          // 1) ولّدي الأول
        deleteFolder(RESULTS);     // 2) وبعدين امسحي النتايج الخام
    }

    private void generateReport() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "cmd", "/c", "allure", "generate",
                    RESULTS.toString(), "-o", REPORT.toString(),
                    "--clean", "--single-file");
            pb.inheritIO();
            System.out.println("Allure exit code: " + pb.start().waitFor());
        } catch (Exception e) {
            System.out.println("Could not generate report: " + e.getMessage());
        }
    }

    private void deleteFolder(Path folder) {
        if (!Files.exists(folder)) return;
        try (Stream<Path> paths = Files.walk(folder)) {
            paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            System.out.println("Could not delete " + folder + ": " + e.getMessage());
        }
    }
}