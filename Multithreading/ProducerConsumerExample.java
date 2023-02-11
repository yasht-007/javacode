import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerExample {
    public static class ProducerConsumer {
        Queue<Integer> item = new LinkedList<>();
        int capacity = 4;

        private void Produce() throws InterruptedException {
            int value = 10;
            while (true) {
                synchronized (this) {
                    while (item.size() == capacity) {
                        wait();
                    }

                    value += 10;
                    System.out.println("Producer produced:  " + value);
                    item.add(value);

                    notify();

                    Thread.sleep(1000);
                }
            }
        }

        private void Consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (item.size() == 0)
                        wait();

                    int consumedValue = item.remove();

                    System.out.println("Consumer consumed:  " + consumedValue);
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ProducerConsumer producerConsumer = new ProducerConsumer();
        Thread producerThread = new Thread() {
            @Override
            public void run() {
                try {
                    producerConsumer.Produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread consumerThread = new Thread() {
            @Override
            public void run() {
                try {
                    producerConsumer.Consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
