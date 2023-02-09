
class Bank extends Thread {

    Bank(int withdraw) {
        this.withdraw = withdraw;
    }

    static int bal = 5000;
    static int withdraw;

    public static synchronized void Withdraw() {
        if (withdraw <= bal) {
            System.out.println("Money withdrawn");
            bal = -withdraw;
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public void run() {
        Withdraw();
    }
}

public class StaticSynchronizationExample {
    public static void main(String[] args) {
        Bank obj = new Bank(5000);
        Bank obj2 = new Bank(5000);
        Thread t1 = new Thread(obj);
        Thread t2 = new Thread(obj);
        Thread t3 = new Thread(obj2);
        Thread t4 = new Thread(obj2);

        t1.start();
        t2.start();

        t3.start();
        t4.start();
    }
}
