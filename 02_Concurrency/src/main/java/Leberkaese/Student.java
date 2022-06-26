package Leberkaese;

import java.util.Random;

public class Student implements Runnable{

    private KitchenCounter theke;
    private String name;

    public String getName() {
        return name;
    }

    public Student(KitchenCounter theke, String name) {
        this.theke = theke;
        this.name = name;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(new Random().nextInt(10000));
                theke.take(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
