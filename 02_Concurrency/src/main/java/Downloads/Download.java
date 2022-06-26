package Downloads;

import javax.swing.JProgressBar;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

// aktive Klasse
public class Download extends Thread
{

    private final JProgressBar balken;
    private CountDownLatch start, stop;
    // weitere Attribute zur Synchronisation hier definieren

    public Download(JProgressBar balken, CountDownLatch start, CountDownLatch stop /* ggf. weitere Objekte */) {
        this.balken = balken;
        this.start = start;
        this.stop = stop;
        // ...
    }


    /*  hier die Methode definieren, die jeweils den Balken weiterschiebt
     *  Mit balken.getMaximum() bekommt man den Wert fuer 100 % gefuellt
     *  Mit balken.setValue(...) kann man den Balken einstellen (wieviel gefuellt) dargestellt wird
     *  Setzen Sie den value jeweils und legen Sie die Methode dann für eine zufaellige Zeit schlafen
     */
    public void load (JProgressBar balken){
        int timePerKb = new Random().nextInt(500);
        System.out.println(Thread.currentThread().getName() + " run");
        for (int i=0; i <= balken.getMaximum(); i++) {
            try {
                Thread.sleep(timePerKb);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balken.setValue(i);
        }
    }

    @Override
    public void run(){
        try {
            start.await();
            System.out.println(Thread.currentThread().getName() + " start nach await");
            load(this.balken);
            stop.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
