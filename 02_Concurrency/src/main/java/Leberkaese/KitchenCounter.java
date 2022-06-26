package Leberkaese;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenCounter {

    private int buns;
    private int kapazität;
    Lock monitor = new ReentrantLock();
    Condition student = monitor.newCondition();
    Condition waiter = monitor.newCondition();

    public KitchenCounter(int kapazität) {
        this.kapazität = kapazität;
    }

    public void put(Waiter wtrObj){
        monitor.lock();
        try {
            while (this.buns == kapazität) {
                try {
                    System.out.println("    " + wtrObj.getName() + " muss warten,da genügend Semmeln vorhanden.");
                    waiter.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Anzahl Buns: " + buns + " wird von " + wtrObj.getName() + " um 1 erhöht.");
            buns++;
            student.signal();
        } finally {
            monitor.unlock();
        }
    }

    public void take(Student stdObj){
        monitor.lock();
        try {
            while (this.buns <= 0) {
                try {
                    System.out.println("    " + stdObj.getName() + " muss warten bis neue Semmeln auf der theke.");
                    student.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buns--;
            System.out.println(stdObj.getName() + " isst eine Semmel");
            waiter.signal();
        } finally {
            monitor.unlock();
        }
    }
}
