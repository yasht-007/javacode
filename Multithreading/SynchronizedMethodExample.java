public class SynchronizedMethodExample extends Thread {
      synchronized void printValues() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(i + " " + Thread.currentThread().getName());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void run() {
        printValues();
    }

    public static void main(String[] args) {
        SynchronizedMethodExample t1 = new SynchronizedMethodExample();
        SynchronizedMethodExample t2 = new SynchronizedMethodExample();
        SynchronizedMethodExample t3 = new SynchronizedMethodExample();

        t1.start();
        t2.start();
        t3.start();
    }
}
