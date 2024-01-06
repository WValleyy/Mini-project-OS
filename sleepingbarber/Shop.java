package sleepingbarber;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Shop {
    private final ReentrantLock mutex = new ReentrantLock();
    private int waitingChairs, noOfBarbers, availableBarbers;
    private int TotalHairGotCuts, BackLaterCounter;
    private List<Customer> CustomerList;
    private List<Customer> CustomerBackLater;
    private Semaphore Availabe;
    private Random r = new Random();
    private Session form;

    public Shop(int nChairs, int nB, int nCustomers, Session form) {
        this.waitingChairs = nChairs;
        this.noOfBarbers = nB;
        this.availableBarbers = nB;
        this.form = form;
        Availabe = new Semaphore(availableBarbers);
        this.CustomerList = new LinkedList<Customer>();
        this.CustomerBackLater = new ArrayList<Customer>(nCustomers);
    }



    public int getTotalhaircuts() {
        return TotalHairGotCuts;
    }

    public int getBackLaterCounter() {
        return BackLaterCounter;
    }

    public void getHairCut(int B_ID){
        Customer customer;


        synchronized(CustomerList){
            while (CustomerList.size() == 0) {
                form.SleepBarber(B_ID);
                System.out.println("\nBarber "+B_ID+" is waiting "
                        + "for the customer and sleeps in his chair");
                try {
                    CustomerList.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            customer = (Customer) ((LinkedList<?>)CustomerList).poll();
            System.out.println("Customer "+customer.getCustomerId()+
                    " finds Barber available and get hair cut "
                    + "the Barber "+B_ID);
        }
        int Delay;
        try {
            if (Availabe.tryAcquire() && CustomerList.size() == waitingChairs){
                Availabe.acquire();
            }
            form.BusyBarber(B_ID);
            System.out.println("Barber "+B_ID+" does hair cut of "+
                    customer.getCustomerId());

            double val = r.nextGaussian() * 2000 + 4000;
            Delay = Math.abs((int) Math.round(val));
            Thread.sleep(Delay);

            System.out.println("\nCompleted hair cuts of "+
                    customer.getCustomerId()+" by Barber " +
                    B_ID +" in "+(int)(Delay/1000)+ " seconds.");
            mutex.lock();
            try {
                TotalHairGotCuts++;
            } finally {
                mutex.unlock();
            }

            if (CustomerList.size() > 0) {
                System.out.println("Barber "+B_ID+
                        " Calls a Customer to enter the  shop ");
                form.ReturnChair(B_ID);
            }
            Availabe.release();

        } catch (InterruptedException e) {
        }



    }





    public void EnterShop(Customer customer ){
        System.out.println("\nCustomer "+customer.getCustomerId()+
                " tries to enter shop to get hair cut "
                +customer.getInTime());

        synchronized(CustomerList){
            if (CustomerList.size() == waitingChairs) {

                System.out.println("\nNo chair available "
                        + "for customers "+customer.getCustomerId()+
                        " so customer leaves and will come back later");

                CustomerBackLater.add(customer);
                mutex.lock();
                try {
                    BackLaterCounter++;
                } finally {
                    mutex.unlock();
                }
                return;
            }
            else if (Availabe.availablePermits() > 0 ) {
                ((LinkedList<Customer>)CustomerList).offer(customer);
                CustomerList.notify();
            }
            else{
                try {
                    ((LinkedList<Customer>)CustomerList).offer(customer);
                    form.TakeChair();
                    System.out.println("All Barbers are busy so Customer "+
                            customer.getCustomerId()+
                            " takes a chair in the waiting room");

                    if (CustomerList.size() == 1) {
                        CustomerList.notify();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        }
    }

    public List<Customer> Backlater(){
        return CustomerBackLater;
    }

}
