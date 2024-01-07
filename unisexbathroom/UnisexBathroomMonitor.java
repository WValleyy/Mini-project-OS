package unisexbathroom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class UnisexBathroom {
    private int capacity;
    private int maleCount;
    private int femaleCount;
    private Lock lock;
    private Condition maleCondition;
    private Condition femaleCondition;

    public UnisexBathroom(int capacity) {
        this.capacity = capacity;
        this.maleCount = 0;
        this.femaleCount = 0;
        this.lock = new ReentrantLock();
        this.maleCondition = lock.newCondition();
        this.femaleCondition = lock.newCondition();
    }

    public void maleUseBathroom() throws InterruptedException {
        lock.lock();
        try {
            while (femaleCount > 0 || (capacity - maleCount) <= 0) {
                maleCondition.await();
            }
            maleCount++;
            System.out.println("Male using bathroom. Total males inside: " + maleCount);
            Thread.sleep(2000); // Simulating bathroom usage
            maleCount--;
            System.out.println("Male left bathroom. Total males inside: " + maleCount);
            femaleCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void femaleUseBathroom() throws InterruptedException {
        lock.lock();
        try {
            while (maleCount > 0 || (capacity - femaleCount) <= 0) {
                femaleCondition.await();
            }
            femaleCount++;
            System.out.println("Female using bathroom. Total females inside: " + femaleCount);
            Thread.sleep(2000); // Simulating bathroom usage
            femaleCount--;
            System.out.println("Female left bathroom. Total females inside: " + femaleCount);
            maleCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Person extends Thread {
    private UnisexBathroom bathroom;
    private String gender;

    public Person(UnisexBathroom bathroom, String gender) {
        this.bathroom = bathroom;
        this.gender = gender;
    }

    public void run() {
        try {
            if (gender.equals("male")) {
                bathroom.maleUseBathroom();
            } else if (gender.equals("female")) {
                bathroom.femaleUseBathroom();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class UnisexBathroomMonitor {
    public static void main(String[] args) {
        UnisexBathroom bathroom = new UnisexBathroom(2); // Bathroom capacity: 2

        Person male1 = new Person(bathroom, "male");
        Person female1 = new Person(bathroom, "female");
        Person male2 = new Person(bathroom, "male");
        Person female2 = new Person(bathroom, "female");

        male1.start();
        female1.start();
        male2.start();
        female2.start();
    }
}
