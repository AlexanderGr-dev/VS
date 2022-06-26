package Leberkaese;

import java.util.Random;

public class Waiter implements Runnable{

    private KitchenCounter theke;
    private String name;

    public String getName() {
        return name;
    }

    public Waiter(KitchenCounter theke, String name) {
        this.theke = theke;
        this.name = name;
    }



    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(new Random().nextInt(10));
                theke.put(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
