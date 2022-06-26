import java.util.Random;

public class Auto implements Runnable{

    private String licenseNumber;
    private Parkhaus garage;

    public Auto(String licenseNumber, Parkhaus garage) {
        this.licenseNumber = licenseNumber;
        this.garage = garage;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public void run() {
        while(true) {
            try {
                drive();
                parking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drive() throws InterruptedException {
        Thread.sleep(new Random().nextInt(10000));
    }

    private void parking() throws InterruptedException {
        garage.parkIn(this);
        Thread.sleep(new Random().nextInt(10000));
        garage.parkOut(this);
    }
}
