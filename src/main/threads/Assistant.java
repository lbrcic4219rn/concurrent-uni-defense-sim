package main.threads;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Assistant implements Runnable {

    private final int id;
    private final BlockingDeque<Student> queue;
    private final AtomicInteger totalScore;
    private final AtomicInteger processedCount;
    private final long startTime;

    public Assistant(int id, BlockingDeque<Student> queue, AtomicInteger totalScore, AtomicInteger processedCount, long startTime) {
        this.id = id;
        this.queue = queue;
        this.totalScore = totalScore;
        this.processedCount = processedCount;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Student student = queue.take();

                long sleepTime = (student.getArrival() + startTime) - System.currentTimeMillis();
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }

                long defenseStart = System.currentTimeMillis() - startTime;
                int grade = random.nextInt(5) + 6;

                System.out.printf("Thread: %d | Arrival: %d | Assistant: %d | TTC: %d : %d | Score: %d%n",
                        student.getId(), student.getArrival(), id, student.getLen(), defenseStart, grade);

                Thread.sleep(student.getLen());
                totalScore.addAndGet(grade);
                processedCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
