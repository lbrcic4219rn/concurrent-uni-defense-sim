package main.threads;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Professor implements Runnable{

    private final int id;
    private final BlockingDeque<Student> queue;
    private final AtomicInteger totalScore;
    private final AtomicInteger processedCount;
    private final long startTime;
    private final int totalStudents;

    public Professor(int id, BlockingDeque<Student> queue, AtomicInteger totalScore,
                     AtomicInteger processedCount, long startTime, int totalStudents) {
        this.id = id;
        this.queue = queue;
        this.totalScore = totalScore;
        this.processedCount = processedCount;
        this.startTime = startTime;
        this.totalStudents = totalStudents;
    }

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (processedCount.get() >= totalStudents) {
                    break;
                }

                Student s = queue.poll(100, TimeUnit.MILLISECONDS);
                if (s == null) continue;

                long sleepTime = (s.getArrival() + startTime) - System.currentTimeMillis();
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }

                long defenseStart = System.currentTimeMillis() - startTime;
                int grade = random.nextInt(5) + 6;

                System.out.printf("Thread: %d | Arrival: %d | Professor: %d | TTC: %d : %d | Score: %d%n",
                        s.getId(), s.getArrival(), this.id, s.getLen(), defenseStart, grade);

                Thread.sleep(s.getLen());
                totalScore.addAndGet(grade);
                processedCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }
    }
}
