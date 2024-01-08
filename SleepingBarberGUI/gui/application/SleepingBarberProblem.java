package SleepingBarberGUI.gui.application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class BarberShop {
    private int totalChairs;
    private int availableChairs;
    private LinkedList<Customer> waitingCustomers;

    public BarberShop(int totalChairs) {
        this.totalChairs = totalChairs;
        this.availableChairs = totalChairs;
        this.waitingCustomers = new LinkedList<>();
    }

    public synchronized void addCustomer(Customer customer) {
        if (availableChairs > 0) {
            System.out.println("Customer " + customer.getId() + " enters the waiting room.");
            waitingCustomers.add(customer);
            availableChairs--;
            notify(); // Notify the barber that a customer is waiting
        } else {
            System.out.println("Customer " + customer.getId() + " leaves since all chairs are occupied.");
        }
    }

    public synchronized Customer getNextCustomer() {
        while (waitingCustomers.isEmpty()) {
            try {
                System.out.println("Barber is sleeping.");
                wait(); // Wait for a customer to arrive
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Customer nextCustomer = waitingCustomers.poll();
        availableChairs++;
        System.out.println("Barber calls Customer " + nextCustomer.getId() + " for a haircut.");
        return nextCustomer;
    }
    public int getAvailableChairs() {
        return availableChairs;
    }

}

class Customer implements Runnable {
    private static int servedCustomers = 0;
    private static int idCounter = 1;
    private int id;

    public Customer() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        // Customer behavior
    }
    public void setServedCustomers() {
        this.servedCustomers ++;
    }
    public int getservedCustomers() {
        return servedCustomers;
    }
}

class Barber implements Runnable {
    private BarberShop barberShop;
    private int numCustomers;
    public Barber(BarberShop barberShop, int numCustomers) {
        this.barberShop = barberShop;
        this.numCustomers = numCustomers;
    }

    @Override
    public void run() {
        while (true) {
            Customer customer = barberShop.getNextCustomer();
            // Barber cuts hair
            System.out.println("Barber is cutting hair for Customer " + customer.getId());
            try {
                // Simulate the time it takes to cut hair
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            customer.setServedCustomers();
            System.out.println("Barber finished cutting hair for Customer " + customer.getId());
            if (customer.getservedCustomers() == numCustomers ) {
                // No more customers, exit the loop
                System.out.println("Barber is done for the day. All customers have been served.");
                break;
            }
        }
    }
}


public class SleepingBarberProblem {
    private int totalChairs;
    private int totalCustomers;
    public SleepingBarberProblem(int totalChairs, int totalCustomers) {
        this.totalChairs = totalChairs;
        this.totalCustomers = totalCustomers;
    }
    public void start() {
        BarberShop barberShop = new BarberShop(totalChairs);
        Barber barber = new Barber(barberShop, totalCustomers);
        Thread barberThread = new Thread(barber);
        barberThread.start();
        List<Integer> numberList = new ArrayList<>();
        numberList.add(1000);
        numberList.add(750);
        numberList.add(500);
        // Create a few customers
        for (int i = 1; i <= totalCustomers; i++) {
            Customer customer = new Customer();
            Thread customerThread = new Thread(customer);
            customerThread.start();
            barberShop.addCustomer(customer);
            try {
                // Simulate time between customer arrivals
                // Get a random index within the size of the list
                int randomIndex = new Random().nextInt(numberList.size());

                // Retrieve the number at the randomly chosen index
                int randomNumber = numberList.get(randomIndex);
                Thread.sleep(randomNumber);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Wait for the Barber thread to finish
        try {
            barberThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SleepingBarberProblem sbp = new SleepingBarberProblem(3, 10);
        sbp.start();
    }
}