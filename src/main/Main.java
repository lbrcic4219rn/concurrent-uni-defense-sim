package main;

import main.threads.Assistant;
import main.threads.Professor;
import main.threads.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please insert student count: ");
        int n = sc.nextInt();
        sc.close();

        BlockingDeque<Student> queue = new LinkedBlockingDeque<>();
        AtomicInteger totalScore = new AtomicInteger(0);
        AtomicInteger processedCount = new AtomicInteger(0);

        List<Student> studentsList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            studentsList.add(new Student(i));
        }

        Collections.sort(studentsList);
        queue.addAll(studentsList);

        long startTime = System.currentTimeMillis();

        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.submit(new Professor(1, queue, totalScore, processedCount, startTime, studentsList.size()));
        pool.submit(new Professor(2, queue, totalScore, processedCount, startTime, studentsList.size()));
        pool.submit(new Assistant(1, queue, totalScore, processedCount, startTime));

        while (processedCount.get() < studentsList.size()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Properly shut down the pool
        pool.shutdownNow();

        int totalProcessed = processedCount.get();
        if (totalProcessed > 0) {
            double average = (double) totalScore.get() / totalProcessed;
            System.out.printf("%nAverage grade: %.2f (defense count: %d)%n", average, totalProcessed);
        } else {
            System.out.println("\nNo student managed to defend.");
        }
    }
}
