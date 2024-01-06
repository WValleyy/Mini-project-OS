package sleepingbarber;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer implements Runnable{

    private int CustomerId;
    private Shop shop;
    private Date inTime;

    public Customer(Shop shop) {
        this.shop = shop;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public void setCustomerIdId(int C_Id) {
        this.CustomerId = C_Id;
    }

    public Date getInTime() {
        return inTime;
    }

    public int getCustomerId() {
        return CustomerId;
    }




    @Override
    public void run(){
        try {
            getHairCut();
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void getHairCut() throws InterruptedException {

        shop.EnterShop(this);
    }

}
