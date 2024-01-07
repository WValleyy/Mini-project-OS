import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher implements Runnable {
    private Random timeGenerator = new Random();

    private int id;

    private DiningPhilosophersMonitor monitor;

    private int eat_count = 0;


    public Philosopher(int id, DiningPhilosophersMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    public void run() {
        try {
            while (true) {
                think(id);
                monitor.pickUpChopsticks(id);
                eat_count += 1;
                eat(id);
                monitor.putDownChopsticks(id);
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id + " was interrupted.\n");

        }
    }

    public void print_eat_count() {
        System.out.println("Philosopher " + id + " eat " + eat_count + " times.");
    }


    private void think(int id) throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep(timeGenerator.nextInt(1000));
    }


    public void eat(int id) throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(timeGenerator.nextInt(1000));
    }
}

class DiningPhilosophersMonitor {
    private int NUM_PHILOSOPHERS;

    private enum State {THINKING, HUNGRY, EATING}
    private State[] philosopherState;
    private Lock lock = new ReentrantLock();
    private Condition[] condition;



    /**
     * mã khởi tạo
     */
    public DiningPhilosophersMonitor(int numPhilosophers) {
        this.NUM_PHILOSOPHERS = numPhilosophers;
        philosopherState = new State[numPhilosophers];
        condition = new Condition[NUM_PHILOSOPHERS];
        for (int i = 0; i < numPhilosophers; i++) {
            philosopherState[i] = State.THINKING;
        }
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            condition[i] = lock.newCondition();
        }
    }

    public void pickUpChopsticks(int philosopherId) throws InterruptedException {

        lock.lock();
        try {
            philosopherState[philosopherId] = State.HUNGRY;
            System.out.println("Philosopher " + philosopherId + " is hungry.");
            test(philosopherId);
            if (philosopherState[philosopherId] != State.EATING) {
                condition[philosopherId].await();
            }


        } finally {
            lock.unlock();
        }

    }

    void test(int i) {
        if ((philosopherState[(i + 1) % NUM_PHILOSOPHERS] != State.EATING) &&
                (philosopherState[i] == State.HUNGRY) &&
                (philosopherState[(i + NUM_PHILOSOPHERS - 1) % NUM_PHILOSOPHERS] != State.EATING)) {
            philosopherState[i] = State.EATING;
            condition[i].signal();
        } else if (philosopherState[i] == State.HUNGRY) {
            System.out.println("Philosopher " + i + " is waiting to eat.");
        }
    }

    public void putDownChopsticks(int philosopherId) {
        lock.lock();
        try {
            System.out.println("Philosopher " + philosopherId + " is done eating and put down chopsticks.");
            philosopherState[philosopherId] = State.THINKING;

            int leftPhilosopher = (philosopherId + NUM_PHILOSOPHERS - 1) % NUM_PHILOSOPHERS;
            int rightPhilosopher = (philosopherId + 1) % NUM_PHILOSOPHERS;

            test(leftPhilosopher);
            test(rightPhilosopher);

        } finally {
            lock.unlock();
        }
    }
}


public class DiningPhilosophersUsingMonitor {
    private static final int NUM_PHILOSOPHERS = 5;

    public static void main(String[] args) throws InterruptedException {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        Thread[] philosopherThreads = new Thread[NUM_PHILOSOPHERS];

        DiningPhilosophersMonitor monitor = new DiningPhilosophersMonitor(NUM_PHILOSOPHERS);
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, monitor);
            philosopherThreads[i] = new Thread(philosophers[i]);
            philosopherThreads[i].start();
        }

// chạy 10s sau đó dừng lại
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread thread : philosopherThreads) {
            thread.interrupt();
        }
        // in ra số lần ăn
        for (Philosopher philosopher : philosophers) {
            philosopher.print_eat_count();
        }

    }
}