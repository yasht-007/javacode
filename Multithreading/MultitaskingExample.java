
class First extends Thread {
    public void run() {
        System.out.println("Task one");
    }
}

class Second extends Thread {
    public void run() {
        System.out.println("Task two");
    }
}

public class MultitaskingExample {
    public static void main(String[] args) {
        First t0 = new First();
        Second t1 = new Second();

        t0.start();
        t1.start();
    }
}