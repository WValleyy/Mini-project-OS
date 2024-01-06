package sleepingbarber;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SleepingBarber {
    private int noOfCustomers;
    private int noOfChairs;
    private int noOfBarbers;

    public SleepingBarber(int noOfCustomers, int noOfChairs, int noOfBarbers) {
        this.noOfCustomers = noOfCustomers;
        this.noOfChairs = noOfChairs;
        this.noOfBarbers = noOfBarbers;
    }



    public void Start(Session form) throws InterruptedException{
        ExecutorService exec = Executors.newFixedThreadPool(12);
        Shop shop = new Shop(noOfChairs, noOfBarbers, noOfCustomers, form);
        Random r = new Random();

        System.out.println("Shop is opened with "+ noOfBarbers+" Barbers");

        long startTime  = System.currentTimeMillis();

        for (int i = 1; i <= noOfBarbers; i++) {
            Barber barber = new Barber(shop, i);
            Thread thB = new Thread(barber);
            exec.execute(thB);
        }

        for (int i = 1; i <= noOfCustomers; i++) {
            try {
                Customer customer = new Customer(shop);
                customer.setInTime(new Date());
                customer.setCustomerIdId(i);
                Thread thcustomer = new Thread(customer);
                exec.execute(thcustomer);

                double val = r.nextGaussian() * 2000 + 2000;
                int Delay = Math.abs((int) Math.round(val));
                Thread.sleep(Delay);


            } catch (InterruptedException ex) {
                Logger.getLogger(SleepingBarber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Customer> backLater =shop.Backlater();
        if (backLater.size() > 0 ) {
            for (int i = 0; i < backLater.size(); i++) {
                try {
                    Customer customer = backLater.get(i);
                    customer.setInTime(new Date());
                    Thread thcustomer = new Thread(customer);
                    exec.execute(thcustomer);

                    double val = r.nextGaussian() * 2000 + 2000;
                    int Delay = Math.abs((int) Math.round(val));
                    Thread.sleep(Delay);


                } catch (InterruptedException ex) {
                    Logger.getLogger(SleepingBarber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        exec.awaitTermination(12, SECONDS);
        exec.shutdown();

        long elapsedTime = (System.currentTimeMillis() - startTime)/1000;

        System.out.println("shop is closed");
        System.out.println("\nTotal time elapsed in seconds"
                + " for serving "+noOfCustomers+" customers' hair by "
                +noOfBarbers+" barbers  with "+noOfChairs+
                " chairs in the waiting room is: "
                +elapsedTime);
        System.out.println("\nTotal customers : "+noOfCustomers+
                "\nTotal customers  served: "+shop.getTotalhaircuts()
                +"\nTotal customers returned: "+shop.getBackLaterCounter());
    }

}
