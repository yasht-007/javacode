import java.util.concurrent.atomic.AtomicInteger;

class AtomicData {
    AtomicInteger counter = new AtomicInteger(0);

    void incrementCounter() {
        // counter.addAndGet(1);
        int x = counter.get();
        x++;
        counter.set(x);
        System.out.println(Thread.currentThread().getName() + " incremented me");
    }

    void decrementCounter() {
        // counter.getAndDecrement();
        int x = counter.get();
        x--;
        counter.set(x);
        System.out.println(Thread.currentThread().getName() + " decremented me");
    }
}

class AtomicIncrement extends Thread {
    AtomicData atomicData;

    public AtomicIncrement(AtomicData atomicData) {
        for (int index = 0; index < 10; index++) {
            this.atomicData = atomicData;
        }
    }


    @Override
    public void run() {
        for (int index = 0; index < 10; index++) {
            atomicData.incrementCounter();
        }
    }
}

class AtomicDecrement extends Thread {
    AtomicData atomicData;

    public AtomicDecrement(AtomicData atomicData) {
        this.atomicData = atomicData;
    }

    @Override
    public void run() {
        for (int index = 0; index < 10; index++) {
            atomicData.decrementCounter();
        }
    }
}

public class AtomicExamle {
    public static void main(String[] args) throws InterruptedException {
        final AtomicData data = new AtomicData();
        AtomicIncrement incrementingThread = new AtomicIncrement(data);
        AtomicDecrement decrementingThread = new AtomicDecrement(data);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println(data.counter);
    }
}
