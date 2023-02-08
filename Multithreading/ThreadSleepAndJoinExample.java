
class Thdemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(200);
                System.out.println("The current thread name is: " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
        }
    }
}

public class ThreadSleepAndJoinExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable r1 = new Thdemo();

        Thread t0 = new Thread(r1);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);

        t0.start();
        t0.join();
        t1.start();
        t1.join();
        t2.start();
         t2.join();
        // from here main thread will run
        r1.run();

    }
}
