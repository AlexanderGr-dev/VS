public class Parkhaus {

    private int parkingSlots;
    private final Object monitor = new Object();

    public Parkhaus(int parkingSlots) {
        this.parkingSlots = parkingSlots;
    }

    public void parkIn(Auto car){
        synchronized (monitor){
            while(parkingSlots == 0){
                try {
                    System.out.println("  Warten an Schranke: " + car.getLicenseNumber());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Einfahrt: " + car.getLicenseNumber());
            this.parkingSlots--;
            monitor.notifyAll();
        }
    }

    public synchronized void parkOut(Auto car){
        synchronized (monitor){
            while (parkingSlots>4){
                try {
                    System.out.println("  Warten auf einfahrendes Auto: " + car.getLicenseNumber());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Ausfahrt: " + car.getLicenseNumber());
            this.parkingSlots++;
            monitor.notifyAll();
        }
    }

}
