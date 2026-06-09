# concurrent-uni-defense-sim

A multi-threaded Java simulation modeling student thesis defenses using concurrent queues, thread pools, and asynchronous grading roles.

## Features

* **Thread-Safe Queueing:** Uses `BlockingDeque` to manage student arrival and scheduling order smoothly across multiple consumers.
* **Role-Based Execution:** Simulates distinct grading logic and score ranges for `Professor` and `Assistant` threads.
* **Resource Optimization:** Leverages a managed `ExecutorService` thread pool and high-performance `ThreadLocalRandom` generators.
* **Streamlined Architecture:** Flat hierarchy utilizing a focused `threads` package alongside a root execution entry point.

## Project Structure

```text
src/
├── Main.java        # Application entry point
└── threads/         # Simulation workers and models (Professor, Assistant, Student)

```

## Configuration

When launched, the console will prompt you to enter the total student count. The system will automatically instantiate the students with randomized arrival timelines and evaluation lengths, process them through the pool, and output the final average score breakdown.