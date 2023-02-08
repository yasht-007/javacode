public class DaemonThreadExample extends Thread {

    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        DaemonThreadExample t0 = new DaemonThreadExample();
        DaemonThreadExample t1 = new DaemonThreadExample();

        System.out.println("Is thread 0 Daemon thread ? " + t0.isDaemon());
        System.out.println("Is thread 1 Daemon thread ? " + t1.isDaemon());

        System.out.println();
        System.out.println("Let's make Thread 0 as daemon thread");
        System.out.println();

        t0.setDaemon(true);

        System.out.println("Is thread 0 Daemon thread ? " + t0.isDaemon());

        t0.start();
        t0.join();
        t1.start();

        System.out.println(t0.getState());
        System.out.println(t1.getState());
    }
}
