package main.threads;

import java.util.concurrent.ThreadLocalRandom;

public class Student implements Comparable<Student> {
    private final long arrival;
    private final long len;
    private final int id;

    public Student(int id) {
        this.id = id;
        this.arrival = ThreadLocalRandom.current().nextInt(1000);
        this.len = ThreadLocalRandom.current().nextInt(500) + 500;
    }

    public long getArrival() { return arrival; }
    public int getId() { return id; }
    public long getLen() { return len; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", arrival=" + arrival + ", len=" + len + "}";
    }

    @Override
    public int compareTo(Student o) {
        return Long.compare(this.arrival, o.arrival);
    }
}
