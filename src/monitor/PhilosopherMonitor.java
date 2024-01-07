package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherMonitor {
    private int NUM_PHILOSOPHERS;

    private enum State {THINKING, HUNGRY, EATING}
    private State[] philosopherState;
    private Lock lock = new ReentrantLock();
    private Condition[] condition;

    /**
     * mã khởi tạo
     */
    public PhilosopherMonitor(int numPhilosophers) {
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

    public State[] getPhilosopherState() {
        return philosopherState;   
    }
}