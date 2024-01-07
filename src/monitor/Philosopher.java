package monitor;
import java.util.Random;

class Philosopher implements Runnable {
    private Random timeGenerator = new Random();

    private int id;

    private PhilosopherMonitor monitor;

    private int eat_count = 0;


    public Philosopher(int id, PhilosopherMonitor monitor) {
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

    public String print_eat_count() {
        System.out.println("Philosopher " + id + " eat " + eat_count + " times.");
        return "Philosopher" + id + " eat " + eat_count + " times.";
    }


    private String think(int id) throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep(timeGenerator.nextInt(1000));
        return "Philosopher " + id + " is thinking.";
    }


    public String eat(int id) throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(timeGenerator.nextInt(1000));
        return "Philospher " + id + " is eating";
    }
}