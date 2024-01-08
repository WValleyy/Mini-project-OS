package monitor;

public class Main {
    private static final int NUM_PHILOSOPHERS = 5;

    private static OutputListener outputListener;
    private static PhilosopherMonitor monitor;

    public static void main(String[] args) throws InterruptedException {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        Thread[] philosopherThreads = new Thread[NUM_PHILOSOPHERS];

        monitor = new PhilosopherMonitor(NUM_PHILOSOPHERS);

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

        for (Philosopher philosopher : philosophers) {
            philosopher.print_eat_count();
        }
    }

    public static void setOutputListener(OutputListener listener) {
        outputListener = listener;
    }

    public static void logEvent(String event) {
        System.out.println("Event logged: " + event);
        if (outputListener != null) {
            outputListener.onEventLogged(event);
        } else {
            System.out.println(event);
        }
    }

    public static PhilosopherMonitor getPhilosopherMonitor() {
        return monitor;
    }
}