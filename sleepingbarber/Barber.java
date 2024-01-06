package sleepingbarber;

public class Barber implements Runnable{
    private Shop shop;
    private int B_ID;

    public Barber(Shop shop, int B_ID) {
        this.shop = shop;
        this.B_ID = B_ID;
    }

    @Override
    public void run(){
        while (true) {
            shop.getHairCut(B_ID);
        }
    }

}
