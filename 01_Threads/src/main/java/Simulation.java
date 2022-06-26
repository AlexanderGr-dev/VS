public class Simulation {

    public static void main(String[]	args)	throws InterruptedException {
        //	neues	Parkhaus	mit	Kapazitaet	fuer 10	Autos
        Parkhaus parkhaus = new Parkhaus(10);

        for (int i = 1; i <= 20; i++) {
            Thread car = new Thread(new Auto("R-FH	" + i, parkhaus));
            car.setDaemon(true);
            car.start();
        }

        Thread.sleep(30000);
        System.out.println("Ende	der	Simulation!");
    }
}
