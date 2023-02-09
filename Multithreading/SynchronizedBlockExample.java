class Resources {
    static int counterValue = 0;

      void increment() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                counterValue++;
            }
        }
    }

    void decrement() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                counterValue--;
            }
        }
    }
}

class IncrementingCounter implements Runnable {
    Resources res= new Resources();

    @Override
    public void run() {
        res.increment();
    }
}

class DecrementingCounter implements Runnable {
    Resources res= new Resources();
    @Override
    public void run() {
        res.decrement();
    }
}

public class SynchronizedBlockExample {
    public static void main(String[] args) throws InterruptedException {
        IncrementingCounter inccounter = new IncrementingCounter();
        DecrementingCounter deccounter = new DecrementingCounter();
        Thread threadFirst = new Thread(inccounter, "ThreadFirst");
        Thread threadSecond = new Thread(deccounter, "ThreadSecond");

        threadFirst.start();
        threadSecond.start();

        threadFirst.join();
        threadSecond.join();
        System.out.println(Resources.counterValue);
    }
}
